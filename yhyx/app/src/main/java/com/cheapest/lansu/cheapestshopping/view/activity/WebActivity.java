package com.cheapest.lansu.cheapestshopping.view.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.utils.MyWebViewClient;

import butterknife.Bind;

public class WebActivity extends BaseActivity {

    @Bind(R.id.webview)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyWebViewClient myWebViewClient = new MyWebViewClient(this);
        //打开
       String stringUrl=getIntent().getStringExtra(Constants.INTENT_KEY_URL);
       String title=getIntent().getStringExtra(Constants.INTENT_KEY_TITLE);
        getToolbarTitle().setText(title);
        mWebView.loadUrl(stringUrl);
       setWebViewAttribute(mWebView);
        mWebView.setWebViewClient(myWebViewClient);



    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            try {
                mWebView.setVisibility(View.GONE);
                mWebView.removeAllViews();
                mWebView.clearHistory();
                mWebView.clearCache(true);
                mWebView.destroy();
            } catch (Throwable t) {
            }
            mWebView = null;
        }
    }
    @Override
    public int bindLayout() {
        return R.layout.activity_web;
    }
    /**
     * 设置WebView 属性
     */
    public static void setWebViewAttribute(WebView view) {
        WebSettings webSettings = view.getSettings();

        webSettings.setJavaScriptEnabled(true);  //支持js

        webSettings.setUseWideViewPort(false);  //将图片调整到适合webview的大小

        webSettings.setSupportZoom(true);  //支持缩放

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局

        webSettings.supportMultipleWindows();  //多窗口

        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存

        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);// 打开本地缓存提供JS调用,至关重要
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);// 实现8倍缓存
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
    }

}
