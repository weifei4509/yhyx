package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.cheapest.lansu.cheapestshopping.model.entity.ProductEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.StringUtil;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 搜索界面
 */
public class SearchActivity extends BaseActivity {
	private boolean isFirst = false;

	@Bind(R.id.rlv)
	RecyclerView rlv;
	@Bind(R.id.tablayout)
	TabLayout tablayout;
	@Bind(R.id.lin_baobeileixing)
	LinearLayout linBaobeileixing;
	private int page = 1;
	private int size = 10;
	private int sortType = 1;
	private String type = "";
	private HomeAdapter homeAdapter;
	private List<ProductEntity.DatasBean> mList = new ArrayList<>();

	String keywords = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		keywords = getIntent().getStringExtra("keywords");
		if (keywords != null) {
			getToolbarTitle().setText(keywords);
		}
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
				getData(keywords);

			}
		}, rlv);

		tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				sortType = tab.getPosition() + 1;
				page = 1;
				homeAdapter.notifyItemRangeRemoved(0, mList.size());
				getData(keywords);
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
				Intent intent = new Intent(SearchActivity.this, GoodsDetailActivity.class);
				intent.putExtra(Constants.INTENT_KEY_ID, mList.get(position).getId());
				intent.putExtra(Constants.INTENT_KEY_URL, mList.get(position).getIconUrl());
				startActivity(intent);
			}
		});
		PopupWindow popupWindow = new PopupWindow(this);
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
				getData(keywords);
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
				getData(keywords);
				popupWindow.dismiss();
			}
		});


		linBaobeileixing.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.showAsDropDown(linBaobeileixing);
			}
		});

		getData(keywords);

		homeAdapter.setEmptyView(R.layout.view_empty_search);
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_search;
	}

	class HomeAdapter extends BaseQuickAdapter<ProductEntity.DatasBean, BaseViewHolder> {
		public HomeAdapter(int layoutResId, List data) {
			super(layoutResId, data);
		}

		@Override
		protected void convert(BaseViewHolder helper, ProductEntity.DatasBean item) {
			helper.setText(R.id.item_home_pro_title, StringUtils.addImageLabel(SearchActivity.this, item.getType() == 1 ? R.mipmap.icon_taobao : R.mipmap.icon_tianma, item.getName()));
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


	public void getData(String s) {
		if (!isFirst) {
			rlv.setAdapter(homeAdapter);
			isFirst = true;
		}

		RetrofitFactory.getInstence().API()
				.SearchCommodityList(s, type, sortType, page, size)
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


	}
}
