package com.cheapest.lansu.cheapestshopping.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ClassifyEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.MenuEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ProductEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;
import com.cheapest.lansu.cheapestshopping.view.activity.GoodsDetailActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.ProductSumActivity;
import com.cheapest.lansu.cheapestshopping.view.adapter.ClassifyListAdapter;
import com.cheapest.lansu.cheapestshopping.view.custom.IScrollViewListener;
import com.cheapest.lansu.cheapestshopping.view.custom.ObservableScrollView;
import com.cheapest.lansu.cheapestshopping.view.fragment.home.ProductListFragment;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.http.Query;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * @author 码农哥
 * @date 2018/7/22 0022  22:22
 * @email 441293364@qq.com
 * @TODO <p/>首页
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
@SuppressLint("ValidFragment")
public class HomeTwoFragment extends Fragment {

	@Bind(R.id.refreshLayout)
	TwinklingRefreshLayout refreshLayout;

	@Bind(R.id.tablayout)
	TabLayout tablayout;

	@Bind(R.id.lin_baobeileixing)
	LinearLayout linBaobeileixing;

	@Bind(R.id.rlv)
	RecyclerView rlv;

	@Bind(R.id.scrollView)
	ObservableScrollView scrollView;

	@Bind(R.id.iv_zhiding)
	ImageView iv_zhiding;

	String categoryId = "";//	int	N	一级分类id 查询分类列表时必传，搜券时选填
	String subCategoryId = "";//	int	N	二级分类id
	String type = "";//	int	N	宝贝类型 1-淘宝 2-天猫 不传则为全部
	String sortType = "1";//	int	N	排序字段 1销量 2- 最新 3-券额 4-券后价 默认1
	int page = 1;//	int	N	分页参数
	int size = 10;//	int	N	分页参数

//	String specialCategoryId = "";//	int	N	特色分类id
//	int module = 1;//	int	N	模块 1-爱生活 2-品牌精选
//	int imported = 0;//	int	N	是否进口优选 1-是 0-否
//	int needBuyed = 0;//		N	是否必买清单 1-是 0-否
//	String keywords = "";//	String	N	搜券时候传入的关键字
//	int orderType = 2;//	int	N	排序 方式 1-升序 2-降序 默认1


	private HomeAdapter homeAdapter;
	private List<ProductEntity.DatasBean> mList = new ArrayList<>();

	String pid;


	//	顶部分类
	@Bind(R.id.recyclerView)
	RecyclerView recyclerView;

	public static HomeTwoFragment newInstance(Bundle extras) {
		HomeTwoFragment fragment = new HomeTwoFragment();
		if (extras != null) {
			fragment.setArguments(extras);
		}
		return fragment;
	}


	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home_two, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
//		this.categoryId = categoryId + "";
//		this.pid = categoryId + "";

		this.categoryId = getArguments().getInt("categoryId") + "";
		this.pid = categoryId;
		head_height = getActivity().getResources().getDimension(R.dimen.head_height);

		initView();
		initData();
	}


	float head_height;

	private List<MenuEntity> categoryList = new ArrayList<>();

	ClassifyListAdapter mAdapter = null;

	private void initView() {
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


		refreshLayout.setEnableLoadmore(true);
		refreshLayout.setEnableRefresh(true);
		refreshLayout.setAutoLoadMore(true);
		refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
			@Override
			public void onLoadMore(TwinklingRefreshLayout refresh) {
				super.onLoadMore(refresh);
				getCommodityList();
			}

			@Override
			public void onRefresh(TwinklingRefreshLayout refreshLayout) {
				super.onRefresh(refreshLayout);
				page = 1;
				initData();
				refreshLayout.setEnableLoadmore(true);
			}
		});

		tablayout.addTab(tablayout.newTab().setText("销量"));
		tablayout.addTab(tablayout.newTab().setText("最新"));
		tablayout.addTab(tablayout.newTab().setText("券额"));
		tablayout.addTab(tablayout.newTab().setText("券后价"));

		GridLayoutManager hotLayoutManager = new GridLayoutManager(getActivity(), 4);
		recyclerView.setLayoutManager(hotLayoutManager);
		mAdapter = new ClassifyListAdapter(categoryList);
		recyclerView.setAdapter(mAdapter);
		mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				MenuEntity menuEntity = (MenuEntity) adapter.getItem(position);
				Intent intent = new Intent(getActivity(), ProductSumActivity.class);
				intent.putExtra(Constants.INTENT_KEY_TITLE, menuEntity.getName());
				intent.putExtra(Constants.INTENT_KEY_ID, menuEntity.getId() + "");
				intent.putExtra(Constants.INTENT_KEY_TYPE, 5);
				startActivity(intent);
			}
		});

		rlv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
		homeAdapter = new HomeTwoFragment.HomeAdapter(R.layout.item_priduct_sum, mList);
		homeAdapter.openLoadAnimation();
//		homeAdapter.setUpFetchEnable(true);
		// rlv.addItemDecoration(new DividerItemDecoration(this , DividerItemDecoration.VERTICAL));
//		homeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//			@Override
//			public void onLoadMoreRequested() {
//				getCommodityList();
//
//			}
//		}, rlv);
		rlv.setAdapter(homeAdapter);

		tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				sortType = (tab.getPosition() + 1) + "";
				page = 1;
				homeAdapter.notifyItemRangeRemoved(0, mList.size());
				getCommodityList();
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
				Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
				intent.putExtra(Constants.INTENT_KEY_ID, mList.get(position).getId());
				intent.putExtra(Constants.INTENT_KEY_URL, mList.get(position).getIconUrl());
				startActivity(intent);
			}
		});

		PopupWindow popupWindow = new PopupWindow(getActivity(), null, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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
				WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
				lp.alpha = 1.0f; //0.0-1.0
				getActivity().getWindow().setAttributes(lp);
			}
		});
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_baobei, null);
		popupWindow.setContentView(view);
		Button btnTianmao = popupWindow.getContentView().findViewById(R.id.btn_tianmao);
		btnTianmao.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				type = "2";
				page = 1;
				homeAdapter.notifyItemRangeRemoved(0, mList.size());
				getCommodityList();
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
				getCommodityList();
				popupWindow.dismiss();
			}
		});


		linBaobeileixing.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
				lp.alpha = 0.5f; //0.0-1.0
				getActivity().getWindow().setAttributes(lp);
				popupWindow.showAsDropDown(linBaobeileixing);
			}
		});
	}

	private void initData() {
		getCategories();
		getCommodityList();
	}

	private void getCommodityList() {
		RetrofitFactory.getInstence().API()
				.getHomeProductList(categoryId, subCategoryId, type, sortType, page, size)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<ProductEntity>(getActivity()) {
					@Override
					protected void onSuccees(final BaseEntity<ProductEntity> t) throws Exception {
						refreshLayout.finishRefreshing();
						refreshLayout.finishLoadmore();

						if (mList.size() >= t.getData().getTotal()) {
							//数据全部加载完毕
							homeAdapter.loadMoreComplete();
							homeAdapter.loadMoreEnd();
						} else {
							if (page == 1) {
								mList.clear();
								homeAdapter.setNewData(t.getData().getDatas());
							} else {
								homeAdapter.addData(t.getData().getDatas());
							}
							//   mCurrentCounter = mQuickAdapter.getData().size();
							mList.addAll(t.getData().getDatas());
							page++;
							homeAdapter.loadMoreComplete();
						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
						homeAdapter.loadMoreComplete();
					}
				});


//		RetrofitFactory.getInstence().API()
//				.commodities(categoryId, subCategoryId, specialCategoryId, module, imported, needBuyed, keywords, type, sortType, orderType, page, size)
//				.subscribeOn(Schedulers.io())
//				.observeOn(AndroidSchedulers.mainThread())
//				.subscribe(new BaseObserver<ProductEntity>(getActivity()) {
//					@Override
//					protected void onSuccees(final BaseEntity<ProductEntity> t) throws Exception {
//						refreshLayout.finishRefreshing();
//						refreshLayout.finishLoadmore();
//
//						if (mList.size() >= t.getData().getTotal()) {
//							//数据全部加载完毕
//							homeAdapter.loadMoreComplete();
//							homeAdapter.loadMoreEnd();
//						} else {
//							if (page == 1) {
//								mList.clear();
//								homeAdapter.setNewData(t.getData().getDatas());
//							} else {
//								homeAdapter.addData(t.getData().getDatas());
//							}
//							//   mCurrentCounter = mQuickAdapter.getData().size();
//							mList.addAll(t.getData().getDatas());
//							page++;
//							homeAdapter.loadMoreComplete();
//						}
//					}
//
//					@Override
//					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//						homeAdapter.loadMoreComplete();
//					}
//				});
	}

	class HomeAdapter extends BaseQuickAdapter<ProductEntity.DatasBean, BaseViewHolder> {

		public HomeAdapter(int layoutResId, List data) {
			super(layoutResId, data);
		}

		@Override
		protected void convert(BaseViewHolder helper, ProductEntity.DatasBean item) {
			helper.setText(R.id.item_home_pro_title, StringUtils.addImageLabel(getActivity(), item.getType() == 1 ? R.mipmap.icon_taobao : R.mipmap.icon_tianma, item.getName()));
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

	/**
	 * 获取分类
	 */
	private void getCategories() {
		RetrofitFactory.getInstence().API().categories(pid).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<List<ClassifyEntity>>(getActivity()) {

					@Override
					protected void onSuccees(BaseEntity<List<ClassifyEntity>> t) throws Exception {
						if (t.getData() != null && t.getData().size() > 0) {
							if (page == 1) {
								categoryList.clear();
								mAdapter.setNewData(t.getData().get(0).getSubCategories());
							}
							categoryList.addAll(t.getData().get(0).getSubCategories());
							mAdapter.notifyDataSetChanged();
						}
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
}
