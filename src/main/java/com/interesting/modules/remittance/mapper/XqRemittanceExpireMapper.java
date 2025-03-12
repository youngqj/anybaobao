package com.interesting.modules.remittance.mapper;

import com.interesting.modules.remittance.entity.XqRemittanceExpire;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.remittance.vo.QueryDataNotInDetailVO;

import java.util.List;

/**
* @author 26773
* @description 针对表【xq_remittance_expire】的数据库操作Mapper
* @createDate 2023-08-15 15:37:10
* @Entity com.interesting.modules.remittance.entity.XqRemittanceExpire
*/
public interface XqRemittanceExpireMapper extends BaseMapper<XqRemittanceExpire> {

    List<QueryDataNotInDetailVO> queryDataNotInDetail();

}




