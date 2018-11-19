package com.cheapest.lansu.cheapestshopping.view.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ProductEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.AppUtils;
import com.cheapest.lansu.cheapestshopping.utils.ImageUrl;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 根据类别查看商品
 */
public class ProductSumActivity extends BaseActivity {

	@Bind(R.id.lin_baobeileixing)
	LinearLayout linBaobeileixing;

	@Bind(R.id.tablayout)
	TabLayout tablayout;
	@Bind(R.id.rlv)
	RecyclerView rlv;
	private int typeBrand = 1;              //是品牌商家还是分类         1 普通分类  2 特殊分类  3 商家 4今日推荐

//	private int page = 1;
//	private int size = 4;
//	private int sortType = 1;
//	private String type = "";
//	private int orderType = 2;

	String specialCategoryId = "";//	int	N	特色分类id
	int module = 1;//	int	N	模块 1-爱生活 2-品牌精选
	int imported = 0;//	int	N	是否进口优选 1-是 0-否
	int needBuyed = 0;//		N	是否必买清单 1-是 0-否
	String keywords = "";//	String	N	搜券时候传入的关键字
	String type = "";//	int	N	宝贝类型 1-淘宝 2-天猫 不传则为全部
	int sortType = 1;//	int	N	排序字段 1销量 2- 最新 3-券额 4-券后价 默认1
	int orderType = 2;//	int	N	排序 方式 1-升序 2-降序 默认1
	int page = 1;//	int	N	分页参数
	int size = 10;//	int	N	分页参数

	private HomeAdapter homeAdapter;
	private String productId;
	private List<ProductEntity.DatasBean> mList = new ArrayList<>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		productId = getIntent().getStringExtra(Constants.INTENT_KEY_ID);
		specialCategoryId = productId + "";
		super.onCreate(savedInstanceState);
		String title = getIntent().getStringExtra(Constants.INTENT_KEY_TITLE);
		typeBrand = getIntent().getIntExtra(Constants.INTENT_KEY_TYPE, 1);
		if (title != null) {
			getToolbarTitle().setText(title);
		}

		if (typeBrand == 3) {
			linBaobeileixing.setVisibility(View.GONE);
		}

		//  tablayout.addTab(tablayout.newTab().setText("综合"));
		tablayout.addTab(tablayout.newTab().setText("销量"));
		tablayout.addTab(tablayout.newTab().setText("最新"));
		tablayout.addTab(tablayout.newTab().setText("券额"));
		tablayout.addTab(tablayout.newTab().setText("券后价"));
		//   LinearLayoutManager llm = new LinearLayoutManager(this);
		//    llm.setOrientation(LinearLayoutManager.VERTICAL);
		rlv.setLayoutManager(new GridLayoutManager(this, 2));
		homeAdapter = new HomeAdapter(R.layout.item_priduct_sum, mList);
		homeAdapter.openLoadAnimation();
		homeAdapter.setUpFetchEnable(true);
		// rlv.addItemDecoration(new DividerItemDecoration(this , DividerItemDecoration.VERTICAL));
		homeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
			@Override
			public void onLoadMoreRequested() {
				getData();

			}
		}, rlv);
		rlv.setAdapter(homeAdapter);
		homeAdapter.setEmptyView(R.layout.view_empty);

		tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				sortType = tab.getPosition() + 1;
				page = 1;
				homeAdapter.notifyItemRangeRemoved(0, mList.size());
				getData();
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});


		homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				Intent intent = new Intent(ProductSumActivity.this, GoodsDetailActivity.class);
				intent.putExtra(Constants.INTENT_KEY_ID, mList.get(position).getId());
				intent.putExtra(Constants.INTENT_KEY_URL, mList.get(position).getIconUrl());
				startActivity(intent);
			}
		});
		getData();
		PopupWindow popupWindow = new PopupWindow(this, null, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		popupWindow.setFocusable(true);
		popupWindow.setTouchable(true);
		popupWindow.setTouchInterceptor(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					popupWindow.dismiss();
					return false;
				}
				return false;
			}
		});
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1.0f; //0.0-1.0
				getWindow().setAttributes(lp);
			}
		});
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		View view = LayoutInflater.from(this).inflate(R.layout.dialog_baobei, null);
		popupWindow.setContentView(view);
		Button btnTianmao = popupWindow.getContentView().findViewById(R.id.btn_tianmao);
		btnTianmao.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				type = "2";
				page = 1;
				homeAdapter.notifyItemRangeRemoved(0, mList.size());
				getData();
				popupWindow.dismiss();
			}
		});

		Button bymTaobao = popupWindow.getContentView().findViewById(R.id.btn_taobao);
		bymTaobao.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				type = "1";
				page = 1;
				homeAdapter.notifyItemRangeRemoved(0, mList.size());
				getData();
				popupWindow.dismiss();
			}
		});


		linBaobeileixing.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 0.5f; //0.0-1.0
				getWindow().setAttributes(lp);
				popupWindow.showAsDropDown(linBaobeileixing);
			}
		});
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_product_sum;
	}

	class HomeAdapter extends BaseQuickAdapter<ProductEntity.DatasBean, BaseViewHolder> {
		public HomeAdapter(int layoutResId, List data) {
			super(layoutResId, data);
		}

		@Override
		protected void convert(BaseViewHolder helper, ProductEntity.DatasBean item) {
			helper.setText(R.id.item_home_pro_title, StringUtils.addImageLabel(ProductSumActivity.this, item.getType() == 1 ? R.mipmap.icon_taobao : R.mipmap.icon_tianma, item.getName()));
			helper.setText(R.id.item_home_pro_quan, (int) item.getCouponAmount() + "元券");
			helper.setText(R.id.item_home_pro_yuanjia, "￥ " + item.getPrice());
			helper.setText(R.id.item_home_pro_quanhoujia, "￥ " + item.getDiscountPrice());
			helper.setText(R.id.tv_commision, "预估佣金：¥" + item.getMineCommision());
			((TextView) helper.getView(R.id.item_home_pro_yuanjia)).getPaint().setFlags(
					Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
			Glide.with(mContext).load(item.getIconUrl()).crossFade().into((ImageView) helper.getView(R.id.item_home_pro_image));
			helper.setText(R.id.item_home_xiaoliang, "月销  " + item.getSellNum());

		}
	}


	public void getData() {
		switch (typeBrand) {
			case 1:
//				RetrofitFactory.getInstence().API()
//						.commodityList(productId, type, sortType, orderType, page, size)
//						.subscribeOn(Schedulers.io())
//						.observeOn(AndroidSchedulers.mainThread())
//						.subscribe(new BaseObserver<ProductEntity>(this) {
//							@Override
//							protected void onSuccees(final BaseEntity<ProductEntity> t) throws Exception {
//								if (page == 1) {
//									mList.clear();
//								}
//								if (mList.size() >= t.getData().getTotal()) {
//									//数据全部加载完毕
//									homeAdapter.loadMoreEnd();
//								} else {
//									homeAdapter.addData(t.getData().getDatas());
//									//   mCurrentCounter = mQuickAdapter.getData().size();
//									homeAdapter.loadMoreComplete();
//									page++;
//								}
//
//							}
//
//							@Override
//							protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//
//							}
//						});
				RetrofitFactory.getInstence().API()
						.featureList(specialCategoryId, sortType, type, page, size)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new BaseObserver<ProductEntity>(this) {
							@Override
							protected void onSuccees(final BaseEntity<ProductEntity> t) throws Exception {
								if (page == 1) {
									mList.clear();
								}
								if (mList.size() >= t.getData().getTotal()) {
									//数据全部加载完毕
									homeAdapter.loadMoreEnd();
								} else {
									homeAdapter.addData(t.getData().getDatas());
									//   mCurrentCounter = mQuickAdapter.getData().size();
									homeAdapter.loadMoreComplete();
									page++;
								}
							}

							@Override
							protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

							}
						});

				break;
			case 2:
				RetrofitFactory.getInstence().API()
						.specialCommodityList(productId, type, sortType, orderType, page, size)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new BaseObserver<ProductEntity>(this) {
							@Override
							protected void onSuccees(final BaseEntity<ProductEntity> t) throws Exception {
								if (page == 1) {
									mList.clear();
								}
								if (mList.size() >= t.getData().getTotal()) {
									//数据全部加载完毕
									homeAdapter.loadMoreEnd();
								} else {
									homeAdapter.addData(t.getData().getDatas());
									//   mCurrentCounter = mQuickAdapter.getData().size();
									homeAdapter.loadMoreComplete();
									page++;
								}

							}

							@Override
							protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

							}
						});
				break;
			case 3:
				RetrofitFactory.getInstence().API()
						.brandCommodityList(productId, sortType, orderType, page, size)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new BaseObserver<ProductEntity>(this) {
							@Override
							protected void onSuccees(final BaseEntity<ProductEntity> t) throws Exception {
								if (page == 1) {
									mList.clear();
								}
								if (mList.size() >= t.getData().getTotal()) {
									//数据全部加载完毕
									homeAdapter.loadMoreEnd();
								} else {
									homeAdapter.addData(t.getData().getDatas());
									//   mCurrentCounter = mQuickAdapter.getData().size();
									homeAdapter.loadMoreComplete();
									page++;
								}

							}

							@Override
							protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

							}
						});
				break;
			case 4:
				RetrofitFactory.getInstence().API()
						.todayRecommendedCommodityList(type, sortType + "", orderType + "", page, size)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new BaseObserver<ProductEntity>(this) {
							@Override
							protected void onSuccees(final BaseEntity<ProductEntity> t) throws Exception {
								if (page == 1) {
									mList.clear();
								}
								if (mList.size() >= t.getData().getTotal()) {
									//数据全部加载完毕
									homeAdapter.loadMoreEnd();
								} else {
									homeAdapter.addData(t.getData().getDatas());
									//   mCurrentCounter = mQuickAdapter.getData().size();
									homeAdapter.loadMoreComplete();
									page++;
								}

							}

							@Override
							protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

							}
						});
				break;
//			http://jiuchiwl.cn/api/commodity/commodities?subCategoryId=52&sortType=1&page=1&size=10
//			http://jiuchiwl.cn/api/commodity/commodities?subCategoryId=27&sortType=1&page=1&size=10

			case 5:
				RetrofitFactory.getInstence().API()
						.getClassifyList(specialCategoryId, sortType, type, page, size)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new BaseObserver<ProductEntity>(this) {
							@Override
							protected void onSuccees(final BaseEntity<ProductEntity> t) throws Exception {
								if (page == 1) {
									mList.clear();
								}
								if (mList.size() >= t.getData().getTotal()) {
									//数据全部加载完毕
									homeAdapter.loadMoreEnd();
								} else {
									homeAdapter.addData(t.getData().getDatas());
									//   mCurrentCounter = mQuickAdapter.getData().size();
									homeAdapter.loadMoreComplete();
									page++;
								}

							}

							@Override
							protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

							}
						});
				break;

			case 6://爱生活模块
				RetrofitFactory.getInstence().API()
						.getLoveLifeList(specialCategoryId, sortType, type, page, size, 1)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new BaseObserver<ProductEntity>(this) {
							@Override
							protected void onSuccees(final BaseEntity<ProductEntity> t) throws Exception {
								if (page == 1) {
									mList.clear();
								}
								if (mList.size() >= t.getData().getTotal()) {
									//数据全部加载完毕
									homeAdapter.loadMoreEnd();
								} else {
									homeAdapter.addData(t.getData().getDatas());
									//   mCurrentCounter = mQuickAdapter.getData().size();
									homeAdapter.loadMoreComplete();
									page++;
								}
							}

							@Override
							protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

							}
						});
				break;

			case 7://品牌精选
				RetrofitFactory.getInstence().API()
						.getLoveLifeList(specialCategoryId, sortType, type, page, size, 2)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new BaseObserver<ProductEntity>(this) {
							@Override
							protected void onSuccees(final BaseEntity<ProductEntity> t) throws Exception {
								if (page == 1) {
									mList.clear();
								}
								if (mList.size() >= t.getData().getTotal()) {
									//数据全部加载完毕
									homeAdapter.loadMoreEnd();
								} else {
									homeAdapter.addData(t.getData().getDatas());
									//   mCurrentCounter = mQuickAdapter.getData().size();
									homeAdapter.loadMoreComplete();
									page++;
								}
							}

							@Override
							protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

							}
						});
				break;
		}

	}


}
