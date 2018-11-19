package com.cheapest.lansu.cheapestshopping.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.ProductEntity;
import com.cheapest.lansu.cheapestshopping.utils.StringUtils;

import java.util.List;

/**
 * @author 码农哥
 * @date 2018/7/13 0013  0:46
 * @email 441293364@qq.com
 * @TODO <p/>省惠优选排行榜适配器
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
public class PreferenceRankAdapter extends BaseQuickAdapter<ProductEntity.DatasBean, BaseViewHolder> {

	Context mContext;

	public PreferenceRankAdapter(Context mContext, List data) {
		super(R.layout.item_preference_rank_listview, data);
		this.mContext = mContext;
	}

	@Override
	protected void convert(BaseViewHolder helper, ProductEntity.DatasBean item) {
		Glide.with(mContext).load(item.getIconUrl()).crossFade().into((ImageView) helper.getView(R.id.iv_preference_thumb));
		helper.setText(R.id.tv_preference_name, StringUtils.addImageLabel(mContext, item.getType() == 1 ? R.mipmap.icon_taobao : R.mipmap.icon_tianma, item.getName()));
		helper.setText(R.id.item_home_pro_quan, (int) item.getCouponAmount() + "元券");
		helper.setText(R.id.item_home_pro_yuanjia, "￥" + item.getPrice());
		helper.setText(R.id.item_home_pro_quanhoujia, "￥" + item.getDiscountPrice());
		((TextView) helper.getView(R.id.item_home_pro_yuanjia)).getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰

		helper.setText(R.id.item_home_xiaoliang, "月销  " + item.getSellNum());
	}
}
