package com.cheapest.lansu.cheapestshopping.view.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.ExchangeEntity;

import java.util.List;

/**
 * @author 码农哥
 * @date 2018/6/29 0029  2:01
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
public class FindExchangeAdapter extends BaseQuickAdapter<ExchangeEntity.DatasBean, BaseViewHolder> {

	public FindExchangeAdapter(List<ExchangeEntity.DatasBean> mList) {
		super(R.layout.item_find_exchange_layout, mList);
	}

	@Override
	protected void convert(BaseViewHolder helper, ExchangeEntity.DatasBean item) {
		Glide.with(mContext).load(item.getIconUrl()).crossFade().into((ImageView) helper.getView(R.id.iv_album_exchange));
		helper.setText(R.id.tv_exchange_name, item.getName());
		helper.setText(R.id.tv_price_num, item.getPrice() + "");
		helper.setText(R.id.tv_surplus_num, item.getStock() + "");
		helper.setText(R.id.tv_exchange_num, item.getExchangeNum() + "");
		helper.setText(R.id.tv_exchange_price, item.getScore() + "金豆兑换");
	}

}
