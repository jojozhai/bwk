/*
 * 项目名称：bwk-core
 * 类名称: ProductController
 * 创建时间: 2016年9月20日 上午9:09:54
 * 创建人: zhailiang@pz365.com
 *
 * 修改历史:
 * 
 * Copyright: 2016 www.pz365.com Inc. All rights reserved.
 * 
 */
package com.ymt.bwk.web.controller.weixin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ymt.bwk.dto.ProductInfo;
import com.ymt.bwk.service.ProductService;

/**
 * 
 * 
 * @author zhailiang@pz365.com
 * @version 1.0.0
 */
@RestController
@Profile({"weixin","app"})
public class ProductWeixinController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public Page<ProductInfo> query(ProductInfo productInfo, Pageable pageable) {
        return productService.query(productInfo, pageable);
    }
    
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public ProductInfo getInfo(@PathVariable Long id) {
        return productService.getInfo(id);
    }

    
}
