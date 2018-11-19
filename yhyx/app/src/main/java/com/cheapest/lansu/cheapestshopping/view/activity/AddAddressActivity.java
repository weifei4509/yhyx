package com.cheapest.lansu.cheapestshopping.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.AddressModel;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.CityInfoModel;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.LiteOrmHelper;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;
import com.cheapest.lansu.cheapestshopping.utils.Util;
import com.cheapest.lansu.cheapestshopping.view.custom.pickerView.ConditionPickerView;
import com.google.gson.Gson;
import com.litesuits.orm.db.assit.QueryBuilder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 码农哥
 * @date 2018/7/5 0005  0:47
 * @email 441293364@qq.com
 * @TODO <p/>添加收貨地址
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
public class AddAddressActivity extends BaseActivity {


	@Bind(R.id.et_shouhuo_name)
	EditText et_shouhuo_name;//姓名
	@Bind(R.id.et_shouhuo_phone)
	EditText et_shouhuo_phone;//手機號碼
	@Bind(R.id.et_address_detial)
	EditText et_address_detial;//詳細地址
	@Bind(R.id.tv_select_city)
	TextView tv_select_city;

	AddressModel addressModel = null;

	private String provinceId;//省份Id
	private String cityId;//市Id
	private String areaId;//地区Id


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getToolbarTitle().setText("添加收货地址");
		addressModel = (AddressModel) getIntent().getSerializableExtra("addr_item");
		if (null != addressModel) {
			initAddressView();
		}
		getProvinceInfo();
	}

	private void initAddressView() {
		et_shouhuo_name.setText(addressModel.getDeliveryName());
		et_shouhuo_phone.setText(addressModel.getDeliveryMobile());
		tv_select_city.setText(addressModel.getProvinceName() + " " + addressModel.getCityName() + " " + addressModel.getAreaName());
		et_address_detial.setText(addressModel.getAddress());
//		et_youbian_num.setText(addressModel.get);
		provinceId = addressModel.getProvinceId();
		cityId = addressModel.getCityId();
		areaId = addressModel.getAreaId();
	}


	@Override
	public int bindLayout() {
		return R.layout.activity_add_address;
	}


	@OnClick({R.id.btn_save_address, R.id.layout_item_03})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.btn_save_address:
				addAddress();
				break;

			case R.id.layout_item_03:
				List<CityInfoModel> models = LiteOrmHelper.getInstance().query(CityInfoModel.class);
				Log.e("TAG", "DBSAVE 444" + models);
				//居住地
				if (provinceItems == null || provinceItems.size() == 0) {
					getProvinceInfo();
				} else {
					initCityDialog();
				}

				if (cityDialog != null && !cityDialog.isShowing()) {
					cityDialog.show();
				}
				break;
		}
	}

	private void addAddress() {
		String deliveryMobile = et_shouhuo_phone.getText().toString().trim();
		String deliveryName = et_shouhuo_name.getText().toString().trim();
		String address = et_address_detial.getText().toString().trim();

		if (TextUtils.isEmpty(deliveryName)) {
			ToastUtils.show(mContext, "收货人姓名不能为空");
			return;
		}


		if (TextUtils.isEmpty(deliveryMobile)) {
			ToastUtils.show(mContext, "手机号不能为空");
			return;
		}

		if (TextUtils.isEmpty(provinceId) || TextUtils.isEmpty(cityId) || TextUtils.isEmpty(areaId)) {
			ToastUtils.show(mContext, "请选择省市区");
			return;
		}

		if (TextUtils.isEmpty(address)) {
			ToastUtils.show(mContext, "详情地址不能为空");
			return;
		}

		if (null != addressModel) {
			updateUserAddress(deliveryName, deliveryMobile, address);
		} else {
			addUserAddress(deliveryName, deliveryMobile, address);
		}
	}


	ConditionPickerView cityDialog = null;

	private String levelType = "";//1省级，2地市级，3区县级 ； 默认1，此时parentId不传 或者传入100000
	private String parentId = "";


	private ArrayList<CityInfoModel> provinceItems = null;
	private Map<Integer, ArrayList<CityInfoModel>> cityItems = null;
	private ArrayList<Map<Integer, ArrayList<CityInfoModel>>> areaItems = null;

	/**
	 * 初始化城市选择框
	 */
	private void initCityDialog() {
		cityDialog = new ConditionPickerView<>(mContext);
		cityDialog.setPicker(provinceItems, cityItems, areaItems, true);
		cityDialog.setCyclic(false, false, false);
		cityDialog.setCancelable(false);
		cityDialog.setSelectOptions(0, 0, 0);
		cityDialog.setOnoptionsSelectListener(new ConditionPickerView.OnOptionsSelectListener() {

			@Override
			public void onOptionsSelect(int options1, int options2, int options3) {
				HashMap<Integer, ArrayList<CityInfoModel>> option2Items = (HashMap<Integer, ArrayList<CityInfoModel>>) cityDialog.getmOptions2Items();
				ArrayList<Map<Integer, ArrayList<CityInfoModel>>> option3Items = (ArrayList<Map<Integer, ArrayList<CityInfoModel>>>) cityDialog.getmOptions3Items();
				tv_select_city.setText(provinceItems.get(options1).getName() + option2Items.get(options1).get(options2).getName() + option3Items.get(options1).get(options2).get(options3).getName());
				provinceId = provinceItems.get(options1).getId();
				cityId = option2Items.get(options1).get(options2).getId();
				areaId = option3Items.get(options1).get(options2).get(options3).getId();
			}
		});
		cityDialog.show();
	}


	/**
	 * 更新收貨地址
	 *
	 * @param deliveryName
	 * @param deliveryMobile
	 * @param address
	 */
	private void updateUserAddress(String deliveryName, String deliveryMobile, String address) {
		showProgressDialog("提交中...");
		RetrofitFactory.getInstence().API().updateAddress(addressModel.getId(), CacheInfo.getUserID(this), deliveryMobile, deliveryName, provinceId, cityId, areaId, address)
				.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<String>(this) {

					@Override
					protected void onSuccees(BaseEntity<String> t) throws Exception {
						if (t.getCode() == 0) {
							ToastUtils.show(mContext, "修改成功");
						} else {
							ToastUtils.show(mContext, t.getMsg());
						}
						discussProgressDialog();
						EventBus.getDefault().post(200);
						finish();
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
						discussProgressDialog();
					}
				});
	}

	/**
	 * 新增收货地址
	 *
	 * @param deliveryName
	 * @param deliveryMobile
	 * @param address
	 */
	private void addUserAddress(String deliveryName, String deliveryMobile, String address) {
		showProgressDialog("提交中...");
		RetrofitFactory.getInstence().API().addAddress(CacheInfo.getUserID(this), deliveryMobile, deliveryName, provinceId, cityId, areaId, address)
				.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<String>(this) {

					@Override
					protected void onSuccees(BaseEntity<String> t) throws Exception {
						if (t.getCode() == 0) {
							ToastUtils.show(mContext, "操作成功");
						} else {
							ToastUtils.show(mContext, t.getMsg());
						}
						discussProgressDialog();
						EventBus.getDefault().post(200);
						finish();
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
						discussProgressDialog();
					}
				});
	}


	/**
	 * 獲取城市信息
	 */
	private void getProvinceInfo() {
		provinceItems = LiteOrmHelper.getInstance().query(new QueryBuilder<CityInfoModel>(CityInfoModel.class).whereEquals("levelType", "1"));
		if (Util.isNotEmpty(provinceItems)) {
			cityItems = new HashMap<>();
			for (int i = 0; i < provinceItems.size(); i++) {
				ArrayList<CityInfoModel> datas = LiteOrmHelper.getInstance().query(new QueryBuilder<CityInfoModel>(CityInfoModel.class).whereEquals("levelType", "2").whereAppendAnd().whereEquals("parentId", provinceItems.get(i).getId()));
				cityItems.put(i, datas);
			}

			areaItems = new ArrayList<>();
			for (int index = 0; index < cityItems.size(); index++) {
				final int mIndex = index;
				final Map<Integer, ArrayList<CityInfoModel>> map = new HashMap<>();
				for (int i = 0; i < cityItems.get(index).size(); i++) {
					ArrayList<CityInfoModel> areaList = LiteOrmHelper.getInstance().query(new QueryBuilder<CityInfoModel>(CityInfoModel.class).whereEquals("levelType", "3").whereAppendAnd().whereEquals("parentId", cityItems.get(index).get(i).getId()));
					map.put(i, areaList);
				}
				areaItems.add(mIndex, map);
			}
			return;
		}

		showProgressDialog("请稍候...");
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
