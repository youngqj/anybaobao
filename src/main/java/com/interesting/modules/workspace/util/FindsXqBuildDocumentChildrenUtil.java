package com.interesting.modules.workspace.util;

import com.interesting.common.util.oConvertUtils;
import com.interesting.modules.workspace.entity.XqWorkspaceCatalog;
import com.interesting.modules.workspace.model.XqWorkspaceFileIdModel;
import com.interesting.modules.workspace.model.XqWorkspaceFileTreeModel;

import java.util.ArrayList;
import java.util.List;

public class FindsXqBuildDocumentChildrenUtil {
    /**
     * queryTreeList的子方法 ====1=====
     * 该方法是将类型的list集合转换成XqWarehouseTypeTreeModel类型的集合
     */
    public static List<XqWorkspaceFileTreeModel> wrapTreeDataToTreeList(List<XqWorkspaceCatalog> recordList) {
        // 在该方法每请求一次,都要对全局list集合进行一次清理
        //idList.clear();
        List<XqWorkspaceFileIdModel> idList = new ArrayList<>();
        List<XqWorkspaceFileTreeModel> records = new ArrayList<>();
        for (XqWorkspaceCatalog xqBuildDocument : recordList) {
            records.add(new XqWorkspaceFileTreeModel(xqBuildDocument));
        }
        List<XqWorkspaceFileTreeModel> tree = findChildren(records, idList);
        setEmptyChildrenAsNull(tree);
        return tree;
    }

//    /**
//     * 获取 DepartIdModel
//     * @param recordList
//     * @return
//     */
//    public static List<DepartIdModel> wrapTreeDataToDepartIdTreeList(List<SysDepart> recordList) {
//        // 在该方法每请求一次,都要对全局list集合进行一次清理
//        //idList.clear();
//        List<DepartIdModel> idList = new ArrayList<DepartIdModel>();
//        List<SysDepartTreeModel> records = new ArrayList<>();
//        for (int i = 0; i < recordList.size(); i++) {
//            SysDepart depart = recordList.get(i);
//            records.add(new SysDepartTreeModel(depart));
//        }
//        findChildren(records, idList);
//        return idList;
//    }

    /**
     * queryTreeList的子方法 ====2=====
     * 该方法是找到并封装顶级父类的节点到TreeList集合
     */
    private static List<XqWorkspaceFileTreeModel> findChildren(List<XqWorkspaceFileTreeModel> recordList,
                                                               List<XqWorkspaceFileIdModel> xqWorkspaceFileIdModels) {

        List<XqWorkspaceFileTreeModel> treeList = new ArrayList<>();
        for (XqWorkspaceFileTreeModel branch : recordList) {
            if (oConvertUtils.isEmpty(branch.getPId()) || branch.getPId().equals("0")) {
                treeList.add(branch);
                XqWorkspaceFileIdModel xqBuildDocumentIdModel = new XqWorkspaceFileIdModel().convert(branch);
                xqWorkspaceFileIdModels.add(xqBuildDocumentIdModel);
            }
        }
        getGrandChildren(treeList, recordList, xqWorkspaceFileIdModels);

        //idList = departIdList;
        return treeList;
    }

    /**
     * queryTreeList的子方法====3====
     * 该方法是找到顶级父类下的所有子节点集合并封装到TreeList集合
     */
    private static void getGrandChildren(List<XqWorkspaceFileTreeModel> treeList, List<XqWorkspaceFileTreeModel> recordList, List<XqWorkspaceFileIdModel> idList) {

        for (int i = 0; i < treeList.size(); i++) {
            XqWorkspaceFileTreeModel model = treeList.get(i);
            XqWorkspaceFileIdModel idModel = idList.get(i);
            for (XqWorkspaceFileTreeModel m : recordList) {
                if (m.getPId() != null && m.getPId().equals(model.getId())) {
                    model.getChildren().add(m);
                    XqWorkspaceFileIdModel dim = new XqWorkspaceFileIdModel().convert(m);
                    idModel.getChildren().add(dim);
                }
            }
            getGrandChildren(treeList.get(i).getChildren(), recordList, idList.get(i).getChildren());
        }

    }


    /**
     * queryTreeList的子方法 ====4====
     * 该方法是将子节点为空的List集合设置为Null值
     */
    private static void setEmptyChildrenAsNull(List<XqWorkspaceFileTreeModel> treeList) {

        for (XqWorkspaceFileTreeModel model : treeList) {
            if (model.getChildren().size() == 0) {
                model.setChildren(null);
                model.setIsLeaf(true);
            } else {
                setEmptyChildrenAsNull(model.getChildren());
                model.setIsLeaf(false);
            }
        }
        // sysDepartTreeList = treeList;
    }
}
