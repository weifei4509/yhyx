package com.cheapest.lansu.cheapestshopping.utils.ImageShow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.utils.BitMaker;
import com.cheapest.lansu.cheapestshopping.utils.BitmapMaker;
import com.cheapest.lansu.cheapestshopping.utils.BitmapUtil;
import com.cheapest.lansu.cheapestshopping.utils.NormalUtil;
import com.cheapest.lansu.cheapestshopping.utils.StringUtil;
import com.cheapest.lansu.cheapestshopping.view.activity.ShareComissionActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.ShowPictureActivity;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


/**
 * Created by fhw on 2017/3/11 0011.
 * email 11044818@qq.com
 */
public class ImageAdapter extends ArrayAdapter<String> {
	 	private boolean scrolling = false;
	 	private Context mContext;
	 	private List<String> imgs;
	 	private Bitmap mBitmap;
	 	private int itemSize;

	 	public boolean isScrolling() {
	 		return scrolling;
	 	}

	 	public void setScrolling(boolean scrolling) {
	 		this.scrolling = scrolling;
	 	}
       
        public ImageAdapter(Context context, List<String> imgs, int itemSize) {
            super(context, 0, imgs);
            this.mContext = context;
            this.imgs = imgs;
            this.itemSize= itemSize;

        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            Activity activity = (Activity) getContext();

            ViewHolder viewHold;
            if (convertView == null) {
            	viewHold = new ViewHolder();
                LayoutInflater inflater = activity.getLayoutInflater();
                convertView = inflater.inflate(R.layout.imageview, null);
                viewHold.imageView = (ImageView) convertView.findViewById(R.id.image);
                convertView.setTag(viewHold);
            } else {
                viewHold = (ViewHolder) convertView.getTag();
            }

			String imageUrl = getItem(position);
			final int width;
			final int hight;

			if (getCount() == 1) {
				Point sizeData = StringUtil.getThumbSize(imageUrl);
				width = sizeData.x;
				hight = sizeData.y;
				viewHold.imageView.setScaleType(ImageView.ScaleType.FIT_START);
			}else {
				width = itemSize;
				hight = itemSize;
				viewHold.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			}
            viewHold.imageView.getLayoutParams().width = width;
            viewHold.imageView.getLayoutParams().height = hight;
            imageUrl = StringUtil.getThumb(imageUrl, hight, width);
            if (getCount()==1){
				new BitMaker(mContext, imgs.get(0), new BitmapMaker.loadImage() {
                    @Override
                    public void loadFinish(Bitmap bitmap) {
                       mBitmap=bitmap;
                        viewHold.imageView.setImageBitmap(bitmap);


                    }
                });

			}else if(!NormalUtil.isNull(imageUrl)  && (!scrolling || BitmapUtil.checkImageExist(imageUrl))) {
           	 	ImageLoader.getInstance().displayImage( imageUrl, viewHold.imageView);
	   		}else {
	   			viewHold.imageView.setImageResource(R.color.bg_gray);
	   		}

            convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(mContext,ShowPictureActivity.class);
					Bundle bundle = new Bundle();
					int[] location = new int[2];
				    v.getLocationInWindow(location);
				    int x = location[0];
				    int y = location[1];
				    bundle.putInt("x", x);
					bundle.putInt("y", y);
					
					bundle.putInt("width", width);
					bundle.putInt("hight", hight);
					Gson gson = new Gson();
					bundle.putString("imgdatas", gson.toJson(imgs));
					bundle.putInt("position", position);
					bundle.putInt("column_num", 3);
					bundle.putInt("horizontal_space",  NormalUtil.dip2px(mContext, 3));
					bundle.putInt("vertical_space", NormalUtil.dip2px(mContext, 3));

					intent.putExtras(bundle);
					mContext.startActivity(intent);
				}
			});
            return convertView;
        }
        
        public class ViewHolder{
        	ImageView imageView;
        }

}