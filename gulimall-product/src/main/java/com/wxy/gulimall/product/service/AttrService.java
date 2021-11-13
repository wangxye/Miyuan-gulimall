package com.wxy.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxy.common.utils.PageUtils;
import com.wxy.gulimall.product.entity.AttrEntity;
import com.wxy.gulimall.product.vo.AttrGroupRelationVo;
import com.wxy.gulimall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author Wangxuanye
 * @email Wangxuanye@gulimall.com
 * @date 2020-09-03 10:32:09
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type);

    List<AttrEntity> getRelationAttr(Long attrGroupId);

    void saveAttr(AttrVo attr);

    void updateAttrById(AttrVo attr);

    List<Long> selectSearchAttrs(List<Long> attrIds);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId);

    void deleteRelation(AttrGroupRelationVo[] vos);
}

