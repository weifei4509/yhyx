package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.UserEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewPasswordActivity extends BaseActivity {

    @Bind(R.id.et_activity_register_phone)
    EditText etActivityRegisterPhone;
    @Bind(R.id.et_activity_register_psw)
    EditText etActivityRegisterPsw;
    @Bind(R.id.btn_activity_register_commit)
    Button btnActivityRegisterCommit;
    private String phone;
    private String sms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbarTitle().setText("输入新密码");
         phone=getIntent().getStringExtra(Constants.INTENT_KEY_PHONE);
         sms=getIntent().getStringExtra(Constants.INTENT_KEY_SMS);
        btnActivityRegisterCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!StringUtils.isPassword(etActivityRegisterPhone.getText().toString().trim())){
                    Toast.makeText(NewPasswordActivity.this, "新密码格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (!StringUtils.isPassword(etActivityRegisterPsw.getText().toString().trim())){
                    Toast.makeText(NewPasswordActivity.this, "第二次输入的密码格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!etActivityRegisterPsw.getText().toString().trim().equals(etActivityRegisterPsw.getText().toString().trim())){
                    Toast.makeText(NewPasswordActivity.this, "两次的输入的密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }

                RetrofitFactory.getInstence().API()
                        .forgetPassWord(phone,sms,etActivityRegisterPsw.getText().toString().trim())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<UserEntity>(NewPasswordActivity.this) {
                            @Override
                            protected void onSuccees(BaseEntity<UserEntity> t) throws Exception {

                                ToastUtils.showShort(NewPasswordActivity.this,"修改成功，请去登录");
                                 finish();


                            }

                            @Override
                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                            }
                        });



            }
        });







    }

    @Override
    public int bindLayout() {
        return R.layout.activity_new_password;
    }
}
