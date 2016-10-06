/*
 * 项目名称：bwk-core
 * 类名称: ProductInfo
 * 创建时间: 2016年9月20日 上午9:05:16
 * 创建人: zhailiang@pz365.com
 *
 * 修改历史:
 * 
 * Copyright: 2016 www.pz365.com Inc. All rights reserved.
 * 
 */
package com.ymt.bwk.dto;

import java.math.BigDecimal;

import com.ymt.mirage.lesson.dto.LessonInfo;

/**
 *
 *
 * @author zhailiang@pz365.com
 * @version 1.0.0
 */
public class ProductInfo {
    
    private Long id;
    /**
     * 顺序号
     */
    private Integer index;
    /**
     * 名称
     */
    private String name;
    /**
     * 关键商品
     */
    private boolean key;
    /**
     * 上架
     */
    private Boolean enable;
    /**
     * 图片
     */
    private String image;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 简介
     */
    private String intro;
    /**
     * 描述
     */
    private String desc;
    /**
     * 卖出人次
     */
    private int saleCount;
    /**
     * 卖出人次加成
     */
    private int saleCountPlus;
    /**
     * 课程信息
     */
    private LessonInfo lessonInfo;
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }
    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }
    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }
    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    /**
     * @return the intro
     */
    public String getIntro() {
        return intro;
    }
    /**
     * @param intro the intro to set
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }
    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }
    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
    /**
     * @return the saleCount
     */
    public int getSaleCount() {
        return saleCount;
    }
    /**
     * @param saleCount the saleCount to set
     */
    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }
    /**
     * @return the lessonInfo
     */
    public LessonInfo getLessonInfo() {
        return lessonInfo;
    }
    /**
     * @param lessonInfo the lessonInfo to set
     */
    public void setLessonInfo(LessonInfo lessonInfo) {
        this.lessonInfo = lessonInfo;
    }
    /**
     * @return the saleCountPlus
     */
    public int getSaleCountPlus() {
        return saleCountPlus;
    }
    /**
     * @param saleCountPlus the saleCountPlus to set
     */
    public void setSaleCountPlus(int saleCountPlus) {
        this.saleCountPlus = saleCountPlus;
    }
    /**
     * @return the enable
     */
    public Boolean getEnable() {
        return enable;
    }
    /**
     * @param enable the enable to set
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
    /**
     * @return the key
     */
    public boolean isKey() {
        return key;
    }
    /**
     * @param key the key to set
     */
    public void setKey(boolean key) {
        this.key = key;
    }
    /**
     * @return the index
     */
    public Integer getIndex() {
        return index;
    }
    /**
     * @param index the index to set
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

}
