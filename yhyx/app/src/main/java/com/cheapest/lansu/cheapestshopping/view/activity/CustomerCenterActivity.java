package com.cheapest.lansu.cheapestshopping.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;

import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 码农哥
 * @date 2018/7/5 0005  1:01
 * @email 441293364@qq.com
 * @TODO <p/>客服中心
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
public class CustomerCenterActivity extends BaseActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getToolbarTitle().setText("客服服务");
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_customer_center;
	}

	@OnClick({R.id.layout_call_phone})
	public void onViewClicked(View v) {
		switch (v.getId()) {
			case R.id.layout_call_phone:
				callCustomer();
				break;
		}
	}

	/**
	 * 打客服电话
	 */
	private void callCustomer() {

		RetrofitFactory.getInstence().API()
				.contact()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<String>(mContext) {
					@Override
					protected void onSuccees(BaseEntity<String> t) throws Exception {
						int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
							ActivityCompat.requestPermissions(CustomerCenterActivity.this, new String[]{
									Manifest.permission.CALL_PHONE
							}, 1001);
						} else {
							Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + t.getData()));
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(intent);
						}


					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});

	}
}
