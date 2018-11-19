package com.cheapest.lansu.cheapestshopping.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.ProductEntity;
import com.cheapest.lansu.cheapestshopping.view.activity.GoodsDetailActivity;
import com.cheapest.lansu.cheapestshopping.view.adapter.PreferenceRankAdapter;
import com.cheapest.lansu.cheapestshopping.view.custom.GradientRecycleView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 码农哥
 * @date 2018/7/13 0013  0:26
 * @email 441293364@qq.com
 * @TODO <p/>省惠优选排行榜Fragment
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
public class PreferenceRankFragment extends Fragment {

	GradientRecycleView recyclerView;

	PreferenceRankAdapter rankAdapter;

	List<ProductEntity.DatasBean> datas = new ArrayList();


	public PreferenceRankFragment() {

	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_preference_rank, container, false);

		recyclerView = view.findViewById(R.id.recyclerView);
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(layoutManager);


		ProductEntity.DatasBean bean01 = new ProductEntity.DatasBean();
		bean01.setId(111439);
		bean01.setCouponAmount(30);
		bean01.setDiscountPrice(88.00);
		bean01.setIconUrl("http://img.alicdn.com/bao/uploaded/i1/1040003909/TB2JAisxGSWBuNjSsrbXXa0mVXa_!!1040003909-0-item_pic.jpg");
		bean01.setName("夏季薄款宽松男裤商务男直筒裤中年休闲裤中老年爸爸裤男装长裤子");
		bean01.setType(2);
		bean01.setPrice(118);
		bean01.setSellNum(415);

		datas.add(bean01);
		datas.add(bean01);
		datas.add(bean01);
		datas.add(bean01);
		datas.add(bean01);
		datas.add(bean01);


		rankAdapter = new PreferenceRankAdapter(getActivity(), datas);
		rankAdapter.openLoadAnimation();
		rankAdapter.setUpFetchEnable(true);

		recyclerView.setAdapter(rankAdapter);

		rankAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
			@Override
			public void onLoadMoreRequested() {

			}
		}, recyclerView);

		rankAdapter.loadMoreEnd();


		rankAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
				intent.putExtra(Constants.INTENT_KEY_ID, datas.get(position).getId());
				intent.putExtra(Constants.INTENT_KEY_URL, datas.get(position).getIconUrl());
				startActivity(intent);
			}
		});

//		rankAdapter.loadMoreComplete();
		return view;
	}
}
