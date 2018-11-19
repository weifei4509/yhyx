package com.cheapest.lansu.cheapestshopping.model.http;

import android.accounts.NetworkErrorException;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.utils.ProgressDlgUtil;
import com.cheapest.lansu.cheapestshopping.utils.UserInfoUtils;
import com.cheapest.lansu.cheapestshopping.view.App;
import com.cheapest.lansu.cheapestshopping.view.activity.SettingActivity;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import rx.Observer;

/*
 * 文件名：BaseObserver
 * 描    述：
 * 作    者：lansu
 * 时    间：2018/4/26 12:06
 */
public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {
	protected Context mContext;

	public BaseObserver(Context cxt) {
		this.mContext = cxt;
	}

	public BaseObserver() {

	}


	@Override
	public void onCompleted() {

	}

	@Override
	public void onNext(BaseEntity<T> tBaseEntity) {
		onRequestEnd();
		if (tBaseEntity.isSuccess()) {
			try {
				onSuccees(tBaseEntity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				if (tBaseEntity.isTokenLose()) {
					Toast.makeText(mContext, tBaseEntity.getMsg(), Toast.LENGTH_SHORT).show();
					UserInfoUtils.logOff(mContext);

				} else {
					onCodeError(tBaseEntity);
				}


			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onError(Throwable e) {
		Log.e("TAG", "Error->" + e.toString());
		ProgressDlgUtil.stopProgressDlg();
		Log.w("网络", "onError: 网络错误");
		onRequestEnd();
		try {
			if (e instanceof ConnectException
					|| e instanceof TimeoutException
					|| e instanceof NetworkErrorException
					|| e instanceof UnknownHostException) {
				onFailure(e, true);
				Toast.makeText(mContext, "服务器无响应", Toast.LENGTH_SHORT).show();
			} else {
				onFailure(e, false);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}


	/**
	 * 返回成功
	 *
	 * @param t
	 * @throws Exception
	 */
	protected abstract void onSuccees(BaseEntity<T> t) throws Exception;

	/**
	 * 返回成功了,但是code错误
	 *
	 * @param t
	 * @throws Exception
	 */
	protected void onCodeError(BaseEntity<T> t) throws Exception {

		Toast.makeText(mContext, t.getMsg(), Toast.LENGTH_SHORT).show();
		ProgressDlgUtil.stopProgressDlg();


	}

	/**
	 * 返回失败
	 *
	 * @param e
	 * @param isNetWorkError 是否是网络错误
	 * @throws Exception
	 */
	protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;


	protected void onRequestEnd() {
		closeProgressDialog();
	}

	public void showProgressDialog() {
		//   ProgressDialog.show(mContext, false, "请稍后");
	}

	public void closeProgressDialog() {
		//  ProgressDialog.cancle();
	}

}