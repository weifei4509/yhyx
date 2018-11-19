package com.cheapest.lansu.cheapestshopping.model.entity;

import java.util.List;

/**
 * @author 码农哥
 * @date 2018/7/20 0020  22:59
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
public class OrderEntity {

	private int total;
	private int size;
	private int page;
	private int pages;
	private int curSize;
	private List<OrderEntity.DatasBean> datas;

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

	public List<OrderEntity.DatasBean> getDatas() {
		return datas;
	}

	public void setDatas(List<OrderEntity.DatasBean> datas) {
		this.datas = datas;
	}

	public static class DatasBean {

		private int id;//": 2,//拼团订单id
		private String groupName;//": "拼团的名称",
		private String iconUrl;//": "图标地址",
		private String createTime;//": "",//拼团时间
		private int status;//": 1//状态 0-初始 1-完成 2-失败 3-取消

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getGroupName() {
			return groupName;
		}

		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}

		public String getIconUrl() {
			return iconUrl;
		}

		public void setIconUrl(String iconUrl) {
			this.iconUrl = iconUrl;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
	}
}
