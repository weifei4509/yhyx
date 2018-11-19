package com.cheapest.lansu.cheapestshopping.model.entity;

import java.io.Serializable;

/**
 * @author 码农哥
 * @date 2018/7/30 0030  22:15
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
public class TbkGoodsEntity implements Serializable {
	private String catLeafName;//":"T恤",
	private String catName;//":"女装/女士精品",
	private String itemUrl;//":"https://item.taobao.com/item.htm?id=566732857871",
	private String nick;//":"BBDR Studio",
	private String numIid;//":566732857871,
	private String pictUrl;//":"https://img.alicdn.com/bao/uploaded/i3/402599885/TB2B_SAhhSYBuNjSspjXXX73VXa_!!402599885.jpg",
	private String provcity;//":"广东 广州",
	private String reservePrice;//":"100",
	private String sellerId;//":402599885,
	private String[] smallImages;//
	private String title;//":"2018夏下衣失踪chic打底衫印花上衣宽松BF风中长款短袖t恤女装ins",
	private String userType;//":0,
	private String volume;//":13284,
	private String zkFinalPrice;//":"100"
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCatLeafName() {
		return catLeafName;
	}

	public void setCatLeafName(String catLeafName) {
		this.catLeafName = catLeafName;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNumIid() {
		return numIid;
	}

	public void setNumIid(String numIid) {
		this.numIid = numIid;
	}

	public String getPictUrl() {
		return pictUrl;
	}

	public void setPictUrl(String pictUrl) {
		this.pictUrl = pictUrl;
	}

	public String getProvcity() {
		return provcity;
	}

	public void setProvcity(String provcity) {
		this.provcity = provcity;
	}

	public String getReservePrice() {
		return reservePrice;
	}

	public void setReservePrice(String reservePrice) {
		this.reservePrice = reservePrice;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String[] getSmallImages() {
		return smallImages;
	}

	public void setSmallImages(String[] smallImages) {
		this.smallImages = smallImages;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getZkFinalPrice() {
		return zkFinalPrice;
	}

	public void setZkFinalPrice(String zkFinalPrice) {
		this.zkFinalPrice = zkFinalPrice;
	}
}
