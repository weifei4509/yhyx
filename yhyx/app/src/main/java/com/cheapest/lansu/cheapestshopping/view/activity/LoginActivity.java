package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cheapest.lansu.cheapestshopping.Constant.AppConfig;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.UserEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.ActivityCollectorUtlis;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;
import com.cheapest.lansu.cheapestshopping.utils.TimeCountUtils;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;
import com.cheapest.lansu.cheapestshopping.utils.UserInfoUtils;
import com.cheapest.lansu.cheapestshopping.view.adapter.LoginPaperAdapter;
import com.cheapest.lansu.cheapestshopping.wxapi.WeiXinPayMethod;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.cheapest.lansu.cheapestshopping.Constant.Constants.ACTIVITY_REQUEST_OK;

/*
   * 文件名：LoginActivity
   * 描    述：登录界面
   * 作    者：lansu
   * 时    间：2018/4/26 12:25
   * 版    权： lansus 版权所有
   */
public class LoginActivity extends BaseActivity implements TimeCountUtils.CountDownListener {

    @Bind(R.id.tablayout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.tv_activity_login_fogget_password)
    TextView tvActivityLoginFoggetPassword;
    @Bind(R.id.tv_activity_login_protocol)
    TextView tvActivityLoginProtocol;
    EditText etViewLoginByPasswordPhone;
    EditText etViewLoginByPasswordPsw;
    EditText etViewLoginBySmsPhone;
    EditText etViewLoginBySmsPsw;
    Button btnSms;
    @Bind(R.id.ic_back)
    ImageView icBack;
    private TimeCountUtils mTimeCountButton;
    private String registerId;
    private  LoginActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removeToolBar();
           instance=this;
        ArrayList<View> aList = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();
        View view = li.inflate(R.layout.view_login_by_password, null, false);
        View view2 = li.inflate(R.layout.view_login_by_sms, null, false);
        aList.add(view);
        aList.add(view2);
        LoginPaperAdapter mAdapter = new LoginPaperAdapter(aList);
        viewpager.setAdapter(mAdapter);
        registerId= JPushInterface. getRegistrationID(this);
        tabLayout.setupWithViewPager(viewpager);
        setIndicator(tabLayout, 30, 30);
        etViewLoginByPasswordPhone = view.findViewById(R.id.et_view_login_by_password_phone);
        etViewLoginByPasswordPsw = view.findViewById(R.id.et_view_login_by_password_psw);
        etViewLoginBySmsPhone = view2.findViewById(R.id.et_view_login_by_sms_phone);
        etViewLoginBySmsPsw = view2.findViewById(R.id.et_view_login_by_sms_psw);
        btnSms = view2.findViewById(R.id.btn_login_sms);
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isMobile(etViewLoginBySmsPhone.getText().toString().trim())) {
                    getSms(etViewLoginBySmsPhone.getText().toString().trim());
                } else {
                    Toast.makeText(LoginActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });

    icBack.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;


    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip)

    {
        DisplayMetrics displaysMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaysMetrics);
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        tabStrip.setAccessible(true);
        LinearLayout ll_tab = null;
        try {
            ll_tab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int left = (int) (displaysMetrics.density * leftDip);
        int right = (int)
                (displaysMetrics.density * rightDip);
        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    @OnClick({R.id.btn_login, R.id.tv_activity_login_fogget_password, R.id.lin_activity_login_register, R.id.tv_activity_login_protocol, R.id.lin_activity_login_wechat_login})
    public void onViewClicked(View view) {


        switch (view.getId()) {
            case R.id.btn_login:
                if (tabLayout.getSelectedTabPosition() == 0) {
                    loginByPassword();
                } else {
                    loginBySms();
                }
                break;

            case R.id.lin_activity_login_register:
                startActivityForResult(new Intent(this, RegisterActivity.class), ACTIVITY_REQUEST_OK);
                break;
            case R.id.tv_activity_login_protocol:
                startActivity(new Intent(this, UserProtocolActivity.class));
                break;
            case R.id.lin_activity_login_wechat_login:
                showProgressDialog();
                 IWXAPI api = WXAPIFactory.createWXAPI(this, null);
                api.registerApp(AppConfig.app_id);
                if ( api.isWXAppInstalled()) {
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test_neng";
                    api.sendReq(req);
                } else
                    Toast.makeText(this, "用户未安装微信", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_activity_login_fogget_password:
                startActivityForResult(new Intent(this, ForgetPasswordActivity.class), ACTIVITY_REQUEST_OK);
                break;
        }
    }

    /**
     * 短信登录
     */
    private void loginBySms() {
        if (!StringUtils.isMobile(etViewLoginBySmsPhone.getText().toString().trim())) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(etViewLoginBySmsPsw.getText().toString().trim())) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        RetrofitFactory.getInstence().API()
                .loginBySms(etViewLoginBySmsPhone.getText().toString(), etViewLoginBySmsPsw.getText().toString(), registerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<UserEntity>(this) {
                    @Override
                    protected void onSuccees(BaseEntity<UserEntity> t) throws Exception {
                        dealLoginSuccess(t);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });

    }

    /**
     * 密码登录
     */
    private void loginByPassword() {
        if (!StringUtils.isMobile(etViewLoginByPasswordPhone.getText().toString().trim())) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!StringUtils.isPassword(etViewLoginByPasswordPsw.getText().toString().trim())) {
            Toast.makeText(this, "密码格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        RetrofitFactory.getInstence().API()
                .loginByPassword(etViewLoginByPasswordPhone.getText().toString(), etViewLoginByPasswordPsw.getText().toString(), registerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<UserEntity>(this) {
                    @Override
                    protected void onSuccees(BaseEntity<UserEntity> t) throws Exception {
                        dealLoginSuccess(t);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });


    }

    /**
     * 处理结果
     *
     * @param t
     */
    private void dealLoginSuccess(BaseEntity<UserEntity> t) {
        discussProgressDialog();
        ToastUtils.showShort(LoginActivity.this, "登录成功");
        UserInfoUtils.saveUserInfo(t.getData(), LoginActivity.this);
        ActivityCollectorUtlis.finishAll();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    /**
     * 开始计时回调
     *
     * @param millisUntilFinished
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void countDownOnTick(long millisUntilFinished) {
        btnSms.setClickable(false);
        btnSms.setText(millisUntilFinished / 1000 + "s后重试");
        btnSms.setBackground(getResources().getDrawable(R.drawable.bg_sms_selected));
        btnSms.setTextColor(Color.parseColor("#000000"));

    }

    /**
     * 结束计时回调
     *
     * @param
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void countDownFinish() {
        btnSms.setClickable(true);
        btnSms.setTextColor(Color.WHITE);
        btnSms.setBackground(getResources().getDrawable(R.drawable.bg_corner_get_sms));
        btnSms.setText("获取验证码");
    }

    /**
     * 开始计时
     *
     * @param
     */
    private void startTimeCount() {
        if (mTimeCountButton == null) {
            mTimeCountButton = new TimeCountUtils(60000, 1000);
            mTimeCountButton.addCountDownListener(this);
        }
        mTimeCountButton.start();
    }

    /**
     * 取消计时
     *
     * @param
     */
    private void cancelTimeCountButton() {
        if (mTimeCountButton != null) {
            mTimeCountButton.cancel();
            mTimeCountButton = null;
        }
    }

    private void getSms(String str) {
        showProgressDialog();
        RetrofitFactory.getInstence().API()
                .getSms(str)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(this) {
                    @Override
                    protected void onSuccees(BaseEntity<String> t) throws Exception {

                        ToastUtils.showShort(LoginActivity.this, "发送成功");
                        startTimeCount();

                        discussProgressDialog();


                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

}
