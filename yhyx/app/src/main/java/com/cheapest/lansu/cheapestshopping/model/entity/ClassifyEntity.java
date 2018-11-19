package com.cheapest.lansu.cheapestshopping.model.entity;

import java.util.List;

/**
 * @author 码农哥
 * @date 2018/7/22 0022  11:28
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
public class ClassifyEntity {

	/**
	 * id : 1
	 * name : 分类名称
	 * icon : 1
	 * iconUrl : http://39.106.13.51:10081/attachment/ajax/visit/1
	 */

	private int id;
	private String name;
	private int icon;
	private String iconUrl;
	private List<MenuEntity> subCategories;
	private List<MenuEntity> hotCategories;

//	"id": 56,
//			"pid": 27,
//			"name": "连衣裙",
//			"icon": 14797,
//			"subCategories": [],
//			"hotCategories": [],
//			"iconUrl": "http://jiuchiwl.cn/attachment/attachment/ajax/visit/14797"


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

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public List<MenuEntity> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<MenuEntity> subCategories) {
		this.subCategories = subCategories;
	}

	public List<MenuEntity> getHotCategories() {
		return hotCategories;
	}

	public void setHotCategories(List<MenuEntity> hotCategories) {
		this.hotCategories = hotCategories;
	}
}
