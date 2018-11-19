package com.cheapest.lansu.cheapestshopping.view.custom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.ProductEntity;
import com.cheapest.lansu.cheapestshopping.utils.TextUtil;
import com.cheapest.lansu.cheapestshopping.view.activity.BrandSpecialActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.BrowseActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.GoodsDetailActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.ProductSumActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 商店集合的item
 */
public class ShopGridView extends LinearLayout {

	private Context mContext;
	private ShopGridViewAdapter shopAdapter;
	private int id = 0;

	public TextView getTvShopViewTitle() {
		return tvShopViewTitle;
	}

	public void setTvShopViewTitle(TextView tvShopViewTitle) {
		this.tvShopViewTitle = tvShopViewTitle;
	}

	TextView tvShopViewTitle;
	RecyclerView rlShopView;
	private List<ProductEntity.DatasBean> mList = new ArrayList<>();

	public ShopGridView(Context context) {
		super(context);
		mContext = context;
	}

	public ShopGridView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		View view = LayoutInflater.from(context).inflate(R.layout.view_shop_gridview, null, false);
		LayoutParams param = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, 1.0f);
		view.setLayoutParams(param);
		addView(view);
		tvShopViewTitle = view.findViewById(R.id.tv_shop_view_title);
		rlShopView = view.findViewById(R.id.rl_shop_view);
		TextUtil.setTextType(mContext, tvShopViewTitle);
		LinearLayoutManager llm = new LinearLayoutManager(context);
		llm.setOrientation(LinearLayoutManager.HORIZONTAL);
		rlShopView.setLayoutManager(llm);
		shopAdapter = new ShopGridViewAdapter(R.layout.item_shope_grid_view, mList);
		shopAdapter.openLoadAnimation();
		shopAdapter.setUpFetchEnable(false);
		shopAdapter.setEnableLoadMore(false);
		rlShopView.setAdapter(shopAdapter);
		shopAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				Intent intent = new Intent(mContext, GoodsDetailActivity.class);
				intent.putExtra(Constants.INTENT_KEY_ID, mList.get(position).getId());
				intent.putExtra(Constants.INTENT_KEY_URL, mList.get(position).getIconUrl());
				mContext.startActivity(intent);
			}
		});
		tvShopViewTitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, ProductSumActivity.class);
				intent.putExtra(Constants.INTENT_KEY_ID, id + "");
				intent.putExtra(Constants.INTENT_KEY_TITLE, tvShopViewTitle.getText());
				intent.putExtra(Constants.INTENT_KEY_TYPE, 3);

				mContext.startActivity(intent);

			}
		});
	}

	public ShopGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;

	}

	class ShopGridViewAdapter extends BaseQuickAdapter<ProductEntity.DatasBean, BaseViewHolder> {
		public ShopGridViewAdapter(int layoutResId, List data) {
			super(layoutResId, data);
		}

		@Override
		protected void convert(BaseViewHolder helper, ProductEntity.DatasBean item) {
			//     helper.setText(R.id.item_home_pro_quan, item.getTitle());
			//  helper.setImageResource(R.id.icon, item.());
//            // 加载网络图片
//            Glide.with(mContext).load(item.getUserAvatar()).crossFade().into((ImageView) helper.getView(R.id.iv));

			Glide.with(mContext).load(item.getIconUrl()).crossFade().into((ImageView) helper.getView(R.id.item_home_pro_image));
			helper.setText(R.id.item_home_pro_title, item.getName());
			helper.setText(R.id.item_home_pro_quan, "领券减" + (int) item.getCouponAmount() + "元");
			helper.setText(R.id.item_home_pro_quanhoujia, "￥ " + item.getDiscountPrice());
		}
	}

	/**
	 * 设置数据
	 *
	 * @param productEntity 商品列表数据
	 * @param title         标题
	 */
	public void setData(ProductEntity productEntity, String title) {
		mList = productEntity.getDatas();
		shopAdapter.addData(mList);
		tvShopViewTitle.setText(title);

	}

	/**
	 * 设置数据
	 *
	 * @param productEntity 商品列表数据
	 * @param title         标题
	 */
	public void setData(List<ProductEntity.DatasBean> productEntity, String title, int id) {
		this.id = id;
		mList = productEntity;
		shopAdapter.addData(mList);
		tvShopViewTitle.setText(title);

	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}

	public void setTitleClickEnble(boolean enble) {
		tvShopViewTitle.setClickable(enble);
	}
}
