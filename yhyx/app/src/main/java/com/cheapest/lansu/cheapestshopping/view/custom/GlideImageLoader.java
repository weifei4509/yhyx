package com.cheapest.lansu.cheapestshopping.view.custom;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cheapest.lansu.cheapestshopping.R;
import com.youth.banner.loader.ImageLoader;

/*
* 文件名：GlideImageLoader
* 描    述：
* 作    者：lansu
* 时    间：2018/4/27 14:08
* 版    权： 云杉智慧新能源技术有限公司版权所有
*/

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
}
