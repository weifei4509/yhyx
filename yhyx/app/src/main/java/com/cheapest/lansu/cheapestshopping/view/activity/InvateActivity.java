package com.cheapest.lansu.cheapestshopping.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.view.custom.CircleImageView;

import butterknife.Bind;

public class InvateActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_scanner)
    ImageView ivScanner;
    @Bind(R.id.iv_icon)
    CircleImageView ivIcon;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_content)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removeToolBar();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Glide.with(this).load(CacheInfo.getUserInfoFromCache(this).getRecommendQcodePathUrl()).asBitmap().into(ivScanner);
        Glide.with(this).load(CacheInfo.getUserInfoFromCache(this).getHeadpicUrl()).asBitmap().into(ivIcon);
        tvName.setText("我是 "+CacheInfo.getUserInfoFromCache(this).getNickname() );
        tvContent.setText("我为 "+CacheInfo.getUserInfoFromCache(this).getNickname() +"代言" );
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_invate;
    }
}
