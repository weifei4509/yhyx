package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.NewsEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;
import com.cheapest.lansu.cheapestshopping.utils.UserInfoUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 码农哥
 * @date 2018/7/21 0021  7:29
 * @email 441293364@qq.com
 * @TODO <p/>
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
public class NewsDetailActivity extends BaseActivity {

	@Bind(R.id.tv_news_title)
	TextView tv_news_title;

	@Bind(R.id.tv_author)
	TextView tv_author;

	@Bind(R.id.tv_time)
	TextView tv_time;

	@Bind(R.id.iv_zan_img)
	ImageView iv_zan_img;

	@Bind(R.id.tv_zan_num)
	TextView tv_zan_num;

	@Bind(R.id.webview)
	WebView webView;

	int id = 0;

	boolean praised = false;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		id = getIntent().getIntExtra("id", 0);
		initData();
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_news_detail;
	}

	private void initData() {
		getNewsDetail();
	}

	/**
	 * 获取新闻详情
	 */
	private void getNewsDetail() {
		RetrofitFactory.getInstence().API().newsDetail("api/news/article/" + id, CacheInfo.getUserID(this)).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<NewsEntity.DatasBean>(mContext) {

					@Override
					protected void onSuccees(BaseEntity<NewsEntity.DatasBean> t) throws Exception {
						if (t.getData() != null) {
							initNewsData(t.getData());
						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}

	@OnClick({R.id.layout_zan})
	public void onViewClicked(View v) {
		switch (v.getId()) {
			case R.id.layout_zan:
				if (UserInfoUtils.isSharedLogin(mContext)) {
					newsPraise();
				} else {
					Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(mContext, LoginActivity.class));
				}
				break;
		}
	}

	/**
	 * 点赞成功
	 */
	private void newsPraise() {
		RetrofitFactory.getInstence().API().newsPraise(id, CacheInfo.getUserID(this)).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<String>(mContext) {

					@Override
					protected void onSuccees(BaseEntity<String> t) throws Exception {
						Log.e("TAG", t.getCode() + "");
//						if (t.getCode() == 0) {
//							getNewsDetail();
//						} else {
//							ToastUtils.show(mContext, t.getMsg());
//						}

						if (t.getCode() == 0) {
							praised = !praised;
							if (praised) {
								praisedNum += 1;
								iv_zan_img.setImageResource(R.mipmap.icon_collect);
							} else {
								praisedNum -= 1;
								iv_zan_img.setImageResource(R.mipmap.collect_1);
								ToastUtils.show(mContext, "取消点赞");
							}
							tv_zan_num.setText("点赞(" + praisedNum + ")");
						} else {
							ToastUtils.show(mContext, t.getMsg());
						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}

	int praisedNum = 0;

	private void initNewsData(NewsEntity.DatasBean data) {
		praised = data.isPraised();
		praisedNum = data.getPraiseNum();

		tv_news_title.setText(data.getTitle());
		tv_author.setText(data.getAuthor());
		tv_time.setText(data.getCreateTime());
		tv_zan_num.setText("点赞(" + data.getPraiseNum() + ")");
		if (praised) {
			iv_zan_img.setImageResource(R.mipmap.icon_collect);
		} else {
			iv_zan_img.setImageResource(R.mipmap.collect_1);
		}

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
