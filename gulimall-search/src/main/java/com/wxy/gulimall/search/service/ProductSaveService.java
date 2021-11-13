package com.wxy.gulimall.search.service;

import com.wxy.common.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: wxy
 * @createTime: 2020-06-06 16:53
 **/
public interface ProductSaveService {

    boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;

    boolean productStatusdown(List<Long> skuIds) throws  IOException;
}
