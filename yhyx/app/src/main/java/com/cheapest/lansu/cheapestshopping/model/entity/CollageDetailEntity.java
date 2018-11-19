package com.cheapest.lansu.cheapestshopping.model.entity;

import java.util.List;

/**
 * @author 码农哥
 * @date 2018/7/23 0023  22:33
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
public class CollageDetailEntity {

	private int id;//": 1,
	private String name;//": "mingc",
	private String price;//": 5.6,//原价格
	private String groupPrice;//": 0,//拼团价格，前端写死 0元
	private String stock;//": 10,//库存
	private String groupNum;//": 10,//已拼数
	private String labels;//": "xx,yy,zz",//标签 英文逗号分隔
	private String content;//": "图文详情",//详情页用到此字段
	private String targetNum;//": 10,//完成此拼团需要邀请的人数
	private String createTime;//": "",//创建时间
	private boolean canOpen;//": true,//当前用户是否可开启此拼团 也是详情页面使用此字段
	private	CustomerGroupModel customerGroup;//": {//如果当前用户已经开启了此拼团 则显示此数据(拼团订单)
	private String iconUrl;//": "图标url",
	private String[] imagesUrls;//": ["",""]//图标数组

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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getGroupPrice() {
		return groupPrice;
	}

	public void setGroupPrice(String groupPrice) {
		this.groupPrice = groupPrice;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(String groupNum) {
		this.groupNum = groupNum;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTargetNum() {
		return targetNum;
	}

	public void setTargetNum(String targetNum) {
		this.targetNum = targetNum;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public boolean isCanOpen() {
		return canOpen;
	}

	public void setCanOpen(boolean canOpen) {
		this.canOpen = canOpen;
	}

	public CustomerGroupModel getCustomerGroup() {
		return customerGroup;
	}

	public void setCustomerGroup(CustomerGroupModel customerGroup) {
		this.customerGroup = customerGroup;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String[] getImagesUrls() {
		return imagesUrls;
	}

	public void setImagesUrls(String[] imagesUrls) {
		this.imagesUrls = imagesUrls;
	}

	public static class CustomerGroupModel{

		private int id;//": 2,//订单id
		private int groupId;//": 1,//所属拼团id
		private int customerId;//": 2,//此用户id
		private int targetNum;//": 10,//目标人数
		private int inviteNum;//": 8,//已经邀请的人数
		private int status;//": null,//状态 0-初始 1-完成 2-失败 3-取消
		private String createTime;//": "",//拼团开始时间
		private String endTime;//": "2018-12-12 12:12:12",//结束时间
		private long residueSecond;//": 3600//离结束还有多少秒 前端也可根据 endTime 自己计算
		private List<CustModel> custList;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getGroupId() {
			return groupId;
		}

		public void setGroupId(int groupId) {
			this.groupId = groupId;
		}

		public int getCustomerId() {
			return customerId;
		}

		public void setCustomerId(int customerId) {
			this.customerId = customerId;
		}

		public int getTargetNum() {
			return targetNum;
		}

		public void setTargetNum(int targetNum) {
			this.targetNum = targetNum;
		}

		public int getInviteNum() {
			return inviteNum;
		}

		public void setInviteNum(int inviteNum) {
			this.inviteNum = inviteNum;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		public long getResidueSecond() {
			return residueSecond;
		}

		public void setResidueSecond(long residueSecond) {
			this.residueSecond = residueSecond;
		}

		public List<CustModel> getCustList() {
			return custList;
		}

		public void setCustList(List<CustModel> custList) {
			this.custList = custList;
		}
	}

}
