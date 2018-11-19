package com.cheapest.lansu.cheapestshopping.view.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.OrderEntity;

import java.util.List;

/**
 * @author 码农哥
 * @date 2018/7/20 0020  23:11
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
public class CollageOrderAdapter extends BaseQuickAdapter<OrderEntity.DatasBean, BaseViewHolder> {

	public CollageOrderAdapter(@Nullable List<OrderEntity.DatasBean> data) {
		super(R.layout.item_collage_order, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, OrderEntity.DatasBean item) {
		Glide.with(mContext).load(item.getIconUrl()).crossFade().into((ImageView) helper.getView(R.id.iv_order_thumb));

		helper.setText(R.id.tv_order_name, item.getGroupName());
		helper.setText(R.id.tv_order_price, "拼团价：￥0");

		//0-初始 1-完成 2-失败 3-取消
		if (item.getStatus() == 0) {
			helper.setText(R.id.tv_collage_state, "进行中");
		} else if (item.getStatus() == 1) {
			helper.setText(R.id.tv_collage_state, "拼团完成");
		} else if (item.getStatus() == 2) {
			helper.setText(R.id.tv_collage_state, "拼团失败");
		} else {
			helper.setText(R.id.tv_collage_state, "取消拼团");
		}

	}
}
