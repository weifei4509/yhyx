package com.cheapest.lansu.cheapestshopping.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.cheapest.lansu.cheapestshopping.Constant.AppConfig;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*
* 文件名：shareUtils
* 描    述：
* 作    者：lansu
* 时    间：2018/5/25 14:20
* 版    权：lansus
*/
public class shareUtils {
    private String shareUrl="http://www.baidu.com";
    private Context mContext;
    private String title;
    private String APP_Id = "101475401";
    private List<String> uris=new ArrayList<>();
    private Bitmap mBitMap;
    private List<File> files=new ArrayList<>();

    public shareUtils(String shareUrl, Context mContext, String title, String content, Bitmap bitmap, List<String> urls) {
        this.uris = urls;
        this.mBitMap = bitmap;
        this.shareUrl = shareUrl;
        this.mContext = mContext;
        this.title = title;
        this.content = content;
    }

    private String content;

    /**
     * 分享到微信
     */
    public void weixinShare() {
        IWXAPI api = WXAPIFactory.createWXAPI(mContext, AppConfig.app_id);
        api.registerApp(AppConfig.app_id);
        if (!api.isWXAppInstalled()) {
            Toast.makeText(mContext, "您还未安装微信客户端",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //这一步一定要clear,不然分享了朋友圈马上分享好友图片就会翻倍
                files.clear();

                try {
                   files.add(saveFile(mBitMap,IMAGE_NAME + i+ ".jpg"));
                    for (int i = 1; i < uris.size(); i++) {
                        File file = saveImageToSdCard(mContext, uris.get(i));
                        files.add(file);
                    }

                    Intent intent = new Intent();
                    ComponentName comp;


                        comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");

                    intent.setComponent(comp);
                    intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                    intent.setType("image/*");

                    ArrayList<Uri> imageUris = new ArrayList<Uri>();
                    for (File f : files) {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                            imageUris.add(Uri.fromFile(f));
                        }else {
                            //修复微信在7.0崩溃的问题
                            Uri uri =Uri.parse(android.provider.MediaStore.Images.Media.insertImage(mContext.getContentResolver(), f.getAbsolutePath(), "bigbang.jpg", null));
                            imageUris.add(uri);
                        }
                        ;
                    }

                    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                    mContext.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 分享到微信
     */
    public void weixinShareZone() {
        IWXAPI api = WXAPIFactory.createWXAPI(mContext, AppConfig.app_id);
        api.registerApp(AppConfig.app_id);
        if (!api.isWXAppInstalled()) {
            Toast.makeText(mContext, "您还未安装微信客户端",
                    Toast.LENGTH_SHORT).show();
                  return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                //这一步一定要clear,不然分享了朋友圈马上分享好友图片就会翻倍
                files.clear();

                try {
                    files.add(saveFile(mBitMap,IMAGE_NAME + i+ ".jpg"));
                    for (int i = 1; i < uris.size(); i++) {
                        File file = saveImageToSdCard(mContext, uris.get(i));
                        files.add(file);
                    }

                    Intent intent = new Intent();
                    ComponentName comp;


                    comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                    intent.putExtra("Kdescription", content);

                    intent.setComponent(comp);
                    intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                    intent.setType("image/*");

                    ArrayList<Uri> imageUris = new ArrayList<Uri>();
                    for (File f : files) {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                            imageUris.add(Uri.fromFile(f));
                        }else {
                            //修复微信在7.0崩溃的问题
                            Uri uri =Uri.parse(android.provider.MediaStore.Images.Media.insertImage(mContext.getContentResolver(), f.getAbsolutePath(), "bigbang.jpg", null));
                            imageUris.add(uri);
                        }
                    }

                    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                    mContext.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 构造一个用于请求的唯一标识
     *
     * @param type 分享的内容类型
     */
    public String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }

    public  void QQZoneShare(String s) {
        if (!isQQClientAvailable(mContext)) {
            Toast.makeText(mContext, "您还未安装微信客户端",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        Tencent mTencent = Tencent.createInstance(APP_Id, mContext);

        final Bundle params = new Bundle();
        ArrayList<String> imageUrls = new ArrayList<String>();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,QQShare.SHARE_TO_QQ_TYPE_IMAGE);

        try {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,  saveFile(mBitMap,IMAGE_NAME + i+ ".jpg").getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }

     //   for (int i=1;i<imageUrls.size();i++){
       //     imageUrls.add(imageUrls.get(i));
       // }

//            files.add(saveFile(mBitMap,IMAGE_NAME + i+ ".jpg"));
//            for (int i = 1; i < uris.size(); i++) {
//                File file = saveImageToSdCard(mContext, uris.get(i));
//                files.add(file);
//            }

       // params.putStringArrayList(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        mTencent.shareToQQ((Activity) mContext, params, listener);
    }

    /**
     * 分享到qq
     */
    public void QQShare() {
        if (!isQQClientAvailable(mContext)) {
            Toast.makeText(mContext, "您还未安装QQ客户端",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                //这一步一定要clear,不然分享了朋友圈马上分享好友图片就会翻倍
                files.clear();

                try {
                    files.add(saveFile(mBitMap,IMAGE_NAME + i+ ".jpg"));
                    for (int i = 1; i < uris.size(); i++) {
                        File file = saveImageToSdCard(mContext, uris.get(i));
                        files.add(file);
                    }

                    Intent intent = new Intent();
                    ComponentName comp;

                    comp = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
                    intent.putExtra("Kdescription", content);

                    intent.setComponent(comp);
                    intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                    intent.setType("image/*");

                    ArrayList<Uri> imageUris = new ArrayList<Uri>();
                    for (File f : files) {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                            imageUris.add(Uri.fromFile(f));
                        }else {
                            //修复微信在7.0崩溃的问题
                            Uri uri =Uri.parse(android.provider.MediaStore.Images.Media.insertImage(mContext.getContentResolver(), f.getAbsolutePath(), "bigbang.jpg", null));
                            imageUris.add(uri);
                        }
                    }

                    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                    mContext.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }    /**
     * 分享到sina微博
     */
    public void shareSina() {

 Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        PackageManager pm = mContext.getPackageManager();
        List<ResolveInfo> matches = pm.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        String packageName = "com.sina.weibo";
        ResolveInfo info = null;
        for (ResolveInfo each : matches) {
            String pkgName = each.activityInfo.applicationInfo.packageName;
            if (packageName.equals(pkgName)) {
                info = each;
                break;
            }
        }
        if (info==null){
            ToastUtils.show(mContext, "您还未安装微博客户端");
            return;
        }
        ResolveInfo finalInfo = info;
        new Thread(new Runnable() {
            @Override
            public void run() {
                //这一步一定要clear,不然分享了朋友圈马上分享好友图片就会翻倍


                files.clear();

                try {
                    files.add(saveFile(mBitMap,IMAGE_NAME + i+ ".jpg"));
                    for (int i = 1; i < uris.size(); i++) {
                        File file = saveImageToSdCard(mContext, uris.get(i));
                        files.add(file);
                    }


                    intent.setClassName(packageName, finalInfo.activityInfo.name);
                    intent.putExtra(Intent.EXTRA_TEXT, content);
                    intent.setAction(Intent.ACTION_SEND_MULTIPLE);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ArrayList<Uri> imageUris = new ArrayList<Uri>();
                    for (File f : files) {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                            imageUris.add(Uri.fromFile(f));
                        }else {
                            //修复微信在7.0崩溃的问题
                            Uri uri =Uri.parse(android.provider.MediaStore.Images.Media.insertImage(mContext.getContentResolver(), f.getAbsolutePath(), "bigbang.jpg", null));
                            imageUris.add(uri);
                        }
                    }

                    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                    mContext.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    IUiListener listener = new IUiListener() {
        @Override
        public void onComplete(Object o) {

        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    };

    public static String IMAGE_NAME = "iv_share_";
    public static int  i =0;

    //根据网络图片url路径保存到本地
    public  final File saveImageToSdCard(Context context, String image) {
        boolean success = false;
        File file = null;
        try {
            file = createStableImageFile(context);

            Bitmap bitmap = null;
            URL url = new URL(image);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
            InputStream is = null;
            is = conn.getInputStream();
            bitmap =  BitmapFactory.decodeStream(is);

            FileOutputStream outStream;

            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (success) {
            return file;
        } else {
            return null;
        }
    }

    /**
     * 将Bitmap转换成文件
     * 保存文件
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public  File saveFile(Bitmap bm, String fileName) throws IOException {
        File myCaptureFile = new File(mContext.getExternalCacheDir() , fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }

    //创建本地保存路径
    public  File createStableImageFile(Context context) throws IOException {
        i++;
        String imageFileName =IMAGE_NAME + i+ ".jpg";
        File storageDir = context.getExternalCacheDir();
        File image = new File(storageDir, imageFileName);
        return image;
    }
    //判断是否安装Qq
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }
}
