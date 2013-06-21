package jie.android.bigscreen;

import java.io.IOException;

import jie.android.bigscreen.utils.Utils;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class BSImageShowActivity extends Activity {

	private static final String Tag = BSImageShowActivity.class.getSimpleName();
	
	private RelativeLayout layout = null;
	private ImageView iv = null;

    Drawable enterShape = null;;
    Drawable normalShape = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.activity_imageshow);		
		layout = (RelativeLayout) this.findViewById(R.id.layout);
		
		int location = -1;
		String content = null;
		Intent intent = this.getIntent();
		if (intent != null) {
			content = intent.getExtras().getString("content");
		}
		
		iv = new ImageView(this, Utils.getAttributeSet(this, ImageView.class.getSimpleName(), R.layout.show_bsimage));
		try {
			iv.setImageDrawable(Drawable.createFromStream(this.getAssets().open(content), content));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onImageClick(v);
			}
			
		});		

		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv.getLayoutParams();
		if (params == null) {
			params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,	LayoutParams.MATCH_PARENT);
		}
		params.setMargins(0, 0, 0, 0);
		
		layout.addView(iv, params);
		
	}
	protected void onImageClick(View v) {
		this.finish();
	}
	
}
