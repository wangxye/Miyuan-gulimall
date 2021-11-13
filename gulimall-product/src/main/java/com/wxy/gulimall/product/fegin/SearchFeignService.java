package com.wxy.gulimall.product.fegin;

import com.wxy.common.es.SkuEsModel;
import com.wxy.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("gulimall-search")//检索服务
@Component
public interface SearchFeignService {

    @PostMapping(value = "/search/save/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);

    @PostMapping(value = "/search/remove/product")
    public R productStatusdown(@RequestBody List<Long> skuIds);
}
