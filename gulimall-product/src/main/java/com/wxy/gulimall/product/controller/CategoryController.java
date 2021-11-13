package com.wxy.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.wxy.gulimall.product.entity.CategoryEntity;
import com.wxy.gulimall.product.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wxy.common.utils.PageUtils;
import com.wxy.common.utils.R;



/**
 * 商品三级分类
 *
 * @author Wangxuanye
 * @email Wangxuanye@gulimall.com
 * @date 2020-09-03 10:32:09
 */
@RestController
@RequestMapping("product/category")
@Api(value = "分类信息服务",description = "商品分类信息的增删改查")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list/tree")
    //@RequiresPermissions("product:category:list")
    @ApiOperation(value="获取分类父子树形结构")
    public R listTree(){
        List<CategoryEntity> entities = categoryService.listWithTree();

        return R.ok().put("data", entities);
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("product:category:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    //@RequiresPermissions("product:category:info")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("product:category:save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("product:category:update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateById(category);

        return R.ok();
    }

    @PostMapping("/update/sort")
    //@RequiresPermissions("product:category:update")
    @ApiOperation(value="批量更新分类信息")
    public R updateSort(@RequestBody CategoryEntity[] category){
        categoryService.updateBatchById(Arrays.asList(category));

        return R.ok();
    }


    /**
     * 删除
     */
    @PostMapping("/delete")
    //@RequiresPermissions("product:category:delete")
    public R delete(@RequestBody Long[] catIds){
		categoryService.removeByIds(Arrays.asList(catIds));

        return R.ok();
    }

}
