package com.interesting.modules.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.base.IdAndNameVO;
import com.interesting.modules.order.entity.XqOrderTransferRecord;
import com.interesting.modules.order.vo.ListXqOrderTransferRecordVO;

import java.util.List;

/**
 * 面单管理-转让记录表(XqOrderTransferRecord)表服务接口
 *
 * @author 郭征宇
 * @since 2023-09-15 09:19:48
 */
public interface IXqOrderTransferRecordService extends IService<XqOrderTransferRecord> {


    List<ListXqOrderTransferRecordVO> listTransferRecord(String orderId);

    List<IdAndNameVO> listOrderFollower();
}
