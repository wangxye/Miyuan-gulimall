package com.wxy.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxy.common.utils.PageUtils;
import com.wxy.gulimall.product.entity.SpuInfoEntity;
import com.wxy.gulimall.product.vo.SpuSaveVo;

import java.util.List;
import java.util.Map;

/**
 * spu信息
 *
 * @author Wangxuanye
 * @email Wangxuanye@gulimall.com
 * @date 2020-09-03 10:32:09
 */


public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void savesupInfo(SpuSaveVo vo);

    void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity);

    void up(Long spuId);

    void down(Long spuId);

    List<SpuInfoEntity> selectBatchIds(List<Long> spuIds);
}

