package com.wxy.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxy.common.utils.PageUtils;
import com.wxy.gulimall.product.entity.ProductAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author Wangxuanye
 * @email Wangxuanye@gulimall.com
 * @date 2020-09-03 10:32:09
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<ProductAttrValueEntity> baseAttrListforspu(Long spuId);

    void updateSpuAttr(Long spuId, List<ProductAttrValueEntity> entities);

    void saveProductAttr(List<ProductAttrValueEntity> collect);
}

