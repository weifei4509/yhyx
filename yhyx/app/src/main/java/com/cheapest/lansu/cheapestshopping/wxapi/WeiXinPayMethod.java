package com.cheapest.lansu.cheapestshopping.wxapi;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;


import com.cheapest.lansu.cheapestshopping.Constant.AppConfig;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WeiXinPayMethod {

	private static final String TAG = "MicroMsg.SDKSample.PayActivity";
	//appid
	//请同时修改  androidmanifest.xml里面，.PayActivity里的属性<data android:scheme="wxb4ba3c02aa476ea1"/>为新设置的appid
	public static  String APP_ID= AppConfig.app_id ;
	//商户号
	public static  String MCH_ID ;
	//  API密钥，在商户平台设置
	public static String API_KEY = "";
	/**包括订单号的结果HashMap*/
	public static Map<String,String> resultunifiedorder;

	/**生成APP微信支付签名参数Bean*/
	public static PayReq req;

	private static String notifyWexinURL;

	private static String values; //充值金额

	private static String tradNo; //订单号
	/**将该APP注册到微信*/
	public static void regist_WeiXinPay(Context context){
		final IWXAPI api = WXAPIFactory.createWXAPI(context, null);
		api.registerApp(WeiXinPayMethod.APP_ID);


	}

	/**微信支付方法*/
	public static void pay(Context context, String no, String vales, String WeiXin_PRIVATE, String notifyUrl,String appid,String mch_id) {
		values=vales;
		tradNo=no;
		API_KEY = WeiXin_PRIVATE;
		APP_ID=appid;
		MCH_ID=mch_id;
		notifyWexinURL=notifyUrl;
		GetPrepayIdTask getPrepayId = new GetPrepayIdTask(context);
		getPrepayId.execute();

	}

	private static class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String,String>> {

		private ProgressDialog dialog;
		private Context context;

		public GetPrepayIdTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(context, "提示", "正在获取预支付订单...");
		}

		@Override
		protected void onPostExecute(Map<String,String> result) {
			if (dialog != null) {
				dialog.dismiss();
			}
			resultunifiedorder=result;
		//	Toast.makeText(context, "订单号Bean获取成功", Toast.LENGTH_SHORT).show();
			genPayReq(context);
		}



		@Override
		protected Map<String,String> doInBackground(Void... params) {

			String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
			String entity = genProductArgs();

			Log.e("orion",entity);

			byte[] buf = Util.httpPost(url, entity);

			String content = new String(buf);
			Log.e("orion", content);
			Map<String,String> xml=decodeXml(content);

			return xml;
		}
	}

	/**获取商品参数*/
	private static String genProductArgs() {
		StringBuffer xml = new StringBuffer();

		try {
			String nonceStr = genNonceStr();
			xml.append("</xml>");
			List<NameValuePair> packageParams = new LinkedList<>();
			packageParams.add(new BasicNameValuePair("appid", WeiXinPayMethod.APP_ID));
			packageParams.add(new BasicNameValuePair("body", "赞助省惠优选"));
			packageParams.add(new BasicNameValuePair("mch_id", WeiXinPayMethod.MCH_ID));
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
			packageParams.add(new BasicNameValuePair("notify_url", notifyWexinURL));
			packageParams.add(new BasicNameValuePair("out_trade_no",tradNo));
			packageParams.add(new BasicNameValuePair("spbill_create_ip","192.168.1.1"));
			//	packageParams.add(new BasicNameValuePair("total_fee", values+"00"));
			packageParams.add(new BasicNameValuePair("total_fee", ((int)Double.parseDouble(values)*100)+""));
			packageParams.add(new BasicNameValuePair("trade_type", "APP"));

			String sign = genPackageSign(packageParams);
			packageParams.add(new BasicNameValuePair("sign", sign));


			String xmlstring =toXml(packageParams);

			return new String(xmlstring.toString().getBytes("UTF-8"), "ISO8859-1");


		} catch (Exception e) {
			return null;
		}
	}

	/**解析XML*/
	public static Map<String,String> decodeXml(String content) {

		try {
			Map<String, String> xml = new HashMap<>();
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {

				String nodeName=parser.getName();
				switch (event) {
					case XmlPullParser.START_DOCUMENT:

						break;
					case XmlPullParser.START_TAG:

						if(!"xml".equals(nodeName)){
							//实例化student对象
							xml.put(nodeName,parser.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						break;
				}
				event = parser.next();
			}

			return xml;
		} catch (Exception e) {
			Log.e("orion",e.toString());
		}
		return null;
	}

	/**拼接XML*/
	private static String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<"+params.get(i).getName()+">");


			sb.append(params.get(i).getValue());
			sb.append("</"+params.get(i).getName()+">");
		}
		sb.append("</xml>");

		Log.e("orion",sb.toString());
		return sb.toString();
	}

	private static String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}

	private static String genOutTradNo() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}

	/**生成签名*/
	private static String genPackageSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(WeiXinPayMethod.API_KEY);


		String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		Log.e("orion",packageSign);
		return packageSign;
	}

	/**封装APP微信支付参数*/
	private static void genPayReq(Context context) {

		req = new PayReq();
		req.appId = WeiXinPayMethod.APP_ID;//公众账号ID(微信分配的公众账号ID)
		req.partnerId = WeiXinPayMethod.MCH_ID;//商户号(微信支付分配的商户号)
		req.prepayId = resultunifiedorder.get("prepay_id");//预支付交易会话ID(微信返回的支付交易会话ID)
		req.packageValue = "Sign=WXPay";//扩展字段(暂填写固定值Sign=WXPay)
		req.nonceStr = genNonceStr();//随机字符串(随机字符串，不长于32位。推荐随机数生成算法)
		req.timeStamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳(时间戳，请见接口规则-参数规定)


		List<NameValuePair> signParams = new LinkedList<>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

		req.sign = genAppSign(signParams);//签名()
		Log.e("orion", signParams.toString());
	//	Toast.makeText(context, "封装APP微信支付参数", Toast.LENGTH_SHORT).show();
		sendPayReq(context,req);
	}

	private static String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(WeiXinPayMethod.API_KEY);

		String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		Log.e("orion",appSign);
		return appSign;
	}

	/**发送微信支付请求*/
	private static void sendPayReq(Context context, PayReq req) {

		final IWXAPI api = WXAPIFactory.createWXAPI(context, null);
		api.registerApp(WeiXinPayMethod.APP_ID);
		boolean b = api.sendReq(req);
	//	Toast.makeText(context, "发送支付请求--->"+b, Toast.LENGTH_SHORT).show();
	}

	/**检查为新版本是否支持支付*/
	public boolean supportPay(Context context){
		IWXAPI api = WXAPIFactory.createWXAPI(context, WeiXinPayMethod.APP_ID);
		return api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
	}
}
