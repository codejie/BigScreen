package jie.android.bigscreen;

import jie.android.bigscreen.utils.Utils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;

public class BSImageShowActivity extends Activity {

	private AbsoluteLayout layout = null;
	private ImageView iv = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.activity_imageshow);
		layout = (AbsoluteLayout) this.findViewById(R.id.layout);
		
		int resId = -1;
		Intent intent = this.getIntent();
		if (intent != null) {
			resId = intent.getExtras().getInt("resource_id");
		}
		
		iv = new ImageView(this, Utils.getAttributeSet(this, ImageView.class.getSimpleName(), R.layout.show_bsimage));
		iv.setImageResource(resId);
		layout.addView(iv);		
	}
	
}
