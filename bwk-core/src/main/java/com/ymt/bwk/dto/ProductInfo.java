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
     * 分享标题
     */
    private String shareTitle;
    /**
     * 描述
     */
    private String shareTip;
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
     * 价格描述
     */
    private String priceDesc;
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
     * 商品类型
     */
    private String type;
    /**
     * 
     */
    private Boolean requireName;
    /**
     * 
     */
    private Boolean requirePhone;
    /**
     * 
     */
    private Boolean requireWeixin;
    /**
     * 
     */
    private Boolean requireAddress;
    
    private Boolean top;
    
    private Integer topIndex;
    
    /**
     * @return the top
     */
    public Boolean getTop() {
        return top;
    }
    /**
     * @param top the top to set
     */
    public void setTop(Boolean top) {
        this.top = top;
    }
    /**
     * @return the topIndex
     */
    public Integer getTopIndex() {
        return topIndex;
    }
    /**
     * @param topIndex the topIndex to set
     */
    public void setTopIndex(Integer topIndex) {
        this.topIndex = topIndex;
    }
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
    /**
     * @return the priceDesc
     */
    public String getPriceDesc() {
        return priceDesc;
    }
    /**
     * @param priceDesc the priceDesc to set
     */
    public void setPriceDesc(String priceDesc) {
        this.priceDesc = priceDesc;
    }
    /**
     * @return the shareTitle
     */
    public String getShareTitle() {
        return shareTitle;
    }
    /**
     * @param shareTitle the shareTitle to set
     */
    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }
    /**
     * @return the shareTip
     */
    public String getShareTip() {
        return shareTip;
    }
    /**
     * @param shareTip the shareTip to set
     */
    public void setShareTip(String shareTip) {
        this.shareTip = shareTip;
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
    /**
     * @return the requireName
     */
    public Boolean getRequireName() {
        return requireName;
    }
    /**
     * @param requireName the requireName to set
     */
    public void setRequireName(Boolean requireName) {
        this.requireName = requireName;
    }
    /**
     * @return the requirePhone
     */
    public Boolean getRequirePhone() {
        return requirePhone;
    }
    /**
     * @param requirePhone the requirePhone to set
     */
    public void setRequirePhone(Boolean requirePhone) {
        this.requirePhone = requirePhone;
    }
    /**
     * @return the requireWeixin
     */
    public Boolean getRequireWeixin() {
        return requireWeixin;
    }
    /**
     * @param requireWeixin the requireWeixin to set
     */
    public void setRequireWeixin(Boolean requireWeixin) {
        this.requireWeixin = requireWeixin;
    }
    /**
     * @return the requireAddress
     */
    public Boolean getRequireAddress() {
        return requireAddress;
    }
    /**
     * @param requireAddress the requireAddress to set
     */
    public void setRequireAddress(Boolean requireAddress) {
        this.requireAddress = requireAddress;
    }

}
