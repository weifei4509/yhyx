package com.cheapest.lansu.cheapestshopping.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.NewsEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ProductEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitService;
import com.cheapest.lansu.cheapestshopping.view.adapter.CollageAdapter;
import com.cheapest.lansu.cheapestshopping.view.adapter.NewsListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 码农哥
 * @date 2018/7/11 0011  0:29
 * @email 441293364@qq.com
 * @TODO <p/>省惠快報
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
public class NewsListActivity extends BaseActivity {

	@Bind(R.id.recyclerView)
	RecyclerView recyclerView;

	NewsListAdapter mAdapter;

	List<NewsEntity.DatasBean> mList = new ArrayList<>();

	int page = 1;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getToolbarTitle().setText("省惠快报");

		initView();
		initData();
	}

	private void initView() {
		LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(layoutManager);
		if (mAdapter == null) {
			mAdapter = new NewsListAdapter(mList);
			recyclerView.setAdapter(mAdapter);
		}

		View header = LayoutInflater.from(mContext).inflate(R.layout.layout_news_header, null, false);
		mAdapter.addHeaderView(header);
//		com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration dividerItemDecoration = new com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration(getActivity(),
//				com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration.VERTICAL_LIST);
//		dividerItemDecoration.setDivider(R.drawable.list_divider_line_05_bg);
//		collage_recycler.addItemDecoration(dividerItemDecoration);

		mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
			@Override
			public void onLoadMoreRequested() {
				recyclerView.postDelayed(new Runnable() {
					@Override
					public void run() {
						getNewsList();
					}

				}, 1000);
			}
		}, recyclerView);

		mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				Intent intent = new Intent(mContext, NewsDetailActivity.class);
				intent.putExtra("id", mList.get(position).getId());
				startActivity(intent);
			}
		});
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		getNewsList();
	}

	private void getNewsList() {
		RetrofitFactory.getInstence().API().newsList(page, 6).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<NewsEntity>(mContext) {

					@Override
					protected void onSuccees(BaseEntity<NewsEntity> t) throws Exception {
						Log.e("TAG", JSON.toJSONString(t.getData().getDatas()));
						if (mList.size() >= t.getData().getTotal()) {
							//数据全部加载完毕
							mAdapter.loadMoreEnd();
						} else {
							mAdapter.addData(t.getData().getDatas());
							mAdapter.loadMoreComplete();
							page++;
						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_news_list;
	}
}
