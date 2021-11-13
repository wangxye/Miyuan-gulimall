package com.wxy.gulimall.product.service.impl;

import com.wxy.gulimall.product.dao.BrandDao;
import com.wxy.gulimall.product.dao.CategoryBrandRelationDao;
import com.wxy.gulimall.product.dao.CategoryDao;
import com.wxy.gulimall.product.entity.BrandEntity;
import com.wxy.gulimall.product.entity.CategoryBrandRelationEntity;
import com.wxy.gulimall.product.entity.CategoryEntity;
import com.wxy.gulimall.product.service.BrandService;
import com.wxy.gulimall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxy.common.utils.PageUtils;
import com.wxy.common.utils.Query;

import javax.annotation.Resource;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Resource
    private BrandDao brandDao;

    @Resource
    private CategoryDao categoryDao;

    @Autowired
    private CategoryBrandRelationDao relationDao;

    @Autowired
    private BrandService brandService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCatelogId();

        //1、查询品牌详细信息
        BrandEntity brandEntity = brandDao.selectById(brandId);
        //2、查询分类详细信息
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        //将信息保存在categoryBrandRelation中
        categoryBrandRelation.setBrandName(brandEntity.getName());
        categoryBrandRelation.setCatelogName(categoryEntity.getName());

        //保存数据库
        this.baseMapper.insert(categoryBrandRelation);
    }

    @Override
    public List<BrandEntity> getBrandsByCatId(Long catId) {

        List<CategoryBrandRelationEntity> catelogId = relationDao.selectList(new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));

        List<BrandEntity> collect = catelogId.stream().map(item -> {
            Long brandId = item.getBrandId();
            BrandEntity byId = brandService.getById(brandId);
            return byId;
        }).collect(Collectors.toList());


        return collect;
    }

}