package com.cheapest.lansu.cheapestshopping.view.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.utils.SystemConfig;
import com.cheapest.lansu.cheapestshopping.view.activity.CollageActivity;

/**
 * @author 码农哥
 * @date 2018/8/2 0002  0:47
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
public class CollageDialog extends BaseDialogFragment implements View.OnClickListener {

	public static CollageDialog newInstance(Bundle bundle) {
		CollageDialog dialog = new CollageDialog();
		dialog.setArguments(bundle);
		return dialog;
	}

	public View onCreateView(LayoutInflater layoutInflater, ViewGroup paramViewGroup, Bundle bundle) {
		SystemConfig.init(layoutInflater.getContext());
		bundle = getArguments();
		if (bundle != null) {

		}

		if (this.mMainView == null) {
			this.mMainView = layoutInflater.inflate(R.layout.dialog_collage_act, paramViewGroup, false);
			Log.e("TAG", "width->" + SystemConfig.getWidth());
			Window window = getDialog().getWindow();
			WindowManager.LayoutParams lp = window.getAttributes();
			lp.width = (int) (SystemConfig.getWidth() * 0.8f);
			lp.y = 0;
			window.setAttributes(lp);
			mMainView.findViewById(R.id.layout_collage_hint).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					startActivity(new Intent(getActivity(), CollageActivity.class));
					dismiss();
				}
			});

			mMainView.findViewById(R.id.iv_dialog_close).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					dismiss();
				}
			});

		}
		return this.mMainView;
	}

	@Override
	public void onClick(View view) {

	}
}
