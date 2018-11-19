package com.cheapest.lansu.cheapestshopping.view.fragment.collage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.CollageEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ExchangeEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.view.activity.CollageDetailActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.ExchangeDetailActivity;
import com.cheapest.lansu.cheapestshopping.view.adapter.CollageAdapter;
import com.cheapest.lansu.cheapestshopping.view.adapter.FindExchangeAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 码农哥
 * @date 2018/7/20 0020  20:57
 * @email 441293364@qq.com
 * @TODO <p/>
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
public class CollageFragment extends Fragment {

	int page = 1;

	RecyclerView collage_recycler;

	CollageAdapter mAdapter = null;

	List<CollageEntity.DatasBean> mList = new ArrayList<>();

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_collage, container, false);


		initView(view);
		initData();

		return view;
	}

	private void initView(View view) {
		collage_recycler = view.findViewById(R.id.collage_recycler);

		LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
		collage_recycler.setLayoutManager(layoutManager);
		if (mAdapter == null) {
			mAdapter = new CollageAdapter(mList);
			collage_recycler.setAdapter(mAdapter);
		}
//		com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration dividerItemDecoration = new com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration(getActivity(),
//				com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration.VERTICAL_LIST);
//		dividerItemDecoration.setDivider(R.drawable.list_divider_line_05_bg);
//		collage_recycler.addItemDecoration(dividerItemDecoration);

		mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
			@Override
			public void onLoadMoreRequested() {
				collage_recycler.postDelayed(new Runnable() {
					@Override
					public void run() {
						getCollageList();
					}

				}, 1000);
			}
		}, collage_recycler);

		mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				Intent intent = new Intent(getActivity(), CollageDetailActivity.class);
				intent.putExtra("id", mList.get(position).getId());
				startActivity(intent);
			}
		});
	}

	private void initData() {
		getCollageList();
	}


	/**
	 * 获取拼团列表
	 */
	private void getCollageList() {
		RetrofitFactory.getInstence().API().groupList(page, 10)
				.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<CollageEntity>(getActivity()) {

					@Override
					protected void onSuccees(BaseEntity<CollageEntity> t) throws Exception {
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

}
