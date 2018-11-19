package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.AddressModel;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ExchangeEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.NewsEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.SigninEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;
import com.cheapest.lansu.cheapestshopping.utils.UserInfoUtils;
import com.cheapest.lansu.cheapestshopping.view.dialog.ExchangeResultDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 码农哥
 * @date 2018/7/7 0007  2:31
 * @email 441293364@qq.com
 * @TODO <p/>提交兑换
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
public class ExchangeOrderActivity extends BaseActivity {

	@Bind(R.id.layout_unselect_address)
	RelativeLayout layout_unselect_address;

	@Bind(R.id.layout_select_address)
	RelativeLayout layout_select_address;

	@Bind(R.id.tv_user_name)
	TextView tv_user_name;

	@Bind(R.id.tv_user_phone)
	TextView tv_user_phone;

	@Bind(R.id.tv_address_info)
	TextView tv_address_info;

	AddressModel addressModel = null;

	ExchangeEntity.DatasBean datasBean = null;

	@Bind(R.id.iv_exchange_thumb)
	ImageView iv_exchange_thumb;
	@Bind(R.id.tv_exchange_name)
	TextView tv_exchange_name;
	@Bind(R.id.tv_exchange_jindou_num)
	TextView tv_exchange_jindou_num;
	@Bind(R.id.tv_exchange_jd)
	TextView tv_exchange_jd;
	@Bind(R.id.edit_exchange_remarks)
	EditText edit_exchange_remarks;

	@Bind(R.id.tv_jindou_num)
	TextView tv_jindou_num;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
		datasBean = (ExchangeEntity.DatasBean) getIntent().getSerializableExtra("product");
		getToolbarTitle().setText("提交兑换");

		initView();

	}

	private void initView() {
		if (datasBean != null) {
//			tv_jindou_num.setText(CacheInfo.getUserInfoFromCache(mContext).getScore());
			Glide.with(mContext).load(datasBean.getIconUrl()).crossFade().into(iv_exchange_thumb);
			tv_exchange_name.setText(datasBean.getName());
			tv_exchange_jindou_num.setText("兑换：" + datasBean.getScore() + "金豆");
			tv_exchange_jd.setText("使用" + datasBean.getScore() + "金豆");
		}

		getSigninInfo();
	}

	private void getSigninInfo() {
		RetrofitFactory.getInstence().API().survey(CacheInfo.getUserID(this)).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<SigninEntity>(mContext) {

					@Override
					protected void onSuccees(BaseEntity<SigninEntity> t) throws Exception {
						if (t.getData() != null) {
							//初始化可用金豆数量
							tv_jindou_num.setText(t.getData().getSocre());
						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_exchange_order;
	}

	@OnClick({R.id.layout_unselect_address, R.id.layout_select_address, R.id.btn_exchange})
	public void onClickViewed(View v) {
		switch (v.getId()) {
			case R.id.btn_exchange:
				if (UserInfoUtils.isSharedLogin(mContext)) {
					if (addressModel == null) {
						Toast.makeText(mContext, "请选择收货地址", Toast.LENGTH_SHORT).show();
						return;
					}

					if (datasBean == null) {
						Toast.makeText(mContext, "系统异常，请联系管理员", Toast.LENGTH_SHORT).show();
						return;
					}

					String remark = edit_exchange_remarks.getText().toString().trim();
					exchangeGoods(CacheInfo.getUserID(this), addressModel.getId(), datasBean.getId() + "", 1, remark);

				} else {
					startActivity(new Intent(mContext, LoginActivity.class));
					Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.layout_unselect_address:
			case R.id.layout_select_address:
				if (UserInfoUtils.isSharedLogin(mContext)) {
					startActivity(new Intent(mContext, AddressManagerActivity.class));
				} else {
					startActivity(new Intent(mContext, LoginActivity.class));
					Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
				}

				break;
		}
	}

	/**
	 * 兌換商品
	 *
	 * @param userId
	 * @param addressId
	 * @param goodsId
	 * @param quantity
	 * @param remark
	 */
	private void exchangeGoods(String userId, String addressId, String goodsId, int quantity, String remark) {
		RetrofitFactory.getInstence().API().swap(userId, addressId, goodsId, quantity, remark).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<String>(mContext) {

					@Override
					protected void onSuccees(BaseEntity<String> t) throws Exception {
						if (t != null && t.getCode() == 0) {
							ExchangeResultDialog dialog = ExchangeResultDialog.newInstance(null);
							dialog.show(getSupportFragmentManager(), "exchangeResult");
						} else {
							ToastUtils.show(mContext, t.getMsg());
						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(Object object) {
		AddressModel model = (AddressModel) object;
		if (null != model) {
			this.addressModel = model;
			Log.e("TAG", "获取地址信息：" + JSON.toJSONString(addressModel));
			layout_unselect_address.setVisibility(View.GONE);
			layout_select_address.setVisibility(View.VISIBLE);
			initUserAddress(model);
		} else {
			int code = (int) object;
			if (code == 200) {
				finish();
			}
		}

	}

	/**
	 * 初始化用户收货地址
	 *
	 * @param addressModel
	 */
	private void initUserAddress(AddressModel addressModel) {
		tv_user_name.setText(addressModel.getDeliveryName());
		tv_user_phone.setText(addressModel.getDeliveryMobile());
		//【默认】广东省广东市白云区岗贝路1号A座1211房 白云区大道1号A座1211房
		String addressInfo = "[默认]" + addressModel.getProvinceName() + addressModel.getCityName() + addressModel.getAreaName() + addressModel.getAddress();
		tv_address_info.setText(addressInfo);
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
