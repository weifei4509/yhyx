package com.cheapest.lansu.cheapestshopping.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.UserEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;
import com.cheapest.lansu.cheapestshopping.utils.TimeCountUtils;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 注册界面
 */
public class RegisterActivity extends BaseActivity implements TimeCountUtils.CountDownListener {

    @Bind(R.id.et_activity_register_phone)
    EditText etActivityRegisterPhone;
    @Bind(R.id.et_activity_register_psw)
    EditText etActivityRegisterPsw;
    @Bind(R.id.et_activity_register_invate)
    EditText etActivityRegisterInvate;
    @Bind(R.id.btn_activity_register_commit)
    Button btnActivityRegisterCommit;
    @Bind(R.id.btn_view_login_by_password_sms)
    Button btnViewLoginByPasswordSms;
    @Bind(R.id.et_view_login_by_password_psw)
    EditText etViewLoginByPasswordPsw;
    private TimeCountUtils mTimeCountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbarTitle().setText("注册");

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_register;

    }

    /**
     * 判断输入是否正确
     *
     * @return
     */
    private boolean inputIsFormat() {
        if (!StringUtils.isMobile(etActivityRegisterPhone.getText().toString())) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!StringUtils.isPassword(etActivityRegisterPsw.getText().toString())) {
            Toast.makeText(this, "密码格式是6到8位", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    /**
     * 注册
     */
    private void register() {
        showProgressDialog();
        RetrofitFactory.getInstence().API()
                .register(etActivityRegisterPhone.getText().toString(), etViewLoginByPasswordPsw.getText().toString(),etActivityRegisterPsw.getText().toString(),etActivityRegisterInvate.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<UserEntity>(this) {
                    @Override
                    protected void onSuccees(BaseEntity<UserEntity> t) throws Exception {

                     discussProgressDialog();
                    ToastUtils.showShort(RegisterActivity.this,"注册成功");
                     finish();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @OnClick({R.id.btn_view_login_by_password_sms, R.id.btn_activity_register_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_view_login_by_password_sms:
                if (StringUtils.isMobile(etActivityRegisterPhone.getText().toString())) {
                    getSms(etActivityRegisterPhone.getText().toString()); //获取验证码

                } else {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();

                }

                break;
            case R.id.btn_activity_register_commit:
                if (inputIsFormat()) {
                    register();
                }
                break;
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

                        ToastUtils.showShort(RegisterActivity.this, "发送成功");
                        startTimeCount();

                        discussProgressDialog();


                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    /**
     * 开始计时回调
     *
     * @param millisUntilFinished
     */
    @Override
    public void countDownOnTick(long millisUntilFinished) {
        btnViewLoginByPasswordSms.setClickable(false);
        btnViewLoginByPasswordSms.setText(millisUntilFinished / 1000 + "s后重试");
        btnViewLoginByPasswordSms.setBackground(getResources().getDrawable(R.drawable.bg_sms_selected));
        btnViewLoginByPasswordSms.setTextColor(Color.parseColor("#000000"));

    }

    /**
     * 结束计时回调
     *
     * @param
     */
    @Override
    public void countDownFinish() {
        btnViewLoginByPasswordSms.setClickable(true);
        btnViewLoginByPasswordSms.setTextColor(Color.WHITE);
        btnViewLoginByPasswordSms.setBackground(getResources().getDrawable(R.drawable.bg_corner_get_sms));
        btnViewLoginByPasswordSms.setText("获取验证码");
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


}
