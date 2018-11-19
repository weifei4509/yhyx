package com.cheapest.lansu.cheapestshopping.model.entity;

import java.io.Serializable;
import java.util.List;

public class GoodDetailEntity implements Serializable{
    /**
     * id : 1
     * name : 11
     * type : 1
     * iconUrl : http://112.74.200.84:10081/attachment/ajax/visit/1
     * imageUrls : ["http://112.74.200.84:10081/attachment/ajax/visit/1"]
     * price : 20.0
     * discountPrice : 48.0
     * sellNum : 12
     * categoryId : 1
     * couponStartDate : 2018-04-27 12:20
     * couponEndDate : 2018-05-03 12:20
     * couponAmount : 10.0
     * couponUrl : www.baidu.com
     * summary : asdasdasd
     * content : asdasdasdasd
     * createTime : 2018-04-27 12:20
     */

    private int id;
    private String name;
    private int type;
    private String iconUrl;

    public String getFreeShip() {
        return freeShip;
    }

    public void setFreeShip(String freeShip) {
        this.freeShip = freeShip;
    }

    private String freeShip;
    public String getTaoToken() {
        return taoToken;
    }

    public void setTaoToken(String taoToken) {
        this.taoToken = taoToken;
    }

    private String taoToken;
    private double price;
    private double discountPrice;
    private int sellNum;
    private int categoryId;
    private String couponStartDate;
    private String couponEndDate;
    private String couponAmount;
    private String couponUrl;
    private String summary;
    private String content;
    private String createTime;
    private String mineCommision;
    private String itemId;

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    private  boolean collected;
    private List<String> imageUrls;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getSellNum() {
        return sellNum;
    }

    public void setSellNum(int sellNum) {
        this.sellNum = sellNum;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCouponStartDate() {
        return couponStartDate;
    }

    public void setCouponStartDate(String couponStartDate) {
        this.couponStartDate = couponStartDate;
    }

    public String getCouponEndDate() {
        return couponEndDate;
    }

    public void setCouponEndDate(String couponEndDate) {
        this.couponEndDate = couponEndDate;
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getCouponUrl() {
        return couponUrl;
    }

    public void setCouponUrl(String couponUrl) {
        this.couponUrl = couponUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getMineCommision() {
        return mineCommision;
    }

    public void setMineCommision(String mineCommision) {
        this.mineCommision = mineCommision;
    }

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
}
