package com.example.transitiondemo;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Sample[] samples = new Sample[] {
				new Sample("crossfade", CrossFadeActivity.class),
				new Sample("screen slide", ScreenSlideActivity.class),
				new Sample(getString(R.string.title_card_flip),
						CardFlipActivity.class),
				new Sample(getString(R.string.title_layout_changes),
						LayoutChangesActivity.class) };

		ArrayAdapter<Sample> adapter = new ArrayAdapter<Sample>(this,
				android.R.layout.simple_list_item_1, samples);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		Sample sample = (Sample) getListAdapter().getItem(position);
		Class<? extends Activity> activityClass = sample.mActivityClass;
		startActivity(new Intent(this, activityClass));
	}

	public class Sample
	{
		private String mTitle;
		private Class<? extends Activity> mActivityClass;

		public Sample(String title, Class<? extends Activity> activityClass)
		{
			mTitle = title;
			mActivityClass = activityClass;
		}

		@Override
		public String toString()
		{
			return mTitle;
		}
	}
}
