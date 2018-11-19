package com.cheapest.lansu.cheapestshopping.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.SplashEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TResult;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShaiShaiActivity extends TakePhotoActivity implements View.OnClickListener {

    @Bind(R.id.toolbar_right_tv)
    TextView toolbarRightTv;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar_return_iv)
    ImageView toolbarReturnIv;
    @Bind(R.id.toolbar_right_iv)
    ImageView toolbarRightIv;
    @Bind(R.id.toolbar_close_iv)
    ImageView toolbarCloseIv;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.add_image)
    ImageView addImage;
    @Bind(R.id.et_content)
    EditText etContent;
private  TResult result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shai_shai);
        ButterKnife.bind(this);
        toolbarTitle.setText("晒晒成绩");
        toolbarReturnIv.setOnClickListener(this);
        toolbarRightTv.setOnClickListener(this);
        toolbarRightTv.setText("发布");
        toolbarRightTv.setVisibility(View.VISIBLE);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePhoto takePhoto = getTakePhoto();
                getTakePhoto().onEnableCompress(new
                                CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create(),
                        true);
                takePhoto.onPickFromGallery();
            }
        });
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
       this.result=result;
        Glide.with(this).load(result.getImage().getPath()).into(addImage);


    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_right_tv:
             if (StringUtils.isEmpty(etContent.getText().toString().trim())){
                 Toast.makeText(ShaiShaiActivity.this, "请填写晒晒内容", Toast.LENGTH_SHORT).show();
                 return;
             }
             if (result==null){
                 Toast.makeText(ShaiShaiActivity.this, "请选择一张分享的图片", Toast.LENGTH_SHORT).show();
                 return;
             }
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
                             commitInfo( String.valueOf(t.getId()));
                            }

                            @Override
                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                            }
                        });
                break;
            case R.id.toolbar_return_iv:
                finish();
                break;
        }
    }

    private void commitInfo(String url) {
        RetrofitFactory.getInstence().API()
                .harvest(url, CacheInfo.getUserID(this),etContent.getText().toString().trim() )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(this) {
                    @Override
                    protected void onSuccees(BaseEntity<String> t) throws Exception {
                        Toast.makeText(ShaiShaiActivity.this,"发表成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }
}
