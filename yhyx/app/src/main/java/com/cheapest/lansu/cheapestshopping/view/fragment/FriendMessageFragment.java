package com.cheapest.lansu.cheapestshopping.view.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.MessageEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.AppUtils;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.view.custom.CircleImageView;
import com.cheapest.lansu.cheapestshopping.view.custom.ItemRemoveRecyclerView;
import com.cheapest.lansu.cheapestshopping.view.custom.RecyListViewOnItemClick;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 好友消息界面
 * A simple {@link Fragment} subclass.
 */
public class FriendMessageFragment extends Fragment {


	@Bind(R.id.rlv)
	ItemRemoveRecyclerView rlv;
	private HomeAdapter homeAdapter;
	private List<MessageEntity.DatasBean> mList = new ArrayList();
	private int page = 1;

	public FriendMessageFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_friend, container, false);
		ButterKnife.bind(this, view);

		LinearLayoutManager llm = new LinearLayoutManager(getActivity());
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		rlv.setLayoutManager(llm);
// 设置菜单创建器。
		rlv.setItem_delete(R.id.tv_delete);
		rlv.setmListener(new RecyListViewOnItemClick() {
			@Override
			public void onItemClick(View v, int position) {
				deleteMes(mList.get(position).getId(), position);
			}
		});
		homeAdapter = new HomeAdapter(R.layout.item_message, mList);
		homeAdapter.openLoadAnimation();
		homeAdapter.setUpFetchEnable(true);
//		rlv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
		com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration dividerItemDecoration = new com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration(getActivity(),
				com.cheapest.lansu.cheapestshopping.view.custom.DividerItemDecoration.VERTICAL_LIST);
		dividerItemDecoration.setDivider(R.drawable.news_divider_line_bg);
		rlv.addItemDecoration(dividerItemDecoration);
		homeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
			@Override
			public void onLoadMoreRequested() {
				getData();
			}
		}, rlv);
		rlv.setAdapter(homeAdapter);

		homeAdapter.setEmptyView(R.layout.view_empty);
		getData();
		return view;

	}


	class HomeAdapter extends BaseQuickAdapter<MessageEntity.DatasBean, BaseViewHolder> {
		public HomeAdapter(int layoutResId, List data) {
			super(layoutResId, data);
		}

		@Override
		protected void convert(BaseViewHolder helper, MessageEntity.DatasBean item) {
			helper.setText(R.id.tv_content, item.getContent());
			helper.setText(R.id.tv_time, item.getCreateTime());
//			Glide.with(mContext).load(CacheInfo.getUserInfoFromCache(getActivity()).getHeadpicUrl()).crossFade().into((CircleImageView) helper.getView(R.id.iv_icon));
		}
	}

	public void getData() {
		RetrofitFactory.getInstence().API()
				.getMsgs(page, 6, CacheInfo.getUserID(getActivity()), 1)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<MessageEntity>(getActivity()) {
					@Override
					protected void onSuccees(BaseEntity<MessageEntity> t) throws Exception {
						mList.addAll(t.getData().getDatas());
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
						homeAdapter.loadMoreComplete();
					}
				});


	}

	/**
	 * 删除收藏列表
	 *
	 * @param id
	 * @param position
	 */
	public void deleteMes(int id, final int position) {
		RetrofitFactory.getInstence().API()
				.deleteMsg("api/personal/msg/" + id + "/delete", CacheInfo.getUserID(getActivity()))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<String>(getActivity()) {
					@Override
					protected void onSuccees(BaseEntity<String> t) throws Exception {
						homeAdapter.remove(position);
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
