package com.cheapest.lansu.cheapestshopping.view.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.utils.SystemConfig;

import org.greenrobot.eventbus.EventBus;

/**
 * @author 码农哥
 * @date 2018/7/8 0008  0:24
 * @email 441293364@qq.com
 * @TODO <p/>
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
public class ExchangeResultDialog extends BaseDialogFragment implements View.OnClickListener {

	private boolean canDismiss = false;

	ImageView iv_dialog_close;

	public static ExchangeResultDialog newInstance(Bundle bundle) {
		ExchangeResultDialog dialog = new ExchangeResultDialog();
		dialog.setArguments(bundle);
		return dialog;
	}

	public View onCreateView(LayoutInflater layoutInflater, ViewGroup paramViewGroup, Bundle bundle) {
		SystemConfig.init(layoutInflater.getContext());
		bundle = getArguments();
		if (bundle != null) {

		}

		if (this.mMainView == null) {
			this.mMainView = layoutInflater.inflate(R.layout.dialog_exchange_order_success, paramViewGroup, false);
			iv_dialog_close = mMainView.findViewById(R.id.iv_dialog_close);
			Log.e("TAG", "width->" + SystemConfig.getWidth());
			Window window = getDialog().getWindow();
			WindowManager.LayoutParams lp = window.getAttributes();
			lp.width = (int) (SystemConfig.getWidth() * 0.8f);
			lp.y = 0;
			window.setAttributes(lp);
			iv_dialog_close.setOnClickListener(this);

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
