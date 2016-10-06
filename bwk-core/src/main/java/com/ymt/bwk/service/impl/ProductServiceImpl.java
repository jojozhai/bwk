/*
 * 项目名称：mirage-lesson
 * 类名称: ProductServiceImpl
 * 创建时间: 2016年9月19日 上午10:18:05
 * 创建人: zhailiang@pz365.com
 *
 * 修改历史:
 * 
 * Copyright: 2016 www.pz365.com Inc. All rights reserved.
 * 
 */
package com.ymt.bwk.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ymt.bwk.domain.LessonProduct;
import com.ymt.bwk.domain.Product;
import com.ymt.bwk.dto.ProductInfo;
import com.ymt.bwk.repository.LessonProductRepository;
import com.ymt.bwk.repository.ProductRepository;
import com.ymt.bwk.repository.spec.ProductSpec;
import com.ymt.bwk.service.ProductService;
import com.ymt.mirage.lesson.domain.Teacher;
import com.ymt.mirage.lesson.dto.LessonInfo;
import com.ymt.mirage.lesson.dto.TeacherInfo;
import com.ymt.mirage.lesson.repository.LessonRepository;
import com.ymt.mirage.lesson.service.LessonService;
import com.ymt.mirage.order.domain.Order;
import com.ymt.mirage.order.dto.OrderViewInfo;
import com.ymt.pz365.data.jpa.domain.Goods;
import com.ymt.pz365.data.jpa.spi.order.OrderGoodsService;
import com.ymt.pz365.data.jpa.support.AbstractDomain2InfoConverter;
import com.ymt.pz365.data.jpa.support.QueryResultConverter;

/**
 *
 *
 * @author zhailiang@pz365.com
 * @version 1.0.0
 */
@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService, OrderGoodsService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private LessonRepository lessonRepository;
    
    @Autowired
    private LessonProductRepository lessonProductRepository;
    
    @Autowired
    private LessonService lessonService;

    @Override
    public Page<ProductInfo> query(Long id, Pageable pageable) {
        Page<LessonProduct> pageData = lessonProductRepository.findByLessonId(id, pageable);
        return QueryResultConverter.convert(pageData, pageable, new AbstractDomain2InfoConverter<LessonProduct, ProductInfo>() {
            @Override
            protected void doConvert(LessonProduct domain, ProductInfo info) throws Exception {
                info.setId(domain.getProduct().getId());
                info.setName(domain.getProduct().getName());
                info.setImage(domain.getProduct().getImage());
                setTeacherInfoForProductQuery(domain.getProduct(), info);
            }
        });
    }
    
    @Override
    public Page<ProductInfo> query(ProductInfo productInfo, Pageable pageable) {
        Page<Product> pageData = productRepository.findAll(new ProductSpec(productInfo), pageable);
        return QueryResultConverter.convert(pageData, pageable, new AbstractDomain2InfoConverter<Product, ProductInfo>() {
            @Override
            protected void doConvert(Product domain, ProductInfo info) throws Exception {
                setTeacherInfoForProductQuery(domain, info);
            }
        });
    }
    
    private void setTeacherInfoForProductQuery(Product domain, ProductInfo info) {
        Teacher teacher = domain.getLesson().getTeacher();
        LessonInfo lessonInfo = new LessonInfo();
        TeacherInfo teacherInfo = new TeacherInfo();
        BeanUtils.copyProperties(teacher, teacherInfo);
        teacherInfo.setDesc("");
        lessonInfo.setTeacherInfo(teacherInfo);
        info.setLessonInfo(lessonInfo);
        info.setDesc("");
    }

    @Override
    public ProductInfo create(ProductInfo productInfo) {
        Product product = new Product();
        BeanUtils.copyProperties(productInfo, product);
        productInfo.setId(productRepository.save(product).getId());
        product.setLesson(lessonRepository.getOne(productInfo.getLessonInfo().getId()));
        return productInfo;
    }

    @Override
    public ProductInfo getInfo(Long id) {
        Product product = productRepository.findOne(id);
        ProductInfo info = new ProductInfo();
        BeanUtils.copyProperties(product, info);
        info.setLessonInfo(lessonService.getInfo(product.getLesson().getId()));
        return info;
    }

    @Override
    public ProductInfo update(ProductInfo productInfo) {
        Product product = productRepository.findOne(productInfo.getId());
        BeanUtils.copyProperties(productInfo, product);
        product.setLesson(lessonRepository.getOne(productInfo.getLessonInfo().getId()));
        productRepository.save(product);
        return productInfo;
    }

    @Override
    public void delete(Long id) {
        productRepository.delete(id);       
    }

    @Override
    public Goods getGoodsInfo(Long productId) {
        return productRepository.findOne(productId);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List convertOrderInfo(List orders) {
        List<OrderViewInfo> infos = new ArrayList<OrderViewInfo>();
        for (Object _order : orders) {
            Order order = (Order)_order;
            OrderViewInfo info = new OrderViewInfo();
            info.setId(order.getId());
            Product product = productRepository.findOne(order.getProducts().get(0).getGoodsId());
            Teacher teacher = product.getLesson().getTeacher();
            info.setProductName(product.getName());
            info.setTeacherName(teacher.getName());
            info.setTeacherImage(teacher.getImage());
            info.setTeacherTitle(teacher.getTitle());
            info.setState(order.getState());
            info.setSaleCount(teacher.getSaleCount() + teacher.getSaleCountPlus());
            infos.add(info);
        }
        return infos;
    }

    @Override
    public List<ProductInfo> findAll() {
        List<Object[]> teachers = productRepository.findAllForOption();
        List<ProductInfo> result = new ArrayList<ProductInfo>();
        for (Object[] data : teachers) {
            ProductInfo info = new ProductInfo();
            info.setId((Long) data[0]);
            info.setName((String) data[1]);
            result.add(info);
        }
        return result;
    }

    @Override
    public List<com.ymt.mirage.lesson.dto.ProductInfo> getRecommend(Long lessonId) {
        List<LessonProduct> products = lessonProductRepository.findByLessonId(lessonId);
        return QueryResultConverter.convert(products, new AbstractDomain2InfoConverter<LessonProduct, com.ymt.mirage.lesson.dto.ProductInfo>() {
            @Override
            protected void doConvert(LessonProduct domain, com.ymt.mirage.lesson.dto.ProductInfo info) throws Exception {
                BeanUtils.copyProperties(domain.getProduct(), info);
            }
        });
    }

}
