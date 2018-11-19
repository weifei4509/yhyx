package com.cheapest.lansu.cheapestshopping.view.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.CustModel;
import com.cheapest.lansu.cheapestshopping.utils.Util;
import com.cheapest.lansu.cheapestshopping.view.custom.CircleImageView;

import java.util.List;

/**
 * @author 码农哥
 * @date 2018/7/23 0023  22:24
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
public class UserFaceAdapter extends BaseQuickAdapter<CustModel, BaseViewHolder> {

	public UserFaceAdapter(@Nullable List<CustModel> data) {
		super(R.layout.item_user_face, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, CustModel item) {
		if (Util.isEmpty(item.getHeadpicUrl())) {
			((CircleImageView) helper.getView(R.id.iv_user_icon)).setImageResource(R.mipmap.icon_tuxiang);
		} else {
			Glide.with(mContext).load(item.getHeadpicUrl()).crossFade().into((ImageView) helper.getView(R.id.iv_user_icon));
		}
	}
}
