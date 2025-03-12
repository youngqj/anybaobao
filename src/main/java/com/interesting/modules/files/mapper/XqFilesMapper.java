package com.interesting.modules.files.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.files.dto.ListXqFileDTO;
import com.interesting.modules.files.entity.XqFiles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.files.vo.ListXqFileVO;
import org.apache.ibatis.annotations.Param;

/**
* @author 26773
* @description 针对表【xq_files(附件表)】的数据库操作Mapper
* @createDate 2023-06-21 17:13:29
* @Entity com.interesting.modules.files.entity.XqFiles
*/
public interface XqFilesMapper extends BaseMapper<XqFiles> {

    IPage<ListXqFileVO> pageList(@Param("page") Page<ListXqFileVO> page, @Param("dto") ListXqFileDTO dto);

    IPage<ListXqFileVO> pageFileByNodeId(@Param("page") Page<ListXqFileVO> page,
                                         @Param("nodeId") String nodeId,
                                         @Param("username") String username,
                                         @Param("fileName") String fileName);
}




