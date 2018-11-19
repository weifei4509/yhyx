package com.cheapest.lansu.cheapestshopping.view.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.View;

import com.cheapest.lansu.cheapestshopping.R;

/**
 * @author 码农哥
 * @date 2018/7/5 0005  0:00
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
public class BaseDialogFragment extends DialogFragment {

	public View mMainView;

	public void onCreate(@Nullable Bundle paramBundle) {
		super.onCreate(paramBundle);
		setStyle(STYLE_NO_TITLE, R.style.dialog);
		setCancelable(true);
	}

	public void onStart() {
		super.onStart();
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
		getDialog().getWindow().setLayout(localDisplayMetrics.widthPixels, getDialog().getWindow().getAttributes().height);
		getDialog().getWindow().setWindowAnimations(R.style.style_item);
//        getDialog().getWindow().setGravity(80);
	}

}