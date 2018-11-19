package com.cheapest.lansu.cheapestshopping.view.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.UserEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.VipEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;
import com.cheapest.lansu.cheapestshopping.utils.TimeCountUtils;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;
import com.cheapest.lansu.cheapestshopping.utils.UserInfoUtils;
import com.cheapest.lansu.cheapestshopping.utils.Util;
import com.cheapest.lansu.cheapestshopping.utils.WorkAvailable;
import com.cheapest.lansu.cheapestshopping.wxapi.WeiXinPayMethod;

import java.util.HashMap;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 支付宝管理页面
 */
public class AlipayManageActivity extends BaseActivity implements TimeCountUtils.CountDownListener {

	@Bind(R.id.tv_yibangding)
	TextView tvYibangding;
	@Bind(R.id.btn_bangding)
	Button btnBangding;
	@Bind(R.id.et_input)
	EditText etInput;
	private String zhifubao;

	@Bind(R.id.btn_login_sms)
	Button btn_login_sms;

	@Bind(R.id.layout_smscode)
	RelativeLayout layout_smscode;

	@Bind(R.id.et_code)
	EditText et_code;

	boolean is_getcode = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		zhifubao = getIntent().getStringExtra(Constants.INTENT_KEY_ID);

		if (StringUtils.isEmpty(zhifubao)) {
//			layout_smscode.setVisibility(View.VISIBLE);
			getToolbarTitle().setText("绑定支付宝");
			tvYibangding.setText("未绑定");
			tvYibangding.setTextColor(Color.WHITE);
			btnBangding.setText("确定绑定");
			tvYibangding.setBackgroundResource(R.drawable.zhifubao_bg);
			btnBangding.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {      //绑定支付宝
					if (StringUtils.isEmpty(etInput.getText().toString().trim())) {
						Toast.makeText(AlipayManageActivity.this, "请输入支付宝账号", Toast.LENGTH_SHORT).show();
						return;
					}

					if (is_getcode && Util.isNotEmpty(et_code.getText().toString().trim())) {
						bindAlipay(etInput.getText().toString().trim());
					} else {
						ToastUtils.show(mContext, "请先获取验证码");
					}

				}
			});

			btn_login_sms.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (StringUtils.isMobile(etInput.getText().toString().trim())) {
						getSms(etInput.getText().toString().trim());
					} else {
						Toast.makeText(AlipayManageActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
					}
				}
			});

		} else {
//			layout_smscode.setVisibility(View.GONE);
			btnBangding.setText("确定解绑");
			etInput.setEnabled(false);
			etInput.setFocusable(false);
			etInput.setText(zhifubao);
			getToolbarTitle().setText("解绑支付宝");

			btn_login_sms.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					getSms(zhifubao);
				}
			});

			btnBangding.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {            //解绑支付宝
					if (is_getcode && Util.isNotEmpty(et_code.getText().toString().trim())) {
						bindAlipay("");
					} else {
						ToastUtils.show(mContext, "请先获取验证码");
					}

				}
			});
		}
	}

	private void getSms(String str) {
		showProgressDialog();
		RetrofitFactory.getInstence().API()
				.getSms(str)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<String>(this) {
					@Override
					protected void onSuccees(BaseEntity<String> t) throws Exception {
						is_getcode = true;
						ToastUtils.showShort(AlipayManageActivity.this, "发送成功");
						startTimeCount();
						discussProgressDialog();
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}


	private TimeCountUtils mTimeCountButton;

	/**
	 * 开始计时
	 *
	 * @param
	 */
	private void startTimeCount() {
		if (mTimeCountButton == null) {
			mTimeCountButton = new TimeCountUtils(60000, 1000);
			mTimeCountButton.addCountDownListener(this);
		}
		mTimeCountButton.start();
	}

	/**
	 * 取消计时
	 *
	 * @param
	 */
	private void cancelTimeCountButton() {
		if (mTimeCountButton != null) {
			mTimeCountButton.cancel();
			mTimeCountButton = null;
		}
	}


	private void bindAlipay(String s) {
		HashMap<String, String> hashMap = new HashMap<>();
		hashMap.put("aliAccount", s);
		hashMap.put("id", CacheInfo.getUserID(mContext));
		RetrofitFactory.getInstence().API()
				.editInfo(hashMap)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<UserEntity>(mContext) {
					@Override
					protected void onSuccees(BaseEntity<UserEntity> t) throws Exception {
						is_getcode = false;
						UserInfoUtils.saveUserInfo(t.getData(), AlipayManageActivity.this);
						finish();
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}


	@Override
	public int bindLayout() {
		return R.layout.activity_alipay_manage;
	}

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void countDownOnTick(long millisUntilFinished) {
		btn_login_sms.setClickable(false);
		btn_login_sms.setText(millisUntilFinished / 1000 + "s后重试");
		btn_login_sms.setBackground(getResources().getDrawable(R.drawable.bg_sms_selected));
		btn_login_sms.setTextColor(Color.parseColor("#000000"));
	}

	/**
	 * 结束计时回调
	 *
	 * @param
	 */
	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void countDownFinish() {
		btn_login_sms.setClickable(true);
		btn_login_sms.setTextColor(Color.WHITE);
		btn_login_sms.setBackground(getResources().getDrawable(R.drawable.bg_corner_get_sms));
		btn_login_sms.setText("获取验证码");
	}
}
