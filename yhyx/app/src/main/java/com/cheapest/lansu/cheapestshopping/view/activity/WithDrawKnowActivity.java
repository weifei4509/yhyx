package com.cheapest.lansu.cheapestshopping.view.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.VersionEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.AppUtils;

import butterknife.Bind;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.internal.util.ObserverSubscriber;
import rx.schedulers.Schedulers;

public class WithDrawKnowActivity extends BaseActivity {

    @Bind(R.id.tv_content)
    WebView wvGoodsDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbarTitle().setText("提现须知");

        RetrofitFactory.getInstence().API()
                .fetchNotice()
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
        return R.layout.activity_with_draw_know;
    }
}
