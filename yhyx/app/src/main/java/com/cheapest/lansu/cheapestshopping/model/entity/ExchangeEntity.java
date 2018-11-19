package com.cheapest.lansu.cheapestshopping.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author 码农哥
 * @date 2018/7/19 0019  22:41
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
public class ExchangeEntity {

	private int total;
	private int size;
	private int page;
	private int pages;
	private int curSize;
	private List<ExchangeEntity.DatasBean> datas;

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

	public List<ExchangeEntity.DatasBean> getDatas() {
		return datas;
	}

	public void setDatas(List<ExchangeEntity.DatasBean> datas) {
		this.datas = datas;
	}

	public static class DatasBean implements Serializable {

		private int id;//": 1,
		private String name;//": "名称",
		private String summary;//": "描述",
		private int score;//": 50,//所需金豆
		private int stock;//": 20,//库存
		private int exchangeNum;//": 5,//已经兑换的数量
		private String iconUrl;
		private int icon;//": "",//图标地址
		private String[] imageUrls;//": ["",""],//图片列表
		private float price;//": 5.60,//价值
		private int enabled;
		private String createTime;//": ""
		private String content;

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

		public String getSummary() {
			return summary;
		}

		public void setSummary(String summary) {
			this.summary = summary;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}

		public int getStock() {
			return stock;
		}

		public void setStock(int stock) {
			this.stock = stock;
		}

		public int getExchangeNum() {
			return exchangeNum;
		}

		public void setExchangeNum(int exchangeNum) {
			this.exchangeNum = exchangeNum;
		}

		public String getIconUrl() {
			return iconUrl;
		}

		public void setIconUrl(String iconUrl) {
			this.iconUrl = iconUrl;
		}

		public int getIcon() {
			return icon;
		}

		public void setIcon(int icon) {
			this.icon = icon;
		}

		public String[] getImageUrls() {
			return imageUrls;
		}

		public void setImageUrls(String[] imageUrls) {
			this.imageUrls = imageUrls;
		}

		public float getPrice() {
			return price;
		}

		public void setPrice(float price) {
			this.price = price;
		}

		public int getEnabled() {
			return enabled;
		}

		public void setEnabled(int enabled) {
			this.enabled = enabled;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}

}
