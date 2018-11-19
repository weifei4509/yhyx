package com.cheapest.lansu.cheapestshopping.view.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 添加了一个监听的自定义RecycleView，用来对上拉下拉的位置变化进行同步处理
 */
public class GradientRecycleView extends RecyclerView {

	private ScrollViewListener mScrollViewListener;


	public GradientRecycleView(Context context) {
		super(context);
	}

	public GradientRecycleView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public GradientRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setmScrollViewListener(ScrollViewListener mScrollViewListener) {
		this.mScrollViewListener = mScrollViewListener;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);

		if (mScrollViewListener != null) {
			if (oldt < t) {         //屏幕向下滑动
				mScrollViewListener.onScroll(oldt, t, true);
			} else {                //屏幕向下滑动
				mScrollViewListener.onScroll(oldt, t, false);
			}
		}
	}
}
