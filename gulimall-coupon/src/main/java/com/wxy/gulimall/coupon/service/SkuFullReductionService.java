package com.wxy.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxy.common.to.SkuReductionTo;
import com.wxy.common.utils.PageUtils;
import com.wxy.gulimall.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author Wangxuanye
 * @email Wangxuanye@gulimall.com
 * @date 2020-09-03 16:05:23
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuReduction(SkuReductionTo skuReductionTo);
}

