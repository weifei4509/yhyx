package com.cheapest.lansu.cheapestshopping.view.custom;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import com.cheapest.lansu.cheapestshopping.Constant.AppConfig;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.VipEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.utils.ToastUtils;
import com.cheapest.lansu.cheapestshopping.utils.WorkAvailable;
import com.cheapest.lansu.cheapestshopping.utils.alipay.AuthResult;
import com.cheapest.lansu.cheapestshopping.utils.alipay.OrderInfoUtil2_0;
import com.cheapest.lansu.cheapestshopping.utils.alipay.PayResult;
import com.cheapest.lansu.cheapestshopping.wxapi.WeiXinPayMethod;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.cheapest.lansu.cheapestshopping.Constant.AppConfig.RSA_PRIVATE;

/*
* 文件名：IndicateDialog
* 描    述：
* 作    者：lansu
* 时    间：2018/5/17 9:58
* 版    权：lansus
*/


public class IndicateDialog implements View.OnClickListener {
    private RelativeLayout rlZhifubao;
    private RelativeLayout rlwexin;
    private ImageView ivClose;

    //上下文环境
    private Context mContext;
    private RelativeLayout ll_popup;
    private PopupWindow pop;
    private View mParentView;
    private View mCurrentView;;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;


    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                        closePopWindow();

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                        closePopWindow();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(mContext,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(mContext,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                }
                break;

            }
        }
    };





    /**
     * 构造函数
     */
    public IndicateDialog(Context context, View parenView) {
        mContext=context;
        //新建popwindows，定义属性
        pop = new PopupWindow(context,null,R.style.custom_dialog_style);

        mCurrentView=parenView;
        mParentView = LayoutInflater.from(context).inflate(R.layout.dialog_indicate, (ViewGroup) parenView,false);
        //设置popwindows宽和高
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置背景色
        pop.setBackgroundDrawable(new BitmapDrawable());
        //设置popwindows焦点
        pop.setFocusable(true);
        //设置popwindows以外可以触摸
        pop.setOutsideTouchable(true);
        //设置popwindows的视图
        pop.setContentView(mParentView);
        //设置popwindow的弹出和弹出动画
        ll_popup = (RelativeLayout) mParentView.findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(context, R.anim.activity_fade_in));
        pop.showAtLocation(parenView, Gravity.TOP, 10, 10);
        mParentView.setVisibility(View.VISIBLE);
        initView();
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mContext != null) {
                    setBackgroundAlpha((Activity) mContext, 1f);
                }
            }
        });
        setBackgroundAlpha((Activity) mContext,0.4f);
    }

    /**
     * 初始化界面
     */
    private void initView( ){
rlwexin=mParentView.findViewById(R.id.rl_weixin);
rlZhifubao=mParentView.findViewById(R.id.rl_zhifubao);
ivClose=mParentView.findViewById(R.id.iv_close);
        rlwexin.setOnClickListener(this);
        ivClose.setOnClickListener(this);
rlZhifubao.setOnClickListener(this);
ivClose.setOnClickListener(this);




    }


    /**
     * 关闭弹窗
     */
    private void closePopWindow(){
       pop.dismiss();
        ll_popup.clearAnimation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.rl_weixin:
                 tobeVip(2);
                    closePopWindow();
                break;
            case R.id.rl_zhifubao:
                tobeVip(1);
                closePopWindow();
                break;
            case R.id.iv_close:
                closePopWindow();
                break;

        }
    }

    /**
     *支付
     * @param i  支付类型  1 支付宝  2 微信
     */
    private void tobeVip(int i) {
        RetrofitFactory.getInstence().API()
                .upgradeVip(CacheInfo.getUserID(mContext),i+"" )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<VipEntity>(mContext) {
                    @Override
                    protected void onSuccees(BaseEntity<VipEntity> t) throws Exception {
                          switch (t.getData().getPayType()){
                              case  1:   //todo  支付宝支付
                                  payV2(t.getData().getPayNo(),t.getData().getAmount()+"" , "http://jiuchiwl.cn/api/callback/alipay");
                                  break;
                              case 2: //todo 微信支付
                                  if (WorkAvailable.isAvilible(mContext, "com.tencent.mm")) {
                                     WeiXinPayMethod.pay(mContext, t.getData().getPayNo(),t.getData().getAmount()+"",AppConfig. WeiXin_PRIVATE, "http://jiuchiwl.cn/api/callback/wxpay",AppConfig.app_id,AppConfig.mch_id);
                                  } else {
                                      ToastUtils.showShort(mContext, "请先安装微信客户端");
                                  }
                                  break;


                          }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }
    /**
     * 支付宝支付业务
     *
     * @param
     */
    public void payV2(String orderNo, String values, String notifyUrl) {
        if (TextUtils.isEmpty(AppConfig.APPID) || (TextUtils.isEmpty(AppConfig.RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(mContext).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {


                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (AppConfig.RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(AppConfig.APPID, rsa2, orderNo, values, notifyUrl);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String privateKey = rsa2 ? AppConfig.RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    /**
     * 设置页面的透明度
     * @param bgAlpha 1表示不透明
     */
    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.getWindow().setAttributes(lp);
    }

}


