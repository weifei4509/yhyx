package com.cheapest.lansu.cheapestshopping.model.http;

import com.cheapest.lansu.cheapestshopping.Constant.AppConfig;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.view.App;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*
* 文件名：RetrofitFactory
* 描    述：网络帮助类
* 作    者：lansu
* 时    间：2018/4/26 11:21
* 版    权： lansus 版权所有
*/
public class RetrofitFactory {
    private static RetrofitFactory mRetrofitFactory;
    private static RetrofitService mAPIFunction;
    private RetrofitFactory(){
        OkHttpClient mOkHttpClient=new OkHttpClient.Builder()
                .connectTimeout(AppConfig.HTTP_TIME, TimeUnit.SECONDS)
                .readTimeout(AppConfig.HTTP_TIME, TimeUnit.SECONDS)
                .writeTimeout(AppConfig.HTTP_TIME, TimeUnit.SECONDS)
                .addInterceptor(new LogInterceptor())
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request = original.newBuilder()
                                .header("token", CacheInfo.getUserToken(App.app))
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    } } )
                .build();
        Retrofit mRetrofit=new Retrofit.Builder()
                .baseUrl(AppConfig.DEBUG?AppConfig.DEBUG_URL:AppConfig.Release_URL)
                .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加rxjava转换器
                .client(mOkHttpClient)
                .build();
        mAPIFunction=mRetrofit.create(RetrofitService.class);

    }

    public static RetrofitFactory getInstence(){
        if (mRetrofitFactory==null){
            synchronized (RetrofitFactory.class) {
                if (mRetrofitFactory == null)
                    mRetrofitFactory = new RetrofitFactory();
            }

        }
        return mRetrofitFactory;
    }
    public  RetrofitService API(){
        return mAPIFunction;
    }


}
