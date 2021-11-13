package com.wxy.gulimall.search.controller;

import com.wxy.common.es.SkuEsModel;
import com.wxy.common.exception.BizCodeEnum;
import com.wxy.common.utils.R;
import com.wxy.gulimall.search.service.ProductSaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequestMapping(value = "/search/remove")
@RestController
@Api(value = "ES删除服务",description = "商品移除检索")
public class ElasticRemoveController {

    @Autowired
    private ProductSaveService productSaveService;

    @PostMapping(value = "/product")
    @ApiOperation(value = "下架商品")
    public R productStatusUp(@RequestBody List<Long> skuIds) {
        boolean status=false;
        try {
            status = productSaveService.productStatusdown(skuIds);
        } catch (IOException e) {
            return R.error(BizCodeEnum.PRODUCT_DOWN_EXCEPTION.getCode(),BizCodeEnum.PRODUCT_DOWN_EXCEPTION.getMessage());
        }

        if(status){
            return R.error(BizCodeEnum.PRODUCT_DOWN_EXCEPTION.getCode(),BizCodeEnum.PRODUCT_DOWN_EXCEPTION.getMessage());
        }else {
            return R.ok();
        }
    }

}
