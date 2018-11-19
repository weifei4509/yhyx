package com.cheapest.lansu.cheapestshopping.view.fragment.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;
import com.cheapest.lansu.cheapestshopping.view.activity.GoodsDetailActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.HomeMenuTwoActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.ProductSumActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.VideoActivity;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 码农哥
 * @date 2018/7/28 0028  1:29
 * @email 441293364@qq.com
 * @TODO <p/>  商品列表
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
public class ProductListFragment extends Fragment {

	@Bind(R.id.refreshlayout)
	TwinklingRefreshLayout refreshlayout;

	@Bind(R.id.recyclerView)
	RecyclerView recyclerView;
	HomeAdapter mAdapter = null;
	RankAdapter mRankAdapter = null;
	private List<ProductEntity.DatasBean> mList = new ArrayList<>();

	String productId = "";
	String id = "";
	int type = 1;
	int page = 1;


	public ProductListFragment(int id, String productId, int type) {
		this.id = id + "";
		this.productId = productId;
		this.type = type;
	}


	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home_product_list, container, false);
		ButterKnife.bind(this, view);
		initView();
		initData();
		return view;
	}

	private void initView() {
		refreshlayout.setEnableLoadmore(true);
		refreshlayout.setEnableRefresh(true);
		refreshlayout.setAutoLoadMore(true);
		refreshlayout.setOnRefreshListener(new RefreshListenerAdapter() {
			@Override
			public void onLoadMore(TwinklingRefreshLayout refresh) {
				super.onLoadMore(refresh);
				sendRequest();
			}

			@Override
			public void onRefresh(TwinklingRefreshLayout refreshLayout) {
				super.onRefresh(refreshLayout);
				page = 1;
				sendRequest();
				refreshLayout.setEnableLoadmore(true);
			}
		});


		if (type == 1 || type == 3) {
			GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
			recyclerView.setLayoutManager(manager);
			mAdapter = new HomeAdapter(R.layout.item_priduct_sum, mList);
			mAdapter.openLoadAnimation();
			mAdapter.setUpFetchEnable(true);
			recyclerView.setAdapter(mAdapter);
//		mAdapter.setEmptyView(R.layout.view_empty);
			mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
				@Override
				public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
					ProductEntity.DatasBean datasBean = (ProductEntity.DatasBean) adapter.getItem(position);
					if (null != datasBean) {
						Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
						intent.putExtra(Constants.INTENT_KEY_ID, datasBean.getId());
						intent.putExtra(Constants.INTENT_KEY_URL, datasBean.getIconUrl());
						startActivity(intent);
					}
				}
			});
		} else if (type == 2) {//省惠优选排行榜
			LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
			recyclerView.setLayoutManager(manager);
			mRankAdapter = new RankAdapter(mList);
			mRankAdapter.openLoadAnimation();
			mRankAdapter.setUpFetchEnable(true);
			recyclerView.setAdapter(mRankAdapter);
			mRankAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
				@Override
				public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
					ProductEntity.DatasBean datasBean = (ProductEntity.DatasBean) adapter.getItem(position);
					if (null != datasBean) {
						Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
						intent.putExtra(Constants.INTENT_KEY_ID, datasBean.getId());
						intent.putExtra(Constants.INTENT_KEY_URL, datasBean.getIconUrl());
						startActivity(intent);
					}
				}
			});
		}
	}

	private void initData() {
		sendRequest();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);
	}

	private void sendRequest() {
		if (id.equalsIgnoreCase("1001")) id = "";
		if (type == 1 || type == 3) {
			RetrofitFactory.getInstence().API().getProductList(productId, id, page, 6).subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(new BaseObserver<ProductEntity>(getActivity()) {

						@Override
						protected void onSuccees(BaseEntity<ProductEntity> t) throws Exception {
							refreshlayout.finishRefreshing();
							refreshlayout.finishLoadmore();

							if (mList.size() >= t.getData().getTotal()) {
								//数据全部加载完毕
								mAdapter.loadMoreEnd();
							} else {
								if (page == 1) {
									mAdapter.setNewData(t.getData().getDatas());
									mList.clear();
								} else {
									mAdapter.addData(t.getData().getDatas());
								}
								mList.addAll(t.getData().getDatas());
								//   mCurrentCounter = mQuickAdapter.getData().size();
								page++;
								mAdapter.loadMoreComplete();
							}
						}

						@Override
						protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
							mAdapter.loadMoreComplete();
						}
					});
		} else if (type == 2) {
			RetrofitFactory.getInstence().API().getProductRankList(id, page, 6).subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(new BaseObserver<ProductEntity>(getActivity()) {

						@Override
						protected void onSuccees(BaseEntity<ProductEntity> t) throws Exception {
							refreshlayout.finishRefreshing();
							refreshlayout.finishLoadmore();

							if (mList.size() >= t.getData().getTotal()) {
								//数据全部加载完毕
								mRankAdapter.loadMoreEnd();
							} else {
								if (page == 1) {
									mRankAdapter.setNewData(t.getData().getDatas());
									mList.clear();
								} else {
									mRankAdapter.addData(t.getData().getDatas());
								}
								mList.addAll(t.getData().getDatas());
								//   mCurrentCounter = mQuickAdapter.getData().size();
								page++;
								mRankAdapter.loadMoreComplete();
							}
						}

						@Override
						protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
							mRankAdapter.loadMoreComplete();
						}
					});
		}
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
			helper.getView(R.id.item_video_icon).setVisibility(View.GONE);

			if (type == 3) {
				helper.getView(R.id.item_video_icon).setVisibility(View.VISIBLE);
				helper.getView(R.id.item_home_pro_image).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(mContext, VideoActivity.class);
						intent.putExtra("videoPath", item.getVideoUrl());
						intent.putExtra("videoTitle", "视频优购");
						startActivity(intent);
					}
				});
			}

		}
	}

	class RankAdapter extends BaseQuickAdapter<ProductEntity.DatasBean, BaseViewHolder> {

		public RankAdapter(List data) {
			super(R.layout.item_preference_rank_listview, data);
		}

		@Override
		protected void convert(BaseViewHolder helper, ProductEntity.DatasBean item) {
			int index = helper.getAdapterPosition();
			ImageView iv_rank = helper.getView(R.id.iv_rank_num);
			TextView tv_rank = helper.getView(R.id.tv_rank_num);
			if (index <= 2) {
				iv_rank.setVisibility(View.VISIBLE);
				tv_rank.setVisibility(View.GONE);
				iv_rank.setImageResource(R.mipmap.icon_home_rank_01 + index);
			} else {
				tv_rank.setVisibility(View.VISIBLE);
				iv_rank.setVisibility(View.GONE);
				tv_rank.setText((index + 1) + "");
			}

			helper.setText(R.id.tv_preference_name, StringUtils.addImageLabel(getActivity(), item.getType() == 1 ? R.mipmap.icon_taobao : R.mipmap.icon_tianma, item.getName()));
			helper.setText(R.id.item_home_pro_quan, (int) item.getCouponAmount() + "元券");
			helper.setText(R.id.item_home_pro_yuanjia, "￥ " + item.getPrice());
			helper.setText(R.id.item_home_pro_quanhoujia, "￥ " + item.getDiscountPrice());
//			helper.setText(R.id.tv_commision, "预估佣金：¥" + item.getMineCommision());
			((TextView) helper.getView(R.id.item_home_pro_yuanjia)).getPaint().setFlags(
					Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
			Glide.with(mContext).load(item.getIconUrl()).crossFade().into((ImageView) helper.getView(R.id.iv_preference_thumb));
			helper.setText(R.id.item_home_xiaoliang, "月销  " + item.getSellNum());
		}
	}
}
