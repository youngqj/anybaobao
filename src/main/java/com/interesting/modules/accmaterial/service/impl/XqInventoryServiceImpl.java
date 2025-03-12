package com.interesting.modules.accmaterial.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.constant.CommonConstant;
import com.interesting.common.util.BeanUtils;
import com.interesting.modules.accmaterial.dto.InstOrUpdtAccInventoryDTO;
import com.interesting.modules.accmaterial.dto.QueryXqAccInventoryDTO2;
import com.interesting.modules.accmaterial.entity.XqAccessoriesPurchase;
import com.interesting.modules.accmaterial.entity.XqInventory;
import com.interesting.modules.accmaterial.mapper.XqInventoryMapper;
import com.interesting.modules.accmaterial.service.IXqAccessoriesPurchaseService;
import com.interesting.modules.accmaterial.service.XqInventoryService;
import com.interesting.modules.accmaterial.vo.AccInventoryDetails2VO;
import com.interesting.modules.accmaterial.vo.AccInventoryPageVO;
import com.interesting.modules.accmaterial.vo.AccInventoryPageVO2;
import com.interesting.modules.warehouse.service.XqWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @author 26773
* @description 针对表【xq_inventory(库存管理)】的数据库操作Service实现
* @createDate 2023-07-05 16:19:33
*/
@Service
public class XqInventoryServiceImpl extends ServiceImpl<XqInventoryMapper, XqInventory>
    implements XqInventoryService{

    @Autowired
    private XqWarehouseService xqWarehouseService;

    @Autowired
    private IXqAccessoriesPurchaseService xqAccessoriesPurchaseService;

    @Override
    public IPage<AccInventoryDetails2VO> listInventoryDetails(Page<AccInventoryDetails2VO> page,
                                                              String warehouseId,
                                                              String accessoryId,
                                                              String relativeTimeBegin,
                                                              String relativeTimeEnd) {
        IPage<AccInventoryDetails2VO> accInventoryDetails2VOIPage =
                this.baseMapper.listInventoryDetails(page, warehouseId, accessoryId, relativeTimeBegin, relativeTimeEnd);
        List<AccInventoryDetails2VO> records = accInventoryDetails2VOIPage.getRecords();
        records.forEach(i -> {
            if (i.getNum() > 0 && i.getSourceType().equals(CommonConstant.INVENTORY_FLCG)) {
                String sourceTypeTxt = i.getSourceTypeTxt();
                i.setSourceTypeTxt(sourceTypeTxt + "(退料)");
            } else if (i.getNum() < 0 && i.getSourceType().equals(CommonConstant.INVENTORY_FLCG)) {
                String sourceTypeTxt = i.getSourceTypeTxt();
                i.setSourceTypeTxt(sourceTypeTxt + "(用料)");
            }
        });
        return accInventoryDetails2VOIPage;
    }

    @Override
    public Integer getInventoryNum(String warehouseId, String accessoryId, String receiptNum, BigDecimal unitPrice) {
        QueryXqAccInventoryDTO2 queryXqAccInventoryDTO2 = new QueryXqAccInventoryDTO2();

        queryXqAccInventoryDTO2.setPageNo(1);
        queryXqAccInventoryDTO2.setPageSize(Integer.MAX_VALUE);
        queryXqAccInventoryDTO2.setWarehouseId(warehouseId);
        queryXqAccInventoryDTO2.setAccessoryId(accessoryId);
        queryXqAccInventoryDTO2.setReceiptNum(receiptNum);
        queryXqAccInventoryDTO2.setUnitPrice(unitPrice);
        Integer totalInventoryNum = 0;
        IPage<AccInventoryPageVO2> resultPage =
                xqWarehouseService.listAccInventoryPage2(queryXqAccInventoryDTO2);
        if (CollectionUtil.isNotEmpty(resultPage.getRecords())) {
            for (AccInventoryPageVO2 vo2 : resultPage.getRecords()) {
                totalInventoryNum = totalInventoryNum + vo2.getInventoryNum();
            }
            return totalInventoryNum;
        } else {
            return 0;
        }
    }

    @Override
    public BigDecimal getSkuNum(String accessoryId) {
        return baseMapper.getSkuNum(accessoryId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUseInventoryDetails(String sourceId,
                                           List<InstOrUpdtAccInventoryDTO> useInventoryDetails,
                                           String accessoryId) {
        List<XqInventory> list = this.lambdaQuery()
                .eq(XqInventory::getSourceId, sourceId)
                .eq(XqInventory::getDirection, CommonConstant.EXIT_DIRECTION)
                .list();
        Map<String, InstOrUpdtAccInventoryDTO> detailsMap = new LinkedHashMap<>();
        for (InstOrUpdtAccInventoryDTO detail : useInventoryDetails) {
            detailsMap.put(detail.getId(), detail);
        }

        List<XqInventory> toUpdateComs = new ArrayList<>();
        List<String> toDeleteIds = new ArrayList<>();
        List<XqInventory> toAddComs = new ArrayList<>();

        for (XqInventory originalCom : list) {
            InstOrUpdtAccInventoryDTO addCom = detailsMap.get(originalCom.getId());
            if (addCom != null) {
                // 更新记录
                BeanUtils.copyProperties(addCom, originalCom);
                originalCom.setItemId(accessoryId);
                toUpdateComs.add(originalCom);
                // 从传入的佣金记录列表中移除已经更新的记录
                detailsMap.remove(originalCom.getId());
            } else {
                // 删除记录
                toDeleteIds.add(originalCom.getId());
            }
        }

        this.updateBatchById(toUpdateComs);
        this.removeByIds(toDeleteIds);

        for (InstOrUpdtAccInventoryDTO addCom : detailsMap.values()) {


            XqInventory newCom = new XqInventory();
            BeanUtils.copyProperties(addCom, newCom);

            newCom.setId(null);
            newCom.setDirection(CommonConstant.EXIT_DIRECTION);
            newCom.setSourceId(sourceId);
            newCom.setUnitPrice(addCom.getUnitPrice());
            newCom.setCurrency(addCom.getCurrency());
            newCom.setItemId(accessoryId);
            newCom.setSourceType("flcg");
            toAddComs.add(newCom);
        }

        this.saveBatch(toAddComs);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBackInventoryDetails(String sourceId,
                                            List<InstOrUpdtAccInventoryDTO> backInventoryDetails,
                                            String accessoryId, String orderNum) {
        List<XqInventory> list = this.lambdaQuery()
                .eq(XqInventory::getSourceId, sourceId)
                .eq(XqInventory::getDirection, CommonConstant.ENTER_DIRECTION)
                .list();
        Map<String, InstOrUpdtAccInventoryDTO> detailsMap = new LinkedHashMap<>();
//        XqAccessoriesPurchase xqAccessoriesPurchase = xqAccessoriesPurchaseService.getById(sourceId);
        for (InstOrUpdtAccInventoryDTO detail : backInventoryDetails) {

//            detail.setUnitPrice(xqAccessoriesPurchase.getUnitPrice());
//            detail.setCurrency(xqAccessoriesPurchase.getCurrency());
            detailsMap.put(detail.getId(), detail);
        }

        List<XqInventory> toUpdateComs = new ArrayList<>();
        List<String> toDeleteIds = new ArrayList<>();
        List<XqInventory> toAddComs = new ArrayList<>();

        for (XqInventory originalCom : list) {
            InstOrUpdtAccInventoryDTO addCom = detailsMap.get(originalCom.getId());
            if (addCom != null) {
                // 更新记录
                BeanUtils.copyProperties(addCom, originalCom);
                originalCom.setItemId(accessoryId);
                originalCom.setSourceRepNum(orderNum);

                toUpdateComs.add(originalCom);
                detailsMap.remove(originalCom.getId());
            } else {
                // 删除记录
                toDeleteIds.add(originalCom.getId());
            }
        }

        this.updateBatchById(toUpdateComs);
        this.removeByIds(toDeleteIds);

        for (InstOrUpdtAccInventoryDTO addCom : detailsMap.values()) {
            XqInventory newCom = new XqInventory();
            BeanUtils.copyProperties(addCom, newCom);

            newCom.setId(null);
            newCom.setDirection(CommonConstant.ENTER_DIRECTION);
            newCom.setSourceId(sourceId);
            newCom.setUnitPrice(addCom.getUnitPrice());
            newCom.setCurrency(addCom.getCurrency());
            newCom.setItemId(accessoryId);
            newCom.setSourceType("flcg");
            newCom.setSourceRepNum(orderNum);

            toAddComs.add(newCom);
        }

        this.saveBatch(toAddComs);
    }



}




