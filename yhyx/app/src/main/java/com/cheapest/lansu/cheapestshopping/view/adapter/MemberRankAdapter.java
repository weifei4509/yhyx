package com.cheapest.lansu.cheapestshopping.view.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.MemberEntity;
import com.cheapest.lansu.cheapestshopping.utils.Util;

/**
 * @author 码农哥
 * @date 2018/7/7 0007  0:21
 * @email 441293364@qq.com
 * @TODO <p/>会员排行榜
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
public class MemberRankAdapter extends BaseQuickAdapter<MemberEntity, BaseViewHolder> {

	public MemberRankAdapter() {
		super(R.layout.item_member_rank_listview, null);
	}

	@Override
	protected void convert(BaseViewHolder helper, MemberEntity item) {
		helper.setText(R.id.tv_rank_num, "No." + (4 + mData.indexOf(item)));
		ImageView face = (ImageView) helper.getView(R.id.iv_user_icon);
		if (Util.isNotEmpty(item.getHeadpicUrl()) && item.getHeadpicUrl().startsWith("http")) {
			Glide.with(mContext).load(item.getHeadpicUrl()).asBitmap().error(R.mipmap.icon_tuxiang_2).placeholder(R.mipmap.icon_tuxiang_2).into(face);
		} else {
			Glide.with(mContext).load(R.mipmap.icon_tuxiang_2).asBitmap().into(face);
		}
//		Glide.with(mContext).load(item.getHeadpicUrl()).crossFade().into((ImageView) helper.getView(R.id.iv_user_icon));
		helper.setText(R.id.tv_user_name, item.getNickname());
		helper.setText(R.id.tv_jiangli_num, item.getMoney());
	}
}
