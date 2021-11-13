package com.wxy.gulimall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.wxy.common.es.SkuEsModel;
import com.wxy.gulimall.search.constant.EsConstant;
import com.wxy.gulimall.search.service.ProductSaveService;
import com.wxy.gulimall.search.config.GulimallElasticSearchConfig;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: wxy
 * @createTime: 2020-06-06 16:54
 **/

@Slf4j
@Service("productSaveService")
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    private RestHighLevelClient esRestClient;

    @Override
    public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {

        if (skuEsModels!=null && skuEsModels.size()>0){
        //1.在es中建立索引，建立号映射关系（doc/json/product-mapping.json）

        //2. 在ES中保存这些数据
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel skuEsModel : skuEsModels) {
            //构造保存请求
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(skuEsModel.getSkuId().toString());
            String jsonString = JSON.toJSONString(skuEsModel);
            indexRequest.source(jsonString, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }


        BulkResponse bulk = esRestClient.bulk(bulkRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);

        //TODO 如果批量错误
        boolean hasFailures = bulk.hasFailures();

        List<String> collect = Arrays.asList(bulk.getItems()).stream().map(item -> {
            return item.getId();
        }).collect(Collectors.toList());

        log.info("商品上架完成：{}",collect);

        return hasFailures;

        }

        return false;
    }

    @Override
    public boolean productStatusdown(List<Long> skuIds) throws IOException {

        if (skuIds !=null&&skuIds.size()>0){
            String [] ids = new String[skuIds.size()];
            int index = 0;
            for (Long id:skuIds) {
                ids[index++] = id.toString();
            }
            DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(EsConstant.PRODUCT_INDEX);
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
            boolQueryBuilder.must(QueryBuilders.idsQuery().addIds(ids));
            deleteByQueryRequest.setQuery(boolQueryBuilder);
            esRestClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);

            log.info("商品上架完成：{}",skuIds);
        }
        return false;
    }
}
