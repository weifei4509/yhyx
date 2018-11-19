package com.cheapest.lansu.cheapestshopping.view.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 关于我
 */
public class AboutUsActivity extends BaseActivity {


    @Bind(R.id.wv)
    WebView wvGoodsDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbarTitle().setText("关于我们");
        RetrofitFactory.getInstence().API()
                .aboutus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(this) {
                    @Override

                    protected void onSuccees(BaseEntity<String> t) throws Exception {

                        wvGoodsDetail.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
                        wvGoodsDetail.getSettings().setUseWideViewPort(true);
                        wvGoodsDetail.getSettings().setLoadWithOverviewMode(true);
                        wvGoodsDetail.loadData(t.getData(), "text/html; charset=UTF-8", null);

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_about_us;
    }
}
