package com.wxy.gulimall.product.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.wxy.gulimall.product.dao.AttrGroupDao;
import com.wxy.gulimall.product.entity.AttrEntity;
import com.wxy.gulimall.product.entity.AttrGroupEntity;
import com.wxy.gulimall.product.service.AttrAttrgroupRelationService;
import com.wxy.gulimall.product.service.AttrGroupService;
import com.wxy.gulimall.product.service.AttrService;
import com.wxy.gulimall.product.vo.AttrGroupWithAttrsVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Resource
    private AttrService attrService;

    @Resource
    private AttrAttrgroupRelationService relationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        String key = (String) params.get("key");

        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(key)){
            wrapper.and(obj->{
                obj.eq("attr_group_id",key).or().like("attr_group_name",key);
            });
        }

        if(catelogId==0){
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params),
                    wrapper);
            return new PageUtils(page);
        }else{
            wrapper.eq("catelog_id",catelogId);
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params),
                    wrapper);
            return new PageUtils(page);
        }
    }

    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId) {

        List<AttrGroupEntity> attrGroupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));

        List<AttrGroupWithAttrsVo> collect = attrGroupEntities.stream().map(group -> {
            AttrGroupWithAttrsVo attrGroupWithAttrsVo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(group, attrGroupWithAttrsVo);

            List<AttrEntity> attrs = attrService.getRelationAttr(attrGroupWithAttrsVo.getAttrGroupId());
            attrGroupWithAttrsVo.setAttrs(attrs);

            return attrGroupWithAttrsVo;

        }).collect(Collectors.toList());


        return collect;
    }

    @Override
    public void deleteAttrGroup(List<Long> asList) {
        if (asList!=null||asList.size()>0){
            this.baseMapper.deleteBatchIds(asList);
            relationService.removeRelation(asList);
        }
    }

}