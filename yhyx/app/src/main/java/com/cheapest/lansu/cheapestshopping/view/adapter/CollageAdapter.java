package com.cheapest.lansu.cheapestshopping.view.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.CollageEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ExchangeEntity;

import java.util.List;

/**
 * @author 码农哥
 * @date 2018/7/20 0020  22:17
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
public class CollageAdapter extends BaseQuickAdapter<CollageEntity.DatasBean, BaseViewHolder> {


	public CollageAdapter(@Nullable List<CollageEntity.DatasBean> data) {
		super(R.layout.item_listview_collage, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, CollageEntity.DatasBean item) {
		Glide.with(mContext).load(item.getIconUrl()).crossFade().into((ImageView) helper.getView(R.id.iv_collage_thumb));

		helper.setText(R.id.tv_pintuan_name, item.getName());
		helper.setText(R.id.tv_pintuan_jia, "拼团价：￥" + item.getGroupPrice());
		helper.setText(R.id.tv_pintuan_price, "市场价：￥" + item.getPrice());
		helper.setText(R.id.tv_group_num, "已拼：" + item.getGroupNum());
		helper.setText(R.id.tv_stock_num, "剩余：" + item.getStock());
	}
}
