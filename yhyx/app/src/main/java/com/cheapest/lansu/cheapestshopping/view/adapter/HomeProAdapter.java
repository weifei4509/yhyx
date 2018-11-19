package com.cheapest.lansu.cheapestshopping.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.CategoriesEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.HomeProEntity;

import java.util.ArrayList;
import java.util.List;

/*
* 文件名：HomeProAdapter
* 描    述：
* 作    者：lansu
* 时    间：2018/4/27 15:08
* 版    权： 云杉智慧新能源技术有限公司版权所有
*/
public class HomeProAdapter extends BaseAdapter {
    int mIndex;
    private List<CategoriesEntity.DatasBean> mList=new ArrayList<>();
    private Context mContext;
    private int mPargerSize=8;

    public HomeProAdapter(List<CategoriesEntity.DatasBean> mList, Context mContext,int i) {
        this.mList = mList;
        this.mContext = mContext;
        mIndex=i;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size() > (mIndex + 1) * mPargerSize ?
                mPargerSize : (mList.size() - mIndex*mPargerSize);
    }

    @Override
    public CategoriesEntity.DatasBean getItem(int arg0) {
        // TODO Auto-generated method stub
        return mList.get(arg0 + mIndex * mPargerSize);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0 + mIndex * mPargerSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate( R.layout.view_gride_home,parent,false);
        }
        final int pos = position + mIndex * 8;//假设mPageSiez
        Glide.with(mContext).load(mList.get(pos).getIconUrl()).asBitmap().into( ((ImageView)convertView.findViewById(R.id.iv_view_gride_home_iv)));
        ((TextView)convertView.findViewById(R.id.tv_view_gride_home_iv)).setText(mList.get(pos).getName());

        return convertView;
    }
}
