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
	
	public PlugLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOrientation(LinearLayout.VERTICAL);
	}
	
	public boolean addChildView(View view) {
	
		view.setOnClickListener(onClickListener);
		addView(view, viewCount);
		++ viewCount;
		return true;
	}
	
	public void setOnClickListener(OnClickListener listener) {
		onClickListener = listener;
	}

}
