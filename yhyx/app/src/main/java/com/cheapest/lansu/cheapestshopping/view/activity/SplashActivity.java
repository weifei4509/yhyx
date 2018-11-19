package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.SplashEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.UserEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.SharedPreferencesUtils;

import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * @author 码农哥
 * @date 2018/7/20 0020  23:41
 * @email 441293364@qq.com
 * @TODO <p/>拼团详情
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
public class SplashActivity extends BaseActivity {

    @Bind(R.id.iv_splash)
    ImageView ivSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removeToolBar();
        RetrofitFactory.getInstence().API()
                .bootPages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
         .subscribe(new BaseObserver<List<SplashEntity>>(this){
             @Override
             protected void onSuccees(BaseEntity<List<SplashEntity>> t) throws Exception {
                 Glide.with(SplashActivity.this).load(t.getData().get(0).getImageUrl()).asBitmap().into(ivSplash);
             }

             @Override
             protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

             }
         });
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_splash;
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Boolean bFirst = (Boolean) SharedPreferencesUtils.getParam(SplashActivity.this, Constants.isFirstUse, true);
            if (!bFirst) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(SplashActivity.this,GuidePageActivity.class));
                finish();
            }
            super.handleMessage(msg);
        }
    };
}
