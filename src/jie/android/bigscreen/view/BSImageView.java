package jie.android.bigscreen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class BSImageView extends ImageView implements ViewInformation {
	
	public BSImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public ViewType getType() {
		return ViewType.IMAGE;
	}

}
