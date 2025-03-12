package com.interesting.modules.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.order.entity.XqOrderTransferRecord;
import com.interesting.modules.order.vo.ListXqOrderTransferRecordVO;

import java.util.List;

/**
 * 面单管理-转让记录表(XqOrderTransferRecord)表数据库访问层
 *
 * @author 郭征宇
 * @since 2023-09-15 09:19:48
 */
public interface XqOrderTransferRecordMapper extends BaseMapper<XqOrderTransferRecord> {


    List<ListXqOrderTransferRecordVO> listTransferRecord(String orderId);


}

