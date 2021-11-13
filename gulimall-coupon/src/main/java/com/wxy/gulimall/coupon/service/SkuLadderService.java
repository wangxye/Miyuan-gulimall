package com.wxy.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxy.common.utils.PageUtils;
import com.wxy.gulimall.coupon.entity.SkuLadderEntity;

import java.util.Map;

/**
 * 商品阶梯价格
 *
 * @author Wangxuanye
 * @email Wangxuanye@gulimall.com
 * @date 2020-09-03 16:05:23
 */
public interface SkuLadderService extends IService<SkuLadderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

