package jie.android.bigscreen.view;

import java.io.File;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class BSImageView extends ImageView implements ViewInformation {
	
	private int resId = -1;
	
	public BSImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public ViewType getType() {
		return ViewType.IMAGE;
	}

	public void loadContent(int resId) {
		this.resId = resId;
		this.setImageResource(resId);		
	}
	
	public void loadContent(String src) {
		Uri uri = Uri.fromFile(new File(src));
		this.setImageURI(uri);
	}
	
	public int getContent() {
		return resId;
	}
}
