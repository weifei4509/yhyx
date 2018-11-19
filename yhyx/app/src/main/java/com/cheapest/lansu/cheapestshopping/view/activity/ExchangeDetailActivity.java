package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ExchangeEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.view.custom.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 码农哥
 * @date 2018/7/7 0007  2:23
 * @email 441293364@qq.com
 * @TODO <p/>兑换商品详情介绍
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
public class ExchangeDetailActivity extends BaseActivity {

	@Bind(R.id.exchange_banner)
	Banner exchange_banner;

	@Bind(R.id.tv_exchange_title)
	TextView tv_exchange_title;

	@Bind(R.id.tv_exchange_summary)
	TextView tv_exchange_summary;

	@Bind(R.id.tv_exchange_score)
	TextView tv_exchange_score;

	@Bind(R.id.tv_exchange_price)
	TextView tv_exchange_price;

	@Bind(R.id.tv_exchange_stock)
	TextView tv_exchange_stock;

	@Bind(R.id.tv_exchange_num)
	TextView tv_exchange_num;

	@Bind(R.id.webview)
	WebView webView;

	private int id;

	ExchangeEntity.DatasBean datasBean = null;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getToolbarTitle().setText("商品详情");

		id = getIntent().getIntExtra("id", 0);

		initView();
		initData();

	}

	private void initView() {
		exchange_banner.setImageLoader(new GlideImageLoader());
		//设置自动轮播，默认为true
		exchange_banner.isAutoPlay(true);
		//设置轮播时间
		exchange_banner.setDelayTime(3000);
		//设置指示器位置（当banner模式中有指示器时）
		exchange_banner.setIndicatorGravity(BannerConfig.CENTER);
	}


	private void initData() {
		getGoodsDetail(id);
	}


	@Override
	public int bindLayout() {
		return R.layout.activity_exchange_detail;
	}


	@OnClick({R.id.btn_exchange})
	public void onViewClicked(View v) {
		switch (v.getId()) {
			case R.id.btn_exchange:
				Intent intent = new Intent(mContext, ExchangeOrderActivity.class);
				intent.putExtra("product", datasBean);
				startActivity(intent);
				break;
		}
	}

	/**
	 * 获取商品详情
	 *
	 * @param id
	 */
	private void getGoodsDetail(int id) {
		RetrofitFactory.getInstence().API().swapGoogs("api/sign/swapGoogs/" + id).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<ExchangeEntity.DatasBean>(this) {

					@Override
					protected void onSuccees(BaseEntity<ExchangeEntity.DatasBean> t) throws Exception {
						if (t.getData() != null) {
							initExchangeDetail(t.getData());
						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}

	/**
	 * 兑换详情
	 *
	 * @param data
	 */
	private void initExchangeDetail(ExchangeEntity.DatasBean data) {
		this.datasBean = data;
		exchange_banner.setImages(Arrays.asList(data.getImageUrls()));
		exchange_banner.start();
		tv_exchange_title.setText(data.getName());
		tv_exchange_summary.setText(data.getSummary());
		tv_exchange_score.setText("兑换：" + data.getScore() + "金豆");
		tv_exchange_price.setText("价值：" + data.getPrice() + "元");
		tv_exchange_stock.setText("库存：" + data.getStock() + "件");
		tv_exchange_num.setText("已兑：" + data.getExchangeNum() + "件");

		webView.getSettings().setJavaScriptEnabled(true);
		// 设置可以支持缩放
		webView.getSettings().setSupportZoom(false);
		// 设置出现缩放工具
		webView.getSettings().setBuiltInZoomControls(false);
		// 为图片添加放大缩小功能
		//webview.getSettings().setUseWideViewPort(true);
		//自适应屏幕
		webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		webView.getSettings().setLoadWithOverviewMode(true);

		webView.setHorizontalScrollBarEnabled(false);//禁止水平滚动
		webView.setVerticalScrollBarEnabled(true);//允许垂直滚动

		webView.loadDataWithBaseURL("", getDetailContent(data.getContent()), "text/html", "UTF-8", "");
	}

	public static String getDetailContent(String htmltext) {
		Document doc = Jsoup.parse(htmltext);
		Elements elements = doc.getElementsByTag("img");
		for (Element element : elements) {
			element.attr("width", "100%").attr("height", "auto");
		}

		return doc.toString();
	}
}
