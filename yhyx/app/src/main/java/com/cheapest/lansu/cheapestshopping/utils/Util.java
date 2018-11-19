package com.cheapest.lansu.cheapestshopping.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author 码农哥
 * @date 2018/7/26 0026  0:04
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
public class Util {

	/**
	 * 判断对象是否不为空
	 *
	 * @param object 当前对象
	 * @return true:不为空 false:为空
	 */
	public static boolean isNotEmpty(Object object) {
		return !isEmpty(object);
	}

	/**
	 * 判断对象是否为空
	 *
	 * @param object 当前对象
	 * @return true:为空 false:不为空
	 */
	public static boolean isEmpty(Object object) {
		if (object == null)
			return true;
		if (object instanceof CharSequence)
			return ((CharSequence) object).length() == 0;
		if (object instanceof Collection)
			return ((Collection<?>) object).isEmpty();
		if (object instanceof Map)
			return ((Map<?, ?>) object).isEmpty();
		if (object.getClass().isArray())
			return java.lang.reflect.Array.getLength(object) == 0;
		return false;
	}
}
