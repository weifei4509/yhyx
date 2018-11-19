package com.cheapest.lansu.cheapestshopping.view.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.VersionEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.AppUtils;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.GlideCacheUtil;
import com.cheapest.lansu.cheapestshopping.utils.SharedPreferencesUtils;
import com.cheapest.lansu.cheapestshopping.utils.UserInfoUtils;
import com.cheapest.lansu.cheapestshopping.view.custom.BaseDialog;
import com.cheapest.lansu.cheapestshopping.view.custom.SwitchView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 设置界面
 */

public class SettingActivity extends BaseActivity {
	@Bind(R.id.tv_version_name)
	TextView tvVersionName;
	@Bind(R.id.btn_login_out)
	Button btnLoginOut;
	@Bind(R.id.tv_cache)
	TextView tvCache;
	@Bind(R.id.switch_1)
	SwitchView switch1;

	private DownloadBuilder builder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getToolbarTitle().setText("设置");
		tvVersionName.setText(AppUtils.getVersionName(this));
		if (!UserInfoUtils.isSharedLogin(this)) {

			btnLoginOut.setVisibility(View.GONE);

		}
		tvCache.setText(GlideCacheUtil.getInstance().getCacheSize(this));
		boolean isOpen = (boolean) SharedPreferencesUtils.getParam(this, Constants.PREFERENCE_KEY_USER_MESSAGE_STATUS, true);

		switch1.setState(isOpen);
		switch1.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
			@Override
			public void toggleToOn() {
				managerJPush(true);
				switch1.toggleSwitch(true);
			}

			@Override
			public void toggleToOff() {
				switch1.toggleSwitch(false);
				managerJPush(false);

			}
		});


	}

	@Override
	public int bindLayout() {
		return R.layout.activity_setting;
	}

	@OnClick({R.id.rl_clear_shouquan, R.id.rl_clear_update, R.id.rl_clear_cache, R.id.btn_login_out})

	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.rl_clear_shouquan:
				startActivity(new Intent(SettingActivity.this, ThirdPartyAuthorizationActivity.class));
				break;
			case R.id.rl_clear_update:
				checkVersion(this);
				break;
			case R.id.rl_clear_cache:
				GlideCacheUtil.getInstance().clearImageAllCache(this);
				Toast.makeText(SettingActivity.this, "清理完成", Toast.LENGTH_LONG).show();
				tvCache.setText(GlideCacheUtil.getInstance().getCacheSize(this));
				break;
			case R.id.btn_login_out:
				RetrofitFactory.getInstence().API()
						.logout(CacheInfo.getUserID(this))
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new BaseObserver<String>(this) {
							@Override
							protected void onSuccees(BaseEntity<String> t) throws Exception {
								UserInfoUtils.logOff(SettingActivity.this);
								finish();


							}

							@Override
							protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

							}
						});


				break;
		}
	}

	/**
	 * 检测更新
	 */
	private void checkVersion(final Context context) {
		showProgressDialog();
		RetrofitFactory.getInstence().API()
				.version("1", "1")
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<VersionEntity>(this) {
					@Override
					protected void onSuccees(BaseEntity<VersionEntity> t) throws Exception {
						discussProgressDialog();
						try {
							if (AppUtils.getVersionCode(SettingActivity.this) < t.getData().getNumber()) {
								sendRequest(t.getData().getDownloadUrl(), t.getData().getLog());


							} else {
								Toast.makeText(SettingActivity.this, "当前是最新版本", Toast.LENGTH_SHORT).show();
							}

						} catch (Exception e) {
							e.printStackTrace();
							Toast.makeText(SettingActivity.this, "当前是最新版本", Toast.LENGTH_SHORT).show();
						}


					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}

	private void sendRequest(String url, String title) {
		UIData uiData = UIData.create();
		uiData.setTitle("版本升级");
		uiData.setDownloadUrl(url);
		uiData.setContent(title);
		builder = AllenVersionChecker
				.getInstance()
				.downloadOnly(uiData);
		builder.setShowDownloadFailDialog(false);
		builder.setCustomVersionDialogListener(createCustomDialogOne());
		builder.excuteMission(this);
	}


	private CustomVersionDialogListener createCustomDialogOne() {
		return new CustomVersionDialogListener() {
			@Override
			public Dialog getCustomVersionDialog(Context context, UIData versionBundle) {
				BaseDialog baseDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.view_down_app);
				TextView textView = baseDialog.findViewById(R.id.tv_msg);
				textView.setText(versionBundle.getContent());
				return baseDialog;
			}
		};
	}

	private void managerJPush(boolean isOpen) {
		if (isOpen) {
			JPushInterface.resumePush(this);

		} else {
			JPushInterface.stopPush(this);
		}

		SharedPreferencesUtils.setParam(this, Constants.PREFERENCE_KEY_USER_MESSAGE_STATUS, isOpen);

	}
}
