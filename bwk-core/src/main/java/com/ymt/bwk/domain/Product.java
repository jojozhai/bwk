/*
 * 项目名称：bwk-core
 * 类名称: Product
 * 创建时间: 2016年9月20日 上午9:02:18
 * 创建人: zhailiang@pz365.com
 *
 * 修改历史:
 * 
 * Copyright: 2016 www.pz365.com Inc. All rights reserved.
 * 
 */
package com.ymt.bwk.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.ymt.mirage.goods.domain.AbstractGoods;
import com.ymt.mirage.lesson.domain.Lesson;

/**
 *
 *
 * @author zhailiang@pz365.com
 * @version 1.0.0
 */
@Entity
public class Product extends AbstractGoods {
    
    @ManyToOne
    private Lesson lesson;

    /**
     * @return the lesson
     */
    public Lesson getLesson() {
        return lesson;
    }

    /**
     * @param lesson the lesson to set
     */
    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

}
