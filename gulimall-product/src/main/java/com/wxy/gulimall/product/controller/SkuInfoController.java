package com.wxy.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wxy.gulimall.product.entity.SkuInfoEntity;
import com.wxy.gulimall.product.service.SkuInfoService;
import com.wxy.common.utils.PageUtils;
import com.wxy.common.utils.R;



/**
 * sku信息
 *
 * @author Wangxuanye
 * @email Wangxuanye@gulimall.com
 * @date 2020-09-03 10:32:09
 */
@RestController
@RequestMapping("product/skuinfo")
@Api(value = "库存量单位(SKU)服务",description = "库存量单位(SKU)商品增删改查")
public class SkuInfoController {
    @Autowired
    private SkuInfoService skuInfoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("product:skuinfo:list")
    @ApiOperation(value="SKU商品列表")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = skuInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{skuId}")
    @ApiOperation(value="SKU商品信息")
    //@RequiresPermissions("product:skuinfo:info")
    public R info(@PathVariable("skuId") Long skuId){
		SkuInfoEntity skuInfo = skuInfoService.getById(skuId);

        return R.ok().put("skuInfo", skuInfo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value="SKU商品保存")
    //@RequiresPermissions("product:skuinfo:save")
    public R save(@RequestBody SkuInfoEntity skuInfo){
		skuInfoService.save(skuInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation(value="SKU商品修改")
    //@RequiresPermissions("product:skuinfo:update")
    public R update(@RequestBody SkuInfoEntity skuInfo){
		skuInfoService.updateById(skuInfo);

        return R.ok();
    }

    /**
     * 删除
     * TODO 判断是否上架
     */
    @DeleteMapping("/delete")
    @ApiOperation(value="SKU商品批量删除")
    //@RequiresPermissions("product:skuinfo:delete")
    public R delete(@RequestBody Long[] skuIds){
//		skuInfoService.removeByIds(Arrays.asList(skuIds));
        skuInfoService.removeSKU(Arrays.asList(skuIds));
        return R.ok();
    }

}
