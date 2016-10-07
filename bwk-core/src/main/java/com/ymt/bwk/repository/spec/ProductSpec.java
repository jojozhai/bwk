/*
 * 项目名称：bwk-core
 * 类名称: ProductSpec
 * 创建时间: 2016年9月20日 上午9:06:45
 * 创建人: zhailiang@pz365.com
 *
 * 修改历史:
 * 
 * Copyright: 2016 www.pz365.com Inc. All rights reserved.
 * 
 */
package com.ymt.bwk.repository.spec;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang.StringUtils;

import com.ymt.bwk.domain.Product;
import com.ymt.bwk.dto.ProductInfo;
import com.ymt.pz365.data.jpa.repository.spec.PzSimpleSpecification;
import com.ymt.pz365.data.jpa.repository.spec.QueryWraper;

/**
 *
 *
 * @author zhailiang@pz365.com
 * @version 1.0.0
 */
public class ProductSpec extends PzSimpleSpecification<Product, ProductInfo> {

    public ProductSpec(ProductInfo condition) {
        super(condition);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addCondition(QueryWraper<Product> queryWraper) {
        addEqualsCondition(queryWraper, "enable");
        if(StringUtils.isNotBlank(getCondition().getName())) {
            Predicate condition1 = queryWraper.getCb().like(getPath(queryWraper.getRoot(), "name"), "%"+getCondition().getName()+"%");
            Predicate condition2 = queryWraper.getCb().like(getPath(queryWraper.getRoot(), "desc"), "%"+getCondition().getName()+"%");
            Predicate condition3 = queryWraper.getCb().like(getPath(queryWraper.getRoot(), "lesson.teacher.name"), "%"+getCondition().getName()+"%");
            queryWraper.getPredicates().add(queryWraper.getCb().or(condition1, condition2, condition3));
        }
    }

}
