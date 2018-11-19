package com.cheapest.lansu.cheapestshopping.view.activity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.CityInfoModel;
import com.cheapest.lansu.cheapestshopping.model.entity.VersionEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.AppUtils;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.LiteOrmHelper;
import com.cheapest.lansu.cheapestshopping.utils.SharedPreferencesUtils;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;
import com.cheapest.lansu.cheapestshopping.utils.Util;
import com.cheapest.lansu.cheapestshopping.utils.WorkAvailable;
import com.cheapest.lansu.cheapestshopping.view.custom.BaseDialog;
import com.cheapest.lansu.cheapestshopping.view.custom.BottomBar;
import com.cheapest.lansu.cheapestshopping.view.dialog.CollageDialog;
import com.cheapest.lansu.cheapestshopping.view.fragment.ClassifyFragment;
import com.cheapest.lansu.cheapestshopping.view.fragment.FindFragment;
import com.cheapest.lansu.cheapestshopping.view.fragment.HomeManageFragment;
import com.cheapest.lansu.cheapestshopping.view.fragment.MineFragment;
import com.cheapest.lansu.cheapestshopping.view.fragment.NightSpecialFragment;
import com.google.gson.Gson;
import com.litesuits.orm.db.assit.QueryBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/***
 * 主界面
 */
public class MainActivity extends BaseActivity {


	private DownloadBuilder builder;
	private long exitTime = 0;

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getProvinceInfo();
		removeToolBar();
		setTranslucentStatus();
		checkVersion(this);
		AppUtils.applyPermission(this);
		BottomBar bottomBar = findViewById(R.id.bottom_bar);
		bottomBar.setContainer(R.id.fl_container)
				.setTitleBeforeAndAfterColor("#666666", "#FB4F67")
				.addItem(HomeManageFragment.class,
						getString(R.string.home_title),
						R.mipmap.icon_home_normal,
						R.mipmap.icon_home_selected)
				.addItem(NightSpecialFragment.class,
						getString(R.string.home_choice),
						R.mipmap.icon_nine_special_normal,
						R.mipmap.icon_nine_special_selected)
				.addItem(ClassifyFragment.class,
						getString(R.string.home_classify),
						R.mipmap.icon_classify_normal,
						R.mipmap.icon_classify_selected)
				.addItem(FindFragment.class,
						getString(R.string.home_find),
						R.mipmap.icon_find_normal,
						R.mipmap.icon_find_selected)
				.addItem(MineFragment.class,
						getString(R.string.home_mime),
						R.mipmap.icon_mine_normal,
						R.mipmap.icon_mine_selected)
				.build();
		if (CacheInfo.getUserID(this) == null) {
			WorkAvailable workAvailable = new WorkAvailable(this);
			workAvailable.setAlias("", "");

		} else {
			WorkAvailable workAvailable = new WorkAvailable(this);
			workAvailable.setAlias(CacheInfo.getUserID(this), CacheInfo.getUserID(this));
		}

		Boolean bFirst = (Boolean) SharedPreferencesUtils.getParam(MainActivity.this, Constants.isFirstOpen, true);
		if (bFirst) {
			SharedPreferencesUtils.setParam(MainActivity.this, Constants.isFirstOpen, false);
			CollageDialog collageDialog = CollageDialog.newInstance(null);
			collageDialog.show(getSupportFragmentManager(), "pintuan");
		}
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_main;
	}

	private void sendRequest(String url, String title) {
		UIData uiData = UIData.create();
		uiData.setTitle("版本升级");
		uiData.setDownloadUrl(url);
		uiData.setContent(title);
		builder = AllenVersionChecker
				.getInstance()
				.downloadOnly(uiData);
		builder.setShowDownloadFailDialog(false);
		builder.setCustomVersionDialogListener(createCustomDialogOne());
		builder.excuteMission(this);
	}


	private CustomVersionDialogListener createCustomDialogOne() {
		return new CustomVersionDialogListener() {
			@Override
			public Dialog getCustomVersionDialog(Context context, UIData versionBundle) {
				BaseDialog baseDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.view_down_app);
				TextView textView = baseDialog.findViewById(R.id.tv_msg);
				textView.setText(versionBundle.getContent());
				return baseDialog;
			}
		};
	}

	/**
	 * 检测更新
	 */
	private void checkVersion(final Context context) {
		showProgressDialog();
		RetrofitFactory.getInstence().API()
				.version("1", "1")
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<VersionEntity>(this) {
					@Override
					protected void onSuccees(BaseEntity<VersionEntity> t) throws Exception {
						discussProgressDialog();
						if (AppUtils.getVersionCode(MainActivity.this) < t.getData().getNumber()) {
							sendRequest(t.getData().getDownloadUrl(), t.getData().getLog());


						}


					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

			if ((System.currentTimeMillis() - exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
			{
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	int index = 0;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// 自己记录fragment的位置,防止activity被系统回收时，fragment错乱的问题
		super.onSaveInstanceState(outState);
		outState.putInt("index", index);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// super.onRestoreInstanceState(savedInstanceState);
		index = savedInstanceState.getInt("index");
	}

	private String levelType = "";//1省级，2地市级，3区县级 ； 默认1，此时parentId不传 或者传入100000
	private String parentId = "";


	private ArrayList<CityInfoModel> provinceItems = null;
	private Map<Integer, ArrayList<CityInfoModel>> cityItems = null;
	private ArrayList<Map<Integer, ArrayList<CityInfoModel>>> areaItems = null;

	private void getProvinceInfo() {
		provinceItems = LiteOrmHelper.getInstance().query(new QueryBuilder<CityInfoModel>(CityInfoModel.class).whereEquals("levelType", "1"));

		if (Util.isNotEmpty(provinceItems)) {
			return;
		}

		RetrofitFactory.getInstence().API().selectCities(levelType, parentId)
				.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<List<CityInfoModel>>(this) {

					@Override
					protected void onSuccees(BaseEntity<List<CityInfoModel>> t) throws Exception {
						if (t.getData() != null && t.getData().size() > 0) {
							provinceItems = (ArrayList<CityInfoModel>) t.getData();
							int saveResult1 = LiteOrmHelper.getInstance().save(t.getData());
							cityItems = new HashMap<>();
							initCityInfo();
						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
						discussProgressDialog();
					}
				});
	}

	private void initCityInfo() {
		cityItems = new HashMap<Integer, ArrayList<CityInfoModel>>();
		for (int i = 0; i < provinceItems.size(); i++) {
			final int position = i;
			levelType = "2";
			parentId = provinceItems.get(i).getId();
			RetrofitFactory.getInstence().API().selectCities(levelType, parentId)
					.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
					.subscribe(new BaseObserver<List<CityInfoModel>>(this) {

						@Override
						protected void onSuccees(BaseEntity<List<CityInfoModel>> t) throws Exception {
							Log.e("city", position + "城市信息=>" + new Gson().toJson(t.getData()));
							if (t.getData() != null && t.getData().size() > 0) {
								ArrayList<CityInfoModel> datas = (ArrayList<CityInfoModel>) t.getData();
								cityItems.put(position, datas);
								int saveResult2 = LiteOrmHelper.getInstance().save(t.getData());
								Log.e("TAG", "DBSAVE222:" + saveResult2);
							} else {
								ArrayList<CityInfoModel> datas = new ArrayList<>();
								datas.add(provinceItems.get(position));
								cityItems.put(position, datas);
							}
							if (provinceItems.size() == cityItems.size()) {
								initCounty();
							}
						}

						@Override
						protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
							discussProgressDialog();
						}
					});
		}
	}

	/**
	 * 初始县级城市
	 */
	private void initCounty() {
		areaItems = new ArrayList<>();
		for (int index = 0; index < cityItems.size(); index++) {
			final int mIndex = index;
			final Map<Integer, ArrayList<CityInfoModel>> map = new HashMap<>();
			for (int i = 0; i < cityItems.get(index).size(); i++) {
				final int position = i;
				levelType = "3";
				parentId = cityItems.get(index).get(i).getId();

				RetrofitFactory.getInstence().API().selectCities(levelType, parentId)
						.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
						.subscribe(new BaseObserver<List<CityInfoModel>>(this) {

							@Override
							protected void onSuccees(BaseEntity<List<CityInfoModel>> t) throws Exception {
								Log.e("TAG", position + "城市信息=>" + new Gson().toJson(t.getData()));
								if (t.getData() != null && t.getData().size() > 0) {
									ArrayList<CityInfoModel> datas = (ArrayList<CityInfoModel>) t.getData();
									map.put(position, datas);

									int saveResult1 = LiteOrmHelper.getInstance().save(t.getData());
									Log.e("TAG", "DBSAVE333:" + saveResult1);
								} else {
									ArrayList<CityInfoModel> datas = new ArrayList<>();
									datas.add(cityItems.get(mIndex).get(position));
									map.put(position, datas);
								}

								if (cityItems.size() == areaItems.size()) {
//									DialogUtils.dismiss();
									//initCityDialog();
								}
							}

							@Override
							protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
								discussProgressDialog();
							}
						});
			}

			areaItems.add(mIndex, map);
		}
	}
}
