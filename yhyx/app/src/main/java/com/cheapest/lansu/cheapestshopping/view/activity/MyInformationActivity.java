package com.cheapest.lansu.cheapestshopping.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.UserEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;
import com.cheapest.lansu.cheapestshopping.utils.UserInfoUtils;
import com.cheapest.lansu.cheapestshopping.view.custom.CircleImageView;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TResult;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 我的信息
 */
public class MyInformationActivity extends TakePhotoActivity {

    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar_return_iv)
    ImageView toolbarReturnIv;
    @Bind(R.id.ic_icon)
    CircleImageView icIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);
        ButterKnife.bind(this);
        toolbarReturnIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbarTitle.setText("我的信息");
        updateUserInfo();
    }


    @OnClick({R.id.rl_tuxiang, R.id.rl_nickname, R.id.rl_sex, R.id.rl_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_tuxiang:
                TakePhoto takePhoto = getTakePhoto();
                getTakePhoto().onEnableCompress(new
                                CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create(),
                        true);
                takePhoto.onPickFromGallery();
                break;

            case R.id.rl_nickname:
                editNickName();
                break;

            case R.id.rl_sex:
                editSex();
                break;

            case R.id.rl_address://收货地址
                startActivity(new Intent(MyInformationActivity.this, AddressManagerActivity.class));
                break;
        }
    }


    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);

        Glide.with(this).load(result.getImage().getPath()).into(icIcon);
        final File file = new File(result.getImage().getPath());
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("upfile", file.getName(), imageBody);
        RetrofitFactory.getInstence().API()
                .upfile(imageBodyPart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(this) {
                    @Override
                    protected void onSuccees(BaseEntity<String> t) throws Exception {

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("headpic", String.valueOf(t.getId()));
                        hashMap.put("id", CacheInfo.getUserID(MyInformationActivity.this));
                        editInfo(hashMap);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });


    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    /**
     * 更新用户信息
     */
    private void updateUserInfo() {

        RetrofitFactory.getInstence().API()
                .updateUserInfo(CacheInfo.getUserID(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<UserEntity>(this) {
                    @Override
                    protected void onSuccees(BaseEntity<UserEntity> t) throws Exception {
                        UserInfoUtils.saveUserInfo(t.getData(), MyInformationActivity.this);
                        tvNickname.setText(t.getData().getNickname());
                        Glide.with(MyInformationActivity.this).load(t.getData().getHeadpicUrl()).error(R.mipmap.icon_tuxiang_2).into(icIcon);
                        switch (t.getData().getGender()) {
                            case 0:
                                tvSex.setText("保密");
                                break;
                            case 1:
                                tvSex.setText("男");
                                break;
                            case 2:
                                tvSex.setText("女");
                                break;

                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });


    }

    public void editInfo(Map<String, String> map) {
        RetrofitFactory.getInstence().API()
                .editInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<UserEntity>(this) {
                    @Override
                    protected void onSuccees(BaseEntity<UserEntity> t) throws Exception {
                        UserInfoUtils.saveUserInfo(t.getData(), MyInformationActivity.this);
                        tvNickname.setText(t.getData().getNickname());
                        Glide.with(MyInformationActivity.this).load(t.getData().getHeadpicUrl()).asBitmap().into(icIcon);
                        switch (t.getData().getGender()) {
                            case 0:
                                tvSex.setText("保密");
                                break;
                            case 1:
                                tvSex.setText("男");
                                break;
                            case 2:
                                tvSex.setText("女");
                                break;

                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });


    }

    /**
     * 修改昵称
     */
    public void editNickName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入");    //设置对话框标题
        final EditText edit = new EditText(this);
        builder.setView(edit);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!StringUtils.isEmpty(edit.getText().toString().trim())) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("nickname", edit.getText().toString().trim());
                    hashMap.put("id", CacheInfo.getUserID(MyInformationActivity.this));
                    editInfo(hashMap);
                } else {
                    Toast.makeText(MyInformationActivity.this, "请输入昵称", Toast.LENGTH_LONG).show();
                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(false);    //设置按钮是否可以按返回键取消,false则不可以取消
        AlertDialog dialog = builder.create();  //创建对话框
        dialog.setCanceledOnTouchOutside(true); //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
        dialog.show();
    }

    public void editSex() {
        final String[] items={"保密","男","女"};
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("单选对话框")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("gender", String.valueOf(which));
                        hashMap.put("id", CacheInfo.getUserID(MyInformationActivity.this));
                       editInfo(hashMap);
                        dialog.dismiss();

                    }
                }).create();
        dialog.show();

    }
}
