package com.cheapest.lansu.cheapestshopping.model.entity;

import java.io.Serializable;

/**
 * @author 码农哥
 * @date 2018/7/30 0030  2:38
 * @email 441293364@qq.com
 * @TODO <p/>
 * ** *** ━━━━━━神兽出没━━━━━━
 * ** ***       ┏┓　　  ┏┓
 * ** *** 	   ┏┛┻━━━┛┻┓
 * ** *** 　  ┃　　　　　　　┃
 * ** *** 　　┃　　　━　　　┃
 * ** *** 　　┃　┳┛　┗┳　┃
 * ** *** 　　┃　　　　　　　┃
 * ** *** 　　┃　　　┻　　　┃
 * ** *** 　　┃　　　　　　　┃
 * ** *** 　　┗━┓　　　┏━┛
 * ** *** 　　　　┃　　　┃ 神兽保佑,代码永无bug
 * ** *** 　　　　┃　　　┃
 * ** *** 　　　　┃　　　┗━━━┓
 * ** *** 　　　　┃　　　　　　　┣┓
 * ** *** 　　　　┃　　　　　　　┏┛
 * ** *** 　　　　┗┓┓┏━┳┓┏┛
 * ** *** 　　　　  ┃┫┫  ┃┫┫
 * ** *** 　　　　  ┗┻┛　┗┻┛
 */
public class SProductModel extends BaseEntity{

	private String itemId; //":"544721331065",
	private String title; //":"白衬衫女短袖夏白色工作服职业衬衣正装工装半袖修身女装</span>OL大码",
	private float currentPrice; //":36.9,
	private float couponPrice; //":20.00,
	private float afterCouponPrice; //":16.90,
	private String biz30Day; //":10284,
	private String clickUrl; //":"//s.click.taobao.com/t?e=m%3D2%26s%3DZCAY8RJ85jNw4vFB6t2Z2ueEDrYVVa64K7Vc7tFgwiHjf2vlNIV67rtDcK7niUPmdgpT%2Fnt4ZAje1onjT6gwpf6LKFcT6qIrfYv1adzRoLgSlWcoi9i1AyaNhWd3kV2she71Bn%2BrDR24rqv732vpCMreVHDRavJcUHZlzPwGoeWcveKD1lQgvTFe7pzVv3xL8dhg3UKc%2BQ7Zbml9VwAkPfLQLulBhJzGCXyduVM7C5FE%2FVrZvHyScHpeIonOPN%2BILuRMPfa1XeKDenWJ2jSVaE%2Fqa4H3LoPg&pvid=201_11.224.197.178_1215449_1508084801524",
	private String shareUrl; //":"//uland.taobao.com/coupon/edetail?e=MGX%2BCwemt8UGQASttHIRqWAcvWvf8LqAS7xtgf2XWVbB76UlhXoJ96LrVvgBIRbOL%2FdVx0HJSYYqpTrWpWtf27NfdVyW9nVyDfqEFBOhTcyOTqg7ldLYfOvDFk%2BLgDAdzmZqVh9Clhor%2FIoBvoIWWdCtedbC7d6D&mt=1&ptl=bucket:_TL-66240;spm:a3126.8759693/b.8001.1;prepvid:201_11.224.197.178_1215449_1508084801524;engpvid:100_10.103.67.57_24186_4311532889524784360;from:temaisearch",
	private String pictUrl; //":"//gw.alicdn.com/img/i4/2274120075/TB2vfFlHh9YBuNjy0FfXXXIsVXa_!!2274120075-0-item_pic.jpg",
	private int userType; //":1,
	private int sendCouponCount; //":300,
	private int totalCouponCount; //":10000

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public float getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(float currentPrice) {
		this.currentPrice = currentPrice;
	}

	public float getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(float couponPrice) {
		this.couponPrice = couponPrice;
	}

	public float getAfterCouponPrice() {
		return afterCouponPrice;
	}

	public void setAfterCouponPrice(float afterCouponPrice) {
		this.afterCouponPrice = afterCouponPrice;
	}

	public String getBiz30Day() {
		return biz30Day;
	}

	public void setBiz30Day(String biz30Day) {
		this.biz30Day = biz30Day;
	}

	public String getClickUrl() {
		return clickUrl;
	}

	public void setClickUrl(String clickUrl) {
		this.clickUrl = clickUrl;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public String getPictUrl() {
		return pictUrl;
	}

	public void setPictUrl(String pictUrl) {
		this.pictUrl = pictUrl;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getSendCouponCount() {
		return sendCouponCount;
	}

	public void setSendCouponCount(int sendCouponCount) {
		this.sendCouponCount = sendCouponCount;
	}

	public int getTotalCouponCount() {
		return totalCouponCount;
	}

	public void setTotalCouponCount(int totalCouponCount) {
		this.totalCouponCount = totalCouponCount;
	}
}
