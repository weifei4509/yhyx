package com.cheapest.lansu.cheapestshopping.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.CollageDetailEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 码农哥
 * @date 2018/7/25 0025  21:54
 * @email 441293364@qq.com
 * @TODO <p/>意見反饋
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
public class FeedBackActivity extends BaseActivity {

	@Bind(R.id.edit_feedback_cont)
	EditText edit_feedback_cont;

	@Bind(R.id.edit_feedback_phone)
	EditText edit_feedback_phone;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getToolbarTitle().setText("意见反馈");
		getToolbar_right_Tv().setVisibility(View.VISIBLE);
		getToolbar_right_Tv().setText("提交");

		initView();
		initData();
	}

	private void initView() {
		getToolbar_right_Tv().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String cont = edit_feedback_cont.getText().toString().trim();
				String phone = edit_feedback_phone.getText().toString().trim();
				if (TextUtils.isEmpty(cont)) {
					ToastUtils.show(mContext, "反馈内容不能为空");
					return;
				}

				if (TextUtils.isEmpty(phone)) {
					ToastUtils.show(mContext, "联系人电话不能为空");
					return;
				}

				sendFeedBack(cont, phone);
			}
		});
	}

	private void initData() {

	}

	/**
	 * 发送意见反馈
	 *
	 * @param cont
	 * @param phone
	 */
	private void sendFeedBack(String cont, String phone) {
		showProgressDialog("请稍等...");
		RetrofitFactory.getInstence().API().addFeedBack(CacheInfo.getUserID(this), phone, cont)
				.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<String>(this) {

					@Override
					protected void onSuccees(BaseEntity<String> t) throws Exception {
						discussProgressDialog();
						ToastUtils.show(mContext, "成功，感谢您宝贵的意见！");
						finish();
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
						discussProgressDialog();
					}
				});
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_feedback;
	}
}
