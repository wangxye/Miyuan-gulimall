package com.wxy.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.wxy.gulimall.product.entity.ProductAttrValueEntity;
import com.wxy.gulimall.product.service.ProductAttrValueService;
import com.wxy.gulimall.product.vo.AttrVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wxy.gulimall.product.entity.AttrEntity;
import com.wxy.gulimall.product.service.AttrService;
import com.wxy.common.utils.PageUtils;
import com.wxy.common.utils.R;



/**
 * 商品属性
 *
 * @author Wangxuanye
 * @email Wangxuanye@gulimall.com
 * @date 2020-09-03 10:32:09
 */
@RestController
@RequestMapping("product/attr")
@Api(value = "商品属性服务",description = "商品属性的增删改查")
public class AttrController {
    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;


    /**
     * 获取spu规格
     * @param spuId
     * @return
     */
    @GetMapping("/base/listforspu/{spuId}")
    @ApiOperation(value="获取SPU属性规格")
    public  R baseAttrlistforspu(@PathVariable("spuId") Long spuId){

        List<ProductAttrValueEntity> entities = productAttrValueService.baseAttrListforspu(spuId);

        return R.ok().put("data",entities);
    }

    //product/attr/sale/list/0?
    ///product/attr/base/list/{catelogId}
    @GetMapping("/{attrType}/list/{catelogId}")
    @ApiOperation(value="获取规格/销售属性的信息")
    public R baseAttrList(@RequestParam Map<String, Object> params,
                          @PathVariable("catelogId") Long catelogId,
                          @PathVariable("attrType")String type){

        PageUtils page = attrService.queryBaseAttrPage(params,catelogId,type);
        return R.ok().put("page", page);
    }



    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value="获取商品属性列表")
    //@RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{attrId}")
    @ApiOperation(value="获取目标属性信息")
    //@RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId){
		AttrEntity attr = attrService.getById(attrId);

        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value="保存商品属性信息")
    //@RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attr){
		attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation(value="修改商品属性信息")
    //@RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrVo attr){
		attrService.updateAttrById(attr);

        return R.ok();
    }

    // /product/attr/update/{spuId}
    @PostMapping("/update/{spuId}")
    @ApiOperation(value="修改SPU商品属性规格")
    public R updateSpuAttr(@PathVariable("spuId") Long spuId,
                           @RequestBody List<ProductAttrValueEntity> entities){

        productAttrValueService.updateSpuAttr(spuId,entities);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation(value="批量删除商品属性")
    //@RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }




}
