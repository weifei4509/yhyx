package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BalanceRecordEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.RewardEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.WelfListEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.WelfareEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 我的钱包
 */
public class MyWalletActivity extends BaseActivity {
	private int page = 1;
	private int size = 10;
	RecyclerView rlv;
	private TextView tvBalance;
	RewardsAdapter rewardsAdapter;
	private List<BalanceRecordEntity.DatasBean> mList = new ArrayList<>();
	private RelativeLayout rlCommit;
	private String balance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getToolbarTitle().setText("我的钱包");
		rlv = findViewById(R.id.rlv);

		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		rlv.setLayoutManager(llm);

		rewardsAdapter = new RewardsAdapter(R.layout.item_rewards, mList);
		View header = LayoutInflater.from(this).inflate(R.layout.header_my_wallet, null, false);
		rewardsAdapter.addHeaderView(header);
		tvBalance = header.findViewById(R.id.tv_banlance);
		rewardsAdapter.openLoadAnimation();
		rewardsAdapter.setUpFetchEnable(false);
		rewardsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
			@Override
			public void onLoadMoreRequested() {
				getList();
			}
		}, rlv);
		rlv.setAdapter(rewardsAdapter);
		rlCommit = header.findViewById(R.id.rl_withdraw);
		rlCommit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyWalletActivity.this, WithDrawActivity.class);
				intent.putExtra(Constants.INTENT_KEY_ID, balance);
				startActivity(intent);
			}
		});
		rewardsAdapter.setEmptyView(R.layout.view_empty_browse);
		rewardsAdapter.setHeaderAndEmpty(true);
		DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
				DividerItemDecoration.VERTICAL_LIST);
		dividerItemDecoration.setDivider(R.drawable.news_divider_line_bg);
		rlv.addItemDecoration(dividerItemDecoration);
		rlv.setItemAnimator(new DefaultItemAnimator());

		getList();


	}

	private void getList() {
		RetrofitFactory.getInstence().API()
				.balances(CacheInfo.getUserID(this), page, size)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<BalanceRecordEntity>(this) {
					@Override
					protected void onSuccees(final BaseEntity<BalanceRecordEntity> t) throws Exception {

						if (mList.size() >= t.getData().getTotal()) {
							//数据全部加载完毕
							rewardsAdapter.loadMoreEnd();
						} else {
							rewardsAdapter.addData(t.getData().getDatas());
							//   mCurrentCounter = mQuickAdapter.getData().size();
							rewardsAdapter.loadMoreComplete();
							page++;
						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});


	}

	private void getBalance() {
		RetrofitFactory.getInstence().API()
				.getBalance(CacheInfo.getUserID(this))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<String>(this) {
					@Override
					protected void onSuccees(final BaseEntity<String> t) throws Exception {
						tvBalance.setText(t.getData());
						balance = t.getData();
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});


	}

	@Override
	public int bindLayout() {
		return R.layout.activity_my_wallet;
	}


	class RewardsAdapter extends BaseQuickAdapter<BalanceRecordEntity.DatasBean, BaseViewHolder> {
		public RewardsAdapter(int layoutResId, List data) {
			super(layoutResId, data);
		}

		@Override
		protected void convert(BaseViewHolder helper, BalanceRecordEntity.DatasBean item) {
			helper.setText(R.id.tv_title, item.getDescribe());
			helper.setText(R.id.tv_value, item.getMoney() + "");
			helper.setText(R.id.tv_time, item.getCreateTime());
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		getBalance();
	}
}
