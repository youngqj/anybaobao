package com.interesting.modules.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ListPageUtil {

    //适用于 PageInfo
    //  //1、开启分页
    //        PageHelper.startPage(currentPage,pageSize);
    //        List<User> userList = userMapper.findAll();
    //        //2、封装list到 PageInfo对象中自动分页
    //        PageInfo<User> userPageInfo = new PageInfo<>(userList);
    //        return userPageInfo;


    // 处理List 数据的分页 不对数据库进行操作
    public static PageInfo listPageFor(Integer pageNo, Integer pageSize, List list) {
        // 处理list的分页 而不是对查询进行分页减少数据库负担
        //创建Page类
        Page page = new Page(pageNo, pageSize);
        //为Page类中的total属性赋值
        int total = list.size();
        page.setTotal(total);
        //计算当前需要显示的数据下标起始值
        int startIndex = (pageNo - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, total);
        //从链表中截取需要显示的子链表，并加入到Page
        page.addAll(list.subList(startIndex, endIndex)); // 这里不是截长度而是 划分区间
        //以Page创建PageInfo
        PageInfo pageInfo = new PageInfo<>(page);
        //将数据传回前端
        return pageInfo;
    }

    // 处理Map 数据的分页 不对数据库进行操作
    public static PageInfo listPageFor(Integer pageNo, Integer pageSize, Map map) {
        // 处理list的分页 而不是对查询进行分页减少数据库负担
        //创建Page类
        Page page = new Page(pageNo, pageSize);
        //为Page类中的total属性赋值
        int total = map.size();
        page.setTotal(total);
        //计算当前需要显示的数据下标起始值
        int startIndex = (pageNo - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, total);
        //从链表中截取需要显示的子链表，并加入到Page
        page.addAll(mapTransitionList(map).subList(startIndex, endIndex)); // 这里不是截长度而是 划分区间
        //以Page创建PageInfo
        PageInfo pageInfo = new PageInfo<>(page);
        //将数据传回前端
        return pageInfo;
    }

    //map转list
    public static List mapTransitionList(Map map) {
        List list1 = new ArrayList();
        Iterator iter = map.entrySet().iterator(); // 获得map的Iterator
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            /*list1.add(entry.getKey());*/
            list1.add(entry.getValue());
        }
        return list1;
    }
}
