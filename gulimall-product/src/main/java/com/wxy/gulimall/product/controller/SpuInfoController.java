package com.wxy.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.wxy.gulimall.product.vo.SpuSaveVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import com.wxy.gulimall.product.entity.SpuInfoEntity;
import com.wxy.gulimall.product.service.SpuInfoService;
import com.wxy.common.utils.PageUtils;
import com.wxy.common.utils.R;


/**
 * spu信息
 *
 * @author Wangxuanye
 * @email Wangxuanye@gulimall.com
 * @date 2020-09-03 10:32:09
 */

@RefreshScope
@RestController
@RequestMapping("product/spuinfo")
@Api(value = "标准化产品单元(SPU)服务",description = "标准化产品单元(SPU)增删改查、上架下架")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;


    //商品上架
    ///product/spuinfo/{spuId}/up
    @PostMapping(value = "/{spuId}/up")
    @ApiOperation(value="SPU商品上架")
    public R spuUp(@PathVariable("spuId") Long spuId) {

        spuInfoService.up(spuId);

        return R.ok();
    }


    //商品下架
    ///product/spuinfo/{spuId}/down
    @PostMapping(value = "/{spuId}/down")
    @ApiOperation(value="SPU商品下架")
    public R spudown(@PathVariable("spuId") Long spuId) {

        spuInfoService.down(spuId);

        return R.ok();
    }



    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value="SPU商品列表")
    //@RequiresPermissions("product:spuinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = spuInfoService.queryPage(params);
        System.out.println(page.getList());
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation(value="SPU商品信息")
    //@RequiresPermissions("product:spuinfo:info")
    public R info(@PathVariable("id") Long id){
        SpuInfoEntity spuInfo = spuInfoService.getById(id);

        return R.ok().put("spuInfo", spuInfo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value="SPU商品发布")
    //@RequiresPermissions("product:spuinfo:save")
    public R save(@RequestBody SpuSaveVo vo){
        //spuInfoService.save(spuInfo);

        spuInfoService.savesupInfo(vo);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation(value="SPU商品更新")
    //@RequiresPermissions("product:spuinfo:update")
    public R update(@RequestBody SpuInfoEntity spuInfo){
        spuInfoService.updateById(spuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation(value="SPU商品批量删除")
    //@RequiresPermissions("product:spuinfo:delete")
    public R delete(@RequestBody Long[] ids){
        spuInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
