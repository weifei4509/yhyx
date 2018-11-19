package com.cheapest.lansu.cheapestshopping.model.entity;

import java.util.List;

/**
 * @author 码农哥
 * @date 2018/7/22 0022  13:49
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
public class CommodityEntity {

	private int total;
	private int size;
	private int page;
	private int pages;
	private int curSize;
	private List<CollageEntity.DatasBean> datas;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getCurSize() {
		return curSize;
	}

	public void setCurSize(int curSize) {
		this.curSize = curSize;
	}

	public List<CollageEntity.DatasBean> getDatas() {
		return datas;
	}

	public void setDatas(List<CollageEntity.DatasBean> datas) {
		this.datas = datas;
	}

	public static class DatasBean {

		private int id;//": 3,//id
		private String name;//": "22",//名称
		private int type;//": 1,//宝贝类型，1-淘宝 2-天猫
		private float price;//": 20.00,//原价
		private float discountPrice;//": 49.00, //折扣后价格
		private int sellNum;//": 11,//销量
		private float couponAmount;//": 10.00,//优惠券金额
		private float mineCommision;//":2.05,//预估佣金
		private String iconUrl;//":"http://112.74.200.84:10081/attachment/ajax/visit/1"//图标地址

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

		public float getPrice() {
			return price;
		}

		public void setPrice(float price) {
			this.price = price;
		}

		public float getDiscountPrice() {
			return discountPrice;
		}

		public void setDiscountPrice(float discountPrice) {
			this.discountPrice = discountPrice;
		}

		public int getSellNum() {
			return sellNum;
		}

		public void setSellNum(int sellNum) {
			this.sellNum = sellNum;
		}

		public float getCouponAmount() {
			return couponAmount;
		}

		public void setCouponAmount(float couponAmount) {
			this.couponAmount = couponAmount;
		}

		public float getMineCommision() {
			return mineCommision;
		}

		public void setMineCommision(float mineCommision) {
			this.mineCommision = mineCommision;
		}

		public String getIconUrl() {
			return iconUrl;
		}

		public void setIconUrl(String iconUrl) {
			this.iconUrl = iconUrl;
		}
	}
}
