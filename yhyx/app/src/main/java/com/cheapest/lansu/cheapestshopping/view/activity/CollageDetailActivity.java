package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.AddressListModel;
import com.cheapest.lansu.cheapestshopping.model.entity.AddressModel;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.CollageDetailEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.CustModel;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.Util;
import com.cheapest.lansu.cheapestshopping.view.adapter.UserFaceAdapter;
import com.cheapest.lansu.cheapestshopping.view.custom.GlideImageLoader;
import com.cheapest.lansu.cheapestshopping.view.dialog.ShareDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 码农哥
 * @date 2018/7/20 0020  23:41
 * @email 441293364@qq.com
 * @TODO <p/>拼团详情
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
public class CollageDetailActivity extends BaseActivity {

	@Bind(R.id.layout_customer_group)
	LinearLayout layout_customer_group;

	@Bind(R.id.recyclerView)
	RecyclerView recyclerView;

	@Bind(R.id.tv_pintuan_title)
	TextView tv_pintuan_title;

	@Bind(R.id.tv_pintuan_num)
	TextView tv_pintuan_num;

	@Bind(R.id.tv_stock_num)
	TextView tv_stock_num;

	@Bind(R.id.tv_pintuan_jia)
	TextView tv_pintuan_jia;

	@Bind(R.id.webView)
	WebView webView;

	UserFaceAdapter mAdapter;

	int id = 0;

	@Bind(R.id.collage_banner)
	Banner collage_banner;

	List<CustModel> datas = new ArrayList<>();

	CollageDetailEntity collageDetail = null;

	@Bind({R.id.recycler_tag})
	RecyclerView recycler_tag;

	GoodsTagAdapter tagAdapter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
		getToolbarTitle().setText("拼团商品");

		id = getIntent().getIntExtra("id", 0);

		initView();
		initData();
	}

	private void initView() {
		collage_banner.setImageLoader(new GlideImageLoader());
		//设置自动轮播，默认为true
		collage_banner.isAutoPlay(true);
		//设置轮播时间
		collage_banner.setDelayTime(3000);
		//设置指示器位置（当banner模式中有指示器时）
		collage_banner.setIndicatorGravity(BannerConfig.CENTER);

		LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
		mAdapter = new UserFaceAdapter(datas);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(mAdapter);

		tagAdapter = new GoodsTagAdapter("#8a8a8a");
		GridLayoutManager gridLayoutManager1 = new GridLayoutManager(mContext, 4);
		recycler_tag.setAdapter(tagAdapter);
		recycler_tag.setLayoutManager(gridLayoutManager1);
		tagAdapter.setEnableLoadMore(false);
	}

	private void initData() {
		getCollageDetail();
	}

	private void getCollageDetail() {
		RetrofitFactory.getInstence().API().collageDetail("api/group/group/" + id + "?userId=" + CacheInfo.getUserID(mContext))
				.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<CollageDetailEntity>(this) {

					@Override
					protected void onSuccees(BaseEntity<CollageDetailEntity> t) throws Exception {
						if (t.getData() != null) {
							initCollageInfo(t.getData());
						}

					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(Object event) {
		AddressModel addressModel = (AddressModel) event;
		if (null != addressModel) {
			openCollage(addressModel.getId());
		}

		Log.e("TAG", "addressModel->" + JSON.toJSONString(addressModel));
	}

	/**
	 * 开启拼团
	 *
	 * @param addressId
	 */
	private void openCollage(String addressId) {
		RetrofitFactory.getInstence().API().openCollage(id, CacheInfo.getUserID(this), addressId)
				.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<CollageDetailEntity.CustomerGroupModel>(this) {

					@Override
					protected void onSuccees(BaseEntity<CollageDetailEntity.CustomerGroupModel> t) throws Exception {
						discussProgressDialog();
						if (t.getData() == null) {
							layout_customer_group.setVisibility(View.GONE);
						} else {
							collageDetail.setCustomerGroup(t.getData());
							layout_customer_group.setVisibility(View.VISIBLE);
							initCustomerGroup(t.getData());
						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
						discussProgressDialog();
					}

				});
	}

	/**
	 * 初始化拼团详情
	 *
	 * @param data
	 */
	private void initCollageInfo(CollageDetailEntity data) {
		collageDetail = data;
		collage_banner.setImages(Arrays.asList(data.getImagesUrls()));
		collage_banner.start();
		tv_pintuan_title.setText(data.getName());
		tv_pintuan_num.setText(data.getGroupNum());
		tv_stock_num.setText(data.getStock());
//		tv_pintuan_jia.setText(data.getGroupPrice());

		if (data.getCustomerGroup() == null) {
			layout_customer_group.setVisibility(View.GONE);
		} else {
			layout_customer_group.setVisibility(View.VISIBLE);
			initCustomerGroup(data.getCustomerGroup());
		}

		tagAdapter.setNewData(Arrays.asList(data.getLabels().split(",")));

		webView.getSettings().setJavaScriptEnabled(true);
		// 设置可以支持缩放
		webView.getSettings().setSupportZoom(false);
		// 设置出现缩放工具
		webView.getSettings().setBuiltInZoomControls(false);
		// 为图片添加放大缩小功能
		//webview.getSettings().setUseWideViewPort(true);
		//自适应屏幕
		webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		webView.getSettings().setLoadWithOverviewMode(true);

		webView.setHorizontalScrollBarEnabled(false);//禁止水平滚动
		webView.setVerticalScrollBarEnabled(true);//允许垂直滚动

		webView.loadDataWithBaseURL("", getDetailContent(data.getContent()), "text/html", "UTF-8", "");
	}

	@Bind(R.id.tv_collage_num)
	TextView tv_collage_num;

	@Bind(R.id.text_time_countdown)
	CountdownView text_time_countdown;

	@Bind(R.id.tv_pintuan)
	TextView tv_pintuan;

	/**
	 * 初始化拼团信息
	 *
	 * @param customerGroup
	 */
	private void initCustomerGroup(CollageDetailEntity.CustomerGroupModel customerGroup) {
		if (collageDetail.getCustomerGroup() != null) {
			int num = customerGroup.getTargetNum() - customerGroup.getInviteNum();
			tv_collage_num.setText("还差" + num + "人拼团成功");
			text_time_countdown.start(customerGroup.getResidueSecond() * 1000);
			datas.addAll(customerGroup.getCustList());
//			for (int i = 0; i < num; i++) {
//				datas.add(new CustModel());
//			}
			mAdapter.notifyDataSetChanged();
			tv_pintuan.setText("邀请好友");
		}

		if (collageDetail.getCustomerGroup() != null && collageDetail.getCustomerGroup().getStatus() == 2) {
			tv_pintuan.setText("拼团失败");
			tv_pintuan.setBackgroundColor(getResources().getColor(R.color.gray));
		} else if (collageDetail.getCustomerGroup() != null && collageDetail.getCustomerGroup().getStatus() == 1) {
			tv_pintuan.setText("拼团完成");
			tv_pintuan.setBackgroundColor(getResources().getColor(R.color.gray));
		} else if (collageDetail.getCustomerGroup() != null && collageDetail.getCustomerGroup().getStatus() == 0) {
			tv_pintuan.setText("邀请好友");
		} else if (collageDetail.getCustomerGroup() != null && collageDetail.getCustomerGroup().getStatus() == 3) {
			tv_pintuan.setText("取消拼团");
			tv_pintuan.setBackgroundColor(getResources().getColor(R.color.gray));
		} else {
			tv_pintuan.setText("马上拼团");
		}
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_collage_detail;
	}


	public static String getDetailContent(String htmltext) {
		Document doc = Jsoup.parse(htmltext);
		Elements elements = doc.getElementsByTag("img");
		for (Element element : elements) {
			element.attr("width", "100%").attr("height", "auto");
		}

		return doc.toString();
	}

	@OnClick({R.id.tv_pintuan})
	public void onClickViewed(View v) {
		switch (v.getId()) {
			case R.id.tv_pintuan:
				if (collageDetail.getCustomerGroup() != null && collageDetail.getCustomerGroup().getStatus() != 2) {
					Bundle args = new Bundle();
					args.putString("title", "帮我免费得商品，就差你来注册了");
					args.putString("content", collageDetail.getName());
					args.putString("iconUrl", collageDetail.getIconUrl());
					args.putString("shareUrl", "http://jiuchiwl.cn/other/recommend?recommend=" + CacheInfo.getUserInfoFromCache(mContext).getRecommendCode());
					ShareDialog.newInstance(this, args).show(CollageDetailActivity.this.getSupportFragmentManager(), "inviteApp");
				} else if (collageDetail.getCustomerGroup() != null && collageDetail.getCustomerGroup().getStatus() == 2) {
					tv_pintuan.setText("拼团失败");
					tv_pintuan.setBackgroundColor(getResources().getColor(R.color.gray));
				} else {
					startActivity(new Intent(mContext, AddressManagerActivity.class));
				}
				break;
		}
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	public class GoodsTagAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
		String color = "#e33e09";

		public GoodsTagAdapter(String mColor) {
			super(R.layout.item_tag_listview, null);
			if (Util.isNotEmpty(mColor))
				color = mColor;
		}

		@Override
		protected void convert(BaseViewHolder helper, String item) {
			helper.setText(R.id.item_title, item);
			helper.setTextColor(R.id.item_title, Color.parseColor(color));
		}
	}

}
