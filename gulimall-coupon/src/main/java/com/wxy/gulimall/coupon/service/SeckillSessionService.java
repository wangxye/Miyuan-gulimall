package com.wxy.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxy.common.utils.PageUtils;
import com.wxy.gulimall.coupon.entity.SeckillSessionEntity;

import java.util.Map;

/**
 * 秒杀活动场次
 *
 * @author Wangxuanye
 * @email Wangxuanye@gulimall.com
 * @date 2020-09-03 16:05:23
 */
public interface SeckillSessionService extends IService<SeckillSessionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

