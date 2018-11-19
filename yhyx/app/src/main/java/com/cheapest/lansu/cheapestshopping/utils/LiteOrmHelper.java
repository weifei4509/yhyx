package com.cheapest.lansu.cheapestshopping.utils;

import com.cheapest.lansu.cheapestshopping.view.App;
import com.litesuits.orm.LiteOrm;

/**
 * @author 码农哥
 * @date 2018/1/22 0022  21:46
 * @email 441293364@qq.com
 * @TODO <p/>数据库管理
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
public class LiteOrmHelper {

	private static final String DB_NAME = "cheapestshopping.db";

	private static volatile LiteOrm sInstance;

	private LiteOrmHelper() {
		// Avoid direct instantiate
	}

	public static LiteOrm getInstance() {
		if (sInstance == null) {
			synchronized (LiteOrmHelper.class) {
				if (sInstance == null) {
					sInstance = LiteOrm.newCascadeInstance(App.getInstance(), DB_NAME);
					sInstance.setDebugged(true);
				}
			}
		}
		return sInstance;
	}
}
