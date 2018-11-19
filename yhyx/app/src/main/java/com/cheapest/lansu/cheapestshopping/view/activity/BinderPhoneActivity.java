package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.solver.Cache;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.UserEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.ActivityCollectorUtlis;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.SharedPreferencesManager;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;
import com.cheapest.lansu.cheapestshopping.utils.TimeCountUtils;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;
import com.cheapest.lansu.cheapestshopping.utils.UserInfoUtils;
import com.cheapest.lansu.cheapestshopping.wxapi.WXEntryActivity;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 绑定手机
 */
public class BinderPhoneActivity extends BaseActivity implements TimeCountUtils.CountDownListener {

    @Bind(R.id.et_view_login_by_password_phone)
    EditText etViewLoginByPasswordPhone;
    @Bind(R.id.et_view_login_by_password_psw)
    EditText etViewLoginByPasswordPsw;
    @Bind(R.id.btn_sms)
    Button btnSms;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.lin_binded)
    LinearLayout linBinded;
    @Bind(R.id.lin_input)
    LinearLayout linInput;
    @Bind(R.id.btn_activity_register_commit)
    Button btnActivityRegisterCommit;
    private TimeCountUtils mTimeCountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getToolbarTitle().setText("绑定手机号");
        if (!StringUtils.isEmpty(CacheInfo.getUserInfoFromCache(this).getMobile())) {
            linInput.setVisibility(View.GONE);
            tvPhone.setText(CacheInfo.getUserInfoFromCache(this).getMobile());
            linBinded.setVisibility(View.VISIBLE);
            btnActivityRegisterCommit.setText("已绑定");
        } else {
            linInput.setVisibility(View.VISIBLE);
            linBinded.setVisibility(View.GONE);
        }

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_binder_phone;
    }

    @OnClick({R.id.btn_sms, R.id.btn_activity_register_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sms:
                if (StringUtils.isMobile(etViewLoginByPasswordPhone.getText().toString())) {
                    getSms(etViewLoginByPasswordPhone.getText().toString()); //获取验证码

                } else {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.btn_activity_register_commit:
                   if (!StringUtils.isEmpty(CacheInfo.getUserInfoFromCache(BinderPhoneActivity.this).getMobile())){
                       finish();
                       return;
                   }
                if (!StringUtils.isMobile(etViewLoginByPasswordPhone.getText().toString().trim())) {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (!StringUtils.isPassword(etViewLoginByPasswordPsw.getText().toString().trim())) {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String phone = etViewLoginByPasswordPhone.getText().toString().trim();
                String sms = etViewLoginByPasswordPsw.getText().toString().trim();
                String userid;
                if (!StringUtils.isEmpty(getIntent().getStringExtra(Constants.INTENT_KEY_ID))){
                    userid=getIntent().getStringExtra(Constants.INTENT_KEY_ID);
                }else {
                 userid=   CacheInfo.getUserID(this) ;
                }

                RetrofitFactory.getInstence().API()
                        .bindPhone(userid, phone, sms)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<UserEntity>(BinderPhoneActivity.this) {
                            @Override
                            protected void onSuccees(BaseEntity<UserEntity> t) throws Exception {

                                ToastUtils.showShort(BinderPhoneActivity.this, "手机绑定成功");
                                if (!StringUtils.isEmpty(getIntent().getStringExtra(Constants.INTENT_KEY_ID))){
                                    UserInfoUtils.saveUserInfo(t.getData(), BinderPhoneActivity.this,getIntent().getStringExtra(Constants.PREFERENCE_KEY_USER_CUSTOKEN));
                                    ActivityCollectorUtlis.finishAll();

                                    startActivity(new Intent(BinderPhoneActivity.this,MainActivity.class));
                                }


                                finish();





                            }

                            @Override
                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                            }
                        });
                ;


                break;
        }
    }

    /**
     * 开始计时回调
     *
     * @param millisUntilFinished
     */
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

                        ToastUtils.showShort(BinderPhoneActivity.this, "发送成功");
                        startTimeCount();
                        discussProgressDialog();


                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }


}
