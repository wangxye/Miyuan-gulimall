package com.wxy.gulimall.product.service.impl;

import com.wxy.gulimall.product.dao.CategoryDao;
import com.wxy.gulimall.product.entity.CategoryEntity;
import com.wxy.gulimall.product.service.CategoryService;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxy.common.utils.PageUtils;
import com.wxy.common.utils.Query;

import static java.awt.SystemColor.menu;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //1、查询所有分类
        List<CategoryEntity>  entities = baseMapper.selectList(null);
        //2、组装父子树形结构
        List<CategoryEntity> levelMenus = entities.stream().filter(e -> e.getParentCid() == 0).map(memu -> {
            memu.setChildren(getChildrens(memu, entities));
            return memu;
        }).sorted((menu, menu2) -> {
            return (menu.getSort() == null ? 0 : menu.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return levelMenus;
    }

    /**
     * 递归查找所有菜单的子菜单
     * @param menu
     * @param entities
     * @return
     */
    private List<CategoryEntity> getChildrens(CategoryEntity menu, List<CategoryEntity> entities) {
        List<CategoryEntity> children = entities.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid().equals(menu.getCatId());
        }).map(categoryEntity -> {
            //1、递归查找子菜单
            categoryEntity.setChildren(getChildrens(categoryEntity, entities));
            return categoryEntity;
        }).sorted((menu1,menu2) -> {
            //2、菜单的排序
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return children;
    }

    @Override
    public Long[] findCatelogPath(Long catelogId){
        List<Long> paths = new ArrayList<>();

        List<Long> parentPath = findParentPath(catelogId,paths);

        Collections.reverse(parentPath);

        return parentPath.toArray(new Long[parentPath.size()]);
    }

    private List<Long> findParentPath(Long catelogId, List<Long> paths) {
        paths.add(catelogId);

        CategoryEntity byId = this.getById(catelogId);

        if(byId.getParentCid()!=0){
            findParentPath(byId.getParentCid(),paths);
        }

        return paths;
    }


}