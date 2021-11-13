package com.wxy.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxy.common.utils.PageUtils;
import com.wxy.gulimall.coupon.entity.HomeAdvEntity;

import java.util.Map;

/**
 * 首页轮播广告
 *
 * @author Wangxuanye
 * @email Wangxuanye@gulimall.com
 * @date 2020-09-03 16:05:23
 */
public interface HomeAdvService extends IService<HomeAdvEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

