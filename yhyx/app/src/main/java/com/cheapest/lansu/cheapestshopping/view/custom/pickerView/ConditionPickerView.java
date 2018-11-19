package com.cheapest.lansu.cheapestshopping.view.custom.pickerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.cheapest.lansu.cheapestshopping.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * 条件选择器 Created by Sai on 15/11/22.
 */
public class ConditionPickerView<T> extends BasePickerView implements View.OnClickListener {

	WheelOptions<T> wheelOptions;
	private View btnSubmit, btnCancel;
	private TextView tvTitle;
	private OnOptionsSelectListener optionsSelectListener;
	private static final String TAG_SUBMIT = "submit";
	private static final String TAG_CANCEL = "cancel";

	public ConditionPickerView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.pickerview_city, contentContainer);
		// -----确定和取消按钮
		btnSubmit = findViewById(R.id.btn_submit);
		btnSubmit.setTag(TAG_SUBMIT);
		btnCancel = findViewById(R.id.btn_cancel);
		btnCancel.setTag(TAG_CANCEL);
		btnSubmit.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		// 顶部标题
		tvTitle = (TextView) findViewById(R.id.tv_title);
		// ----转轮
		final View optionspicker = findViewById(R.id.ll_options_picker);
		wheelOptions = new WheelOptions(optionspicker);
	}

	public void setPicker(ArrayList<T> optionsItems) {
		wheelOptions.setPicker(optionsItems, null, false);
	}

	public void setPicker(ArrayList<T> optionsItems, Map<Integer, ArrayList<T>> options2Items) {
		wheelOptions.setPicker(optionsItems, options2Items, null, false);
	}


	public void setPicker(ArrayList<T> options1Items, Map<Integer, ArrayList<T>> options2Items,
						  ArrayList<Map<Integer, ArrayList<T>>> options3Items, boolean linkage) {
		wheelOptions.setPicker(options1Items, options2Items, options3Items, linkage);
	}

	/**
	 * 设置选中的item位置
	 *
	 * @param option1 位置
	 */
	public void setSelectOptions(int option1) {
		wheelOptions.setCurrentItems(option1, 0, 0);
	}

	/**
	 * 设置选中的item位置
	 *
	 * @param option1 位置
	 * @param option2 位置
	 */
	public void setSelectOptions(int option1, int option2, int option3) {
		wheelOptions.setCurrentItems(option1, option2, option3);
	}


	/**
	 * 设置选项的单位
	 *
	 * @param label1 单位
	 */
	public void setLabels(String label1) {
		wheelOptions.setLabels(label1, null, null);
	}

	/**
	 * 设置选项的单位
	 *
	 * @param label1 单位
	 * @param label2 单位
	 */
	public void setLabels(String label1, String label2, String label3) {
		wheelOptions.setLabels(label1, label2, label3);
	}

	/**
	 * 设置是否循环滚动
	 *
	 * @param cyclic 是否循环
	 */
	public void setCyclic(boolean cyclic) {
		wheelOptions.setCyclic(cyclic);
	}

	public void setCyclic(boolean cyclic01, boolean cyclic02, boolean cyclic03) {
		wheelOptions.setCyclic(cyclic01, cyclic02, cyclic03);
	}

	@Override
	public void onClick(View v) {
		String tag = (String) v.getTag();
		if (tag.equals(TAG_CANCEL)) {
			dismiss();
			return;
		} else {
			if (optionsSelectListener != null) {
				int[] optionsCurrentItems = wheelOptions.getCurrentItems();
				optionsSelectListener.onOptionsSelect(optionsCurrentItems[0], optionsCurrentItems[1], optionsCurrentItems[2]);
			}
			dismiss();
			return;
		}
	}

	public interface OnOptionsSelectListener {
		void onOptionsSelect(int options1, int option2, int option3);
	}

//	public interface OnValueChangeListener{
//		void onValueChange(int index);
//	}

	public Map<Integer, ArrayList<T>> getmOptions2Items() {
		return wheelOptions.getmOptions2Items();
	}

	public ArrayList<Map<Integer, ArrayList<T>>> getmOptions3Items() {
		return wheelOptions.getmOptions3Items();
	}

	public void setOnoptionsSelectListener(OnOptionsSelectListener optionsSelectListener) {
		this.optionsSelectListener = optionsSelectListener;
	}

	public void setTitle(String title) {
		tvTitle.setText(title);
	}
}
