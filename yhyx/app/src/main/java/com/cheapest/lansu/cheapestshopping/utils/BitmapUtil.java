package com.cheapest.lansu.cheapestshopping.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;


import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by fhw on 2017/3/11 0011.
 * email 11044818@qq.com
 */
public class BitmapUtil {

	private final static String Tag = BitmapUtil.class.getSimpleName();


	public static Bitmap zoomImage(Bitmap bgimage, int maxHight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		try {
			// 计算宽高缩放率
			float scaleWidth = ((float) SystemConfig.getWidth()) / width;
			if (maxHight < (int) (height * scaleWidth)) {
				scaleWidth = maxHight / height;
				Bitmap returnBitmap = Bitmap.createScaledBitmap(bgimage,
						(int) (scaleWidth * width), maxHight, true);

				return returnBitmap;
			} else {
				Bitmap returnBitmap = Bitmap.createScaledBitmap(bgimage,
						SystemConfig.getWidth(),
						(int) (height * scaleWidth), true);

				return returnBitmap;
			}
			// 缩放图片动作

		} catch (OutOfMemoryError e) {
			// TODO: handle exception
			return bgimage;
		}

	}


	/**
	 * 图片是否已缓存
	 *
	 * @param url_thumb
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean checkImageExist(String url_thumb) {

		if (!TextUtils.isEmpty(url_thumb) && !url_thumb.equals("null")) {

			if (ImageLoader.getInstance().getMemoryCache().get(url_thumb) != null) {
				return true;
			}
			/*
			 * if (ImageLoader.getInstance().getMemoryCache().keys()
			 * .contains(url_thumb)) { return true; }
			 */

			File file = ImageLoader.getInstance().getDiskCache().get(url_thumb);
			if (file != null && file.exists()) {
				return true;
			}

		}
		return false;
	}


	/**
	 * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
	 *
	 * @param bitmap
	 * @param maxkb  IMAGE_SIZE
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int maxkb) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
		int options = 100;
		while (output.toByteArray().length > maxkb && options != 10) {
			output.reset(); //清空output
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
			options -= 10;
		}

		byte[] b = output.toByteArray();
		return BitmapFactory.decodeByteArray(b, 0, b.length);
	}

}
