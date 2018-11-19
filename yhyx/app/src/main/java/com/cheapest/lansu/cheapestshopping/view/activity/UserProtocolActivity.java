package com.cheapest.lansu.cheapestshopping.view.activity;

import android.os.Bundle;
import android.text.Html;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.cheapest.lansu.cheapestshopping.R;

import butterknife.Bind;

public class UserProtocolActivity extends BaseActivity {
    @Bind(R.id.wv)
    WebView webview;

    /**
     * 用户协议
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbarTitle().setText("用户协议");

        webview.loadUrl("file:///android_asset/protocol.html");
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webview.getSettings().setLoadWithOverviewMode(true);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_user_protocol;
    }
}
