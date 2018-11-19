package com.cheapest.lansu.cheapestshopping.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.NewsEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.SigninEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.SigninModel;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.DateUtils;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;
import com.cheapest.lansu.cheapestshopping.view.adapter.CalendarAdapter;
import com.cheapest.lansu.cheapestshopping.view.dialog.ExchangeResultDialog;
import com.cheapest.lansu.cheapestshopping.view.dialog.SigninResultDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 码农哥
 * @date 2018/7/20 0020  0:01
 * @email 441293364@qq.com
 * @TODO <p/>簽到
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
public class SignInActivity extends BaseActivity {

	@Bind(R.id.tv_current_date)
	TextView tv_current_date;

	@Bind(R.id.gridView)
	GridView gridView;

	CalendarAdapter mAdapter;

	@Bind(R.id.tv_lianxu_days)
	TextView tv_lianxu_days;

	@Bind(R.id.tv_jindou_num)
	TextView tv_jindou_num;

	@Bind(R.id.tv_signin)
	TextView tv_signin;


	String currentDate;
	int year_c;
	int month_c;
	int day_c;

	boolean is_signin = false;

	int signDayNum = 0;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getToolbarTitle().setText("签到");

		initView();
		initData();
	}

	private void initView() {
		_initDate();
		tv_current_date.setText(month_c + "月");
		mAdapter = new CalendarAdapter(this, year_c, month_c, day_c);
		gridView.setAdapter(mAdapter);

	}

	private void _initDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		currentDate = sdf.format(date); // 当期日期
		year_c = Integer.parseInt(currentDate.split("-")[0]);
		month_c = Integer.parseInt(currentDate.split("-")[1]);
		day_c = Integer.parseInt(currentDate.split("-")[2]);
	}

	private void initData() {
		getSigninInfo();
		getSignins();
	}

	@OnClick({R.id.tv_signin})
	public void onViewClicked(View v) {
		switch (v.getId()) {
			case R.id.tv_signin:
				if (!is_signin) {
					userSignin();
				}
//				Bundle bundle = new Bundle();
//				bundle.putString("jindouNum", "2");
//				bundle.putString("dayNum", (signDayNum + 1) + "");
//				SigninResultDialog dialog = SigninResultDialog.newInstance(bundle);
//				dialog.show(getSupportFragmentManager(), "exchangeResult");
				break;
		}
	}

	/**
	 * 获取签到信息
	 */
	private void getSigninInfo() {
		RetrofitFactory.getInstence().API().survey(CacheInfo.getUserID(this)).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<SigninEntity>(mContext) {

					@Override
					protected void onSuccees(BaseEntity<SigninEntity> t) throws Exception {
						if (t.getData() != null) {
							//初始化簽到信息
							tv_lianxu_days.setText(t.getData().getContinuousDay()+"天");
							tv_jindou_num.setText(t.getData().getSocre());
							signDayNum = Integer.parseInt(t.getData().getContinuousDay());
						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}


	/**
	 * 用户签到
	 */
	private void userSignin() {
		RetrofitFactory.getInstence().API().signin(CacheInfo.getUserID(this)).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<String>(mContext) {

					@Override
					protected void onSuccees(BaseEntity<String> t) throws Exception {
						Bundle bundle = new Bundle();
						bundle.putString("jindouNum", t.getData());
						bundle.putString("dayNum", (signDayNum + 1) + "");
						SigninResultDialog dialog = SigninResultDialog.newInstance(bundle);
						dialog.show(getSupportFragmentManager(), "signinResult");
						initData();
//						ToastUtils.show(mContext, "签到成功，+" + t.getData() + "金豆");
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}

	/**
	 * 获取签到列表
	 */
	private void getSignins() {
		RetrofitFactory.getInstence().API().signins(CacheInfo.getUserID(this), year_c + "-" + DateUtils.fillDayNum(month_c)).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<List<SigninModel>>(mContext) {

					@Override
					protected void onSuccees(BaseEntity<List<SigninModel>> t) throws Exception {
						if (t.getData() != null && t.getData().size() > 0) {
							List<SigninModel> signinList = t.getData();
							mAdapter.setSignList(signinList);
							String date = year_c + "-" + DateUtils.fillDayNum(month_c) + "-" + DateUtils.fillDayNum(day_c);
							for (int i = 0; i < signinList.size(); i++) {
								if (date.equalsIgnoreCase(DateUtils.String3Date(signinList.get(i).getCreateTime()))) {
									//今日已签到
									is_signin = true;
									tv_signin.setText(getString(R.string.signed_in));
									tv_signin.setBackgroundResource(R.drawable.shape_signined_background);
								}
							}
						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_sign_in;
	}
}
