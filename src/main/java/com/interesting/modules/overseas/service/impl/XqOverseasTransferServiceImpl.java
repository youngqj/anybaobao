package com.interesting.modules.overseas.service.impl;

import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.business.system.entity.SysUser;
import com.interesting.business.system.service.ISysCodeRoleService;
import com.interesting.business.system.service.ISysUserService;
import com.interesting.common.constant.CommonConstant;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.overseas.dto.EditEnterOrderStatusDTO;
import com.interesting.modules.overseas.dto.InstOverSeasTransferDTO;
import com.interesting.modules.overseas.dto.QueryPageOverSeasInventoryTransferDTO;
import com.interesting.modules.overseas.entity.XqInventoryOverseas;
import com.interesting.modules.overseas.entity.XqOverseasInventoryCheckDetail;
import com.interesting.modules.overseas.entity.XqOverseasTransfer;
import com.interesting.modules.overseas.entity.XqOverseasTransferDetail;
import com.interesting.modules.overseas.mapper.XqOverseasTransferDetailMapper;
import com.interesting.modules.overseas.mapper.XqOverseasTransferMapper;
import com.interesting.modules.overseas.service.XqInventoryOverseasService;
import com.interesting.modules.overseas.service.XqOverseasTransferDetailService;
import com.interesting.modules.overseas.service.XqOverseasTransferService;
import com.interesting.modules.overseas.vo.OverSeasInventoryCheckDetailVO;
import com.interesting.modules.overseas.vo.OverSeasInventoryTransferDetailRecordVO;
import com.interesting.modules.overseas.vo.OverSeasInventoryTransferDetailVO;
import com.interesting.modules.overseas.vo.OverSeasInventoryTransferPageVO;
import com.interesting.modules.warehouse.entity.XqWarehouse;
import com.interesting.modules.warehouse.service.XqWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class XqOverseasTransferServiceImpl extends ServiceImpl<XqOverseasTransferMapper, XqOverseasTransfer> implements XqOverseasTransferService {

    @Autowired
    ISysCodeRoleService sysCodeRoleService;

    @Autowired
    XqInventoryOverseasService xqInventoryOverseasService;

    @Autowired
    XqWarehouseService xqWarehouseService;

    @Autowired
    ISysUserService sysUserService;

    @Autowired
    XqOverseasTransferDetailMapper xqOverseasTransferDetailMapper;
    @Autowired
    XqOverseasTransferDetailService xqOverseasTransferDetailService;

    @Override
    public boolean addInventoryTransfer(InstOverSeasTransferDTO dto) {
        XqOverseasTransfer xqOverseasTransfer = new XqOverseasTransfer();
        BeanUtils.copyProperties(dto, xqOverseasTransfer);
        String codeNumByType2 = sysCodeRoleService.getCodeNumByType2(6, 1);
        xqOverseasTransfer.setRepNum(codeNumByType2);
        xqOverseasTransfer.setId(null);
        this.save(xqOverseasTransfer);

        // insert check detail
        dto.getTransferDetails().forEach(i -> {
            XqOverseasTransferDetail detail = new XqOverseasTransferDetail();

//            Integer currentInventory = xqInventoryOverseasService
//                    .getInventoryNum(i.getProductId(), dto.getWarehouseId(),i.getWarehouseLot(), i.getSourceRepNum());
//            if (!Objects.equals(i.getCurrentInventory(), currentInventory)) {
//                throw new InterestingBootException("当前库存数量与实际库存数量不一致，请刷新页面");
//            }


            BeanUtils.copyProperties(i, detail);
            detail.setSourceId(xqOverseasTransfer.getId());
            detail.setSourceWarehouseId(dto.getWarehouseId());
            detail.setId(null);
            xqOverseasTransferDetailService.save(detail);
        });


//        // insert inventory
//        saveDetailsToInventory(dto);
        return true;
    }

    @Override
    public OverSeasInventoryTransferDetailVO getInventoryTransferDetById(String id) {
        OverSeasInventoryTransferDetailVO vo = new OverSeasInventoryTransferDetailVO();
        XqOverseasTransfer byId = this.getById(id);
        BeanUtils.copyProperties(byId, vo);

        XqWarehouse byId1 = xqWarehouseService.getById(byId.getWarehouseId());
        if (byId1 != null) {
            vo.setWarehouseName(byId1.getName());
        }
        SysUser byId2 = sysUserService.getById(byId.getTransferUserId());
        if (byId2 != null) {
            vo.setTransferUserName(byId2.getUsername());
        }

        List<OverSeasInventoryTransferDetailRecordVO> pdDetails =
                xqOverseasTransferDetailMapper.getInventoryTransferDetListById(id);
        vo.setTransferDetails(pdDetails);

        return vo;
    }

    @Override
    public IPage<OverSeasInventoryTransferPageVO> pageInventoryTransfer(QueryPageOverSeasInventoryTransferDTO dto) {
        Page<OverSeasInventoryTransferPageVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        if (StringUtils.isNotBlank(dto.getTransferTimeStart()) && StringUtils.isNotBlank(dto.getTransferTimeEnd())) {
            if (dto.getTransferTimeStart().equals(dto.getTransferTimeEnd())) {
                dto.setTransferTimeStart(dto.getTransferTimeStart() + " 00:00:00");
                dto.setTransferTimeEnd(dto.getTransferTimeEnd() + " 23:59:59");
            }
        }
        return baseMapper.pageInventoryTransfer(page, dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatchInventoryTransfer(String ids) {
        List<String> strings = Arrays.asList(ids.split(","));
        if (this.lambdaQuery()
                .in(XqOverseasTransfer::getId, strings)
                .eq(XqOverseasTransfer::getAuditStatus, "1")
                .count() > 0) {
            throw new InterestingBootException("已审核调拨单不能删除");
        }

        this.removeByIds(strings);

//        List<String> collect = xqOverseasInventoryCheckDetailService.lambdaQuery()
//                .in(XqOverseasInventoryCheckDetail::getSourceId, strings)
//                .select(XqOverseasInventoryCheckDetail::getId)
//                .list().stream().map(XqOverseasInventoryCheckDetail::getId)
//                .collect(Collectors.toList());

        xqOverseasTransferDetailService.lambdaUpdate()
                .in(XqOverseasTransferDetail::getSourceId, strings)
                .remove();

//        xqInventoryOverseasService.lambdaUpdate()
//                .in(XqInventoryOverseas::getSourceId, collect)
//                .remove();

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editCheckStatus(EditEnterOrderStatusDTO dto) {
        List<String> strings = Arrays.asList(dto.getIds().split(","));

        for (String id : strings) {
            XqOverseasTransfer byId = this.getById(id);
            if (dto.getAuditStatus().equals("1")) {
                if (byId.getAuditStatus().equals("1")) {
                    throw new InterestingBootException("已审核的数据不能再次审核");
                }
                byId.setAuditStatus("1");
                this.updateById(byId);

                List<XqOverseasTransferDetail> list = xqOverseasTransferDetailService.lambdaQuery()
                        .eq(XqOverseasTransferDetail::getSourceId, id)
                        .list();

                // check current inventory
                for (XqOverseasTransferDetail t : list) {
                    Integer currentInventory = xqInventoryOverseasService
                            .getInventoryNum(t.getProductId(), t.getSourceWarehouseId(), t.getWarehouseLot());
                    if (!Objects.equals(t.getCurrentInventory(), currentInventory)) {
                        throw new InterestingBootException("当前库存数量与实际库存数量不一致，请刷新页面");
                    }
                    if (t.getTransferInventory().compareTo(currentInventory) > 0) {
                        throw new InterestingBootException("当前库存数量与不足，请刷新页面");
                    }
                }

                // insert inventory
                saveDetailsToInventory(list);

            }

        }
        return true;
    }

    public void saveDetailsToInventory(List<XqOverseasTransferDetail> list) {

        List<XqInventoryOverseas> dblr = list.stream().map(i -> {
            XqInventoryOverseas xqInventoryOverseas = new XqInventoryOverseas();
            Integer enterNum = i.getTransferInventory();
            String id = i.getId();
            String r = i.getProductId();
            String warehouseLot = i.getWarehouseLot();

            xqInventoryOverseas
                    .setSourceType("dblr")
                    .setDirection(CommonConstant.ENTER_DIRECTION)
                    .setNum(enterNum)
                    .setSourceId(id)
                    .setItemId(r)
                    .setWarehouseId(i.getTransferWarehouseId())
                    .setWarehouseLot(i.getNewWarehouseLot())
                    .setRelativeTime(new Date());

            return xqInventoryOverseas;
        }).collect(Collectors.toList());

        List<XqInventoryOverseas> dblc = list.stream().map(i -> {
            XqInventoryOverseas xqInventoryOverseas = new XqInventoryOverseas();
            Integer enterNum = 0 - i.getTransferInventory();
            String id = i.getId();
            String r = i.getProductId();
            String warehouseLot = i.getWarehouseLot();

            xqInventoryOverseas
                    .setSourceType("dblc")
                    .setDirection(CommonConstant.EXIT_DIRECTION)
                    .setNum(enterNum)
                    .setSourceId(id)
                    .setItemId(r)
                    .setWarehouseId(i.getSourceWarehouseId())
                    .setWarehouseLot(warehouseLot)
                    .setRelativeTime(new Date());

            return xqInventoryOverseas;
        }).collect(Collectors.toList());

        xqInventoryOverseasService.saveBatch(dblr);
        xqInventoryOverseasService.saveBatch(dblc);
    }


}
