/*
 * 项目名称：bwk-core
 * 类名称: ProductRepository
 * 创建时间: 2016年9月20日 上午9:06:21
 * 创建人: zhailiang@pz365.com
 *
 * 修改历史:
 * 
 * Copyright: 2016 www.pz365.com Inc. All rights reserved.
 * 
 */
package com.ymt.bwk.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.ymt.bwk.domain.LessonProduct;
import com.ymt.pz365.data.jpa.repository.PzRepository;

/**
 *
 *
 * @author zhailiang@pz365.com
 * @version 1.0.0
 */
@Repository
public interface LessonProductRepository extends PzRepository<LessonProduct> {

    List<LessonProduct> findByLessonId(Long id);

    Page<LessonProduct> findByLessonId(Long id, Pageable pageable);

}
