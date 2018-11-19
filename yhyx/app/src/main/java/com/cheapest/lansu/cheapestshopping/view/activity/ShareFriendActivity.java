package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.GoodDetailEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.TbkGoodsEntity;
import com.cheapest.lansu.cheapestshopping.utils.BitmapMaker;
import com.cheapest.lansu.cheapestshopping.utils.ScrollOffsetTransformer;
import com.cheapest.lansu.cheapestshopping.utils.TBKBitmapMaker;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;
import com.cheapest.lansu.cheapestshopping.utils.shareUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author 码农哥
 * @date 2018/8/1 0001  1:26
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
public class ShareFriendActivity extends BaseActivity {

	@Bind(R.id.tv_save)
	TextView tvsave;

	@Bind(R.id.view_paper)
	ViewPager mviewPager;

	TbkGoodsEntity tbkGoodsEntity = null;

	String baseUrl = null;
	private Bitmap bitmap;
	private List<View> viewList = new ArrayList<>();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getToolbarTitle().setText("分享好友");
		tbkGoodsEntity = (TbkGoodsEntity) getIntent().getSerializableExtra(Constants.INTENT_KEY_TYPE);
		baseUrl = getIntent().getStringExtra(Constants.INTENT_KEY_URL);
		baseUrl = baseUrl.startsWith("http:") ? baseUrl : "http:" + baseUrl;
		mviewPager.setPageTransformer(true, new ScrollOffsetTransformer()); //设置偏移规则
		mviewPager.setOffscreenPageLimit(2);    //缓存两个布局item

		final View[] viewDetail = {LayoutInflater.from(mContext).inflate(R.layout.item_share_view, null, false)};
		new TBKBitmapMaker(this, tbkGoodsEntity, baseUrl, bitmap -> {
			this.bitmap = bitmap;
			((ImageView) viewDetail[0].findViewById(R.id.iv_share)).setImageBitmap(bitmap);
			viewList.add(viewDetail[0]);
			for (int i = 0; i < tbkGoodsEntity.getSmallImages().length; i++) {
				viewDetail[0] = LayoutInflater.from(mContext).inflate(R.layout.item_share_view, null, false);
				if (mContext != null) {
					Glide.with(mContext).load(tbkGoodsEntity.getSmallImages()[i]).asBitmap().into(((ImageView) viewDetail[0].findViewById(R.id.iv_share)));
				}
				viewList.add(viewDetail[0]);
			}
			MyPagerAdapter myPagerAdapter = new MyPagerAdapter(viewList);
			mviewPager.setAdapter(myPagerAdapter);
		});

		tvsave.setOnClickListener(new View.OnClickListener() {
									  @Override
									  public void onClick(View v) {
										  ToastUtils.showShort(ShareFriendActivity.this, "存储中，稍后可在相册里查看");
										  if (tbkGoodsEntity.getSmallImages().length != 0) {
											  if (bitmap != null) {
												  saveBmp2Gallery(bitmap, System.currentTimeMillis() + "", ShareFriendActivity.this);
											  }

										  }
										  if (tbkGoodsEntity.getSmallImages().length > 1) {
											  for (int i = 1; i < tbkGoodsEntity.getSmallImages().length; i++) {
												  new Task().execute(tbkGoodsEntity.getSmallImages()[i]);
											  }
										  }

									  }
								  }
		);
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_share_friend;
	}

	@OnClick({R.id.lin_share_weixin, R.id.lin_share_pengyouquan, R.id.lin_share_qq, R.id.lin_share_qq_qzone, R.id.lin_share_weibo})
	public void onViewClicked(View view) {
		shareUtils shareUtils = new shareUtils(null, ShareFriendActivity.this, null, null, bitmap, Arrays.asList(tbkGoodsEntity.getSmallImages()));
		switch (view.getId()) {
			case R.id.lin_share_weixin:
				shareUtils.weixinShare();
				break;
			case R.id.lin_share_pengyouquan:
				shareUtils.weixinShareZone();
				break;
			case R.id.lin_share_qq:
				shareUtils.QQShare();
				break;
			case R.id.lin_share_qq_qzone:
				shareUtils.QQZoneShare("");
				break;
			case R.id.lin_share_weibo:
				shareUtils.shareSina();
				break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	//ViewPager适配器
	class MyPagerAdapter extends PagerAdapter {
		private List<View> mViewList;

		public MyPagerAdapter(List<View> mViewList) {
			this.mViewList = mViewList;
		}

		@Override
		public int getCount() {
			return mViewList.size();//页卡数
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;//官方推荐写法
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mViewList.get(position % mViewList.size()));//添加页卡
			return mViewList.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mViewList.get(position % mViewList.size()));//删除页卡
		}

		@Override
		public int getItemPosition(Object object) {
			// 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法

			return POSITION_NONE;


		}


	}

	/*
	 * 获取网络图片
	 * @param imageurl 图片网络地址
	 * @return Bitmap 返回位图
	 */
	public Bitmap GetImageInputStream(String imageurl) {
		URL url;
		HttpURLConnection connection = null;
		Bitmap bitmap = null;
		try {
			url = new URL(imageurl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(6000); //超时设置
			connection.setDoInput(true);
			connection.setUseCaches(false); //设置不使用缓存
			InputStream inputStream = connection.getInputStream();
			bitmap = BitmapFactory.decodeStream(inputStream);
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public static void saveBmp2Gallery(Bitmap bmp, String picName, Context mContext) {

		String fileName = null;
		//系统相册目录
		String galleryPath = Environment.getExternalStorageDirectory()
				+ File.separator + Environment.DIRECTORY_DCIM
				+ File.separator + "Camera" + File.separator;


		// 声明文件对象
		File file = null;
		// 声明输出流
		FileOutputStream outStream = null;

		try {
			// 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
			file = new File(galleryPath, picName + ".jpg");

			// 获得文件相对路径
			fileName = file.toString();
			// 获得输出流，如果文件中有内容，追加内容
			outStream = new FileOutputStream(fileName);
			if (null != outStream) {
				bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
			}

		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			try {
				if (outStream != null) {
					outStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		MediaStore.Images.Media.insertImage(mContext.getContentResolver(),
				bmp, fileName, null);
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri uri = Uri.fromFile(file);
		intent.setData(uri);
		mContext.sendBroadcast(intent);


	}

	class Task extends AsyncTask<String, Integer, Void> {

		protected Void doInBackground(String... params) {
			Bitmap bitmap = GetImageInputStream((String) params[0]);
			saveBmp2Gallery(bitmap, System.currentTimeMillis() + "", ShareFriendActivity.this);
			return null;
		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Message message = new Message();
			message.what = 0x123;


		}

	}
}
