package com.cheapest.lansu.cheapestshopping.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.cheapest.lansu.cheapestshopping.Constant.AppConfig;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.UserEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitService;
import com.cheapest.lansu.cheapestshopping.utils.ActivityCollectorUtlis;
import com.cheapest.lansu.cheapestshopping.utils.SharedPreferencesManager;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;
import com.cheapest.lansu.cheapestshopping.utils.UserInfoUtils;
import com.cheapest.lansu.cheapestshopping.view.activity.BinderPhoneActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.MainActivity;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

import com.tencent.mm.sdk.openapi.WXAPIFactory;
;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	private IWXAPI api;
	private TextView tvTitle;
	private ImageView ivShow;
	private RelativeLayout rlReturn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.pay_result);
		this.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
		api = WXAPIFactory.createWXAPI(this, WeiXinPayMethod.APP_ID);
		api.handleIntent(getIntent(), this);

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {


	}

	@Override
	public void onResp(BaseResp resp) {


		if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
			switch (resp.errCode) {
				case BaseResp.ErrCode.ERR_OK:
					SendAuth.Resp sendResp = (SendAuth.Resp) resp;
					if (sendResp != null) {
						String code = String.valueOf(sendResp.code);
						getAccess_token(code);
					}

					finish();
					break;
				case BaseResp.ErrCode.ERR_USER_CANCEL:

					finish();
					break;
				case BaseResp.ErrCode.ERR_AUTH_DENIED:

					finish();
					break;
				default:
					finish();
					break;
			}

		} else if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if (resp.errCode == 0) {
				tvTitle.setText("充值成功");
			} else {
				tvTitle.setText("充值失败");
			}
			autoFinish();
		} else if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
			finish();
		}
	}

//		if(resp.getType()== ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX){//分享
//			Log.i("ansen","微信分享操作.....");
//			WeiXin weiXin=new WeiXin(2,resp.errCode,"");
//			EventBus.getDefault().post(weiXin);	}


	private void autoFinish() {
		finish();
	}

	/**
	 * 获取openid accessToken值用于后期操作
	 *
	 * @param code 请求码
	 */
	private void getAccess_token(final String code) {
		String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ AppConfig.app_id
				+ "&secret="
				+ "08368ca311dbfcc0ee4b0eba77405dfe"
				+ "&code="
				+ code
				+ "&grant_type=authorization_code";
		//网络请求，根据自己的请求方式
		Call<ResponseBody> call = RetrofitFactory.getInstence().API().get(path);
// 用法和OkHttp的call如出一辙,
// 不同的是如果是Android系统回调方法执行在主线程
		call.enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
				String result = null;
				try {
					result = response.body().string();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Log.i("wexin", "getAccess_token_result:" + result);
				JSONObject jsonObject = null;
				try {
					jsonObject = new JSONObject(result);
					String openid = jsonObject.getString("openid").toString().trim();
					String access_token = jsonObject.getString("access_token").toString().trim();
					getUserMesg(access_token, openid);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				t.printStackTrace();
			}

		});


	}

	/**
	 * 获取微信的个人信息
	 *
	 * @param access_token
	 * @param openid
	 */
	private void getUserMesg(final String access_token, final String openid) {
		String path = "https://api.weixin.qq.com/sns/userinfo?access_token="
				+ access_token
				+ "&openid="
				+ openid;
		Log.i("wexin", "getUserMesg：" + path);
		//网络请求，根据自己的请求方式
		Call<ResponseBody> call = RetrofitFactory.getInstence().API().get(path);
// 用法和OkHttp的call如出一辙,
// 不同的是如果是Android系统回调方法执行在主线程
		call.enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
				JSONObject jsonObject = null;
				try {
					try {
						jsonObject = new JSONObject(response.body().string());

					} catch (IOException e) {
						e.printStackTrace();
					}

					String nickname = jsonObject.getString("nickname");
					int sex = Integer.parseInt(jsonObject.get("sex").toString());
					String headimgurl = jsonObject.getString("headimgurl");

					Log.i("wexin", "用户基本信息:" + jsonObject.toString());
					Log.i("wexin", "nickname:" + nickname);
					Log.i("wexin", "sex:" + sex);
					Log.i("wexin", "headimgurl:" + headimgurl);
					wexinLogin(openid, nickname, headimgurl);

				} catch (JSONException e) {
					e.printStackTrace();
				}
				finish();
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				t.printStackTrace();
			}

		});


	}

	private void wexinLogin(String openid, String nickname, String headimgurl) {
		RetrofitFactory.getInstence().API()
				.thirdLogin(1, openid, nickname, headimgurl)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<UserEntity>(this) {
					@Override
					protected void onSuccees(BaseEntity<UserEntity> t) throws Exception {

						if (StringUtils.isEmpty(t.getData().getMobile())) {

							Intent intent = new Intent(WXEntryActivity.this, BinderPhoneActivity.class);
							intent.putExtra(Constants.INTENT_KEY_ID, t.getData().getId());
							intent.putExtra(Constants.PREFERENCE_KEY_USER_CUSTOKEN, t.getData().getToken());
							startActivity(intent);
						} else {
							UserInfoUtils.saveUserInfo(t.getData(), WXEntryActivity.this);
							ActivityCollectorUtlis.finishAll();
							startActivity(new Intent(WXEntryActivity.this, MainActivity.class));


						}


					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}
}