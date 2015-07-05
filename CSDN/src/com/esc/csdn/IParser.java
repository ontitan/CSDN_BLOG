package com.esc.csdn;

import android.content.Context;
import android.view.View;

public interface IParser {

	public void setView(View layout);
	
	public void setContext(Context context);
	
	public void parse();
}
