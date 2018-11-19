package com.cheapest.lansu.cheapestshopping.view.fragment;


import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BannerEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.CategoriesEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ClassifyEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.CommodityEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ProductEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.AppUtils;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;
import com.cheapest.lansu.cheapestshopping.utils.TextUtil;
import com.cheapest.lansu.cheapestshopping.view.activity.BrowseActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.GoodsDetailActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.ProductSumActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.WebActivity;
import com.cheapest.lansu.cheapestshopping.view.custom.GlideImageLoader;
import com.cheapest.lansu.cheapestshopping.view.custom.IScrollViewListener;
import com.cheapest.lansu.cheapestshopping.view.custom.ObservableScrollView;
import com.cheapest.lansu.cheapestshopping.view.custom.ShopGridView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 9.9专场项目
 * A simple {@link Fragment} subclass.
 */
public class NightSpecialFragment extends Fragment {

	@Bind(R.id.toolbar_title)
	TextView toolbarTitle;
	@Bind(R.id.toolbar_return_iv)
	ImageView toolbarReturnIv;
	@Bind(R.id.rlv)
	RecyclerView rlv;
	private MineAdapter mineAdapter;
	private List<ProductEntity.DatasBean> mList = new ArrayList<>();
	List<String> list1 = new ArrayList<String>();
	private int page = 1;


	@Bind(R.id.fragment_home_banner)
	Banner fragmentHomeBanner;
	@Bind(R.id.fragment_home_banner_default_iv)
	ImageView fragment_home_banner_default_iv;
	@Bind(R.id.tv)
	TextView tv;
	@Bind(R.id.sv_nine_header)
	ShopGridView svNineHeader;

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

	@Bind(R.id.refreshlayout)
	TwinklingRefreshLayout refreshlayout;

	@Bind(R.id.iv_zhiding)
	ImageView iv_zhiding;

	@Bind(R.id.scrollView)
	ObservableScrollView scrollView;

	float head_height;

	public NightSpecialFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_night_special, container, false);
		ButterKnife.bind(this, view);

		head_height = getActivity().getResources().getDimension(R.dimen.head_height);
//		rlv = view.findViewById(R.id.rlv);
//		toolbarTitle = view.findViewById(R.id.toolbar_title);
//		toolbarReturnIv = view.findViewById(R.id.toolbar_return_iv);
		GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
		rlv.setLayoutManager(layoutManager);

		mineAdapter = new MineAdapter(R.layout.item_choice_pro, mList);
//		View header = LayoutInflater.from(getActivity()).inflate(R.layout.header_view_nine, null, false);
//		mineAdapter.addHeaderView(header);
		mineAdapter.openLoadAnimation();
		mineAdapter.setUpFetchEnable(true);
//		mineAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//			@Override
//			public void onLoadMoreRequested() {
//				rlv.postDelayed(new Runnable() {
//					@Override
//					public void run() {
//						getNineBuyedList();
//					}
//
//				}, 1000);
//			}
//		}, rlv);

		rlv.setAdapter(mineAdapter);
//		com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration dividerItemDecoration = new com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration(getActivity(),
//				com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration.VERTICAL_LIST);
//		dividerItemDecoration.setDivider(R.drawable.news_divider_line_bg);
//		rlv.addItemDecoration(dividerItemDecoration);
//		rlv.setItemAnimator(new DefaultItemAnimator());
		mineAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
				intent.putExtra(Constants.INTENT_KEY_ID, mList.get(position).getId());
				intent.putExtra(Constants.INTENT_KEY_URL, mList.get(position).getIconUrl());
				startActivity(intent);
			}
		});

		refreshlayout.setEnableLoadmore(true);
		refreshlayout.setEnableRefresh(true);
		refreshlayout.setAutoLoadMore(true);
		refreshlayout.setOnRefreshListener(new RefreshListenerAdapter() {
			@Override
			public void onLoadMore(TwinklingRefreshLayout refresh) {
				super.onLoadMore(refresh);
				getNineBuyedList();
			}

			@Override
			public void onRefresh(TwinklingRefreshLayout refreshLayout) {
				super.onRefresh(refreshLayout);
				page = 1;
				list1.clear();
				getBanner();
				initeData();
				refreshLayout.setEnableLoadmore(true);
			}
		});

		scrollView.setScrollViewListener(new IScrollViewListener() {

			@Override
			public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
				if (y > head_height * 3) {
					iv_zhiding.setVisibility(View.VISIBLE);
				} else {
					iv_zhiding.setVisibility(View.GONE);
				}
			}
		});

		iv_zhiding.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						scrollView.fullScroll(ScrollView.FOCUS_UP);
					}
				});
			}
		});

		return view;
		//设置图片加载器
	}

	@Override
	public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
		return super.onCreateAnimation(transit, enter, nextAnim);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		/**
		 * 动态计算图片Layout高度
		 */
		LinearLayout.LayoutParams params01 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, AppUtils.dip2px(getActivity(), 126));
		ll_choice_01.setLayoutParams(params01);

		LinearLayout.LayoutParams params02 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, AppUtils.dip2px(getActivity(), 120));
		ll_choice_02.setLayoutParams(params02);

		toolbarReturnIv.setVisibility(View.GONE);
		toolbarTitle.setText(R.string.home_choice);
		fragmentHomeBanner.setImageLoader(new GlideImageLoader());
		//设置自动轮播，默认为true
		fragmentHomeBanner.isAutoPlay(true);
		//设置轮播时间
		fragmentHomeBanner.setDelayTime(3000);
		//设置指示器位置（当banner模式中有指示器时）
		fragmentHomeBanner.setIndicatorGravity(BannerConfig.CENTER);
		getBanner();
		TextUtil.setTextType(getActivity(), tv);

	}

	private void getBanner() {
		RetrofitFactory.getInstence().API()
				.getBanner(2)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<List<BannerEntity>>(getActivity()) {
					@Override
					protected void onSuccees(final BaseEntity<List<BannerEntity>> t) throws Exception {
						for (BannerEntity bannerEntity : t.getData()) {
							list1.add(bannerEntity.getImageUrl());
						}
						if(list1!=null&&list1.size()>0){
							fragment_home_banner_default_iv.setVisibility(View.GONE);
						}
						fragmentHomeBanner.setImages(list1);
						fragmentHomeBanner.setOnBannerListener(new OnBannerListener() {
							@Override
							public void OnBannerClick(int position) {

								if (t.getData().get(position).getUrlType() == 1) {
									Intent intent = new Intent(getActivity(), WebActivity.class);
									intent.putExtra(Constants.INTENT_KEY_TITLE, t.getData().get(position).getTitle());
									intent.putExtra(Constants.INTENT_KEY_URL, t.getData().get(position).getTarget());
									startActivity(intent);
								} else {
									// todo 去特色分类

								}


							}
						});
						fragmentHomeBanner.start();
						initeData();
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});


	}

	/**
	 * 初始化数据
	 */
	private void initeData() {
		getModuleCategory();
		getImportedCommend();//获取进口优选
		getNineBuyedList();
	}


	/**
	 * 获取模块分类
	 */
	private void getModuleCategory() {
		RetrofitFactory.getInstence().API()
				.moduleCategories(2)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<List<ClassifyEntity>>(getActivity()) {
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
									Intent intent = new Intent(getActivity(), ProductSumActivity.class);
									intent.putExtra(Constants.INTENT_KEY_TITLE, t.getData().get(finalI).getName());
									intent.putExtra(Constants.INTENT_KEY_ID, t.getData().get(finalI).getId() + "");
									intent.putExtra(Constants.INTENT_KEY_TYPE, 7);
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
	 * 获取进口优选
	 */
	private void getImportedCommend() {
		RetrofitFactory.getInstence().API()
				.getImportedList(1, page, 100)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<ProductEntity>(getActivity()) {
					@Override
					protected void onSuccees(final BaseEntity<ProductEntity> t) throws Exception {

						svNineHeader.setData(t.getData(), "进口优选");
						svNineHeader.getTvShopViewTitle().setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent = new Intent(getActivity(), ProductSumActivity.class);
								intent.putExtra(Constants.INTENT_KEY_TITLE, "今日推荐");
								intent.putExtra(Constants.INTENT_KEY_ID, "");
								intent.putExtra(Constants.INTENT_KEY_TYPE, 4);
								startActivity(intent);
							}
						});

					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});


	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	class MineAdapter extends BaseQuickAdapter<ProductEntity.DatasBean, BaseViewHolder> {
		public MineAdapter(int layoutResId, List data) {
			super(layoutResId, data);
		}

		@Override
		protected void convert(BaseViewHolder helper, ProductEntity.DatasBean item) {
			Glide.with(mContext).load(item.getIconUrl()).crossFade().into((ImageView) helper.getView(R.id.item_home_pro_image));
			helper.setText(R.id.item_home_pro_title, StringUtils.addImageLabel(getActivity(), item.getType() == 1 ? R.mipmap.icon_taobao : R.mipmap.icon_tianma, item.getName()));
			helper.setText(R.id.item_home_pro_quan, (int) item.getCouponAmount() + "元券");
			helper.setText(R.id.item_home_pro_yuanjia, "￥" + item.getPrice());
			helper.setText(R.id.item_home_pro_quanhoujia, "￥" + item.getDiscountPrice());
			helper.setText(R.id.tv_commision, "预估佣金：¥" + item.getMineCommision());
			((TextView) helper.getView(R.id.item_home_pro_yuanjia)).getPaint().setFlags(
					Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰

			helper.setText(R.id.item_home_xiaoliang, "月销  " + item.getSellNum());
		}
	}

	/**
	 * 获取品牌精选
	 */
	public void getNineBuyedList() {
		RetrofitFactory.getInstence().API()
				.getPinPaiList(2, page, 6)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<ProductEntity>(getActivity()) {
					@Override
					protected void onSuccees(final BaseEntity<ProductEntity> t) throws Exception {
						refreshlayout.finishRefreshing();
						refreshlayout.finishLoadmore();
						if (mList.size() >= t.getData().getTotal()) {
							//数据全部加载完毕
							mineAdapter.loadMoreComplete();
							mineAdapter.loadMoreEnd();
						} else {
							if (page == 1) {
								mList.clear();
								mineAdapter.setNewData(t.getData().getDatas());
							} else {
								mineAdapter.addData(t.getData().getDatas());
							}
							//   mCurrentCounter = mQuickAdapter.getData().size();
							mList.addAll(t.getData().getDatas());
							page++;
							mineAdapter.loadMoreComplete();
						}
//						if (mList.size() >= t.getData().getTotal()) {
//							//数据全部加载完毕
//							mineAdapter.loadMoreEnd();
//						} else {
//							mineAdapter.addData(t.getData().getDatas());
//							mineAdapter.loadMoreComplete();
//							page++;
//						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
						refreshlayout.finishRefreshing();
						refreshlayout.finishLoadmore();
					}
				});
	}


}
