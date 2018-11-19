package com.cheapest.lansu.cheapestshopping.view.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.utils.SystemConfig;

import org.greenrobot.eventbus.EventBus;

/**
 * @author 码农哥
 * @date 2018/7/30 0030  4:00
 * @email 441293364@qq.com
 * @TODO <p/> 签到成功
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
public class SigninResultDialog extends BaseDialogFragment implements View.OnClickListener {

	private boolean canDismiss = false;

	ImageView iv_dialog_close;

	String dayNum = "0";
	String jindouNum = "0";

	TextView tv_signin_num;
	TextView tv_add_jindou;


	public static SigninResultDialog newInstance(Bundle bundle) {
		SigninResultDialog dialog = new SigninResultDialog();
		dialog.setArguments(bundle);
		return dialog;
	}

	public View onCreateView(LayoutInflater layoutInflater, ViewGroup paramViewGroup, Bundle bundle) {
		SystemConfig.init(layoutInflater.getContext());
		bundle = getArguments();
		if (bundle != null) {
			dayNum = bundle.getString("dayNum");
			jindouNum = bundle.getString("jindouNum");
		}

		if (this.mMainView == null) {
			this.mMainView = layoutInflater.inflate(R.layout.dialog_signin_success, paramViewGroup, false);
			iv_dialog_close = mMainView.findViewById(R.id.iv_dialog_close);
			tv_add_jindou = mMainView.findViewById(R.id.tv_add_jindou);
			tv_signin_num = mMainView.findViewById(R.id.tv_signin_num);
			Log.e("TAG", "width->" + SystemConfig.getWidth());
			Window window = getDialog().getWindow();
			WindowManager.LayoutParams lp = window.getAttributes();
			lp.width = (int) (SystemConfig.getWidth() * 0.8f);
			lp.y = 0;
			window.setAttributes(lp);
			iv_dialog_close.setOnClickListener(this);

			tv_signin_num.setText(dayNum);
			tv_add_jindou.setText("+"+jindouNum+"金豆");

		}
		return this.mMainView;
	}


	@Override
	public void onResume() {
		super.onResume();
		if (canDismiss) {
			dismiss();
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.iv_dialog_close:
				dismiss();
				EventBus.getDefault().post(200);
				break;
		}
	}
}