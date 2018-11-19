package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;
import com.cheapest.lansu.cheapestshopping.utils.Util;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author 码农哥
 * @date 2018/7/30 0030  1:13
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
public class SearchMainActivity extends BaseActivity implements TextView.OnEditorActionListener {

	@Bind(R.id.tv_search_01)
	TextView tv_search_01;

	@Bind(R.id.tv_search_02)
	TextView tv_search_02;

	int searchType = 1;

	@Bind(R.id.et_input)
	EditText et_input;

	@Bind(R.id.iv_return)
	ImageView iv_return;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getToolbar().setVisibility(View.GONE);
		et_input.setOnEditorActionListener(this);
		iv_return.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

	}

	@Override
	public int bindLayout() {
		return R.layout.activity_main_search;
	}

	@OnClick({R.id.layout_search_01, R.id.layout_search_02})
	public void onViewClicked(View v) {
		switch (v.getId()) {
			case R.id.layout_search_01:
				et_input.setHint("输入商品关键字");
				tv_search_01.setEnabled(true);
				tv_search_02.setEnabled(false);
				searchType = 1;
				break;

			case R.id.layout_search_02:
				et_input.setHint("粘贴淘宝天猫商品标题到这里");
				tv_search_02.setEnabled(true);
				tv_search_01.setEnabled(false);
				searchType = 2;
				break;
		}
	}

	@Override
	public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
		if (actionId == EditorInfo.IME_ACTION_SEARCH) {
			// 当按了搜索之后关闭软键盘
			((InputMethodManager) et_input.getContext().getSystemService(
					Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
					SearchMainActivity.this.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			toSearch();
			return true;
		}
		return false;
	}

	private void toSearch() {
		String searchCont = et_input.getText().toString().trim();
		if (Util.isEmpty(searchCont)) {
			ToastUtils.show(mContext, "搜索內容不能为空");
			return;
		}

		if (searchType == 1) {//搜索
			Intent intent = new Intent(mContext, SearchActivity.class);
			intent.putExtra("keywords", searchCont);
			startActivity(intent);
		} else if (searchType == 2) {//全网搜索
			Intent intent = new Intent(mContext, WholeSearchActivity.class);
			intent.putExtra("keywords", searchCont);
			startActivity(intent);
		}
	}
}
