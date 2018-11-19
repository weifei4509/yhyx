package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 提现页面
 */
public class WithDrawActivity extends BaseActivity {

	@Bind(R.id.tv_zhifubao_number)
	TextView tvZhifubaoNumber;
	@Bind(R.id.tv_balance)
	TextView tvBalance;
	String zhifubao;
	String balance;
	@Bind(R.id.et_input)
	EditText etInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getToolbarTitle().setText("提现");
		balance = getIntent().getStringExtra(Constants.INTENT_KEY_ID);
		tvBalance.setText("可用余额 " + balance + " 元");
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_with_draw;
	}


	@OnClick({R.id.rl_zhifubao, R.id.tv_all_with_draw, R.id.tv_withdraw_protocol, R.id.btn_withdraw})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.rl_zhifubao:
				Intent intent = new Intent(this, AlipayManageActivity.class);
				intent.putExtra(Constants.INTENT_KEY_ID, zhifubao);
				startActivity(intent);
				break;
			case R.id.tv_all_with_draw:
				etInput.setText(balance);
				break;
			case R.id.tv_withdraw_protocol:
				startActivity(new Intent(this, WithDrawKnowActivity.class));
				break;
			case R.id.btn_withdraw:
				if (StringUtils.isEmpty(zhifubao)) {
					Toast.makeText(WithDrawActivity.this, "请先绑定支付宝账号", Toast.LENGTH_SHORT).show();
					return;
				}

				RetrofitFactory.getInstence().API()
						.fetch(CacheInfo.getUserID(mContext), Double.parseDouble(etInput.getText().toString().trim()))
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new BaseObserver<String>(mContext) {
							@Override
							protected void onSuccees(BaseEntity<String> t) throws Exception {

								Toast.makeText(WithDrawActivity.this, "提现成功", Toast.LENGTH_LONG).show();
								finish();
							}

							@Override
							protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

							}
						});
				break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		zhifubao = CacheInfo.getUserInfoFromCache(this).getAliAccount();
		if (StringUtils.isEmpty(zhifubao)) {
			tvZhifubaoNumber.setText("您未绑定支付宝账号，请先绑定");
		} else {
			tvZhifubaoNumber.setText(zhifubao);
		}
	}
}
