package com.wxy.gulimall.product.vo;

import lombok.Data;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: wxy
 * @createTime: 2020-05-29 09:26
 **/

@Data
public class AttrRespVo extends AttrVo {

    /**
     *
     */
    private String catelogName;

    private String groupName;

    private Long[] catelogPath;

}
