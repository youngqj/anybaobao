package com.interesting.modules.overseas.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.business.system.entity.SysUser;
import com.interesting.business.system.service.ISysCodeRoleService;
import com.interesting.business.system.service.ISysUserService;
import com.interesting.common.constant.CommonConstant;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.overseas.dto.EditEnterOrderStatusDTO;
import com.interesting.modules.overseas.dto.InstOverSeasInventoryCheckDTO;
import com.interesting.modules.overseas.dto.QueryPageOverSeasInventoryCheckDTO;
import com.interesting.modules.overseas.entity.XqInventoryOverseas;
import com.interesting.modules.overseas.entity.XqOverseasInventoryCheck;
import com.interesting.modules.overseas.entity.XqOverseasInventoryCheckDetail;
import com.interesting.modules.overseas.mapper.XqOverseasInventoryCheckDetailMapper;
import com.interesting.modules.overseas.mapper.XqOverseasInventoryCheckMapper;
import com.interesting.modules.overseas.service.XqInventoryOverseasService;
import com.interesting.modules.overseas.service.XqOverseasEnterDetailService;
import com.interesting.modules.overseas.service.XqOverseasInventoryCheckDetailService;
import com.interesting.modules.overseas.service.XqOverseasInventoryCheckService;
import com.interesting.modules.overseas.vo.OverSeasInventoryCheckDetailRecordVO;
import com.interesting.modules.overseas.vo.OverSeasInventoryCheckDetailVO;
import com.interesting.modules.overseas.vo.OverSeasInventoryCheckPageVO;
import com.interesting.modules.warehouse.entity.XqWarehouse;
import com.interesting.modules.warehouse.service.XqWarehouseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author 26773
* @description 针对表【xq_overseas_inventory_check(盘点录入)】的数据库操作Service实现
* @createDate 2023-07-28 09:17:25
*/
@Service
public class XqOverseasInventoryCheckServiceImpl extends ServiceImpl<XqOverseasInventoryCheckMapper, XqOverseasInventoryCheck>
    implements XqOverseasInventoryCheckService{
    @Autowired
    private XqWarehouseService xqWarehouseService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private XqOverseasInventoryCheckDetailMapper xqOverseasInventoryCheckDetailMapper;
    @Autowired
    private ISysCodeRoleService sysCodeRoleService;
    @Autowired
    private XqOverseasInventoryCheckDetailService xqOverseasInventoryCheckDetailService;
    @Autowired
    private XqInventoryOverseasService xqInventoryOverseasService;
    @Autowired
    private XqOverseasEnterDetailService xqOverseasEnterDetailService;

    @Override
    public IPage<OverSeasInventoryCheckPageVO> pageInventoryCheck(QueryPageOverSeasInventoryCheckDTO dto) {
        Page<OverSeasInventoryCheckPageVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        if (StringUtils.isNotBlank(dto.getCheckTimeEnd()) && StringUtils.isNotBlank(dto.getCheckTimeStart())) {
            if (dto.getCheckTimeStart().equals(dto.getCheckTimeEnd())) {
                dto.setCheckTimeStart(dto.getCheckTimeStart() + " 00:00:00");
                dto.setCheckTimeEnd(dto.getCheckTimeEnd() + " 23:59:59");
            }
        }
        return this.baseMapper.pageInventoryCheck(page, dto);
    }

    @Override
    public OverSeasInventoryCheckDetailVO getInventoryCheckDetById(String id) {
        OverSeasInventoryCheckDetailVO vo = new OverSeasInventoryCheckDetailVO();
        XqOverseasInventoryCheck byId = this.getById(id);
        BeanUtils.copyProperties(byId, vo);

        XqWarehouse byId1 = xqWarehouseService.getById(byId.getWarehouseId());
        if (byId1 != null) {
            vo.setWarehouseName(byId1.getName());
        }
        SysUser byId2 = sysUserService.getById(byId.getCheckUserId());
        if (byId2 != null) {
            vo.setCheckUserName(byId2.getUsername());
        }

        List<OverSeasInventoryCheckDetailRecordVO> pdDetails =
                xqOverseasInventoryCheckDetailMapper.getInventoryCheckDetListById(id);
        vo.setPdDetails(pdDetails);

        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatchInventoryCheck(String ids) {
        List<String> strings = Arrays.asList(ids.split(","));
        if (this.lambdaQuery()
                .in(XqOverseasInventoryCheck::getId, strings)
                .eq(XqOverseasInventoryCheck::getAuditStatus, "1")
                .count() > 0) {
            throw new InterestingBootException("已审核的盘点单不能删除");
        }

        this.removeByIds(strings);

//        List<String> collect = xqOverseasInventoryCheckDetailService.lambdaQuery()
//                .in(XqOverseasInventoryCheckDetail::getSourceId, strings)
//                .select(XqOverseasInventoryCheckDetail::getId)
//                .list().stream().map(XqOverseasInventoryCheckDetail::getId)
//                .collect(Collectors.toList());

        xqOverseasInventoryCheckDetailService.lambdaUpdate()
                .in(XqOverseasInventoryCheckDetail::getSourceId, strings)
                .remove();

//        xqInventoryOverseasService.lambdaUpdate()
//                .in(XqInventoryOverseas::getSourceId, collect)
//                .remove();

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addInventoryCheck(InstOverSeasInventoryCheckDTO dto) {
        XqOverseasInventoryCheck xqOverseasInventoryCheck = new XqOverseasInventoryCheck();
        BeanUtils.copyProperties(dto, xqOverseasInventoryCheck);
        String codeNumByType2 = sysCodeRoleService.getCodeNumByType2(3, 1);
        xqOverseasInventoryCheck.setRepNum(codeNumByType2);
        xqOverseasInventoryCheck.setId(null);
        this.save(xqOverseasInventoryCheck);

        // insert check detail
        dto.getPdDetails().forEach(i -> {
            XqOverseasInventoryCheckDetail detail = new XqOverseasInventoryCheckDetail();

            Integer currentInventory = xqInventoryOverseasService
                    .getInventoryNum(i.getProductId(), dto.getWarehouseId(),i.getWarehouseLot(), i.getSourceRepNum());
            if (!Objects.equals(i.getCurrentInventory(), currentInventory)) {
                throw new InterestingBootException("当前库存数量与实际库存数量不一致，请刷新页面");
            }


            BeanUtils.copyProperties(i, detail);
            detail.setSourceId(xqOverseasInventoryCheck.getId());
            detail.setWarehouseId(dto.getWarehouseId());
            detail.setId(null);


            xqOverseasInventoryCheckDetailService.save(detail);
//            i.setWarehouseId(dto.getWarehouseId());
//            i.setId(detail.getId());
        });


//        // insert inventory
//        saveDetailsToInventory(dto);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editCheckStatus(EditEnterOrderStatusDTO dto) {
        List<String> strings = Arrays.asList(dto.getIds().split(","));

        for (String id : strings) {
            XqOverseasInventoryCheck byId = this.getById(id);
            if (dto.getAuditStatus().equals("1")) {
                if (byId.getAuditStatus().equals("1")) {
                    throw new InterestingBootException("已审核的数据不能再次审核");
                }
                byId.setAuditStatus("1");
                this.updateById(byId);

                List<XqOverseasInventoryCheckDetail> list = xqOverseasInventoryCheckDetailService.lambdaQuery()
                        .eq(XqOverseasInventoryCheckDetail::getSourceId, id)
                        .list();

                // check current inventory
                for (XqOverseasInventoryCheckDetail t : list) {
                    Integer currentInventory = xqInventoryOverseasService
                            .getInventoryNum(t.getProductId(), t.getWarehouseId(),t.getWarehouseLot(),
                                    t.getSourceRepNum());
                    if (!Objects.equals(t.getCurrentInventory(), currentInventory)) {
                        throw new InterestingBootException("当前库存数量与实际库存数量不一致，请刷新页面");
                    }
                }

                // insert inventory
                saveDetailsToInventory(list);

            } else {
                if (byId.getAuditStatus().equals("0")) {
                    throw new InterestingBootException("未审核的数据不能取消审核");
                }
                byId.setAuditStatus("0");
                List<XqOverseasInventoryCheckDetail> list = xqOverseasInventoryCheckDetailService.lambdaQuery()
                        .eq(XqOverseasInventoryCheckDetail::getSourceId, id)
                        .list();

                for (XqOverseasInventoryCheckDetail t : list) {
                    Integer currentInventory = xqInventoryOverseasService
                            .getInventoryNum(t.getProductId(), t.getWarehouseId(), t.getWarehouseLot(),
                                    t.getSourceRepNum());
                    if (currentInventory - t.getCurrentInventory().compareTo(0) < 0) {
                        throw new InterestingBootException("库存不足无法反审核");
                    }
                }




                this.updateById(byId);


                // remove inventory
                List<String> collect = xqOverseasInventoryCheckDetailService.lambdaQuery()
                        .eq(XqOverseasInventoryCheckDetail::getSourceId, id)
                        .select(XqOverseasInventoryCheckDetail::getId)
                        .list().stream().map(XqOverseasInventoryCheckDetail::getId)
                        .collect(Collectors.toList());
                xqInventoryOverseasService.lambdaUpdate()
                        .in(XqInventoryOverseas::getSourceId, collect)
                        .remove();
            }

        }
        return true;
    }

    public void saveDetailsToInventory(InstOverSeasInventoryCheckDTO dto) {

        List<XqInventoryOverseas> collect1 = dto.getPdDetails().stream().map(i -> {
            XqInventoryOverseas xqInventoryOverseas = new XqInventoryOverseas();
            Integer enterNum = i.getCheckInventory();
            String id = i.getId();
            String r = i.getProductId();
            String warehouseId = i.getWarehouseId();
            String warehouseLot = i.getWarehouseLot();


            xqInventoryOverseas
                    .setSourceType("pdlr")
                    .setDirection(enterNum > 0 ? CommonConstant.ENTER_DIRECTION : CommonConstant.EXIT_DIRECTION)
                    .setNum(enterNum)
                    .setSourceId(id)
                    .setItemId(r)
                    .setWarehouseId(warehouseId)
                    .setWarehouseLot(warehouseLot)
                    .setRelativeTime(new Date());

            return xqInventoryOverseas;
        }).collect(Collectors.toList());

        xqInventoryOverseasService.saveBatch(collect1);
    }

    public void saveDetailsToInventory(List<XqOverseasInventoryCheckDetail> list) {

        List<XqInventoryOverseas> pdlr = list.stream().map(i -> {
            XqInventoryOverseas xqInventoryOverseas = new XqInventoryOverseas();
            Integer enterNum = i.getCheckInventory();
            String id = i.getId();
            String r = i.getProductId();
            String warehouseId = i.getWarehouseId();
            String warehouseLot = i.getWarehouseLot();

            xqInventoryOverseas
                    .setSourceType("pdlr")
                    .setDirection(enterNum > 0 ? CommonConstant.ENTER_DIRECTION : CommonConstant.EXIT_DIRECTION)
                    .setNum(enterNum)
                    .setSourceId(id)
                    .setItemId(r)
                    .setWarehouseId(warehouseId)
                    .setWarehouseLot(warehouseLot)
                    .setRelativeTime(new Date());

            return xqInventoryOverseas;
        }).collect(Collectors.toList());

        xqInventoryOverseasService.saveBatch(pdlr);
    }

}




