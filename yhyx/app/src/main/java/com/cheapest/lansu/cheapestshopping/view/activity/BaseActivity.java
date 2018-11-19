package com.cheapest.lansu.cheapestshopping.view.activity;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.utils.ActivityCollectorUtlis;
import com.cheapest.lansu.cheapestshopping.utils.ProgressDlgUtil;
import com.cheapest.lansu.cheapestshopping.utils.SharedPreferencesUtils;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;
import com.cheapest.lansu.cheapestshopping.utils.SystemBarTintManager;
import com.cheapest.lansu.cheapestshopping.utils.TextUtil;
import com.cheapest.lansu.cheapestshopping.view.App;
import com.cheapest.lansu.cheapestshopping.view.custom.LoadingDialog;
import com.cheapest.lansu.cheapestshopping.view.custom.SearchDialog;

import butterknife.ButterKnife;


/*
 * 文件名：BaseActivity
 * 描    述：
 * 作    者：lansu
 * 时    间：2018/4/26 12:25
 * 版    权： lansus 版权所有
 */
public abstract class BaseActivity extends AppCompatActivity {
	RelativeLayout linActivityBaseCustom;
	Context mContext;
	public View mContextView = null;
	private TextView toolbar_right_tv;
	private ImageView toolbar_right_iv;
	private TextView toolbar_title;
	private ImageView toolbar_return_iv;
	private ImageView toolbar_close_iv;
	Toolbar mToolbar;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		mContext = this;
		mContextView = LayoutInflater.from(this).inflate(R.layout.activity_base, null);
//      mContextView.setBackgroundColor(this.getResources().getColor(R.color.bg_gray));// 设置全局背景色
		setContentView(mContextView);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);                   //设置禁止横屏
		linActivityBaseCustom = findViewById(R.id.lin_activity_base_custom);
		View view = LayoutInflater.from(this).inflate(bindLayout(), null);
		view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		linActivityBaseCustom.addView(view);
		ButterKnife.bind(this, mContextView);
		ActivityCollectorUtlis.addActivity(this);
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar_right_tv = (TextView) findViewById(R.id.toolbar_right_tv);
		toolbar_right_iv = (ImageView) findViewById(R.id.toolbar_right_iv);
		toolbar_title = (TextView) findViewById(R.id.toolbar_title);
		toolbar_return_iv = (ImageView) findViewById(R.id.toolbar_return_iv);
		toolbar_close_iv = (ImageView) findViewById(R.id.toolbar_close_iv);
		;
		if (getToolbar_Return_Iv() != null)
			getToolbar_Return_Iv().setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});

		if (mToolbar != null) {
			//将Toolbar显示到界面
			setSupportActionBar(mToolbar);
		}
		if (toolbar_title != null) {
			//getTitle()的值是activity的android:lable属性值
			toolbar_title.setText(getTitle());
			//设置默认的标题不显示

			getSupportActionBar().setDisplayShowTitleEnabled(false);
		}

	}

	/**
	 * 如果当前设备版本是23及以上
	 * 检测定位权限
	 */
//    public boolean checkPermissionSDK23(){
//        if (Common.isAbove22SDKVersion()) {
//            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
//            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                        REQUEST_PREMISSION_LOCATION);
//                return false;
//            }
//        }
//        return true;
//    }
	@Override
	protected void onResume() {
		super.onResume();
		searchDialog();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	/**
	 * [绑定布局]
	 *
	 * @return int
	 */

	public abstract int bindLayout();

	/**
	 * 获取头部标题的TextView
	 */
	public Toolbar getToolbar() {
		return mToolbar;
	}

	/**
	 * 获取头部返回iv
	 */
	public ImageView getToolbar_Return_Iv() {
		return toolbar_return_iv;
	}

	/**
	 * 获取头部关闭iv
	 */
	public ImageView getToolbar_Close_Iv() {
		return toolbar_close_iv;
	}

	/**
	 * 获取头部标题的TextView
	 */
	public TextView getToolbarTitle() {
		return toolbar_title;
	}

	/**
	 * 获取头部标题的右面
	 */
	public TextView getToolbar_right_Tv() {
		return toolbar_right_tv;
	}

	/**
	 * 获取头部右面iv
	 */
	public ImageView getToolbarRight_Iv() {
		return toolbar_right_iv;
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

		ActivityCollectorUtlis.removeActivity(this);
	}


	public void removeToolBar() {
		((ViewGroup) mContextView).removeView(getToolbar());
	}

	private LoadingDialog mLoadingDialog;

	public void showProgressDialog() {
		if (mLoadingDialog == null) {
			mLoadingDialog = new LoadingDialog(this);
		}
		mLoadingDialog.setMessage("加载中...");
		mLoadingDialog.show();
	}

	public void showProgressDialog(String msg) {
		if (mLoadingDialog == null) {
			mLoadingDialog = new LoadingDialog(this);
		}
		if (StringUtils.isEmpty(msg)) {
			mLoadingDialog.setMessage("加载中...");
		} else {
			mLoadingDialog.setMessage(msg);
		}
		mLoadingDialog.show();
	}

	private SearchDialog mSearchDialog=null;
	private void searchDialog(){
		Boolean bFirst = (Boolean) SharedPreferencesUtils.getParam(this, Constants.isFirstOpen, true);
		if (bFirst) {
			return;
		}
		if (mLoadingDialog != null&&mLoadingDialog.isShowing()) {
			return;
		}
		if(App.isFristStartApp){
			return;
		}
		ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		ClipData data = cm.getPrimaryClip();
		if(data==null||data.getItemCount()==0){
			return;
		}
		ClipData.Item item = data.getItemAt(0);
		final String content = item.getText().toString();
		if(!TextUtils.isEmpty(content)){
			if(mSearchDialog==null){
			mSearchDialog=new SearchDialog(this,new View.OnClickListener(){
				@Override
				public void onClick(View view) {
					mSearchDialog.dismiss();
					cm.setPrimaryClip(ClipData.newPlainText(null, ""));
					App.isFristStartApp=true;
				}
			},new View.OnClickListener(){
				@Override
				public void onClick(View view) {
					mSearchDialog.dismiss();
					cm.setPrimaryClip(ClipData.newPlainText(null, ""));
					Intent intent = new Intent(mContext, WholeSearchActivity.class);
					intent.putExtra("keywords", content);
					startActivity(intent);
					App.isFristStartApp=true;
				}
			});}
			if(!mSearchDialog.isShowing()){
				mSearchDialog.setMessage(content);
				mSearchDialog.show();
			}else{
				mSearchDialog.setMessage(content);
			}
		}
	}


	public void discussProgressDialog() {
		if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
			mLoadingDialog.dismiss();
		}
//        ProgressDlgUtil.stopProgressDlg();
	}


	/**
	 * 为xml 的根布局添加android:fitsSystemWindows=”true” 属性<br/>
	 */
	protected void setTranslucentStatus() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(false);
		tintManager.setStatusBarTintResource(R.color.colorPrimary);// 通知栏所需颜色

	}

	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
}
