package jie.android.bigscreen;

import jie.android.bigscreen.utils.Utils;
import jie.android.bigscreen.view.BSImageView;
import jie.android.bigscreen.view.PlugLayout;
import jie.android.bigscreen.view.SlotScrollView;
import android.os.Bundle;
import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	
	private SlotScrollView slotScrollView = null;
	private LinearLayout slotLayout = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		slotScrollView = (SlotScrollView) this.findViewById(R.id.slotScrollView1);
		//slotLayout = (LinearLayout) this.findViewById(R.id.slotlayout);
		
		Button btn = (Button) this.findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				onBtnClick();
			}
			
		});
	}

	protected void onBtnClick() {
		AttributeSet attrs = Utils.getAttributeSet(this, PlugLayout.class.getName(), R.layout.view_pluglayout);
		PlugLayout layout = new PlugLayout(this, attrs);

		AttributeSet attrs1 = Utils.getAttributeSet(this, BSImageView.class.getName(), R.layout.view_bsimage);			
		BSImageView iv = new BSImageView(this, attrs1);
		iv.setImageResource(R.drawable.ic_launcher);
		
		layout.addChildView(iv);
		
		//slotLayout.addView(layout);
		slotScrollView.addPlugLayout(layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
