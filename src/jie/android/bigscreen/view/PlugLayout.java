package jie.android.bigscreen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class PlugLayout extends LinearLayout {

	private int viewCount = 0;
	private OnClickListener onClickListener = null;
	private OnLongClickListener onLongClickListener = null;
	
	public PlugLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOrientation(LinearLayout.VERTICAL);
	}
	
	public boolean addChildView(View view, LinearLayout.LayoutParams params) {
	
		view.setOnClickListener(onClickListener);
		view.setOnLongClickListener(onLongClickListener);
		if (params != null) {
			this.addView(view, viewCount, params);
		} else {
			addView(view, viewCount);
		}
		++ viewCount;
		return true;
	}
	
	public void setOnClickListener(OnClickListener listener) {
		onClickListener = listener;
		for (int i = 0; i < this.getChildCount(); ++ i) {
			this.getChildAt(i).setOnClickListener(listener);
		}
	}
	
	public void setOnLongClickListener(OnLongClickListener listener) {
		onLongClickListener = listener;
		for (int i = 0; i < this.getChildCount(); ++ i) {
			this.getChildAt(i).setOnLongClickListener(listener);
		}		
	}

}
