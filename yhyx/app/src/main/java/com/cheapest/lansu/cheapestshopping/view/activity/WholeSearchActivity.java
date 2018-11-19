package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.MemberEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ProductEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.SProductModel;
import com.cheapest.lansu.cheapestshopping.model.entity.SigninModel;
import com.cheapest.lansu.cheapestshopping.model.entity.TbkGoodsEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.DateUtils;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 码农哥
 * @date 2018/7/30 0030  2:20
 * @email 441293364@qq.com
 * @TODO <p/>全网搜索
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
public class WholeSearchActivity extends BaseActivity {

	@Bind(R.id.refreshLayout)
	TwinklingRefreshLayout refreshLayout;

	@Bind(R.id.recyclerView)
	RecyclerView recyclerView;
	HomeAdapter homeAdapter = null;
	List<SProductModel> mList = new ArrayList<>();

	int page = 1;

	String keywords = "";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		keywords = getIntent().getStringExtra("keywords");

		Log.e("TAG", "keywords->" + keywords);

		initView();
		initData();
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_whole_search;
	}

	private void initView() {
		getToolbarTitle().setText(keywords);
		GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
		recyclerView.setLayoutManager(gridLayoutManager);
		homeAdapter = new HomeAdapter(R.layout.item_search_product, mList);
		homeAdapter.openLoadAnimation();
		homeAdapter.setUpFetchEnable(true);
		recyclerView.setAdapter(homeAdapter);

		homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				SProductModel datasBean = (SProductModel) adapter.getItem(position);
				if (null != datasBean) {
					Intent intent = new Intent(mContext, TbkGoodsDetailActivity.class);
					intent.putExtra(Constants.INTENT_KEY_ID, datasBean.getItemId());
					intent.putExtra(Constants.INTENT_KEY_URL, datasBean.getPictUrl());
					startActivity(intent);
				}
			}
		});

		refreshLayout.setEnableLoadmore(true);
		refreshLayout.setEnableRefresh(true);
		refreshLayout.setAutoLoadMore(true);
		refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
			@Override
			public void onLoadMore(TwinklingRefreshLayout refresh) {
				super.onLoadMore(refresh);
				searchProduct();
			}

			@Override
			public void onRefresh(TwinklingRefreshLayout refreshLayout) {
				super.onRefresh(refreshLayout);
				page = 1;
				searchProduct();
				refreshLayout.setEnableLoadmore(true);
			}
		});
	}

	private void initData() {
		searchProduct();
	}

	/**
	 * 全网搜索
	 */
	private void searchProduct() {
//		RetrofitFactory.getInstence().API().searchWholeList(keywords, page).subscribeOn(Schedulers.io())
//				.observeOn(AndroidSchedulers.mainThread())
//				.subscribe(new BaseObserver<List<SProductModel>>(mContext) {
//
//					@Override
//					protected void onSuccees(BaseEntity<List<SProductModel>> t) throws Exception {
////						List<SProductModel> datas = JSON.parseArray(t.getData(), SProductModel.class);
////						Log.e("TAG", "Json=>" + JSON.toJSONString(datas));
//
////						refreshLayout.finishRefreshing();
////						refreshLayout.finishLoadmore();
////						if (page == 1) {
////							mList.clear();
////							homeAdapter.setNewData(t.getData());
////						} else {
////							homeAdapter.addData(t.getData());
////						}
////
////						mList.addAll(t.getData());
////						page++;
////						homeAdapter.loadMoreComplete();
//					}
//
//					@Override
//					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
////						Log.e("TAG", e.getMessage());
//						Log.e("TAG", e.toString());
//					}
//				});

		RetrofitFactory.getInstence().API().searchWholeList(keywords, page).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<List<SProductModel>>(WholeSearchActivity.this) {

					@Override
					protected void onSuccees(BaseEntity<List<SProductModel>> t) throws Exception {
//						Log.e("TAG", "Json=>" + JSON.toJSONString(datas));
						refreshLayout.finishRefreshing();
						refreshLayout.finishLoadmore();
						if (page == 1) {
							mList.clear();
							homeAdapter.setNewData(t.getData());
						} else {
							homeAdapter.addData(t.getData());
						}

						mList.addAll(t.getData());
						page++;
						homeAdapter.loadMoreComplete();
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}


	class HomeAdapter extends BaseQuickAdapter<SProductModel, BaseViewHolder> {
		public HomeAdapter(int layoutResId, List data) {
			super(layoutResId, data);
		}

		@Override
		protected void convert(BaseViewHolder helper, final SProductModel item) {
			helper.setText(R.id.item_home_pro_title, StringUtils.addImageLabel(WholeSearchActivity.this, item.getUserType() == 1 ? R.mipmap.icon_taobao : R.mipmap.icon_tianma, item.getTitle()));
			String picUrl = item.getPictUrl().startsWith("http") ? item.getPictUrl() : "http:" + item.getPictUrl();
			Glide.with(mContext).load(picUrl).crossFade().into((ImageView) helper.getView(R.id.item_home_pro_image));
			helper.setText(R.id.item_home_xiaoliang, "月销  " + item.getBiz30Day());
			helper.setText(R.id.item_home_pro_yuanjia, "￥ " + item.getCurrentPrice());
//			helper.setText(R.id.item_home_pro_quan, (int) item.getCouponAmount() + "元券");
//			helper.setText(R.id.item_home_pro_yuanjia, "￥ " + item.getPrice());
//			helper.setText(R.id.item_home_pro_quanhoujia, "￥ " + item.getCouponPrice());
//			((TextView) helper.getView(R.id.item_home_pro_yuanjia)).getPaint().setFlags(
//					Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
//			helper.setText(R.id.item_home_xiaoliang, "月销  " + item.getBiz30Day());

			helper.getView(R.id.item_share_friend).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					toShareFriend(item.getItemId(), item.getPictUrl());
				}
			});

			helper.getView(R.id.item_to_pay).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					toTaobao(item.getItemId());
				}
			});
		}
	}

	/**
	 * 分享好友
	 *
	 * @param itemId
	 * @param baseImageUrl
	 */
	private void toShareFriend(String itemId, String baseImageUrl) {
		RetrofitFactory.getInstence().API()
				.getTbkGoodDetail(itemId)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<TbkGoodsEntity>(this) {
					@Override
					protected void onSuccees(BaseEntity<TbkGoodsEntity> t) throws Exception {
						Intent intent = new Intent(mContext, ShareFriendActivity.class);
						intent.putExtra(Constants.INTENT_KEY_TYPE, t.getData());
						intent.putExtra(Constants.INTENT_KEY_URL, baseImageUrl);
						startActivity(intent);
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}

	/**
	 * 去Taobao
	 *
	 * @param itemId
	 */
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
							AlibcTrade.show(WholeSearchActivity.this, alibcPage, alibcShowParams, null, null, new AlibcTradeCallback() {

								@Override
								public void onTradeSuccess(TradeResult tradeResult) {

								}

								@Override
								public void onFailure(int code, String msg) {
									//打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息

								}
							});


						} else {
							ToastUtils.showShort(WholeSearchActivity.this, "请先下载淘宝");

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
}
