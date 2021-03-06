package com.cheapest.lansu.cheapestshopping.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.text.TextUtils;


/**
 * Created by fhw on 2017/3/11 0011.
 * email 11044818@qq.com
 */
public class StringUtil {

	protected static String Tag = "StringUtil";


	/**
	 * 获取缩略图   根据项目需求获取
	 * 这里是在淘宝找的图片做的对应的一些处理
	 * @param str
	 * @param hight
	 * @param width
     * @return
     */
	public static String getThumb(String str, int hight, int width) {
		if (TextUtils.isEmpty(str) || str.equals("null")) {
			return str;
		}
		if (str.startsWith("file://") ) {
			return str;
		}
		if (str.contains("alicdn.com/imgextra")) {
			int indexStart = str.indexOf(".jpg_")+5;
			int indexEnd = str.lastIndexOf(".jpg");
			String sizeSrc = str.substring(indexStart,indexEnd);
			String widthAndHight[] = sizeSrc.split("x");
			if (widthAndHight.length == 2) {
				try {
					int widthSrc = Integer.parseInt(widthAndHight[0]);
					int hightSrc = Integer.parseInt(widthAndHight[1]);
					int minSize = Math.min(widthSrc, hightSrc);
					int size = Math.max(hight, width);

					int returnSize = Math.min(minSize, size)/100 == 0 ? 100 : Math.min(minSize, size)/100*100;


					String newStr = str.substring(0, indexStart) + returnSize + "x" + returnSize + str.substring(indexEnd, str.length());
					Logger.i(Tag,Tag + " getThumb:"+newStr);
					return newStr;
				} catch (NumberFormatException e) {

				}
			}
		}
		return str;
	}

	public static Point getThumbSize(String str) {

		int defaultWidth =700;
		int defaultHight = 800;
		Point sizeData = new Point(defaultWidth,defaultHight);
		if (TextUtils.isEmpty(str) || str.equals("null")) {
			return sizeData;
		}
		if (str.startsWith("file://") ) {
			return sizeData;
		}
		if (str.contains("alicdn.com/imgextra")) {
			int indexStart = str.indexOf(".jpg_")+5;
			int indexEnd = str.lastIndexOf(".jpg");
			String sizeSrc = str.substring(indexStart,indexEnd);
			String widthAndHight[] = sizeSrc.split("x");
			if (widthAndHight.length == 2) {
				try {
					int widthSrc = Integer.parseInt(widthAndHight[0]);
					int hightSrc = Integer.parseInt(widthAndHight[1]);
					if (hightSrc >= widthSrc) {
						sizeData.y = defaultHight;
						sizeData.x = defaultWidth * widthSrc / hightSrc;
					} else {
						sizeData.x = defaultWidth;
						sizeData.y = defaultHight * hightSrc / widthSrc;
					}

					return sizeData;
				} catch (NumberFormatException e) {

				}
			}
		}




		return sizeData;
	}
	/**
	 * 获取原图   根据项目需求获取
	 * 这里是在淘宝找的图片做的对应的一些处理
	 * @param str
	 * @return
	 */
	public static String getOrg(String str) {
		if (TextUtils.isEmpty(str) || str.equals("null")) {
			return str;
		}
		if (str.startsWith("file://") ) {
			return str;
		}
		if (str.contains("alicdn.com/imgextra")) {
			int indexStart = str.indexOf(".jpg_");
			int indexEnd = str.lastIndexOf(".jpg");

			String newStr = str.substring(0,indexStart)+str.substring(indexEnd,str.length());
			Logger.i(Tag,Tag + " getOrg:"+newStr);
			return newStr;
		}
		return str;
	}
	public static int[] getImageWidthHeight(String path){
		BitmapFactory.Options options = new BitmapFactory.Options();

		/**
		 * 最关键在此，把options.inJustDecodeBounds = true;
		 * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
		 */
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回的bitmap为null
		/**
		 *options.outHeight为原始图片的高
		 */
		return new int[]{options.outWidth,options.outHeight};
	}
}
