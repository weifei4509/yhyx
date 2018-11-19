package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.NormalUtil;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;
import com.cheapest.lansu.cheapestshopping.utils.UserInfoUtils;
import com.cheapest.lansu.cheapestshopping.view.custom.GlideImageLoader;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 商业详情
 */
public class GoodsDetailActivity extends BaseActivity implements Serializable {
	@Bind(R.id.ban_goods_image)
	Banner banGoodsImage;
	@Bind(R.id.tv_goods_title)
	TextView tvGoodsTitle;
	@Bind(R.id.tv_goods_yuanjia)
	TextView tvGoodsYuanjia;
	@Bind(R.id.tv_quanhoujia)
	TextView tvQuanhoujia;
	@Bind(R.id.tv_goods_xiaoliang)
	TextView tvGoodsXiaoliang;
	@Bind(R.id.tv_stamps_value)
	TextView tvStampsValue;
	@Bind(R.id.tv_goods_qixian)
	TextView tvGoodsQixian;
	@Bind(R.id.tv_get_stamps)
	TextView tvGetStamps;
	@Bind(R.id.wv_goods_detail)
	WebView wvGoodsDetail;
	@Bind(R.id.tv_quanhoujia_bottom)
	TextView tvQuanhoujiaBottom;
	@Bind(R.id.tv_yongjin_num)
	TextView tv_yongjin_num;
	@Bind(R.id.lin_get_stamps)
	LinearLayout linGetStamps;
	@Bind(R.id.iv_collect)
	ImageView ivCollect;
	@Bind(R.id.tv_collect)
	TextView tvCollect;
	@Bind(R.id.lin_collect)
	LinearLayout linCollect;
	@Bind(R.id.lin_share)
	LinearLayout linShare;
	@Bind(R.id.lin_quanhoujia)
	LinearLayout linQuanhoujia;
	private GoodDetailEntity goodDetailEntity;
	private boolean isCollect;
	private String baseImageUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		removeToolBar();
		getToolbarTitle().setText("商品详情");
		tvGoodsYuanjia.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
		banGoodsImage.setImageLoader(new GlideImageLoader());
		baseImageUrl = getIntent().getStringExtra(Constants.INTENT_KEY_URL);
		//设置自动轮播，默认为true
		banGoodsImage.isAutoPlay(true);
		//设置轮播时间
		banGoodsImage.setDelayTime(3000);
		//设置指示器位置（当banner模式中有指示器时）
		banGoodsImage.setIndicatorGravity(BannerConfig.CENTER);

		wvGoodsDetail.getSettings().setJavaScriptEnabled(true);
// 设置缓存模式：不使用缓存
		wvGoodsDetail.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		wvGoodsDetail.getSettings().setDomStorageEnabled(true);
		getData(getIntent().getIntExtra(Constants.INTENT_KEY_ID, 0));
	}

	private void getData(int id) {
		RetrofitFactory.getInstence().API()
				.getGoodDetail("api/commodity/detail/" + id, CacheInfo.getUserID(this))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<GoodDetailEntity>(this) {
					@Override
					protected void onSuccees(BaseEntity<GoodDetailEntity> t) throws Exception {
						goodDetailEntity = t.getData();
						ArrayList<String> list1 = new ArrayList<>();
						for (String imageUrl : t.getData().getImageUrls()) {
							list1.add(imageUrl);
						}

						banGoodsImage.setImages(list1);
						banGoodsImage.setOnBannerListener(new OnBannerListener() {
							@Override
							public void OnBannerClick(int position) {
								Intent intent = new Intent(GoodsDetailActivity.this, ShowPictureActivity.class);
								Bundle bundle = new Bundle();
								int[] location = new int[2];
								banGoodsImage.getLocationInWindow(location);
								int x = location[0];
								int y = location[1];
								bundle.putInt("x", x);
								bundle.putInt("y", y);

								Gson gson = new Gson();
								List<String> list = goodDetailEntity.getImageUrls();
								bundle.putString("imgdatas", gson.toJson(list));
								bundle.putInt("position", position);
								bundle.putInt("column_num", 3);
								bundle.putInt("type", 1);
								bundle.putInt("horizontal_space", NormalUtil.dip2px(GoodsDetailActivity.this, 3));
								bundle.putInt("vertical_space", NormalUtil.dip2px(GoodsDetailActivity.this, 3));
								intent.putExtras(bundle);
								startActivity(intent);
							}
						});
						banGoodsImage.start();


						tvGoodsQixian.setText("使用期限：" + t.getData().getCouponStartDate() + "-" + t.getData().getCouponEndDate());
						tvStampsValue.setText(t.getData().getCouponAmount() + "元优惠券");
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
						tvGoodsYuanjia.setText("原价  ￥" + t.getData().getPrice());
						tvQuanhoujia.setText("￥ " + t.getData().getDiscountPrice());
						tvGoodsXiaoliang.setText(t.getData().getSellNum() + "笔成交");
//						tvQuanhoujiaBottom.setText("￥ " + t.getData().getDiscountPrice());
						tv_yongjin_num.setText("预估佣金￥ " + t.getData().getMineCommision());
						tvQuanhoujiaBottom.setText("领券￥ " + t.getData().getCouponAmount());


						isCollect = t.getData().isCollected();

						setCollectStatus();

						if ("1".equals(t.getData().getFreeShip())) {
//							tvGoodsTitle.setText(t.getData().getName());
							tvGoodsTitle.setText(StringUtils.addImageLabel(mContext, R.mipmap.baoyou, t.getData().getName()));
//							tvGoodsTitle.setText(StringUtils.addImageLabel(GoodsDetailActivity.this, R.mipmap.baoyou, t.getData().getName()));
						} else {
//							tvGoodsTitle.setCompoundDrawables(null, null, null, null);
							tvGoodsTitle.setText(t.getData().getName());
						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});


	}

	private void setCollectStatus() {
		if (isCollect) {
			ivCollect.setImageResource(R.mipmap.icon_collect);
			tvCollect.setText("已收藏");
		} else {
			ivCollect.setImageResource(R.mipmap.collect_1);
			tvCollect.setText("收藏");
		}
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_goods_detail;
	}

	public void toTaobao() {
		if (goodDetailEntity != null)
			showProgressDialog();
		RetrofitFactory.getInstence().API()
				.clickUrl(CacheInfo.getUserID(this), goodDetailEntity.getItemId())
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
							AlibcTrade.show(GoodsDetailActivity.this, alibcPage, alibcShowParams, null, null, new AlibcTradeCallback() {

								@Override
								public void onTradeSuccess(TradeResult tradeResult) {

								}

								@Override
								public void onFailure(int code, String msg) {
									//打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息

								}
							});


						} else {
							ToastUtils.showShort(GoodsDetailActivity.this, "请先下载淘宝");

						}


					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
						discussProgressDialog();
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
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

	@OnClick({R.id.iv_btn_back, R.id.lin_collect, R.id.lin_share, R.id.tv_get_stamps, R.id.lin_quanhoujia})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.iv_btn_back:
				finish();
				break;

			case R.id.tv_get_stamps:
				if (UserInfoUtils.isSharedLogin(this)) {
					toTaobao();
				} else {
					Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(this, LoginActivity.class));
				}

				break;
			case R.id.lin_quanhoujia:

				if (UserInfoUtils.isSharedLogin(this)) {
					toTaobao();
				} else {
					Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(this, LoginActivity.class));
				}
				break;
			case R.id.lin_collect:
				if (UserInfoUtils.isSharedLogin(this)) {
					if (goodDetailEntity != null) {
						collection(goodDetailEntity.getId());
					}

				} else {
					Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(this, LoginActivity.class));
				}

				break;
			case R.id.lin_share:
				if (UserInfoUtils.isSharedLogin(this)) {
					Intent intent = new Intent(this, ShareComissionActivity.class);
					intent.putExtra(Constants.INTENT_KEY_TYPE, goodDetailEntity);
					intent.putExtra(Constants.INTENT_KEY_URL, baseImageUrl);
					startActivity(intent);

				} else {
					Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(this, LoginActivity.class));
				}

				break;
		}
	}

	/**
	 * 删除收藏列表
	 *
	 * @param id
	 */
	public void collection(int id) {
		RetrofitFactory.getInstence().API()
				.addCollections(CacheInfo.getUserID(this), 1, id)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<String>(this) {
					@Override
					protected void onSuccees(BaseEntity<String> t) throws Exception {
						isCollect = !isCollect;
						setCollectStatus();
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});


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
