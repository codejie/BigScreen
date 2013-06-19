package jie.android.bigscreen;

import jie.android.bigscreen.utils.Utils;
import jie.android.bigscreen.view.BSImageView;
import jie.android.bigscreen.view.PlugLayout;
import jie.android.bigscreen.view.SlotScrollView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	
	private SlotScrollView slotScrollView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		slotScrollView = (SlotScrollView) this.findViewById(R.id.slotScrollView1);
		
		Button btn = (Button) this.findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				onBtnClick();
			}
			
		});
	}

	protected void onBtnClick() {
		addPlugLayout();
	}

	private void addPlugLayout() {
		AttributeSet attrs = Utils.getAttributeSet(this, PlugLayout.class.getName(), R.layout.view_pluglayout);
		PlugLayout layout = new PlugLayout(this, attrs);

		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onViewClick(v);
			}
			
		});	
		
		AttributeSet attrs1 = Utils.getAttributeSet(this, BSImageView.class.getName(), R.layout.view_bsimage);			
		BSImageView iv = new BSImageView(this, attrs1);
		iv.loadContent(R.drawable.ic_launcher);
						
		layout.addChildView(iv);
		
//		iv = new BSImageView(this, attrs1);
//		iv.loadContent(R.drawable.ic_launcher);
//		layout.addChildView(iv);
		
		slotScrollView.addPlugLayout(layout);
	}

	protected void onViewClick(View view) {
		
		Intent intent = new Intent(this, BSImageShowActivity.class);
		
		if (view instanceof BSImageView) {			
			intent.putExtra("resource_id", ((BSImageView)view).getContent());			
		} else {
			return;
		}
		
		this.startActivity(intent);
		this.overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
