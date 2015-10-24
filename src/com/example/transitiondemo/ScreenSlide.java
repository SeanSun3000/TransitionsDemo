package com.example.transitiondemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ScreenSlide extends Activity
{
	private TextView mTv;
	public static final String TAG = "hello";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screen_slide);
	}
}
