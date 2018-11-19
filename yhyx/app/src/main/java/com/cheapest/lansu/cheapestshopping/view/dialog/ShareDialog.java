package com.cheapest.lansu.cheapestshopping.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cheapest.lansu.cheapestshopping.Constant.AppConfig;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.utils.BitmapUtil;
import com.cheapest.lansu.cheapestshopping.utils.Util;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;

/**
 * @author 码农哥
 * @date 2018/7/4 0004  23:58
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
public class ShareDialog extends BaseDialogFragment implements View.OnClickListener {

	Context mContext = null;
	static Activity activity = null;

	private String shareTitle;
	private String shareSummary;
	private String shareImage;
	private String shareUrl;
	private boolean canDismiss = false;

	Bitmap bitmap = null;

	private SimpleTarget target = new SimpleTarget<Bitmap>(250, 250) {
		@Override
		public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
			//这里我们拿到回掉回来的bitmap，可以加载到我们想使用到的地方
			ShareDialog.this.bitmap = BitmapUtil.zoomBitmap(bitmap, 32);
		}
	};

	public static ShareDialog newInstance(Activity act, Bundle bundle) {
		activity = act;
		ShareDialog dialog = new ShareDialog();
		dialog.setArguments(bundle);
		return dialog;
	}

	public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
		mContext = layoutInflater.getContext();
		bundle = getArguments();
		mTencent = Tencent.createInstance("101475401", mContext);
//		WbSdk.install(activity, new AuthInfo(activity, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE));

		if (bundle != null) {
			shareImage = bundle.getString("iconUrl");
			shareTitle = bundle.getString("title");
			shareSummary = bundle.getString("content");
			shareUrl = bundle.getString("shareUrl");
			Log.e("TAG", "Url->" + shareImage);
			if (Util.isNotEmpty(shareImage)) {
				Glide.with(mContext).load(shareImage).asBitmap().into(target);
			} else {

			}
		}

		if (this.mMainView == null) {
			this.mMainView = layoutInflater.inflate(R.layout.common_share_layout, viewGroup, false);
			getDialog().getWindow().setGravity(80);
//            this.shareDialogEventInterface = ((ShareDialogEventInterface)getActivity());
		}
		this.mMainView.findViewById(R.id.lin_share_weixin).setOnClickListener(this);
		this.mMainView.findViewById(R.id.lin_share_pengyouquan).setOnClickListener(this);
		this.mMainView.findViewById(R.id.lin_share_qq).setOnClickListener(this);
		this.mMainView.findViewById(R.id.lin_share_qq_qzone).setOnClickListener(this);
//		this.mMainView.findViewById(R.id.lin_share_weibo).setOnClickListener(this);

		return this.mMainView;
	}


	@Override
	public void onClick(View v) {
		canDismiss = true;
		if (v.getId() == R.id.lin_share_weixin) {//微信
			wechatShare(0);
		} else if (v.getId() == R.id.lin_share_pengyouquan) {//朋友圈
			wechatShare(1);
		} else if (v.getId() == R.id.lin_share_qq) { //QQ
			shareToQQ();
		} else if (v.getId() == R.id.lin_share_qq_qzone) {//QQ空间
			shareToQZone();
		}
//		else if (v.getId() == R.id.lin_share_weibo) {//微博
//			shareToWeibo();
//		}
//		if (v.getId() == R.id.tevCancel) {
//			dismiss();
//		}
	}

	private void wechatShare(int flag) {
		IWXAPI api = WXAPIFactory.createWXAPI(mContext, AppConfig.app_id);
		api.registerApp(AppConfig.app_id);
		if (!api.isWXAppInstalled()) {
			Toast.makeText(mContext, "您还未安装微信客户端",
					Toast.LENGTH_SHORT).show();
			return;
		}

		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = shareUrl;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = shareTitle;
		msg.description = shareSummary;
		//这里替换一张自己工程里的图片资源
//		Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
		msg.setThumbImage(bitmap);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
		api.sendReq(req);
	}


	private Bundle params = null;
	private Tencent mTencent = null;

	private void shareToQQ() {
		params = new Bundle();
		params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
		params.putString(QQShare.SHARE_TO_QQ_TITLE, shareTitle);// 标题
		params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareSummary);// 摘要
		params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareUrl);// 内容地址
		params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareImage);// 网络图片地址　　params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "应用名称");// 应用名称
		params.putString(QQShare.SHARE_TO_QQ_EXT_INT, "其它附加功能");
// 分享操作要在主线程中完成

		new Thread(new Runnable() {
			@Override
			public void run() {
				mTencent.shareToQQ(activity, params, new MyIUiListener());
			}
		}).start();
	}

	private void shareToQZone() {
		params = new Bundle();
		params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
		params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareTitle);// 标题
		params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareSummary);// 摘要
		params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareUrl);// 内容地址
		ArrayList<String> imgUrlList = new ArrayList<>();
		imgUrlList.add(shareImage);
		params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrlList);// 图片地址
// 分享操作要在主线程中完成
		new Thread(new Runnable() {
			@Override
			public void run() {
				mTencent.shareToQzone(activity, params, new MyIUiListener());
			}
		}).start();
	}


	class MyIUiListener implements IUiListener {
		@Override
		public void onComplete(Object o) {
			// 操作成功
		}

		@Override
		public void onError(UiError uiError) {
			// 分享异常
		}

		@Override
		public void onCancel() {
			// 取消分享
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (canDismiss) {
			dismiss();
		}
	}


//	private void shareToWeibo() {
//		WbShareHandler shareHandler = new WbShareHandler(activity);
//		shareHandler.registerApp();
//
//		WebpageObject mediaObj = new WebpageObject();
//		//创建文本消息对象
//
//		TextObject textObject = new TextObject();
//
//		textObject.text = "你分享内容的描述" + "分享网页的话加上网络地址";
//
//		textObject.title = "微博标题";
//
//		//创建图片消息对象，如果只分享文字和网页就不用加图片
//
//		WeiboMultiMessage message = new WeiboMultiMessage();
//
//		ImageObject imageObject = new ImageObject();
//
//		// 设置 Bitmap 类型的图片到视频对象里        设置缩略图。 注意：最终压缩过的缩略图大小 不得超过 32kb。
//
//		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//
//		imageObject.setImageObject(bitmap);
//
//		message.textObject = textObject;
//
//		message.imageObject = imageObject;
//
//		message.mediaObject = mediaObj;
//
//		shareHandler.shareMessage(message, false);
//	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
