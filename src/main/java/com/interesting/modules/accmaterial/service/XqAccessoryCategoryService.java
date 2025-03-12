package com.interesting.modules.accmaterial.service;

import com.alibaba.fastjson.JSONObject;
import com.interesting.business.system.model.TreeSelectModel;
import com.interesting.modules.accmaterial.dto.AddUptAccCategoryDTO;
import com.interesting.modules.accmaterial.dto.InstUptCategoryTreeNodeDTO;
import com.interesting.modules.accmaterial.entity.XqAccessoryCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 26773
* @description 针对表【xq_accessory_category(辅料分类表)】的数据库操作Service
* @createDate 2023-07-03 13:48:00
*/
public interface XqAccessoryCategoryService extends IService<XqAccessoryCategory> {

    boolean deleteBatchAccCategory(String ids);

    boolean addAccCategory(AddUptAccCategoryDTO dto);

    List<JSONObject> listAccByCategoryId(String id);

    boolean editAccCategory(AddUptAccCategoryDTO dto);

    List<TreeSelectModel> queryAccCategoryTree(String pid);

    List<TreeSelectModel> queryListByPid(String pid);

    void deleteAccCategoryTreeNode(String id);

    void addAccCategoryTreeNode(InstUptCategoryTreeNodeDTO dto);

    void editAccCategoryTreeNode(InstUptCategoryTreeNodeDTO dto);
}
