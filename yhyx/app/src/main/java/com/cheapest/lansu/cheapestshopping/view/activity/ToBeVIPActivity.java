package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.UserEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.UserInfoUtils;
import com.cheapest.lansu.cheapestshopping.view.custom.IndicateDialog;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ToBeVIPActivity extends BaseActivity {

    @Bind(R.id.wv_content)
    WebView wvGoodsDetail;
    @Bind(R.id.btn_vip)
    Button btnVip;
    @Bind(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int isVip = CacheInfo.getUserInfoFromCache(this).isViped();
        removeToolBar();

        if (isVip == 1) {
            btnVip.setText("您已是尊贵vip");
        } else {
            btnVip.setText("升级VIP会员");
        }

        getToolbarTitle().setText("升级VIP");
//            RetrofitFactory.getInstence().API()
//                    .vipExplain(CacheInfo.getUserID(this))
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new BaseObserver<String>(this) {
//                        @Override
//                        protected void onSuccees(final BaseEntity<String> t) throws Exception {
//                            wvGoodsDetail.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
//                            wvGoodsDetail.getSettings().setUseWideViewPort(true);
//                            wvGoodsDetail.getSettings().setLoadWithOverviewMode(true);
//                            wvGoodsDetail.loadData(t.getData(), "text/html; charset=UTF-8", null);
//                        }
//
//                        @Override
//                        protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//
//                        }
//                    });
        btnVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isVip != 1) {

                    new IndicateDialog(ToBeVIPActivity.this, mContextView);
                } else {
                    finish();
                }
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_to_be_actiity;
    }

    @Override
    protected void onResume() {
        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText(null, ""));
        super.onResume();
        RetrofitFactory.getInstence().API()
                .updateUserInfo(CacheInfo.getUserID(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<UserEntity>(this) {
                    @Override
                    protected void onSuccees(BaseEntity<UserEntity> t) throws Exception {
                        int isVip=t.getData().isViped();
                        if (isVip == 1) {
                            btnVip.setText("您已是尊贵vip");
                        } else {
                            btnVip.setText("升级VIP会员");
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });



    }
}
