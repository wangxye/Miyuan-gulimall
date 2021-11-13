package com.wxy.gulimall.product.service.impl;

import com.wxy.common.constant.ProductConstant;
import com.wxy.gulimall.product.dao.SkuInfoDao;
import com.wxy.gulimall.product.entity.SkuInfoEntity;
import com.wxy.gulimall.product.entity.SpuInfoEntity;
import com.wxy.gulimall.product.fegin.SearchFeignService;
import com.wxy.gulimall.product.service.SpuInfoService;
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

import com.wxy.gulimall.product.service.SkuInfoService;

import javax.annotation.Resource;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Resource
    private SpuInfoService spuInfoService;

    @Autowired
    private SearchFeignService searchFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.baseMapper.insert(skuInfoEntity);
    }

    @Override
    public List<SkuInfoEntity> getSKusBySpuId(Long spuId) {
        List<SkuInfoEntity> skuInfoEntities = this.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));

        return skuInfoEntities;
    }

    @Override
    public void removeSKU(List<Long> asList) {
        if (asList!=null||asList.size()>0){
            List<SkuInfoEntity> skuInfoEntities = this.baseMapper.selectBatchIds(asList);

            List<Long> skuIds = skuInfoEntities.stream().filter(item -> {
                SpuInfoEntity spuInfoEntity = spuInfoService.getById(item.getSpuId());
                return spuInfoEntity.getPublishStatus() == ProductConstant.ProductStatusEnum.SPU_UP.getCode();
            }).map(item -> {
                return item.getSkuId();
            }).collect(Collectors.toList());

            if (skuIds!=null&&skuIds.size()>0){
                searchFeignService.productStatusdown(skuIds);
            }
        }
    }

}