package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.AddressListModel;
import com.cheapest.lansu.cheapestshopping.model.entity.AddressModel;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.AppUtils;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;
import com.cheapest.lansu.cheapestshopping.view.adapter.UserAddressAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 码农哥
 * @date 2018/7/5 0005  0:34
 * @email 441293364@qq.com
 * @TODO <p/>用户收货地址管理
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
public class AddressManagerActivity extends BaseActivity {

	TextView tv_right;

	@Bind(R.id.listView)
	SwipeMenuListView mListView;

	@Bind(R.id.layout_nodata)
	View layout_nodata;

	List<AddressModel> mList = new ArrayList<>();

	int page = 1;

	UserAddressAdapter adapter = null;

	String type = "";//select_address

	@Bind(R.id.refreshlayout)
	TwinklingRefreshLayout refreshlayout;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		EventBus.getDefault().register(this);

		initView();
		initData();
	}

	private void initView() {
		getToolbarTitle().setText("收货地址");
		tv_right = getToolbar_right_Tv();
		tv_right.setText("添加");
		tv_right.setTextColor(getResources().getColor(R.color.black));
		tv_right.setVisibility(View.VISIBLE);
		tv_right.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(mContext, AddAddressActivity.class));
			}
		});

		refreshlayout.setEnableLoadmore(true);
		refreshlayout.setEnableRefresh(true);
		refreshlayout.setAutoLoadMore(true);
		refreshlayout.setOnRefreshListener(new RefreshListenerAdapter() {
			@Override
			public void onRefresh(TwinklingRefreshLayout refreshLayout) {
				super.onRefresh(refreshLayout);
				page = 1;
				mList.clear();
				sendRequest();
				refreshLayout.setEnableLoadmore(true);
			}

			@Override
			public void onLoadMore(TwinklingRefreshLayout refresh) {
				super.onLoadMore(refresh);
				sendRequest();
			}
		});


		mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				switch (index) {
					case 0:
						AddressModel addressModel = mList.get(position);
//						showToast("名称：" + addressModel.getDeliveryName());
						if (addressModel != null)
							deleteAddress(addressModel);
						break;
				}
				return false;
			}
		});

		adapter = new UserAddressAdapter(mContext);
//		LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
		mListView.setAdapter(adapter);

		SwipeMenuCreator creator = new SwipeMenuCreator() {
			@Override
			public void create(SwipeMenu menu) {

				// create "删除" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(mContext);
				// set item background
				deleteItem.setBackground(R.color.colorPrimary);
				// set item width
				deleteItem.setWidth(AppUtils.dip2px(mContext, 70));
				// set a icon
				deleteItem.setTitle("删除");
				deleteItem.setTitleColor(Color.WHITE);
				deleteItem.setTitleSize(16);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};

		mListView.setMenuCreator(creator);

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				AddressModel addressModel = adapter.getItem(position);
				if (addressModel != null) {
					EventBus.getDefault().post(addressModel);
					finish();
				}
			}
		});
	}

	private void initData() {
		sendRequest();
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_address_manager;
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(Object object) {
		int code = (int) object;
		if (code == 200) {
			page = 1;
			mList.clear();
			sendRequest();
		}

	}

	/**
	 * 删除收货地址
	 *
	 * @param addressModel
	 */
	private void deleteAddress(final AddressModel addressModel) {
		showProgressDialog("提交中...");
		RetrofitFactory.getInstence().API().deleteAddress("api/personal/delivery/" + addressModel.getId() + "/delete ")
				.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<String>(this) {

					@Override
					protected void onSuccees(BaseEntity<String> t) throws Exception {
						discussProgressDialog();
						if (t != null && t.getCode() == 0) {
							ToastUtils.show(mContext, "刪除收货地址成功");
							adapter.getData().remove(addressModel);
							adapter.notifyDataSetChanged();
							if (adapter.getCount() == 0) {
								mListView.setVisibility(View.GONE);
								layout_nodata.setVisibility(View.VISIBLE);
								TextView nodata = layout_nodata.findViewById(R.id.item_nodata);
								nodata.setText("暂未添加收货地址");
							}
						} else {
							ToastUtils.show(mContext, "刪除收货地址失败");
						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
						ToastUtils.show(mContext, "刪除收货地址失败");
						discussProgressDialog();
					}
				});
	}

	/**
	 * 获取地址列表
	 */
	private void sendRequest() {
		RetrofitFactory.getInstence().API().getAddressList(CacheInfo.getUserID(this), page, 10)
				.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<AddressListModel>(this) {

					@Override
					protected void onSuccees(BaseEntity<AddressListModel> t) throws Exception {
						if (mList.size() >= t.getData().getTotal()) {
							//数据全部加载完毕
							if (page == 1) {
								mListView.setVisibility(View.GONE);
								layout_nodata.setVisibility(View.VISIBLE);
								TextView nodata = layout_nodata.findViewById(R.id.item_nodata);
								nodata.setText("暂未添加收货地址");
							}
						} else {
							mList.addAll(t.getData().getDatas());
							adapter.setData(mList);
							page++;
						}

						refreshlayout.finishRefreshing();
						refreshlayout.finishLoadmore();
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
