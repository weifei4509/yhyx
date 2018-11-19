package com.cheapest.lansu.cheapestshopping.model.entity;

/**
 * @author 码农哥
 * @date 2018/7/24 0024  22:54
 * @email 441293364@qq.com
 * @TODO <p/>签到信息实体类
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
public class SigninEntity {

	private int id;//": 1,
	private int customerId;//": 2,
	private String socre;//": 100,//金豆数量
	private String continuousDay;//": 2,//连续签到的天数
	private String lastSignDate;//": "2018-12-12",//最后签到日期
	private String continuousStartDate;//": "2018-12-11"//连续签到的起始日期

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getSocre() {
		return socre;
	}

	public void setSocre(String socre) {
		this.socre = socre;
	}

	public String getContinuousDay() {
		return continuousDay;
	}

	public void setContinuousDay(String continuousDay) {
		this.continuousDay = continuousDay;
	}

	public String getLastSignDate() {
		return lastSignDate;
	}

	public void setLastSignDate(String lastSignDate) {
		this.lastSignDate = lastSignDate;
	}

	public String getContinuousStartDate() {
		return continuousStartDate;
	}

	public void setContinuousStartDate(String continuousStartDate) {
		this.continuousStartDate = continuousStartDate;
	}
}
