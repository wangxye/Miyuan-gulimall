package com.wxy.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.wxy.gulimall.product.entity.BrandEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wxy.gulimall.product.service.BrandService;
import com.wxy.common.utils.PageUtils;
import com.wxy.common.utils.R;



/**
 * 品牌
 *
 * @author Wangxuanye
 * @email Wangxuanye@gulimall.com
 * @date 2020-09-03 10:32:09
 */
@RestController
@RequestMapping("product/brand")
@Api(value = "品牌服务",description = "品牌管理增删改查")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value="获取品牌列表")
    //@RequiresPermissions("product:brand:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{brandId}")
    @ApiOperation(value="获取目标品牌列表")
    //@RequiresPermissions("product:brand:info")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value="保存品牌信息")
    //@RequiresPermissions("product:brand:save")
    public R save(@RequestBody BrandEntity brand){
		brandService.save(brand);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation(value="修改品牌信息")
    //@RequiresPermissions("product:brand:update")
    public R update(@RequestBody BrandEntity brand){
		brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation(value="删除品牌信息")
    //@RequiresPermissions("product:brand:delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
