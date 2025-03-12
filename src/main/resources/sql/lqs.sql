ALTER TABLE `xq_raw_material_purchase`
    ADD COLUMN `packaging` varchar(255) NULL COMMENT '包装方式' AFTER `iz_delete`;

ALTER TABLE `xq_order_detail`
    MODIFY COLUMN `packaging` varchar(255) NULL DEFAULT NULL COMMENT '包装方式' AFTER `foreign_tariff`;



--0926新增字段 --已修改
pallets	decimal	  11	2			托盘数
enter_date	datetime	0	0	入库时间

-- 1007新增表 --已修改
xq_credits_insurance

--海外仓库费用表看下 --已修改
--货运的表看下 --已修改

--order_detail里面加上等级

--原料采购里面新加 首款金额 和尾款金额  首款金额手填 尾款金额=采购总价-首款金额

--order_detail 里面加上产品品类

--新建产品品类表



---新建 xq_airport 表√
-- ---新建 xq_workspace_catalog √
-- ---新建 xq_workspace_file √
---xq_freight_cost_info 新加付款金额、付款时间 √
---原料采购表里面新增未付款金额和首付款比例√


-- 新建xq_truck√
-- xq_insurance_expense 新增pay_time 和pay_money√

-- product_info 里面加上 product_category √

---付款金额里面加上一个新的扣款状态  -type xq_payment_detail


--原料采购新增 财务报表导入的字段 gou
--卡车里面新增提货时间提货仓库到货时间到货仓库客户 gou

-- raw里面加上 cutAmount 扣款金额 √

-- xq_order 里面加上 travel_fee √


---xq_freight_cost_info 新加应付金额和已付金额 √


--gnfy_audit	decimal	11	2	-1	0	0	0				国内费用审核
-- gnfy_audit_time	datetime	0	0	-1	0	0	0				国内费用审核时间
-- gwfy_audit	decimal	11	2	-1	0	0	0				国外费用审核
-- gwfy_audit_time	datetime	0	0	-1	0	0	0				国外费用审核时间

-- new_warehouse_lot	varchar	255	0	-1	0	0	0				新仓库lot			utf8	utf8_general_ci

-- 2024-03-01 海外出库表 新增 投保日期
ALTER TABLE xq_overseas_exit_detail ADD COLUMN insurance_effective_date DATE COMMENT '投保日期';

--product_id	varchar	50		产品id
--layout_requirements	varchar	255					版面要求


-- 2024-03-07 新增销售利润表

Create Table

    CREATE TABLE `xq_selling_profit` (
    `id` varchar(32) NOT NULL COMMENT '主键',
    `etd` date DEFAULT NULL COMMENT 'ETD',
    `customer_id` varchar(32) DEFAULT NULL COMMENT '客户id',
    `order_detail_id` varchar(32) DEFAULT NULL COMMENT '订单详情id',
    `customer_name` varchar(255) DEFAULT NULL COMMENT '客户名称',
    `order_num` varchar(32) DEFAULT NULL COMMENT '订单号',
    `product_id` varchar(32) DEFAULT NULL COMMENT '产品id',
    `product_category` varchar(32) DEFAULT NULL COMMENT '产品品类id',
    `category` varchar(32) DEFAULT NULL COMMENT '产品品类名称',
    `product_name` varchar(32) DEFAULT NULL COMMENT '产品名称',
    `collection_additional_fees` decimal(10,2) DEFAULT NULL COMMENT '收汇额外费用',
    `total_boxes` int DEFAULT NULL COMMENT '总箱数（销售数量）',
    `total_weight` decimal(10,2) DEFAULT NULL COMMENT '总重（销售数量）',
    `price_per_box` decimal(10,2) DEFAULT NULL COMMENT '单价/箱（销售单价）',
    `price_per_lb` decimal(10,2) DEFAULT NULL COMMENT '单价/磅（销售单价）',
    `sales_amount` decimal(10,2) DEFAULT NULL COMMENT '销售金额（销售总额）',
    `unit_price` decimal(10,2) DEFAULT NULL COMMENT '采购单价',
    `weight` decimal(10,2) DEFAULT NULL COMMENT '重量（采购数量）',
    `purchase_amount` decimal(10,2) DEFAULT NULL COMMENT '采购金额（采购总价）',
    `customer_commission` decimal(10,2) DEFAULT NULL COMMENT '客户佣金',
    `third_part_import_commission` decimal(10,2) DEFAULT NULL COMMENT '第三方进口佣金',
    `middle_man_commission` decimal(10,2) DEFAULT NULL COMMENT '中间商佣金',
    `quality_deduction` decimal(10,2) DEFAULT NULL COMMENT '质量扣款',
    `discount` decimal(10,2) DEFAULT NULL COMMENT '折扣',
    `service_charge` decimal(10,2) DEFAULT NULL COMMENT '手续费',
    `credit_insurance_premium` decimal(10,2) DEFAULT NULL COMMENT '信保保险费',
    `total_credit_insurance_premium` decimal(10,2) DEFAULT NULL COMMENT '总信保保险费',
    `factoring_interest` decimal(10,2) DEFAULT NULL COMMENT '保理利息',
    `accessory_purchase_total` decimal(10,2) DEFAULT NULL COMMENT '辅料采购金额（辅料合计1）',
    `accessory_purchase_total1` decimal(10,2) DEFAULT NULL COMMENT '辅料采购金额（辅料合计2）',
    `accessory_purchase_total2` decimal(10,2) DEFAULT NULL COMMENT '辅料采购金额（辅料合计3）',
    `total_accessory_purchase_total` decimal(10,2) DEFAULT NULL COMMENT '辅料采购金额（辅料合计1）',
    `total_accessory_purchase_total1` decimal(10,2) DEFAULT NULL COMMENT '辅料采购金额（辅料合计2）',
    `total_accessory_purchase_total2` decimal(10,2) DEFAULT NULL COMMENT '辅料采购金额（辅料合计3）',
    `before_departure_total` decimal(10,2) DEFAULT NULL COMMENT 'CNY出港前总费用）',
    `insurance_fee` decimal(10,2) DEFAULT NULL COMMENT '货物保险费',
    `total_insurance_fee` decimal(10,2) DEFAULT NULL COMMENT '总货物保险费',
    `domestic_shipping_fee` decimal(10,2) DEFAULT NULL COMMENT '国内海运费',
    `customs_clearance_tax_fee` decimal(10,2) DEFAULT NULL COMMENT '清关关税',
    `foreign_customs_clearance_fee` decimal(10,2) DEFAULT NULL COMMENT '国外清关费',
    `foreign_truck_fee` decimal(10,2) DEFAULT NULL COMMENT '国外卡车费',
    `extra_fee` decimal(10,2) DEFAULT NULL COMMENT '额外费用',
    `travel_fee` decimal(10,2) DEFAULT NULL COMMENT '差旅费用',
    `total_travel_fee` decimal(10,2) DEFAULT NULL COMMENT '总差旅费用',
    `tax_refund_amount` decimal(10,2) DEFAULT NULL COMMENT '退税金额',
    `cost_per_lb` decimal(10,2) DEFAULT NULL COMMENT '每磅成本',
    `sell_profit` decimal(10,2) DEFAULT NULL COMMENT '销售利润',
    `lcl_sell_profit` decimal(10,2) DEFAULT NULL COMMENT '拼柜总利润',
    `exchange_rate` decimal(10,2) DEFAULT NULL COMMENT '汇率',
    `all_cost` decimal(10,2) DEFAULT NULL COMMENT '辅料采购金额（辅料合计->除以汇率前）',
    `supplier_id` varchar(32) DEFAULT NULL COMMENT '原料供应商id',
    `supplier_name` varchar(32) DEFAULT NULL COMMENT '原料供应商名称',
    `raw_currency` varchar(32) DEFAULT NULL COMMENT '原料币种',
    `ratio` double DEFAULT NULL COMMENT '比例',
    `record_count` int DEFAULT NULL COMMENT '订单个数',
    `total_middle_man_commission` decimal(10,2) DEFAULT NULL COMMENT '总中间商佣金',
    `total_customer_commission` decimal(10,2) DEFAULT NULL COMMENT '总客户佣金',
    `total_quality_deduction` decimal(10,2) DEFAULT NULL COMMENT '总质量',
    `total_discount` decimal(10,2) DEFAULT NULL COMMENT '总折扣',
    `total_factoring_interest` decimal(10,2) DEFAULT NULL COMMENT '总保理金额',
    `total_domestic_shipping_fee` decimal(10,2) DEFAULT NULL COMMENT '总国内海运费',
    `total_before_departure_total` decimal(10,2) DEFAULT NULL COMMENT '总出港前总费用',
    `total_service_charge` decimal(10,2) DEFAULT NULL COMMENT '总服务费',
    `total_customs_clearance_tax_fee` decimal(10,2) DEFAULT NULL COMMENT '总清关关税',
    `total_foreign_customs_clearance_fee` decimal(10,2) DEFAULT NULL COMMENT '总国外清关费',
    `total_extra_fee` decimal(10,2) DEFAULT NULL COMMENT '总额外费用',
    `total_collection_additional_fees` decimal(10,2) DEFAULT NULL COMMENT '总收汇额外费用',
    `total_foreign_truck_fee` decimal(10,2) DEFAULT NULL COMMENT '国外卡车费',
    `return_fee` decimal(10,2) DEFAULT NULL COMMENT '退运费',
    `total_third_part_import_commission` decimal(10,2) DEFAULT NULL COMMENT '总第三方进口佣金',
    `create_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新人',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `iz_delete` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除 0否 1是',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='销售利润表'



-- 辅料库存表新增 unit_price 和 currency


-- 2024-03-13 仓库费用表
CREATE TABLE xq_warehouse_expenses (
       id VARCHAR(32) NOT NULL COMMENT '主键id',
       warehouse_id VARCHAR(32) COMMENT '仓库ID',
       order_num VARCHAR(255) COMMENT '订单号',
       product_name VARCHAR(255) COMMENT '产品名称',
       invoice_num VARCHAR(32) COMMENT '发票号',
       expense DECIMAL(10,2) COMMENT '费用USD',
       invoice_date DATE COMMENT '发票日期',
       remark VARCHAR(255) COMMENT '备注1',
       iz_settle INT DEFAULT '0' COMMENT '状态：0 未结清  1 结清',
       payment_amount DECIMAL(10,2) COMMENT '付款金额',
       remark2 VARCHAR(255) COMMENT '备注2',
       audit_amount DECIMAL(10,2) COMMENT '财务审核金额',
       audit_date DATE COMMENT '财务审核日期',
       due_date DATE COMMENT '到期付款日',
       payment_date DATE COMMENT '实际付款日',
       create_by VARCHAR(32) COMMENT '创建人',
       create_time DATETIME COMMENT '创建时间',
       update_by VARCHAR(32) COMMENT '更新人',
       update_time DATETIME COMMENT '更新时间',
       iz_delete INT DEFAULT '0' COMMENT '逻辑删除 0否 1是',
       PRIMARY KEY (id)
) COMMENT='仓库费用表';


-- 原料采购表新增采购总价CNY 2024-03-21
ALTER TABLE xq_raw_material_purchase ADD COLUMN purchase_amount_cny decimal(10,2) default '0.00' COMMENT '采购总价CNY';


-- 发货单产品与面单产品不一致 将指定发货单商品id刷成面单的产品id 2024-03-22  订单号 230419
UPDATE
    xq_overseas_exit_detail oed

    LEFT JOIN xq_order xo ON (xo.`order_num` = oed.`source_rep_num` AND xo.`iz_delete` = 0)
    LEFT JOIN xq_order_detail xod ON (xod.`order_id` = xo.`id`  AND xod.iz_delete = 0)

    SET oed.`product_id` = xod.`product_id`
WHERE oed.iz_delete = 0
  AND oed.`source_rep_num` IS NOT NULL
  AND oed.product_id != xod.product_id



---------
-- 根据地址把卡车费用为零数据，修改为客户维护的默认值
-- UPDATE xq_freight_cost_info fci

-- 根据地址查询卡车费用为零并将客户有维护默认值的数据

SELECT o.order_num FROM  xq_freight_cost_info fci

LEFT JOIN xq_order o ON o.`id` = fci.`order_id` AND fci.`customer_id` IS NULL AND fci.`fee_type` LIKE 'gwkcf%' AND fci.`iz_delete` = 0

INNER JOIN

(SELECT
  o.id  orderId,
  fci.*
FROM xq_freight_cost_info fci
       LEFT JOIN xq_order o  ON fci.`customer_id` = o.`customer_id` AND  fci.`fee_type` LIKE 'gwkcf%' AND fci.`iz_delete` = 0 AND o.`delivery_address` = fci.`address`

WHERE o.`iz_delete` = 0
AND fci.`order_id` IS NULL
AND fci.price > 0  ) t ON t.orderId = o.id

-- SET fci.price = t.price,fci.iz_default_color = 0
WHERE o.`iz_delete` = 0 AND o.`order_type` = 2
  AND fci.`price` = 0


-- 客户订单刷默认值

--  SELECT o.order_num FROM  xq_freight_cost_info fci
UPDATE xq_freight_cost_info fci


    LEFT JOIN xq_order o ON o.`id` = fci.`order_id` AND fci.`customer_id` IS NULL AND fci.`fee_type` = 'gwqgf' AND fci.`iz_delete` = 0

    LEFT JOIN
    (
    SELECT
    o.id  orderId,
    fci.*
    FROM xq_freight_cost_info fci
    LEFT JOIN xq_order o  ON fci.`customer_id` = o.`customer_id`
    AND  fci.`fee_type` = 'gwqgf' AND fci.`iz_delete` = 0

    WHERE o.`iz_delete` = 0
    AND fci.`order_id` IS NULL
    ) gwqgf ON gwqgf.orderId = o.id

    SET fci.price = gwqgf.price,fci.iz_default_color = 0
WHERE o.`iz_delete` = 0 AND o.`order_type` = 2
  AND fci.`price` = 0
  AND gwqgf.price > 0


-- 刷客户订单默认清关关费

UPDATE xq_freight_cost_info fci


    LEFT JOIN xq_order o ON o.`id` = fci.`order_id` AND fci.`customer_id` IS NULL AND fci.`fee_type` = 'qggs' AND fci.`iz_delete` = 0

    LEFT JOIN
    (
    SELECT
    o.id  orderId,
    fci.*
    FROM xq_freight_cost_info fci
    LEFT JOIN xq_order o  ON fci.`customer_id` = o.`customer_id`
    AND  fci.`fee_type` = 'qggs' AND fci.`iz_delete` = 0

    WHERE o.`iz_delete` = 0
    AND fci.`order_id` IS NULL
    ) qggs ON qggs.orderId = o.id

    SET fci.price = qggs.price,fci.iz_default_color = 0
WHERE o.`iz_delete` = 0 AND o.`order_type` = 2
  AND fci.`price` = 0
  AND qggs.price > 0


--  刷客户订单额外费用
UPDATE xq_freight_cost_info fci


    LEFT JOIN xq_order o ON o.`id` = fci.`order_id` AND fci.`customer_id` IS NULL AND fci.`fee_type` = 'ewfy1' AND fci.`iz_delete` = 0
    LEFT JOIN
    (
    SELECT
    o.id  orderId,
    fci.*
    FROM xq_freight_cost_info fci
    LEFT JOIN xq_order o  ON fci.`customer_id` = o.`customer_id`
    AND  fci.`fee_type` = 'ewfy1' AND fci.`iz_delete` = 0

    WHERE o.`iz_delete` = 0
    AND fci.`order_id` IS NULL
    ) ewfy ON ewfy.orderId = o.id


    SET fci.price = ewfy.price,fci.iz_default_color = 0
WHERE o.`iz_delete` = 0 AND o.`order_type` = 2
  AND fci.`price` = 0
  AND ewfy.price > 0




-- 2024-03-28 海外仓库仓储费用表 新增分类挑选费
ALTER TABLE xq_overseas_warehouse_fee ADD COLUMN sorting_fee decimal(10,2) default '0' COMMENT '分类挑选费';

ALTER TABLE xq_overseas_enter_detail ADD COLUMN pallet_num INT DEFAULT '1' COMMENT '托盘数量';

-- 2024-04-09 额外费用新增客户订单号
ALTER TABLE xq_order_extra_cost ADD COLUMN customer_order_num VARCHAR(255) COMMENT '客户订单号';
-- 原料采购表 新增预扣款金额
ALTER TABLE xq_raw_material_purchase ADD COLUMN withhold_amount DECIMAL(10,2) DEFAULT '0' COMMENT '预扣金额';
-- 新增预扣款明细
CREATE TABLE `xq_withhold_detail` (
    `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
    `source_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '来源记录id',
    `money` decimal(10,2) DEFAULT '0.00' COMMENT '扣款金额',
    `withhold_time` datetime DEFAULT NULL COMMENT '扣款日期',
    `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
    `create_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新人',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `iz_delete` tinyint DEFAULT '0' COMMENT '逻辑删除 0否 1是',
    `currency` varchar(10) DEFAULT '0' COMMENT '币种'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='预扣款明细'


-- 2024-04-12 仓储费用明细 新增仓库ID
ALTER TABLE `xq_warehouse_cost_detail` ADD COLUMN warehouse_id VARCHAR(36) COMMENT '仓库id'
-- 仓库费用表 新增申请时间
ALTER TABLE `xq_warehouse_expenses` ADD COLUMN apply_for_date DATE COMMENT '申请时间'

-- 2024-04-23 额外费用新增 税率
ALTER TABLE `xq_order_extra_cost` ADD COLUMN exchange_rate decimal(10,4)  COMMENT '税率'


-- 2024-05-11 新增字段
ALTER TABLE xq_raw_material_purchase ADD COLUMN shje2 DECIMAL(10,2) DEFAULT '0' COMMENT '二次收汇金额';
ALTER TABLE xq_raw_material_purchase ADD COLUMN shje3 DECIMAL(10,2) DEFAULT '0' COMMENT '末次收汇金额';
ALTER TABLE xq_raw_material_purchase ADD COLUMN shsj2 VARCHAR(255)  COMMENT '二次收汇时间';
ALTER TABLE xq_raw_material_purchase ADD COLUMN shsj3 VARCHAR(255)  COMMENT '末次收汇时间';

ALTER TABLE xq_freight_info ADD COLUMN return_ship_company VARCHAR(255)  COMMENT '退货船公司';
ALTER TABLE xq_freight_info ADD COLUMN return_voyage_number VARCHAR(255)  COMMENT '退货船名航次';
ALTER TABLE xq_freight_info ADD COLUMN return_bill_of_lading VARCHAR(255)  COMMENT '退货提单号码';
ALTER TABLE xq_freight_info ADD COLUMN return_container_no VARCHAR(255)  COMMENT '退货货柜号码';
ALTER TABLE xq_freight_info ADD COLUMN return_loading_port VARCHAR(255)  COMMENT '退货装运港';
ALTER TABLE xq_freight_info ADD COLUMN return_destination_port VARCHAR(255)  COMMENT '退货目的港';
ALTER TABLE xq_freight_info ADD COLUMN return_net_weight DECIMAL(10,2)  DEFAULT '0' COMMENT '退货净重(KGS）';
ALTER TABLE xq_freight_info ADD COLUMN return_gross_weight VARCHAR(255)  COMMENT '退货毛重(KGS)';
ALTER TABLE xq_freight_info ADD COLUMN return_box_num int DEFAULT '0' COMMENT '退货箱数';

-- 2024-08-28 空运新增字段
ALTER TABLE `xq_airport` ADD COLUMN qghd2 VARCHAR(255)  COMMENT '清关货代2';
ALTER TABLE `xq_airport` ADD COLUMN gwfy2 DECIMAL(11,2) DEFAULT '0.00' COMMENT '国外费用2';
ALTER TABLE `xq_airport` ADD COLUMN gwfy_apply_time2 DATETIME  COMMENT '国外费用申请日期2';
ALTER TABLE `xq_airport` ADD COLUMN gwhkje2 DECIMAL(11,2) DEFAULT '0.00' COMMENT '国外汇款金额2';
ALTER TABLE `xq_airport` ADD COLUMN gwsjfkr2 DATETIME  COMMENT '国外实际汇款日2';
ALTER TABLE `xq_airport` ADD COLUMN gwfy_audit2 DECIMAL(11,2) DEFAULT '0.00' COMMENT '国外财务审核金额2';
ALTER TABLE `xq_airport` ADD COLUMN gwfy_audit_time2 DATETIME  COMMENT '国外财务审核日期2';