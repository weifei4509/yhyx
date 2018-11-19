package com.cheapest.lansu.cheapestshopping.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.GoodDetailEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.TbkGoodsEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author 码农哥
 * @date 2018/8/1 0001  1:53
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
public class TBKBitmapMaker {
	private BitmapMaker.loadImage loadImage;
	View view;
	ImageView ivImage;
	private Bitmap bitMap;
	private Context mContext;
	private String baseurl;
	TbkGoodsEntity tbkGoodsEntity;

	public TBKBitmapMaker(Context context, TbkGoodsEntity tbkGoodsEntity, String baseUrl, BitmapMaker.loadImage loadImage) {
		this.tbkGoodsEntity = tbkGoodsEntity;
		this.mContext = context;
		this.baseurl = baseUrl;
		this.loadImage = loadImage;
		try {
			makerBitMap();     //制作图片
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void makerBitMap() throws Exception {
		view = LayoutInflater.from(mContext).inflate(R.layout.view_bitmap_tbkmaker, null);
		TextView tvTitle = view.findViewById(R.id.tv_title);
		ivImage = view.findViewById(R.id.iv_imge);
		ImageView ivErWeiMa = view.findViewById(R.id.tv_erweima);

		TextView tvQuanhoujia = view.findViewById(R.id.tv_quanhoujia);
		tvQuanhoujia.setText("￥ " + tbkGoodsEntity.getZkFinalPrice());
		tvTitle.setText(tbkGoodsEntity.getTitle());

		Glide.with(mContext).load(CacheInfo.getUserInfoFromCache(mContext).getRecommendQcodePathUrl()).asBitmap().into(ivErWeiMa);
		if (tbkGoodsEntity.getSmallImages() != null && tbkGoodsEntity.getSmallImages().length != 0) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					loadRmoteImage(baseurl);
				}
			});
			thread.start();
		}
	}


	public void viewSaveToImage(View view) {
		Log.e("ssh", "a");

		/**
		 * View组件显示的内容可以通过cache机制保存为bitmap
		 * 我们要获取它的cache先要通过setDrawingCacheEnable方法把cache开启，
		 * 然后再调用getDrawingCache方法就可 以获得view的cache图片了
		 * 。buildDrawingCache方法可以不用调用，因为调用getDrawingCache方法时，
		 * 若果 cache没有建立，系统会自动调用buildDrawingCache方法生成cache。
		 * 若果要更新cache, 必须要调用destoryDrawingCache方法把旧的cache销毁，才能建立新的。
		 */
		//        view.setDrawingCacheEnabled(true);
		//        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
		//设置绘制缓存背景颜色
		//        view.setDrawingCacheBackgroundColor(Color.WHITE);

		// 把一个View转换成图片
		//     Bitmap cachebmp = loadBitmapFromView(view);

		//  aaa.setImageBitmap(cachebmp);//直接展示转化的bitmap

		//保存在本地 产品还没决定要不要保存在本地
		FileOutputStream fos;
		try {
			// 判断手机设备是否有SD卡
			boolean isHasSDCard = Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED);
			if (isHasSDCard) {
				// SD卡根目录
				File sdRoot = Environment.getExternalStorageDirectory();
				Log.e("ssh", sdRoot.toString());
				File file = new File(sdRoot, "test.png");
				fos = new FileOutputStream(file);
			} else
				throw new Exception("创建文件失败!");
			//压缩图片 30 是压缩率，表示压缩70%; 如果不压缩是100，表示压缩率为0
			//    cachebmp.compress(Bitmap.CompressFormat.PNG, 90, fos);

			fos.flush();
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		view.destroyDrawingCache();
	}


	public interface loadImage {
		public void loadFinish(Bitmap bitmap);
	}

	/**
	 * @param imgUrl 远程图片文件的URL
	 *               <p>
	 *               下载远程图片
	 */
	private void loadRmoteImage(String imgUrl) {
		URL fileURL = null;
		Bitmap bitmap = null;

		try {
			fileURL = new URL(imgUrl);
		} catch (MalformedURLException err) {
			err.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) fileURL
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			int length = (int) conn.getContentLength();
			if (length != -1) {
				byte[] imgData = new byte[length];
				byte[] buffer = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(buffer)) > 0) {
					System.arraycopy(buffer, 0, imgData, destPos, readLen);
					destPos += readLen;
				}
				bitmap = BitmapFactory.decodeByteArray(imgData, 0,
						imgData.length);
				Message ms = new Message();
				ms.obj = bitmap;
				handler.sendMessage(ms);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			ivImage.setImageBitmap((Bitmap) msg.obj);
			WindowManager manager = ((Activity) mContext).getWindowManager();
			DisplayMetrics outMetrics = new DisplayMetrics();
			manager.getDefaultDisplay().getMetrics(outMetrics);
			int width = outMetrics.widthPixels;
			int height = outMetrics.heightPixels;
			// 整个View的大小 参数是左上角 和右下角的坐标
			view.layout(0, 0, width, height);
			int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
			int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST);
			/** 当然，measure完后，并不会实际改变View的尺寸，需要调用View.layout方法去进行布局。
			 * 按示例调用layout函数后，View的大小将会变成你想要设置成的大小。
			 */
			view.measure(measuredWidth, measuredHeight);
			view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

			int w = view.getWidth();
			int h = view.getHeight();
			bitMap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(bitMap);

			c.drawColor(Color.WHITE);
			/** 如果不设置canvas画布为白色，则生成透明 */

			view.layout(0, 0, w, h);
			view.draw(c);
			loadImage.loadFinish(bitMap);


		}
	};
}
