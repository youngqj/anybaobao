package com.interesting.modules.accmaterial.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.constant.CommonConstant;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.accmaterial.dto.EditStatusDTO;
import com.interesting.modules.accmaterial.dto.accpurchase.InstUptAccPurchaseDTO;
import com.interesting.modules.accmaterial.dto.accpurchase.InstUptAccPurchaseDTO2;
import com.interesting.modules.accmaterial.dto.accpurchase.QueryPageAccPurchaseDTO;
import com.interesting.modules.accmaterial.entity.XqAccessoryInfo;
import com.interesting.modules.accmaterial.entity.XqAccessoryPurchaseInventory;
import com.interesting.modules.accmaterial.entity.XqAccessoryPurchaseInventoryDetail;
import com.interesting.modules.accmaterial.entity.XqInventory;
import com.interesting.modules.accmaterial.mapper.XqInventoryMapper;
import com.interesting.modules.accmaterial.service.XqAccessoryInfoService;
import com.interesting.modules.accmaterial.service.XqAccessoryPurchaseInventoryDetailService;
import com.interesting.modules.accmaterial.service.XqAccessoryPurchaseInventoryService;
import com.interesting.modules.accmaterial.mapper.XqAccessoryPurchaseInventoryMapper;
import com.interesting.modules.accmaterial.service.XqInventoryService;
import com.interesting.modules.accmaterial.vo.accpurchase.AccessoryPurchaseDetailVO;
import com.interesting.modules.accmaterial.vo.accpurchase.AccessoryPurchaseDetailVO2;
import com.interesting.modules.accmaterial.vo.accpurchase.AccessoryPurchasePageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 26773
 * @description 针对表【xq_accessory_purchase_inventory】的数据库操作Service实现
 * @createDate 2023-07-20 14:09:42
 */
@Service
public class XqAccessoryPurchaseInventoryServiceImpl extends ServiceImpl<XqAccessoryPurchaseInventoryMapper, XqAccessoryPurchaseInventory>
        implements XqAccessoryPurchaseInventoryService {

    @Autowired
    private XqAccessoryPurchaseInventoryDetailService xqAccessoryPurchaseInventoryDetailService;
    @Autowired
    private XqAccessoryInfoService xqAccessoryInfoService;
    @Autowired
    private XqInventoryMapper xqInventoryMapper;

    @Override
    public AccessoryPurchaseDetailVO getAccPurchaseById(String id) {
        AccessoryPurchaseDetailVO acc = this.baseMapper.getAccPurchaseHeadById(id);
        List<AccessoryPurchaseDetailVO2> details = this.baseMapper.getAccPurchaseDetailById(id);
        acc.setDetails(details);

        return acc;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addAccPurchase(InstUptAccPurchaseDTO dto) {
        XqAccessoryPurchaseInventory xqAccessoryPurchaseInventory = new XqAccessoryPurchaseInventory();
        BeanUtils.copyProperties(dto, xqAccessoryPurchaseInventory);
        xqAccessoryPurchaseInventory.setId(null);
        this.save(xqAccessoryPurchaseInventory);

        List<InstUptAccPurchaseDTO2> details = dto.getDetails();
        List<XqAccessoryPurchaseInventoryDetail> collect = details.stream().map(i -> {
            XqAccessoryPurchaseInventoryDetail xqAccessoryPurchaseInventoryDetail =
                    new XqAccessoryPurchaseInventoryDetail();
            BeanUtils.copyProperties(i, xqAccessoryPurchaseInventoryDetail);

            /* 新增辅料信息，有则直接插入id，没有则新增辅料信息再插入id */
            String accessoryName = i.getAccessoryName().trim();
            String size = i.getSize().trim();
            String msf = i.getMaterialSpecification().trim();
            XqAccessoryInfo one = xqAccessoryInfoService.getByCondition(accessoryName,size,msf);
            if (one == null) {
                XqAccessoryInfo xqAccessoryInfo = new XqAccessoryInfo();
                xqAccessoryInfo.setAccessoryName(accessoryName);
                xqAccessoryInfo.setSize(size);
                xqAccessoryInfo.setMaterialSpecification(msf);
                xqAccessoryInfoService.save(xqAccessoryInfo);
                xqAccessoryPurchaseInventoryDetail.setAccessoryId(xqAccessoryInfo.getId());
            } else {
                xqAccessoryPurchaseInventoryDetail.setAccessoryId(one.getId());
            }

            xqAccessoryPurchaseInventoryDetail.setSourceId(xqAccessoryPurchaseInventory.getId());
            return xqAccessoryPurchaseInventoryDetail;
        }).collect(Collectors.toList());

        xqAccessoryPurchaseInventoryDetailService.saveBatch(collect);

        return true;
    }

    @Override
    public IPage<AccessoryPurchasePageVO> pageAccPurchase(QueryPageAccPurchaseDTO dto) {
        Page<AccessoryPurchasePageVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        return this.baseMapper.pageAccPurchase(page, dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAccPurchase(String ids) {
        List<String> strings = Arrays.asList(ids.split(","));
        for (String string : strings) {
            List<XqAccessoryPurchaseInventoryDetail> list =
                    xqAccessoryPurchaseInventoryDetailService.lambdaQuery()
                            .eq(XqAccessoryPurchaseInventoryDetail::getSourceId, string)
                            .list();

            for (XqAccessoryPurchaseInventoryDetail xqAccessoryPurchaseInventoryDetail : list) {
                if (xqAccessoryPurchaseInventoryDetail.getFinanceAuditDate() != null) {
                    throw new InterestingBootException("存在被审核的辅料采购单，无法删除");
                }
            }

        }

        this.lambdaUpdate().in(XqAccessoryPurchaseInventory::getId, strings).remove();
        xqAccessoryPurchaseInventoryDetailService.lambdaUpdate()
                .in(XqAccessoryPurchaseInventoryDetail::getSourceId, strings).remove();

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editAccPurchase(InstUptAccPurchaseDTO dto) {
        XqAccessoryPurchaseInventory byId = this.getById(dto.getId());
        BeanUtils.copyProperties(dto, byId);
        this.updateById(byId);

        List<String> ids = xqAccessoryPurchaseInventoryDetailService.lambdaQuery()
                .eq(XqAccessoryPurchaseInventoryDetail::getSourceId, dto.getId())
                .list().stream().map(XqAccessoryPurchaseInventoryDetail::getId)
                .collect(Collectors.toList());

        xqAccessoryPurchaseInventoryDetailService.physicalDeleteBatch(ids);

        for (InstUptAccPurchaseDTO2 detail : dto.getDetails()) {
            XqAccessoryPurchaseInventoryDetail xqAccessoryPurchaseInventoryDetail =
                    new XqAccessoryPurchaseInventoryDetail();
            BeanUtils.copyProperties(detail, xqAccessoryPurchaseInventoryDetail);

            /* 新增辅料信息，有则直接插入id，没有则新增辅料信息再插入id */
            String accessoryName = detail.getAccessoryName();
            String size = detail.getSize();
            String msf = detail.getMaterialSpecification();
            XqAccessoryInfo one = xqAccessoryInfoService.lambdaQuery()
                    .eq(XqAccessoryInfo::getAccessoryName, accessoryName)
                    .eq(StringUtils.isNotBlank(msf), XqAccessoryInfo::getMaterialSpecification, msf)
                    .eq(XqAccessoryInfo::getSize, size).last("LIMIT 1").one();
            if (one == null) {
                XqAccessoryInfo xqAccessoryInfo = new XqAccessoryInfo();
                xqAccessoryInfo.setAccessoryName(accessoryName);
                xqAccessoryInfo.setSize(size);
                xqAccessoryInfo.setMaterialSpecification(msf);
                xqAccessoryInfoService.save(xqAccessoryInfo);
                xqAccessoryPurchaseInventoryDetail.setAccessoryId(xqAccessoryInfo.getId());
            } else {
                xqAccessoryPurchaseInventoryDetail.setAccessoryId(one.getId());
            }
            xqAccessoryPurchaseInventoryDetail.setSourceId(dto.getId());

            xqAccessoryPurchaseInventoryDetailService.save(xqAccessoryPurchaseInventoryDetail);
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editAccPurchaseStatus(EditStatusDTO dto) {
        List<String> strings = Arrays.asList(dto.getIds().split(","));
        for (String string : strings) {
            if (dto.getAuditStatus().equals("1")) {
                XqAccessoryPurchaseInventory byId = this.getById(string);
                if (byId.getAuditStatus().equals("1")) {
                    throw new InterestingBootException("存在已审核的辅料采购单，无法修改状态");
                }
                byId.setAuditStatus("1");
                this.updateById(byId);

                List<XqAccessoryPurchaseInventoryDetail> list = xqAccessoryPurchaseInventoryDetailService.lambdaQuery()
                        .eq(XqAccessoryPurchaseInventoryDetail::getSourceId, string)
                        .list();

                if (CollectionUtil.isNotEmpty(list)) {
                    for (XqAccessoryPurchaseInventoryDetail xqAccessoryPurchaseInventoryDetail : list) {
                        XqInventory xqInventory = new XqInventory();
                        xqInventory.setSourceType("flcg2")
                                .setSourceId(xqAccessoryPurchaseInventoryDetail.getId())
                                .setNum(xqAccessoryPurchaseInventoryDetail.getOrderQuantity())
                                .setItemId(xqAccessoryPurchaseInventoryDetail.getAccessoryId())
                                .setDirection(CommonConstant.ENTER_DIRECTION)
                                .setWarehouseId(xqAccessoryPurchaseInventoryDetail.getToWarehouseId())
                                .setRelativeTime(byId.getPurchaseDate())
                                .setSourceRepNum(byId.getPurchaseContractNo());

                        xqInventoryMapper.insert(xqInventory);
                    }
                }
            } else {
                XqAccessoryPurchaseInventory byId = this.getById(string);
                if (byId.getAuditStatus().equals("0")) {
                    throw new InterestingBootException("存在审核的辅料采购单，无法修改状态");
                }
                byId.setAuditStatus("0");
                this.updateById(byId);

                List<String> ids = xqAccessoryPurchaseInventoryDetailService.lambdaQuery()
                        .eq(XqAccessoryPurchaseInventoryDetail::getSourceId, string)
                        .list().stream().map(XqAccessoryPurchaseInventoryDetail::getId)
                        .collect(Collectors.toList());

                xqInventoryMapper.physicalDeleteBatch(ids);
            }
        }
        return true;
    }
}




