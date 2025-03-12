package com.interesting.common.job;

import com.interesting.modules.overseas.entity.XqOverseasEnterDetail;
import com.interesting.modules.overseas.entity.XqOverseasExitDetail;
import com.interesting.modules.overseas.entity.XqOverseasWarehouseFee;
import com.interesting.modules.overseas.service.XqOverseasEnterDetailService;
import com.interesting.modules.overseas.service.XqOverseasExitDetailService;
import com.interesting.modules.overseas.service.XqOverseasWarehouseFeeService;
import com.interesting.modules.warehouse.dto.InstOrUpdtXqWarehouseDTO;
import com.interesting.modules.warehouse.entity.XqWarehouseCostDetail;
import com.interesting.modules.warehouse.service.XqWarehouseCostDetailService;
import com.interesting.modules.warehouse.service.XqWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class CreditsSignInQuartz {

    @Autowired
    private XqOverseasEnterDetailService xqOverseasEnterDetailService;
    @Autowired
    private XqOverseasExitDetailService xqOverseasExitDetailService;
    @Autowired
    private XqOverseasWarehouseFeeService xqOverseasWarehouseFeeService;
    @Autowired
    private XqWarehouseCostDetailService xqWarehouseCostDetailService;




    @PostConstruct
    //3.添加定时任务
    @Scheduled(cron = "0 1 0 * * ?")
    //或直接指定时间间隔每天凌晨0点执行
    private void configureTasks() {
        // 获取所有的入库单
        List<XqOverseasEnterDetail> list = xqOverseasEnterDetailService.list();
        if (list == null || list.size() == 0) {
            return;
        }

        // 获取每个仓库收费规则
        Map<String, XqOverseasWarehouseFee> feeMap = xqOverseasWarehouseFeeService.list()
                .stream()
                .collect(Collectors.toMap(XqOverseasWarehouseFee::getWarehouseId, p -> p));


        List<XqWarehouseCostDetail> saveList = new ArrayList<>();

        for (XqOverseasEnterDetail detail : list) {
            XqOverseasWarehouseFee warehouseFee = feeMap.get(detail.getWarehouseId());
            if (warehouseFee != null) {
                XqWarehouseCostDetail one = xqWarehouseCostDetailService.lambdaQuery()
                        .eq(XqWarehouseCostDetail::getWarehouseLot, detail.getWarehouseLot())
                        .eq(XqWarehouseCostDetail::getWarehouseId, detail.getWarehouseId())
                        .eq(XqWarehouseCostDetail::getMonths, 1)
                        .last("limit 1")
                        .one();
                if (one == null) {
                    // 计算第一个月费用
                    XqWarehouseCostDetail costDetail = firstMonthFee(detail, warehouseFee);
                    saveList.add(costDetail);
                }


                // 将入库时间Date对象转换为LocalDate对象
                LocalDate ruKuDate = detail.getCreateTime().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();


                LocalDate currentDate = LocalDate.now();
                // 计算第二个月费用
                if (ruKuDate.plusMonths(1).isBefore(currentDate)) {
                    XqWarehouseCostDetail two = xqWarehouseCostDetailService.lambdaQuery()
                            .eq(XqWarehouseCostDetail::getWarehouseLot, detail.getWarehouseLot())
                            .eq(XqWarehouseCostDetail::getWarehouseId, detail.getWarehouseId())
                            .eq(XqWarehouseCostDetail::getMonths, 2)
                            .last("limit 1")
                            .one();
                    if (two == null) {

                        XqWarehouseCostDetail cost = handle(1, detail, warehouseFee.getSecondMonthFee());
                        if (cost != null) {
                            saveList.add(cost);
                        }
                    }

                } else {
                    continue;
                }

                // 计算第三个月费用

                if (ruKuDate.plusMonths(2).isBefore(currentDate)) {
                    XqWarehouseCostDetail three = xqWarehouseCostDetailService.lambdaQuery()
                            .eq(XqWarehouseCostDetail::getWarehouseLot, detail.getWarehouseLot())
                            .eq(XqWarehouseCostDetail::getWarehouseId, detail.getWarehouseId())
                            .eq(XqWarehouseCostDetail::getMonths, 3)
                            .last("limit 1")
                            .one();
                    if (three == null) {
                        XqWarehouseCostDetail cost = handle(2, detail, warehouseFee.getThirdMonthFee());
                        if (cost != null) {
                            saveList.add(cost);
                        }

                    }

                } else {
                    continue;
                }
                // 计算第四个月费用

                if (ruKuDate.plusMonths(3).isBefore(currentDate)) {

                    XqWarehouseCostDetail four = xqWarehouseCostDetailService.lambdaQuery()
                            .eq(XqWarehouseCostDetail::getWarehouseLot, detail.getWarehouseLot())
                            .eq(XqWarehouseCostDetail::getWarehouseId, detail.getWarehouseId())
                            .eq(XqWarehouseCostDetail::getMonths, 4)
                            .last("limit 1")
                            .one();
                    if (four == null) {
                        XqWarehouseCostDetail cost = handle(3, detail, warehouseFee.getFourthMonthFee());
                        if (cost != null) {
                            saveList.add(cost);
                        }

                    }

                } else {
                    continue;
                }
                // 计算第五个月费用
                if (ruKuDate.plusMonths(4).isBefore(currentDate)) {

                    XqWarehouseCostDetail five = xqWarehouseCostDetailService.lambdaQuery()
                            .eq(XqWarehouseCostDetail::getWarehouseLot, detail.getWarehouseLot())
                            .eq(XqWarehouseCostDetail::getWarehouseId, detail.getWarehouseId())
                            .eq(XqWarehouseCostDetail::getMonths, 5)
                            .last("limit 1")
                            .one();

                    if (five == null) {
                        XqWarehouseCostDetail cost = handle(4, detail, warehouseFee.getFifthMonthFee());
                        if (cost != null) {
                            saveList.add(cost);
                        }

                    }

                    ruKuDate = ruKuDate.plusMonths(4);
                } else {
                    continue;
                }

                // 五月之后按照比较计算仓储费用
                int num = 5;

                while (ruKuDate.plusMonths(1).isBefore(currentDate)) {

                    XqWarehouseCostDetail other = xqWarehouseCostDetailService.lambdaQuery()
                            .eq(XqWarehouseCostDetail::getWarehouseLot, detail.getWarehouseLot())
                            .eq(XqWarehouseCostDetail::getWarehouseId, detail.getWarehouseId())
                            .eq(XqWarehouseCostDetail::getMonths, num + 1)
                            .last("limit 1")
                            .one();

                    if (other == null) {
                        XqWarehouseCostDetail cost = handle(num, detail,
                                warehouseFee.getFifthMonthFee().multiply(BigDecimal.ONE
                                        .add(warehouseFee.getRate().multiply(new BigDecimal(num - 4)).divide(new BigDecimal(100)))));
                        if (cost != null) {
                            saveList.add(cost);
                        }
                    }

                    num += 1;
                    ruKuDate = ruKuDate.plusMonths(1);
                }


            }


        }

        if (saveList.size() > 0) {
            xqWarehouseCostDetailService.saveBatch(saveList);
        }
    }


    /**
     * 计算月仓储费
     *
     * @param num          第几月
     * @param detail       入库明细
     * @param warehouseFee 当月费用比例
     * @return
     */
    public XqWarehouseCostDetail handle(int num, XqOverseasEnterDetail detail, BigDecimal warehouseFee) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(detail.getCreateTime());
        // 入库时间加M个月
        calendar.add(Calendar.MONTH, num);
        Date newDate = calendar.getTime();

        // 查询入库后N个月内出库数量
        List<XqOverseasExitDetail> exitDetails = xqOverseasExitDetailService.lambdaQuery()
                .eq(XqOverseasExitDetail::getWarehouseLot, detail.getWarehouseLot())
                .le(XqOverseasExitDetail::getCreateTime, newDate).list();

        // 查询出库箱数
        Integer exitNum = 0;
        for (XqOverseasExitDetail exitDetail : exitDetails) {
            exitNum += exitDetail.getExitNum();
        }

        // 剩余毛重
        BigDecimal grossWeight = detail.getGrossWeightPerBox().multiply(new BigDecimal(detail.getEnterNum() - exitNum));

        if (grossWeight.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal p = grossWeight.multiply(warehouseFee).divide(new BigDecimal(100));
            XqWarehouseCostDetail cost = new XqWarehouseCostDetail();
            cost.setExpense(p);
            cost.setMonths(num + 1);
            cost.setWarehouseLot(detail.getWarehouseLot());
            cost.setWarehouseId(detail.getWarehouseId());
            return cost;
        }
        return null;
    }


    /**
     * 计算第一个月仓储费
     *
     * @param detail       入库明细
     * @param warehouseFee 仓库费用
     * @return
     */
    public XqWarehouseCostDetail firstMonthFee(XqOverseasEnterDetail detail, XqOverseasWarehouseFee warehouseFee) {
        // 第一笔入库费：（托盘数*（缠绕膜费用+托盘A费用+托盘B费用））+（（搬箱费+操作费+分类挑选费）*总毛重/100） + （第一个月维护的单价乘以总毛重再除以/100）

        // 缠绕膜费用+托盘A费用+托盘B费用
        BigDecimal p1 = warehouseFee.getShrinkWrap().add(warehouseFee.getGradeAPallets()).add(warehouseFee.getGradeBPallets());
        // 托盘数 *（缠绕膜费用+托盘A费用+托盘B费用）
        BigDecimal p2 = p1.multiply(new BigDecimal(detail.getPalletNum()));

        // 搬箱费+操作费+分类挑选费
        BigDecimal pA = warehouseFee.getCarryBoxFee().add(warehouseFee.getDeliveryOperationFee()).add(warehouseFee.getSortingFee());
        // (搬箱费+操作费+分类挑选费)*总毛重/100
        BigDecimal pB = pA.multiply(detail.getGrossWeightTotal()).divide(new BigDecimal(100));
        // 托盘数*（缠绕膜费用+托盘A费用）+ (搬箱费+操作费+分类挑选费)*总毛重/100
        BigDecimal p3 = p2.add(pB);

        // 总毛重 * 第一个月维护的单价乘以总毛重再除以/100
        BigDecimal p4 = detail.getGrossWeightTotal().multiply(warehouseFee.getFirstMonthFee()).divide(new BigDecimal(100));
        BigDecimal oneFee = p3.add(p4);
        XqWarehouseCostDetail costDetail = new XqWarehouseCostDetail();
        costDetail.setExpense(oneFee);
        costDetail.setMonths(1);
        costDetail.setWarehouseLot(detail.getWarehouseLot());
        costDetail.setWarehouseId(detail.getWarehouseId());
        return costDetail;
    }

}