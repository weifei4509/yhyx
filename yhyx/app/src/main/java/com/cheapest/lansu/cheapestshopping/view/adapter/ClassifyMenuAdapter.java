package com.cheapest.lansu.cheapestshopping.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.ClassifyEntity;

import java.util.List;

/**
 * @author 码农哥
 * @date 2018/7/23 0023  1:04
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
public class ClassifyMenuAdapter extends BaseAdapter {

	private Context context;
	private int selectItem = 0;
	private List<ClassifyEntity> list;

	public ClassifyMenuAdapter(Context context, List<ClassifyEntity> list) {
		this.list = list;
		this.context = context;
	}

	public int getSelectItem() {
		return selectItem;
	}

	public void setSelectItem(int selectItem) {
		this.selectItem = selectItem;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
		if (convertView == null|| convertView.getTag(R.mipmap.ic_launcher + arg0) == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_classify_menu, null);
			holder.tv_name = (TextView) convertView.findViewById(R.id.item_name);
			convertView.setTag(R.mipmap.ic_launcher + arg0);
		} else {
			holder = (ViewHolder) convertView.getTag(R.mipmap.ic_launcher + arg0);
		}

		if (arg0 == selectItem) {
			holder.tv_name.setBackgroundColor(Color.WHITE);
			holder.tv_name.setTextColor(context.getResources().getColor(R.color.white));
			holder.tv_name.setBackgroundResource(R.drawable.bg_corner_get_sms);
		} else {
			holder.tv_name.setTextColor(context.getResources().getColor(R.color.black));
		}
		holder.tv_name.setText(list.get(arg0).getName());
		return convertView;
	}

	static class ViewHolder {
		private TextView tv_name;
	}

}