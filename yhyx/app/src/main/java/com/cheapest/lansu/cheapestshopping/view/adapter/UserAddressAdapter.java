package com.cheapest.lansu.cheapestshopping.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.AddressModel;
import com.cheapest.lansu.cheapestshopping.view.activity.AddAddressActivity;
import com.cheapest.lansu.cheapestshopping.view.fragment.strategy.StrategyActivity;

import java.util.List;

/**
 * @author 码农哥
 * @date 2018/7/27 0027  0:01
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
public class UserAddressAdapter extends BaseAdapter {

	private Context context;
	private List<AddressModel> addressModelList;
	private int type = 1;

	public UserAddressAdapter(Context context) {
		this.context = context;
		this.type = type;
	}

	public List<AddressModel> getData() {
		return addressModelList;
	}


	public void setData(List<AddressModel> addressModelList) {
		this.addressModelList = addressModelList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return addressModelList == null ? 0 : addressModelList.size();
	}

	@Override
	public AddressModel getItem(int position) {
		return addressModelList == null ? null : addressModelList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_user_address, null);
			mHolder = new ViewHolder(convertView);
		}
		mHolder = (ViewHolder) convertView.getTag();
		final AddressModel item = addressModelList.get(position);
		if (null != item) {
			Log.i("TAG", "address adpater position:" + position + " " + item.getDeliveryName() + " " + item.toString());
			mHolder.tv_user_name.setText(item.getDeliveryName());
			mHolder.tv_user_phone.setText(item.getDeliveryMobile());
			StringBuffer buffer = new StringBuffer();
			buffer.append(item.getProvinceName()).append(item.getCityName())
					.append(item.getAreaName()).append(item.getAddress());
			mHolder.tv_user_address.setText(buffer.toString());
			mHolder.layout_right.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
//					Bundle extras = new Bundle();
//					extras.putString("title", "修改收货地址");
//					extras.putParcelable("addr_item", item);
//					extras.putString("type", CommonMineActivity.mine_user_add_address);
//					CommonMineActivity.goPage(context, extras);

					Intent intent = new Intent(context, AddAddressActivity.class);
					intent.putExtra("addr_item", item);
					context.startActivity(intent);


				}
			});
		}
		return convertView;
	}


	class ViewHolder {
		TextView tv_user_name;
		TextView tv_user_phone;
		TextView tv_user_address;
		RelativeLayout layout_right;

		public ViewHolder(View view) {
			tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
			tv_user_phone = (TextView) view.findViewById(R.id.tv_user_phone);
			tv_user_address = (TextView) view.findViewById(R.id.tv_user_address);
			layout_right = (RelativeLayout) view.findViewById(R.id.layout_right);
			view.setTag(this);
		}
	}

}
