package com.interesting.modules.accmaterial.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.business.system.service.ISysCodeRoleService;
import com.interesting.common.api.vo.Result;
import com.interesting.common.constant.CommonConstant;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.accmaterial.dto.AddInventoryCheckDTO;
import com.interesting.modules.accmaterial.dto.EditStatusDTO;
import com.interesting.modules.accmaterial.dto.QueryInventoryCheckPageDTO;
import com.interesting.modules.accmaterial.dto.QueryXqAccInventoryDTO;
import com.interesting.modules.accmaterial.entity.XqAccessoryInfo;
import com.interesting.modules.accmaterial.entity.XqInventory;
import com.interesting.modules.accmaterial.entity.XqInventoryCheck;
import com.interesting.modules.accmaterial.entity.XqInventoryCheckDetail;
import com.interesting.modules.accmaterial.mapper.XqInventoryCheckDetailMapper;
import com.interesting.modules.accmaterial.mapper.XqInventoryMapper;
import com.interesting.modules.accmaterial.service.XqAccessoryInfoService;
import com.interesting.modules.accmaterial.service.XqInventoryCheckDetailService;
import com.interesting.modules.accmaterial.service.XqInventoryCheckService;
import com.interesting.modules.accmaterial.mapper.XqInventoryCheckMapper;
import com.interesting.modules.accmaterial.service.XqInventoryService;
import com.interesting.modules.accmaterial.vo.AccInventoryPageVO;
import com.interesting.modules.accmaterial.vo.InventoryCheckDetailRecordVO;
import com.interesting.modules.accmaterial.vo.InventoryCheckDetailVO;
import com.interesting.modules.accmaterial.vo.InventoryCheckPageVO;
import com.interesting.modules.warehouse.entity.XqWarehouse;
import com.interesting.modules.warehouse.service.XqWarehouseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author 26773
 * @description 针对表【xq_inventory_check(盘点录入)】的数据库操作Service实现
 * @createDate 2023-07-11 15:28:29
 */
@Service
public class XqInventoryCheckServiceImpl extends ServiceImpl<XqInventoryCheckMapper, XqInventoryCheck>
        implements XqInventoryCheckService {

    @Autowired
    private XqInventoryService xqInventoryService;
    @Autowired
    private XqWarehouseService xqWarehouseService;
    @Autowired
    private XqAccessoryInfoService xqAccessoryInfoService;
    @Autowired
    private XqInventoryCheckDetailService xqInventoryCheckDetailService;
    @Autowired
    private XqInventoryCheckDetailMapper xqInventoryCheckDetailMapper;
    @Autowired
    private ISysCodeRoleService sysCodeRoleService;


    @Override
    public IPage<InventoryCheckPageVO> pageInventoryCheck(QueryInventoryCheckPageDTO dto) {
        Page<InventoryCheckPageVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        return this.baseMapper.pageInventoryCheck(page, dto);
    }

    @Override
    public InventoryCheckDetailVO getInventoryCheckDetById(String id) {
        return xqInventoryCheckDetailMapper.getInventoryCheckDetById(id);
    }

//    @Override
//    public InventoryCheckDetailVO getInventoryCheckDetById(String id) {
//        InventoryCheckDetailVO inventoryCheckDetailVO = new InventoryCheckDetailVO();
//        XqInventoryCheck byId = this.getById(id);
//        BeanUtils.copyProperties(byId, inventoryCheckDetailVO);
//        List<XqInventory> flpd = xqInventoryService.lambdaQuery().eq(XqInventory::getSourceType, "flpd")
//                .eq(XqInventory::getSourceId, id)
//                .orderByAsc(XqInventory::getCreateTime).list();
//
//        List<InventoryCheckDetailRecordVO> collect = flpd.stream().map(i -> {
//            InventoryCheckDetailRecordVO inventoryCheckDetailRecordVO = new InventoryCheckDetailRecordVO();
//            XqAccessoryInfo byId1 = xqAccessoryInfoService.getById(i.getItemId());
//            if (byId1 != null) {
//                inventoryCheckDetailRecordVO.setAccessoryId(byId1.getId());
//                inventoryCheckDetailRecordVO.setAccessoryName(byId1.getAccessoryName());
//                inventoryCheckDetailRecordVO.setSize(byId1.getSize());
//
//            }
//
//            XqWarehouse byId2 = xqWarehouseService.getById(i.getWarehouseId());
//            if (byId2 != null) {
//                inventoryCheckDetailRecordVO.setWarehouseId(byId2.getId());
//                inventoryCheckDetailRecordVO.setWarehouseName(byId2.getName());
//            }
//
//            inventoryCheckDetailRecordVO.setNum(i.getNum());
//            inventoryCheckDetailRecordVO.setId(i.getId());
//
//            return inventoryCheckDetailRecordVO;
//        }).collect(Collectors.toList());
//
//        inventoryCheckDetailVO.setPdDetails(collect);
//        return inventoryCheckDetailVO;
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatchInventoryCheck(String ids) {
        List<String> strings = Arrays.asList(ids.split(","));

        for (String string : strings) {
            XqInventoryCheck byId = this.getById(string);
            if (byId.getAuditStatus().equals("1")) {
                throw new InterestingBootException("已审核的盘点单不能删除");
            }
        }

        boolean b = this.removeByIds(strings);

        boolean f = xqInventoryCheckDetailService.lambdaUpdate()
                .in(XqInventoryCheckDetail::getSourceId, strings).remove();

        boolean flpd = xqInventoryService.lambdaUpdate()
                .in(XqInventory::getSourceId, strings)
                .eq(XqInventory::getSourceType, "flpd").remove();

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addInventoryCheck(AddInventoryCheckDTO dto) {
        XqInventoryCheck xqInventoryCheck = new XqInventoryCheck();
        BeanUtils.copyProperties(dto, xqInventoryCheck);
        String codeNumByType2 = sysCodeRoleService.getCodeNumByType2(2, 1);
        xqInventoryCheck.setRepNum(codeNumByType2);
        this.save(xqInventoryCheck);


        String id = xqInventoryCheck.getId();
        String warehouseId = dto.getWarehouseId();

        dto.getPdDetails().forEach(i -> {
            XqInventoryCheckDetail xqInventoryCheckDetail = new XqInventoryCheckDetail();

            Integer inventoryNum = xqInventoryService.getInventoryNum(warehouseId, i.getAccessoryId(),
                    StringUtils.isBlank(i.getSourceRepNum()) ? codeNumByType2 : i.getSourceRepNum(),
                    i.getUnitPrice());

            if (!i.getCurrentInventory().equals(inventoryNum)) {
                throw new InterestingBootException("当前库存数量与实际库存数量不一致，请重选或刷新页面");
            }

            // 设置库存盘点子表
            xqInventoryCheckDetail.setSourceId(id)
                    .setRealInventory(i.getRealInventory())
                    .setCurrentInventory(i.getCurrentInventory())
                    .setCheckInventory(i.getCheckInventory())
                    .setWarehouseId(warehouseId)
                    .setAccessoryId(i.getAccessoryId())
                    .setSourceRepNum(StringUtils.isBlank(i.getSourceRepNum()) ? codeNumByType2 : i.getSourceRepNum())
                    .setUnitPrice(i.getUnitPrice())
                    .setCurrency(i.getCurrency());
            xqInventoryCheckDetailService.save(xqInventoryCheckDetail);
        });

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean auditInventoryCheck(EditStatusDTO dto) {
        List<String> strings = Arrays.asList(dto.getIds().split(","));
        for (String string : strings) {
            XqInventoryCheck byId = this.getById(string);

            if (dto.getAuditStatus().equals("1")) {
                if (byId.getAuditStatus().equals("1")) {
                    throw new InterestingBootException("已审核，请勿重复审核！");
                }
                byId.setAuditStatus("1");
                this.updateById(byId);

                List<XqInventoryCheckDetail> list = xqInventoryCheckDetailService.lambdaQuery()
                        .eq(XqInventoryCheckDetail::getSourceId, string)
                        .list();

                ArrayList<XqInventory> xqInventories = new ArrayList<>();
                for (XqInventoryCheckDetail xqInventoryCheckDetail : list) {

                    Integer inventoryNum = xqInventoryService.getInventoryNum(xqInventoryCheckDetail.getWarehouseId(),
                            xqInventoryCheckDetail.getAccessoryId(),
                            xqInventoryCheckDetail.getSourceRepNum(),
                            xqInventoryCheckDetail.getUnitPrice());

                    if (!xqInventoryCheckDetail.getCurrentInventory().equals(inventoryNum)) {
                        throw new InterestingBootException("当前库存数量与实际库存数量不一致，请重选或刷新页面");
                    }

                    // 设置库存子表
                    XqInventory xqInventory = new XqInventory();
                    xqInventory.setItemId(xqInventoryCheckDetail.getAccessoryId());
                    xqInventory.setWarehouseId(xqInventoryCheckDetail.getWarehouseId());
                    xqInventory.setSourceId(xqInventoryCheckDetail.getId());
                    xqInventory.setSourceType("flpd");
                    xqInventory.setRelativeTime(new Date());
                    xqInventory.setNum(xqInventoryCheckDetail.getCheckInventory());
                    xqInventory.setDirection(xqInventoryCheckDetail.getCheckInventory()>0 ? "1" : "2");
                    xqInventory.setRelativeTime(new Date());
                    xqInventory.setSourceRepNum(xqInventoryCheckDetail.getSourceRepNum());
                    xqInventory.setId(null);
                    xqInventory.setUnitPrice(xqInventoryCheckDetail.getUnitPrice());
                    xqInventory.setCurrency(xqInventoryCheckDetail.getCurrency());
                    xqInventories.add(xqInventory);
                }
                xqInventoryService.saveBatch(xqInventories);

            } else {
                if (byId.getAuditStatus().equals("0")) {
                    throw new InterestingBootException("已反审核，请勿重复反审核！");
                }

                byId.setAuditStatus("0");
                this.updateById(byId);

                List<String> collect = xqInventoryCheckDetailService.lambdaQuery()
                        .eq(XqInventoryCheckDetail::getSourceId, string)
                        .list().stream().map(XqInventoryCheckDetail::getId)
                        .collect(Collectors.toList());
                if (collect.size() > 0) {
                    for (String str : collect) {
                        xqInventoryService.remove(new LambdaQueryWrapper<XqInventory>().eq(XqInventory::getSourceId, str));

                    }
                }
                XqInventoryMapper xqInventoryMapper = (XqInventoryMapper) xqInventoryService.getBaseMapper();
                xqInventoryMapper.physicalDeleteBatch(collect);

            }

        }

        return true;
    }
}




