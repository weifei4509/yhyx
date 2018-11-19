package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.CollctionEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ProductEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.VersionEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.AppUtils;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CollectAndReferActivity extends BaseActivity {

	@Bind(R.id.rlv)
	RecyclerView rlv;
	private HomeAdapter homeAdapter;
	private List<CollctionEntity.DatasBean> mList = new ArrayList();
	private int page = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getToolbarTitle().setText("我的收藏");
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
		homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				Intent intent = new Intent(CollectAndReferActivity.this, GoodsDetailActivity.class);
				intent.putExtra(Constants.INTENT_KEY_ID, mList.get(position).getId());
				intent.putExtra(Constants.INTENT_KEY_URL, mList.get(position).getIconUrl());
				startActivity(intent);
			}
		});
		homeAdapter.setEmptyView(R.layout.view_empty_collection);
		getData();
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_collect_and_refer;
	}

	class HomeAdapter extends BaseQuickAdapter<CollctionEntity.DatasBean, BaseViewHolder> {
		public HomeAdapter(int layoutResId, List data) {
			super(layoutResId, data);
		}

		@Override
		protected void convert(BaseViewHolder helper, CollctionEntity.DatasBean item) {
			helper.setText(R.id.item_home_pro_title, StringUtils.addImageLabel(CollectAndReferActivity.this, item.getType() == 1 ? R.mipmap.icon_taobao : R.mipmap.icon_tianma, item.getName()));
			helper.setText(R.id.item_home_pro_quan, (int) item.getCouponAmount() + "元券");
			helper.setText(R.id.item_home_pro_yuanjia, "￥" + item.getPrice());
			helper.setText(R.id.item_home_pro_quanhoujia, "￥" + item.getDiscountPrice());
			helper.setText(R.id.tv_commision, "预估佣金：¥" + item.getMineCommision());
			((TextView) helper.getView(R.id.item_home_pro_yuanjia)).getPaint().setFlags(
					Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
			Glide.with(mContext).load(item.getIconUrl()).crossFade().into((ImageView) helper.getView(R.id.item_home_pro_image));
			helper.setText(R.id.item_home_xiaoliang, "月销  " + item.getSellNum());
			helper.getView(R.id.iv_collct).setVisibility(View.VISIBLE);
			helper.getView(R.id.iv_collct).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					for (int i = 0; i < mList.size(); i++) {
						if (item.getId() == mList.get(i).getId()) {
							cancelCollection(item.getId(), i);
							return;
						}
					}

				}
			});

		}
	}

	public void getData() {
		RetrofitFactory.getInstence().API()
				.collections(CacheInfo.getUserID(this), page, 6)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<CollctionEntity>(this) {
					@Override
					protected void onSuccees(BaseEntity<CollctionEntity> t) throws Exception {
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


	}

	/**
	 * 删除收藏列表
	 *
	 * @param id
	 * @param position
	 */
	public void cancelCollection(int id, final int position) {
		RetrofitFactory.getInstence().API()
				.addCollections(CacheInfo.getUserID(this), 1, id)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<String>(this) {
					@Override
					protected void onSuccees(BaseEntity<String> t) throws Exception {

						homeAdapter.remove(position);
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});


	}


}
