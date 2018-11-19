package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;
import com.cheapest.lansu.cheapestshopping.utils.TimeCountUtils;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.cheapest.lansu.cheapestshopping.Constant.Constants.ACTIVITY_REQUEST_OK;

public class ForgetPasswordActivity extends BaseActivity implements TimeCountUtils.CountDownListener {
  public static ForgetPasswordActivity instance;
    @Bind(R.id.et_view_login_by_password_phone)
    EditText etViewLoginByPasswordPhone;
    @Bind(R.id.et_view_login_by_password_psw)
    EditText etViewLoginByPasswordPsw;
    @Bind(R.id.iv_view_login_by_password_phone)
    ImageView ivViewLoginByPasswordPhone;
    @Bind(R.id.btn_sms)
    Button btnSms;
    @Bind(R.id.btn_activity_register_commit)
    Button btnActivityRegisterCommit;
    private TimeCountUtils mTimeCountButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbarTitle().setText("忘记密码");
        instance=this;

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_oprate_password;
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

                if (!StringUtils.isMobile(etViewLoginByPasswordPhone.getText().toString().trim())){
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (!StringUtils.isPassword(etViewLoginByPasswordPsw.getText().toString().trim())){
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String phone=etViewLoginByPasswordPhone.getText().toString().trim();
                String sms=etViewLoginByPasswordPsw.getText().toString().trim();
                Intent intent =new Intent(this,NewPasswordActivity.class);
                intent.putExtra(Constants.INTENT_KEY_PHONE,phone);
                intent.putExtra(Constants.INTENT_KEY_SMS,sms);
                startActivity(intent);
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

                        ToastUtils.showShort(ForgetPasswordActivity.this, "发送成功");
                        startTimeCount();
                        discussProgressDialog();


                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

}
