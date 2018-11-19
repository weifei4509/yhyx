package com.cheapest.lansu.cheapestshopping.model.entity;

import java.util.List;

/**
 * @author 码农哥
 * @date 2018/7/20 0020  21:30
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
public class CollageEntity {

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
		private int id;
		private String name;
		private float price;//原价格
		private int groupPrice;//": 0,//拼团价格，前端写死 0元
		private int stock;//": 10,//库存
		private int groupNum;//": 10,//已拼数
		private String labels;//": "xx,yy,zz",//标签 英文逗号分隔
		private String lcontent;//": "图文详情",//详情页用到此字段
		private int targetNum;//": 10,//完成此拼团需要邀请的人数
		private String lcreateTime;//": "",//创建时间
		private boolean canOpen;//": true,//当前用户是否可开启此拼团 也是详情页面使用此字段
		private String iconUrl;
		private List<CustModel> custList;
		private String[] imagesUrls;

		public String[] getImagesUrls() {
			return imagesUrls;
		}

		public void setImagesUrls(String[] imagesUrls) {
			this.imagesUrls = imagesUrls;
		}

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

		public float getPrice() {
			return price;
		}

		public void setPrice(float price) {
			this.price = price;
		}

		public int getGroupPrice() {
			return groupPrice;
		}

		public void setGroupPrice(int groupPrice) {
			this.groupPrice = groupPrice;
		}

		public int getStock() {
			return stock;
		}

		public void setStock(int stock) {
			this.stock = stock;
		}

		public int getGroupNum() {
			return groupNum;
		}

		public void setGroupNum(int groupNum) {
			this.groupNum = groupNum;
		}

		public String getLabels() {
			return labels;
		}

		public void setLabels(String labels) {
			this.labels = labels;
		}

		public String getLcontent() {
			return lcontent;
		}

		public void setLcontent(String lcontent) {
			this.lcontent = lcontent;
		}

		public int getTargetNum() {
			return targetNum;
		}

		public void setTargetNum(int targetNum) {
			this.targetNum = targetNum;
		}

		public String getLcreateTime() {
			return lcreateTime;
		}

		public void setLcreateTime(String lcreateTime) {
			this.lcreateTime = lcreateTime;
		}

		public boolean isCanOpen() {
			return canOpen;
		}

		public void setCanOpen(boolean canOpen) {
			this.canOpen = canOpen;
		}

		public String getIconUrl() {
			return iconUrl;
		}

		public void setIconUrl(String iconUrl) {
			this.iconUrl = iconUrl;
		}

		public List<CustModel> getCustList() {
			return custList;
		}

		public void setCustList(List<CustModel> custList) {
			this.custList = custList;
		}
	}

}
