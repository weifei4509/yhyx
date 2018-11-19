package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;
import com.bumptech.glide.Glide;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.UserEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;
import com.cheapest.lansu.cheapestshopping.utils.UserInfoUtils;
import com.google.gson.Gson;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 第三方授权
 */
public class ThirdPartyAuthorizationActivity extends BaseActivity {

	@Bind(R.id.tv_status)
	TextView tvStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getToolbarTitle().setText("第三方授权");
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_third_party_authorization;
	}

	@OnClick(R.id.rl_taobao)
	public void onViewClicked() {

		if (StringUtils.isEmpty(CacheInfo.getUserInfoFromCache(this).getTaobaoAccount())) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					AlibcLogin alibcLogin = AlibcLogin.getInstance();
					alibcLogin.showLogin(ThirdPartyAuthorizationActivity.this, new AlibcLoginCallback() {

						@Override
						public void onSuccess() {

							//获取淘宝用户信息
							HashMap<String, String> hashMap = new HashMap<>();
							hashMap.put("taobaoAccount", String.valueOf(AlibcLogin.getInstance().getSession().openId));
							hashMap.put("id", CacheInfo.getUserID(ThirdPartyAuthorizationActivity.this));
							RetrofitFactory.getInstence().API()
									.editInfo(hashMap)
									.subscribeOn(Schedulers.io())
									.observeOn(AndroidSchedulers.mainThread())
									.subscribe(new BaseObserver<UserEntity>(ThirdPartyAuthorizationActivity.this) {
										@Override
										protected void onSuccees(BaseEntity<UserEntity> t) throws Exception {
											Toast.makeText(ThirdPartyAuthorizationActivity.this, "淘宝授权登录成功 ",
													Toast.LENGTH_LONG).show();
											UserInfoUtils.saveUserInfo(t.getData(), ThirdPartyAuthorizationActivity.this);
											tvStatus.setText("已绑定");

										}

										@Override
										protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

										}
									});
						}

						@Override
						public void onFailure(int code, String msg) {
							Toast.makeText(ThirdPartyAuthorizationActivity.this, "淘宝授权失败",
									Toast.LENGTH_LONG).show();
							Log.i("taobao", "淘宝授权登录失败信息=" + msg + code);

						}
					});
				}
			});

		} else {
			ToastUtils.showShort(this, "您已绑定淘宝账号");
		}
	}


	@Override
	protected void onResume() {
		super.onResume();
		if (StringUtils.isEmpty(CacheInfo.getUserInfoFromCache(this).getTaobaoAccount())) {
			tvStatus.setText("未绑定");

		} else {
			tvStatus.setText("已绑定");
		}


	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		CallbackContext.onActivityResult(requestCode, resultCode, data);
	}
}
