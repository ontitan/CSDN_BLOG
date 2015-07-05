package com.esc.csdn;

import org.netshull.csdn.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class WelcomeJump extends Activity implements Runnable{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_guide);
		new Thread(this).start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(3500);
			startActivity(new Intent(WelcomeJump.this,MainActivity.class));
			this.finish();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
