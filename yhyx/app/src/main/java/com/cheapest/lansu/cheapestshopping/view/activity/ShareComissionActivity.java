package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.ClipboardManager;
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
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.GoodDetailEntity;
import com.cheapest.lansu.cheapestshopping.utils.AppUtils;
import com.cheapest.lansu.cheapestshopping.utils.BitmapMaker;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.ScrollOffsetTransformer;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;
import com.cheapest.lansu.cheapestshopping.utils.shareUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 分享赚佣金
 */
public class ShareComissionActivity extends BaseActivity {

	@Bind(R.id.tablayout)
	TabLayout tablayout;
	@Bind(R.id.viewpager)
	ViewPager viewpager;
	private EditText etShareMsg;
	private TextView tvCopy;
	private TextView tvsave;
	private String baseUrl;
	private Bitmap bitmap;
	private ViewPager mviewPager;
	private GoodDetailEntity goodDetailEntity;
	private List<View> viewList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getToolbarTitle().setText("分享赚佣金");
		goodDetailEntity = (GoodDetailEntity) getIntent().getSerializableExtra(Constants.INTENT_KEY_TYPE);
		baseUrl = getIntent().getStringExtra(Constants.INTENT_KEY_URL);
		ArrayList<View> aList = new ArrayList<View>();
		LayoutInflater li = getLayoutInflater();
		View view = li.inflate(R.layout.view_share_word, null, false);
		View view2 = li.inflate(R.layout.view_share_image, null, false);
		aList.add(view);
		aList.add(view2);
		tvCopy = view.findViewById(R.id.tv_copy);
		tvsave = view2.findViewById(R.id.tv_save);
		etShareMsg = view.findViewById(R.id.et_share_msg);
		mviewPager = view2.findViewById(R.id.view_paper);
		LoginPaperAdapter mAdapter = new LoginPaperAdapter(aList);
		viewpager.setAdapter(mAdapter);
		tablayout.setupWithViewPager(viewpager);
		mviewPager.setPageTransformer(true, new ScrollOffsetTransformer()); //设置偏移规则
		mviewPager.setOffscreenPageLimit(2);    //缓存两个布局item
		setIndicator(tablayout, 60, 60);
		LinearLayout linearLayout = (LinearLayout) tablayout.getChildAt(0);
		linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
		linearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.layout_divider_vertical));
		linearLayout.setDividerPadding(AppUtils.dip2px(this, 12));
		etShareMsg.setText("   " + goodDetailEntity.getName() + "\n" + "【原价】  " + goodDetailEntity.getPrice() + "元" + "\n" + "【券后价】  " + goodDetailEntity.getDiscountPrice() + "元" + "\n\n\n" + "复制这条信息，" + goodDetailEntity.getTaoToken() + "，打开【手机淘宝】即可查看\n");
		etShareMsg.setSelection(etShareMsg.getText().length());
		final View[] viewDetail = {LayoutInflater.from(mContext).inflate(R.layout.item_share_view, null, false)};
		new BitmapMaker(this, goodDetailEntity, baseUrl, bitmap -> {
			this.bitmap = bitmap;
			((ImageView) viewDetail[0].findViewById(R.id.iv_share)).setImageBitmap(bitmap);
			viewList.add(viewDetail[0]);
			for (int i = 0; i < goodDetailEntity.getImageUrls().size(); i++) {
				viewDetail[0] = LayoutInflater.from(mContext).inflate(R.layout.item_share_view, null, false);
				if (mContext != null) {
					Glide.with(mContext).load(goodDetailEntity.getImageUrls().get(i)).asBitmap().into(((ImageView) viewDetail[0].findViewById(R.id.iv_share)));
				}

				viewList.add(viewDetail[0]);
			}
			MyPagerAdapter myPagerAdapter = new MyPagerAdapter(viewList);
			mviewPager.setAdapter(myPagerAdapter);


		});

		tvCopy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				if (CacheInfo.getUserInfoFromCache(ShareComissionActivity.this) != null) {
					cm.setText(etShareMsg.getText().toString() + "");
					Toast.makeText(ShareComissionActivity.this, "复制成功", Toast.LENGTH_LONG).show();
				}
			}
		});


		tvsave.setOnClickListener(new View.OnClickListener() {
									  @Override
									  public void onClick(View v) {
										  ToastUtils.showShort(ShareComissionActivity.this, "存储中，稍后可在相册里查看");
										  if (goodDetailEntity.getImageUrls().size() != 0) {
											  if (bitmap != null) {
												  saveBmp2Gallery(bitmap, System.currentTimeMillis() + "", ShareComissionActivity.this);
											  }

										  }
										  if (goodDetailEntity.getImageUrls().size() > 1) {
											  for (int i = 1; i < goodDetailEntity.getImageUrls().size(); i++) {
												  new Task().execute(goodDetailEntity.getImageUrls().get(i));
											  }
										  }

									  }
								  }
		);


	}

	@Override
	public int bindLayout() {
		return R.layout.activity_share_comission;


	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void setIndicator(TabLayout tabs, int leftDip, int rightDip)

	{
		DisplayMetrics displaysMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaysMetrics);
		Class<?> tabLayout = tabs.getClass();
		Field tabStrip = null;
		try {
			tabStrip = tabLayout.getDeclaredField("mTabStrip");
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		tabStrip.setAccessible(true);
		LinearLayout ll_tab = null;
		try {
			ll_tab = (LinearLayout) tabStrip.get(tabs);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		int left = (int) (displaysMetrics.density * leftDip);
		int right = (int)
				(displaysMetrics.density * rightDip);
		for (int i = 0; i < ll_tab.getChildCount(); i++) {
			View child = ll_tab.getChildAt(i);
			child.setPadding(0, 0, 0, 0);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
			params.leftMargin = left;
			params.rightMargin = right;
			child.setLayoutParams(params);
			child.invalidate();
		}
	}

	@OnClick({R.id.lin_share_weixin, R.id.lin_share_pengyouquan, R.id.lin_share_qq, R.id.lin_share_qq_qzone, R.id.lin_share_weibo})
	public void onViewClicked(View view) {
		shareUtils shareUtils = new shareUtils(null, ShareComissionActivity.this, null, etShareMsg.getText().toString(), bitmap, goodDetailEntity.getImageUrls());
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

	class LoginPaperAdapter extends PagerAdapter {
		private ArrayList<View> viewLists;
		CharSequence[] mTitleArray = {"文案分享", "图片分享"};

		public LoginPaperAdapter() {
		}

		public LoginPaperAdapter(ArrayList<View> viewLists) {
			super();
			this.viewLists = viewLists;
		}

		@Override
		public int getCount() {
			return viewLists.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(viewLists.get(position));
			return viewLists.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(viewLists.get(position));
		}

		@Override
		public CharSequence getPageTitle(int position) {

			return mTitleArray[position];
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

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
			saveBmp2Gallery(bitmap, System.currentTimeMillis() + "", ShareComissionActivity.this);
			return null;
		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Message message = new Message();
			message.what = 0x123;


		}

	}
}
