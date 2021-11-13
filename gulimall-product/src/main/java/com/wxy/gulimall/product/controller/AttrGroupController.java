package com.wxy.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.wxy.gulimall.product.entity.AttrEntity;
import com.wxy.gulimall.product.service.AttrAttrgroupRelationService;
import com.wxy.gulimall.product.service.AttrService;
import com.wxy.gulimall.product.service.CategoryService;
import com.wxy.gulimall.product.vo.AttrGroupRelationVo;
import com.wxy.gulimall.product.vo.AttrGroupWithAttrsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wxy.gulimall.product.entity.AttrGroupEntity;
import com.wxy.gulimall.product.service.AttrGroupService;
import com.wxy.common.utils.PageUtils;
import com.wxy.common.utils.R;



/**
 * 属性分组
 *
 * @author Wangxuanye
 * @email Wangxuanye@gulimall.com
 * @date 2020-09-03 10:32:09
 */
@RestController
@RequestMapping("product/attrgroup")
@Api(value = "商品属性分组服务",description = "商品属性分组的增删改查")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrService attrService;

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    /**
     * 获取属性分组没有关联的其他属性
     */
    @GetMapping(value = "/{attrgroupId}/noattr/relation")
    @ApiOperation(value="获取属性分组没有关联的其他属性")
    public R attrNoattrRelation(@RequestParam Map<String, Object> params,
                                @PathVariable("attrgroupId") Long attrgroupId) {

        // List<AttrEntity> entities = attrService.getRelationAttr(attrgroupId);

        PageUtils page = attrService.getNoRelationAttr(params,attrgroupId);

        return R.ok().put("page",page);
    }

    @PostMapping(value = "/attr/relation")
    @ApiOperation(value="获取属性分组关联")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> vos) {

        attrAttrgroupRelationService.saveBatch(vos);

        return R.ok();

    }

    /**
     * 获取分类下所有分组&关联属性
     * @param catelogId
     * @return
     */
    @GetMapping("/{catelogId}/withattr")
    @ApiOperation(value="获取分类下所有分组&关联属性")
    public R getAttrGroupWithAttrs(@PathVariable("catelogId") Long catelogId){

        //1、查出当前分类下的所有属性分组
        //2、查出每个属性分组下的所有属性
        List<AttrGroupWithAttrsVo> vos =  attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId);

        return R.ok().put("data",vos);
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value="获取属性分组列表")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrGroupService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取属性分组有关联的其他属性
     * @param attrgroupId
     * @return
     */
    ///product/attrgroup/{attrgroupId}/attr/relation
    @GetMapping(value = "/{attrgroupId}/attr/relation")
    @ApiOperation(value="获取属性分组有关联的其他属性")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId) {

        List<AttrEntity> entities = attrService.getRelationAttr(attrgroupId);

        return R.ok().put("data",entities);
    }



    ///product/attrgroup/list/{catelogId}
    @GetMapping("/list/{catelogId}")
    @ApiOperation(value="获取分类下的所有属性列表")
    //@RequiresPermissions("product:attrgroup:list")
    public R listCatelogAttr(@RequestParam Map<String, Object> params,@PathVariable("catelogId") Long catelogId){
        PageUtils page = attrGroupService.queryPage(params,catelogId);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    @ApiOperation(value="获取目标分类信息")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);

        Long catelogId = attrGroup.getCatelogId();
        Long[] path = categoryService.findCatelogPath(catelogId);

        attrGroup.setCatelogPath(path);
        return R.ok().put("attrGroup", attrGroup);
    }


//    public R info(@PathVariable("attrGroupId") Long attrGroupId){
//		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
//
//        return R.ok().put("attrGroup", attrGroup);
//    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value="保存属性分类信息")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation(value="修改属性分类信息")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     * TODO 删除 relation
     */
    @PostMapping("/delete")
    @ApiOperation(value="批量删除属性分类信息")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
//		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));
        attrGroupService.deleteAttrGroup(Arrays.asList(attrGroupIds));
        return R.ok();
    }

    @PostMapping(value = "/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] vos) {

        attrService.deleteRelation(vos);

        return R.ok();
    }

}
