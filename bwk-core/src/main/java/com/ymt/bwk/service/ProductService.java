/*
 * 项目名称：bwk-core
 * 类名称: ProductService
 * 创建时间: 2016年9月20日 上午9:07:39
 * 创建人: zhailiang@pz365.com
 *
 * 修改历史:
 * 
 * Copyright: 2016 www.pz365.com Inc. All rights reserved.
 * 
 */
package com.ymt.bwk.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ymt.bwk.dto.ProductInfo;

/**
 *
 *
 * @author zhailiang@pz365.com
 * @version 1.0.0
 */
public interface ProductService {
    
    Page<ProductInfo> query(ProductInfo productInfo, Pageable pageable);
    
    Page<ProductInfo> query(Long id, Pageable pageable);
    
    ProductInfo create(ProductInfo productInfo) throws Exception;

    ProductInfo getInfo(Long id);
    
    ProductInfo update(ProductInfo productInfo);

    void delete(Long id);
    
    List<ProductInfo> findAll();

    List<com.ymt.mirage.lesson.dto.ProductInfo> getRecommend(Long lessonId);

    

}
