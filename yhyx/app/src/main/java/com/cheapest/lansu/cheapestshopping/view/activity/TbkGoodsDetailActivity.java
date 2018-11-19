package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.GoodDetailEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.TbkGoodsEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.NormalUtil;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;
import com.cheapest.lansu.cheapestshopping.view.custom.GlideImageLoader;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 码农哥
 * @date 2018/7/30 0030  21:39
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
public class TbkGoodsDetailActivity extends BaseActivity {

	@Bind(R.id.iv_btn_back)
	ImageView iv_btn_back;
	@Bind(R.id.ban_goods_image)
	Banner banGoodsImage;
	@Bind(R.id.tv_goods_title)
	TextView tvGoodsTitle;
	@Bind(R.id.tv_quanhoujia)
	TextView tvQuanhoujia;
	@Bind(R.id.tv_goods_xiaoliang)
	TextView tvGoodsXiaoliang;
	@Bind(R.id.wv_goods_detail)
	WebView wvGoodsDetail;
	@Bind(R.id.tv_quanhoujia_bottom)
	TextView tvQuanhoujiaBottom;
	@Bind(R.id.lin_share)
	LinearLayout linShare;
	@Bind(R.id.lin_quanhoujia)
	LinearLayout linQuanhoujia;

	TbkGoodsEntity tbkGoodsEntity = null;

	String baseImageUrl = null;

	String itemId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		removeToolBar();
		baseImageUrl = getIntent().getStringExtra(Constants.INTENT_KEY_URL);

		banGoodsImage.setImageLoader(new GlideImageLoader());
		//设置自动轮播，默认为true
		banGoodsImage.isAutoPlay(true);
		//设置轮播时间
		banGoodsImage.setDelayTime(3000);
		//设置指示器位置（当banner模式中有指示器时）
		banGoodsImage.setIndicatorGravity(BannerConfig.CENTER);

		iv_btn_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		wvGoodsDetail.getSettings().setJavaScriptEnabled(true);
// 设置缓存模式：不使用缓存
		wvGoodsDetail.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		wvGoodsDetail.getSettings().setDomStorageEnabled(true);
		itemId = getIntent().getStringExtra(Constants.INTENT_KEY_ID);
		getData(itemId);
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_tbk_goods_detail;
	}

	private void getData(String id) {
		RetrofitFactory.getInstence().API()
				.getTbkGoodDetail(id)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<TbkGoodsEntity>(this) {
					@Override
					protected void onSuccees(BaseEntity<TbkGoodsEntity> t) throws Exception {
						tbkGoodsEntity = t.getData();

						wvGoodsDetail.getSettings().setUseWideViewPort(true);
						wvGoodsDetail.getSettings().setLoadWithOverviewMode(true);
						wvGoodsDetail.getSettings().setBuiltInZoomControls(true);
						wvGoodsDetail.getSettings().setDisplayZoomControls(false);
						wvGoodsDetail.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); //取消滚动条白边效果
						wvGoodsDetail.setWebChromeClient(new WebChromeClient());

						wvGoodsDetail.getSettings().setDefaultTextEncodingName("UTF-8");
						wvGoodsDetail.getSettings().setBlockNetworkImage(false);
						WebSettings settings = wvGoodsDetail.getSettings();
						settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

// 是否支持js  说了用js，这句是必须加上的
						settings.setJavaScriptEnabled(true);
//  重写 WebViewClient
						wvGoodsDetail.setWebViewClient(new MyWebViewClient());
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
							wvGoodsDetail.getSettings().setMixedContentMode(wvGoodsDetail.getSettings()
									.MIXED_CONTENT_ALWAYS_ALLOW);  //注意安卓5.0以上的权限
						}
						wvGoodsDetail.loadDataWithBaseURL(null, t.getData().getContent(), "text/html", " charset=UTF-8", null);
						tvQuanhoujia.setText("原价  ￥" + t.getData().getZkFinalPrice());

						tvGoodsTitle.setText(StringUtils.addImageLabel(mContext, R.mipmap.baoyou, t.getData().getTitle()));
						tvGoodsXiaoliang.setText(t.getData().getVolume() + "笔成交");
						banGoodsImage.setImages(Arrays.asList(t.getData().getSmallImages()));
						banGoodsImage.start();
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}


	@OnClick({R.id.lin_share, R.id.lin_quanhoujia})
	public void onClickViewed(View v) {
		switch (v.getId()) {
			case R.id.lin_share:
				Intent intent = new Intent(this, ShareFriendActivity.class);
				intent.putExtra(Constants.INTENT_KEY_TYPE, tbkGoodsEntity);
				intent.putExtra(Constants.INTENT_KEY_URL, baseImageUrl);
				startActivity(intent);
				break;

			case R.id.lin_quanhoujia:
				toTaobao(itemId);
				break;
		}
	}

	public void toTaobao(String itemId) {
		showProgressDialog();
		RetrofitFactory.getInstence().API()
				.clickUrl(CacheInfo.getUserID(this), itemId)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<String>(this) {
					@Override
					protected void onSuccees(final BaseEntity<String> t) throws Exception {
						discussProgressDialog();
						if (checkPackage("com.taobao.taobao")) {
							AlibcPage alibcPage = new AlibcPage(t.getData());
							AlibcShowParams alibcShowParams = new AlibcShowParams();
							alibcShowParams.setTitle("   ");
							alibcShowParams.setOpenType(OpenType.Native);
							AlibcTrade.show(TbkGoodsDetailActivity.this, alibcPage, alibcShowParams, null, null, new AlibcTradeCallback() {

								@Override
								public void onTradeSuccess(TradeResult tradeResult) {

								}

								@Override
								public void onFailure(int code, String msg) {
									//打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息

								}
							});


						} else {
							ToastUtils.showShort(TbkGoodsDetailActivity.this, "请先下载淘宝");

						}


					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
						discussProgressDialog();
					}
				});


	}

	/**
	 * 检测该包名所对应的应用是否存在
	 *
	 * @param packageName
	 * @return
	 */

	public boolean checkPackage(String packageName)

	{

		if (packageName == null || "".equals(packageName))

			return false;

		try

		{

			mContext.getPackageManager().getApplicationInfo(packageName, PackageManager
					.GET_UNINSTALLED_PACKAGES);

			return true;

		} catch (PackageManager.NameNotFoundException e)

		{

			return false;

		}

	}

	class MyWebViewClient extends WebViewClient {

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			//  html加载完成之后，调用js的方法
			imgReset();
		}

		private void imgReset() {
			wvGoodsDetail.loadUrl("javascript:(function(){"
					+ "var objs = document.getElementsByTagName('img'); "
					+ "for(var i=0;i<objs.length;i++)  " + "{"
					+ "var img = objs[i];   "
					+ "    img.style.width = '100%';   "
					+ "    img.style.height = 'auto';   "
					+ "}" + "})()");
		}


		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
}
