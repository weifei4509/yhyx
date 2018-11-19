package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ClassifyEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ProductEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.AppUtils;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;
import com.cheapest.lansu.cheapestshopping.view.fragment.HomeFragment;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 码农哥
 * @date 2018/7/29 0029  17:29
 * @email 441293364@qq.com
 * @TODO <p/>爱生活
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
public class LoveLifeActivity extends BaseActivity {

	@Bind(R.id.refreshLayout)
	TwinklingRefreshLayout refreshLayout;

	@Bind({R.id.ll_choice_01})
	LinearLayout ll_choice_01;

	@Bind({R.id.ll_choice_02})
	LinearLayout ll_choice_02;


	@Bind(R.id.iv_header_choice_01)
	ImageView iv_header_choice_01;

	@Bind(R.id.iv_header_choice_02)
	ImageView iv_header_choice_02;

	@Bind(R.id.iv_header_choice_03)
	ImageView iv_header_choice_03;

	@Bind(R.id.iv_header_choice_04)
	ImageView iv_header_choice_04;

	@Bind(R.id.iv_header_choice_05)
	ImageView iv_header_choice_05;

	@Bind(R.id.iv_header_choice_06)
	ImageView iv_header_choice_06;

	@Bind(R.id.lin_baobeileixing)
	LinearLayout linBaobeileixing;

	@Bind(R.id.recyclerView)
	RecyclerView recyclerView;
	HomeAdapter homeAdapter;
	List<ProductEntity.DatasBean> mList = new ArrayList<>();

	@Bind(R.id.tablayout)
	TabLayout tablayout;

	String type = ""; //宝贝类型 1-淘宝 2-天猫 不传则为全部
	String sortType = ""; //排序字段 1销量 2- 最新 3-券额 4-券后价 默认1
	int page = 1;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initData();
	}

	private void initView() {
		getToolbarTitle().setText("爱生活");

		/**
		 * 动态计算图片Layout高度
		 */
		LinearLayout.LayoutParams params01 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, AppUtils.dip2px(mContext, 126));
		ll_choice_01.setLayoutParams(params01);

		LinearLayout.LayoutParams params02 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, AppUtils.dip2px(mContext, 120));
		ll_choice_02.setLayoutParams(params02);

		tablayout.addTab(tablayout.newTab().setText("销量"));
		tablayout.addTab(tablayout.newTab().setText("最新"));
		tablayout.addTab(tablayout.newTab().setText("券额"));
		tablayout.addTab(tablayout.newTab().setText("券后价"));

		tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				sortType = (tab.getPosition() + 1) + "";
				page = 1;
				homeAdapter.notifyItemRangeRemoved(0, mList.size());
				getLoveLifeList();
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});

		GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
		recyclerView.setLayoutManager(gridLayoutManager);
		homeAdapter = new HomeAdapter(R.layout.item_choice_pro, mList);
		homeAdapter.openLoadAnimation();
		homeAdapter.setUpFetchEnable(true);
		recyclerView.setAdapter(homeAdapter);

		homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				ProductEntity.DatasBean datasBean = (ProductEntity.DatasBean) adapter.getItem(position);
				if (null != datasBean) {
					Intent intent = new Intent(mContext, GoodsDetailActivity.class);
					intent.putExtra(Constants.INTENT_KEY_ID, datasBean.getId());
					intent.putExtra(Constants.INTENT_KEY_URL, datasBean.getIconUrl());
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
				getLoveLifeList();
			}

			@Override
			public void onRefresh(TwinklingRefreshLayout refreshLayout) {
				super.onRefresh(refreshLayout);
				page = 1;
				initData();
				refreshLayout.setEnableLoadmore(true);
			}
		});

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
				getLoveLifeList();
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
				getLoveLifeList();
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

	private void initData() {
		getModuleCategory();
		getLoveLifeList();
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_love_life;
	}


	/**
	 * 获取模块分类
	 */
	private void getModuleCategory() {
		RetrofitFactory.getInstence().API()
				.moduleCategories(1)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<List<ClassifyEntity>>(mContext) {
					@Override
					protected void onSuccees(BaseEntity<List<ClassifyEntity>> t) throws Exception {
						List<ImageView> imgList = new ArrayList<>();
						imgList.add(iv_header_choice_01);
						imgList.add(iv_header_choice_02);
						imgList.add(iv_header_choice_03);
						imgList.add(iv_header_choice_04);
						imgList.add(iv_header_choice_05);
						imgList.add(iv_header_choice_06);
						for (int i = 0; i < t.getData().size(); i++) {
							final int finalI = i;
							ImageLoader.getInstance().displayImage(t.getData().get(i).getIconUrl(), imgList.get(i));
							imgList.get(i).setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View view) {
									Intent intent = new Intent(mContext, ProductSumActivity.class);
									intent.putExtra(Constants.INTENT_KEY_TITLE, t.getData().get(finalI).getName());
									intent.putExtra(Constants.INTENT_KEY_ID, t.getData().get(finalI).getId() + "");
									intent.putExtra(Constants.INTENT_KEY_TYPE, 6);
									startActivity(intent);
								}
							});
						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}

	/**
	 * 获取爱生活列表
	 */
	private void getLoveLifeList() {
		RetrofitFactory.getInstence().API()
				.getLoveLifeList(1, type, sortType, page, 6)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<ProductEntity>(mContext) {
					@Override
					protected void onSuccees(BaseEntity<ProductEntity> t) throws Exception {
						refreshLayout.finishRefreshing();
						refreshLayout.finishLoadmore();

						if (mList.size() >= t.getData().getTotal()) {
							//数据全部加载完毕
							homeAdapter.loadMoreComplete();
							homeAdapter.loadMoreEnd();
						} else {
							if (page == 1) {
								homeAdapter.setNewData(t.getData().getDatas());
							} else {
								homeAdapter.addData(t.getData().getDatas());
							}
							//   mCurrentCounter = mQuickAdapter.getData().size();
							page++;
							homeAdapter.loadMoreComplete();
						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}

	class HomeAdapter extends BaseQuickAdapter<ProductEntity.DatasBean, BaseViewHolder> {
		public HomeAdapter(int layoutResId, List data) {
			super(layoutResId, data);
		}

		@Override
		protected void convert(BaseViewHolder helper, ProductEntity.DatasBean item) {
			helper.setText(R.id.item_home_pro_title, StringUtils.addImageLabel(LoveLifeActivity.this, item.getType() == 1 ? R.mipmap.icon_taobao : R.mipmap.icon_tianma, item.getName()));
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
}
