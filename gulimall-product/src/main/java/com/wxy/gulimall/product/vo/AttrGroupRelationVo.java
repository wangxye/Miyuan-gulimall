package com.wxy.gulimall.product.vo;

import lombok.Data;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: wxy
 * @createTime: 2020-05-29 17:21
 **/

@Data
public class AttrGroupRelationVo {

    //[{"attrId":1,"attrGroupId":2}]
    private Long attrId;

    private Long attrGroupId;

}
