package com.cheapest.lansu.cheapestshopping.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.utils.AppUtils;

import butterknife.Bind;

/**
 * @author 码农哥
 * @date 2018/7/22 0022  21:12
 * @email 441293364@qq.com
 * @TODO <p/>分享赚钱
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
public class ShareNoticeActivity extends BaseActivity {

	@Bind(R.id.iv_share_notice)
	ImageView iv_share_notice;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getToolbarTitle().setText("分享赚钱");

		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(iv_share_notice.getLayoutParams());
		int top = AppUtils.dip2px(mContext, 165);
		int left = AppUtils.dip2px(mContext, 15);
		lp.setMargins(left, top, left, 0);
		iv_share_notice.setLayoutParams(lp);
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_share_notice;
	}
}
