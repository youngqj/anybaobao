CREATE TABLE `xq_order_config` (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `credit_insurance_rate` decimal(10,6) DEFAULT '0.000000' COMMENT '中信保费率',
  `factoring_interest_rate` decimal(10,8) DEFAULT '0.000000' COMMENT '保理利息费率',
  `days` int DEFAULT '45' COMMENT '保理天数',
  `create_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `iz_delete` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除 0否 1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='面单配置表';

ALTER TABLE `trade-manage`.`xq_selling_profit`
ADD COLUMN `cut_amount` decimal(11, 2) NULL DEFAULT 0.00 COMMENT '扣款金额' AFTER `total_third_part_import_commission`;

ALTER TABLE `xq_raw_material_purchase`
ADD COLUMN `sdtssj` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收到退税时间' AFTER `tsje`;

ALTER TABLE `xq_freight_cost_info`
ADD COLUMN `warehouse_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仓库id' AFTER `layout_requirements`;

ALTER TABLE `xq_order_extra_cost`
ADD COLUMN `order_detail_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单产品id' AFTER `exchange_rate`;

CREATE TABLE `xq_freight_company` (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `company_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '公司名称',
  `contact_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人名称',
  `contact_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人联系方式',
  `create_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `iz_delete` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除 0否 1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='货代公司';

CREATE TABLE `xq_prepayments` (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `supplier_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '收款工厂id',
  `prepayment_amount` decimal(10, 2) DEFAULT NULL COMMENT '预付金额',
  `currency` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '币种',
  `payment_company_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '付款公司id',
  `payment_date` date DEFAULT NULL COMMENT '付款日期',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `create_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `iz_delete` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除 0否 1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='预付款';

ALTER TABLE `xq_payment_detail`
ADD COLUMN `prepayments_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预付款id' AFTER `type`;

ALTER TABLE `xq_raw_material_purchase`
ADD COLUMN `inspection_note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报检备注' AFTER `shsj3`;

ALTER TABLE `xq_order`
ADD COLUMN `iz_remind_payment` tinyint NULL DEFAULT 1 COMMENT '是否提醒应付款到期日(0 否 1 是)' AFTER `delivery_address_id`;