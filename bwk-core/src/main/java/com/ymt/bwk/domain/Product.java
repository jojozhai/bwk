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

import org.apache.commons.lang.StringUtils;

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
     * 产品类型
     */
    private String type;
    /**
     * 
     */
    private boolean requireName;
    /**
     * 
     */
    private boolean requirePhone;
    /**
     * 
     */
    private boolean requireWeixin;
    /**
     * 
     */
    private boolean requireAddress;
    /**
     * 置顶
     */
    private boolean top;
    /**
     * 置顶顺序
     */
    private int topIndex;
    
    /**
     * @return the top
     */
    public boolean isTop() {
        return top;
    }

    /**
     * @param top the top to set
     */
    public void setTop(boolean top) {
        this.top = top;
    }

    /**
     * @return the topIndex
     */
    public int getTopIndex() {
        return topIndex;
    }

    /**
     * @param topIndex the topIndex to set
     */
    public void setTopIndex(int topIndex) {
        this.topIndex = topIndex;
    }

    /**
     * @return the requireName
     */
    public boolean isRequireName() {
        return requireName;
    }

    /**
     * @param requireName the requireName to set
     */
    public void setRequireName(boolean requireName) {
        this.requireName = requireName;
    }

    /**
     * @return the requirePhone
     */
    public boolean isRequirePhone() {
        return requirePhone;
    }

    /**
     * @param requirePhone the requirePhone to set
     */
    public void setRequirePhone(boolean requirePhone) {
        this.requirePhone = requirePhone;
    }

    /**
     * @return the requireWeixin
     */
    public boolean isRequireWeixin() {
        return requireWeixin;
    }

    /**
     * @param requireWeixin the requireWeixin to set
     */
    public void setRequireWeixin(boolean requireWeixin) {
        this.requireWeixin = requireWeixin;
    }

    /**
     * @return the requireAddress
     */
    public boolean isRequireAddress() {
        return requireAddress;
    }

    /**
     * @param requireAddress the requireAddress to set
     */
    public void setRequireAddress(boolean requireAddress) {
        this.requireAddress = requireAddress;
    }

    /**
     * @return
     * @author zhailiang
     * @since 2017年1月2日
     */
    public boolean isConsult() {
        if(StringUtils.isBlank(type)) {
            return true;
        }else {
            if(StringUtils.equals(getType(), "咨询老师")) {
                return true;
            }else{
                return false;
            }
        }
    }

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

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

}
