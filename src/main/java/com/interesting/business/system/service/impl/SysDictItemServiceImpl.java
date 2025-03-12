package com.interesting.business.system.service.impl;

import com.interesting.business.system.entity.SysDictItem;
import com.interesting.business.system.mapper.SysDictItemMapper;
import com.interesting.business.system.service.ISysDictItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @Author Gcc
 * @since 2018-12-28
 */
@Service
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements ISysDictItemService {

    @Autowired
    private SysDictItemMapper sysDictItemMapper;

    @Override
    public List<SysDictItem> selectItemsByMainId(String mainId) {
        return sysDictItemMapper.selectItemsByMainId(mainId);
    }

    @Override
    public List<SysDictItem> selectItemsByMainCode(String code) {
        return sysDictItemMapper.selectItemsByMainCode(code);
    }

    @Override
    public Map<String, SysDictItem> selectMapItemsNameByMainCode(String item1) {
        return sysDictItemMapper.selectMapItemsByMainCode(item1);
    }
}
