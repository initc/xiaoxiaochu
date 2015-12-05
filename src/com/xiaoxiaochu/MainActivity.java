package com.xiaoxiaochu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.xiaoxiaochu.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainactivity);
		
	}

	
	
	
}
