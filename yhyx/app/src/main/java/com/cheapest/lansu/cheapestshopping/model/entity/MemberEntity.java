package com.cheapest.lansu.cheapestshopping.model.entity;

/**
 * @author 码农哥
 * @date 2018/7/7 0007  0:22
 * @email 441293364@qq.com
 * @TODO <p/>会员排名实体类
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
public class MemberEntity {
	private int id;//": 1,
	private int viped;
	private String mobile;//": "13428858800",
	private String nickname;//": "朋度",
	private String money;//": "858",//奖励的金额
	private String headpicUrl;//": "http://jiuchiwl.cn/attachment/attachment/ajax/visit/14353"//头像地址

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getHeadpicUrl() {
		return headpicUrl;
	}

	public void setHeadpicUrl(String headpicUrl) {
		this.headpicUrl = headpicUrl;
	}
}
