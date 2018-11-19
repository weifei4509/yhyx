package com.cheapest.lansu.cheapestshopping.view.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.NewsEntity;

import java.util.List;

/**
 * @author 码农哥
 * @date 2018/7/21 0021  7:19
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
public class NewsListAdapter extends BaseQuickAdapter<NewsEntity.DatasBean, BaseViewHolder> {

	public NewsListAdapter(@Nullable List<NewsEntity.DatasBean> data) {
		super(R.layout.item_news_listview, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, NewsEntity.DatasBean item) {

		Glide.with(mContext).load(item.getIconUrl()).crossFade().into((ImageView) helper.getView(R.id.iv_news_thumb));

		helper.setText(R.id.tv_news_title, item.getSummary());
		helper.setText(R.id.tv_view_num, item.getViewNum() + "人浏览");
		helper.setText(R.id.tv_author, item.getAuthor());
	}
}
