package com.example.transitiondemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LayoutChangesActivity extends Activity
{
	private ViewGroup mContainerView;
	private static final String[] CONTRIES = { "Ukaine", "USA", "China",
			"France", "Russian" };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_changes);

		mContainerView = (ViewGroup) findViewById(R.id.container);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_layout_changeds, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));;
			return true;
		case R.id.action_add_item:
			findViewById(android.R.id.empty).setVisibility(View.INVISIBLE);
			addItem();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void addItem()
	{
		final View newView = LayoutInflater.from(this).inflate(
				R.layout.list_item_example, mContainerView, false);
		((TextView) newView.findViewById(android.R.id.text1))
				.setText(CONTRIES[(int) (Math.random() * CONTRIES.length)]);

		newView.findViewById(R.id.remove_item).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v)
					{
						mContainerView.removeView(newView);
						if (mContainerView.getChildCount() == 0) {
							findViewById(android.R.id.empty).setVisibility(
									View.VISIBLE);
						}
					}
				});

		mContainerView.addView(newView, 0);
	}
}
