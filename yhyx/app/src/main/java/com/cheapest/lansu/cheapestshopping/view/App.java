package com.cheapest.lansu.cheapestshopping.view;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.Toast;

import com.ali.auth.third.core.callback.InitResultCallback;
import com.ali.auth.third.core.exception.AlibabaSDKException;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.utils.SystemConfig;
import com.cheapest.lansu.cheapestshopping.view.custom.HomeListener;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.bugly.crashreport.CrashReport;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import cn.jpush.android.api.JPushInterface;

/*
 * 文件名：App
 * 描    述：
 * 作    者：lansu
 * 时    间：2018/4/26 12:58
 * 版    权： 云杉智慧新能源技术有限公司版权所有
 */
public class App extends Application {
	public static App app = null;
	public static boolean isFristStartApp=true;
	private HomeListener mHomeListen = null;

	@Override
	public void onCreate() {
		super.onCreate();
		app = this;
		isFristStartApp=true;
		CrashReport.initCrashReport(getApplicationContext(), "5fd1e0e1e8", false);

		TwinklingRefreshLayout.setDefaultHeader(SinaRefreshView.class.getName());
		TwinklingRefreshLayout.setDefaultFooter(LoadingView.class.getName());

		ZXingLibrary.initDisplayOpinion(this);
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
		SystemConfig.init(getApplicationContext());
		initImageLoader(getApplicationContext());
		AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
			@Override
			public void onSuccess() {
				//初始化成功，设置相关的全局配置参数

				// ...
			}

			@Override
			public void onFailure(int code, String msg) {
				//初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
			}
		});

		mHomeListen = new HomeListener(this);
		mHomeListen.setInterface(new HomeListener.KeyFun() {
			@Override
			public void recent() {
				Log.d("app", "recent");
				isFristStartApp=false;
			}

			@Override
			public void longHome() {
				Log.d("app", "longHome");
				isFristStartApp=false;
			}

			@Override
			public void home() {
				Log.d("app", "home");
				isFristStartApp=false;
			}
		});
		mHomeListen.startListen();
		ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		cm.setPrimaryClip(ClipData.newPlainText(null, ""));
	}

	@Override
	public void onTerminate() {
		mHomeListen.stopListen();
		super.onTerminate();
	}

	public static App getInstance() {
		return app;
	}

	private void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.

		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder() //
				.showImageForEmptyUri(R.color.bg_gray) //
				.showImageOnFail(R.color.bg_gray) //
				.cacheInMemory(true) //
				.cacheOnDisk(true) //
				.build();//
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(
				context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.memoryCache(new FIFOLimitedMemoryCache(2 * 1024 * 1024));
		config.defaultDisplayImageOptions(defaultOptions);//

		/*
		 * config.memoryCacheExtraOptions(720, 1280);
		 * config.diskCacheExtraOptions(720, 1280, null);
		 */
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());

		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		// config.writeDebugLogs(); // Remove for release app

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}
}
