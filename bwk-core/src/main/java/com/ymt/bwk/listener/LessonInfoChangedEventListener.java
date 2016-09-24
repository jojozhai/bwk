/*
 * 项目名称：bwk-core
 * 类名称: LessonInfoChangedEventListener
 * 创建时间: 2016年9月24日 下午4:00:52
 * 创建人: zhailiang@pz365.com
 *
 * 修改历史:
 * 
 * Copyright: 2016 www.pz365.com Inc. All rights reserved.
 * 
 */
package com.ymt.bwk.listener;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.ymt.bwk.domain.LessonProduct;
import com.ymt.bwk.repository.LessonProductRepository;
import com.ymt.bwk.repository.ProductRepository;
import com.ymt.mirage.lesson.dto.ProductInfo;
import com.ymt.mirage.lesson.event.LessonInfoChangedEvent;
import com.ymt.mirage.lesson.repository.LessonRepository;

/**
 *
 *
 * @author zhailiang@pz365.com
 * @version 1.0.0
 */
@Component
public class LessonInfoChangedEventListener implements ApplicationListener<LessonInfoChangedEvent> {

    @Autowired
    private LessonRepository lessonRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private LessonProductRepository lessonProductRepository;
    
    @Override
    public void onApplicationEvent(LessonInfoChangedEvent event) {
        lessonProductRepository.delete(lessonProductRepository.findByLessonId(event.getLessonInfo().getId()));
        if(CollectionUtils.isNotEmpty(event.getLessonInfo().getProducts())) {
            for (ProductInfo productInfo : event.getLessonInfo().getProducts()) {
                LessonProduct lessonProduct = new LessonProduct();
                lessonProduct.setLesson(lessonRepository.getOne(event.getLessonInfo().getId()));
                lessonProduct.setProduct(productRepository.getOne(productInfo.getId()));
                lessonProductRepository.save(lessonProduct);
            }
        }
    }

}
