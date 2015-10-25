package com.example.transitiondemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;

public class CrossFadeActivity extends Activity
{
	private ScrollView mContent;
	private ProgressBar mIndicator;
	private int mDuration;
	private boolean mContentLoaded;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crossfade);

		mContent = (ScrollView) findViewById(R.id.content);
		mIndicator = (ProgressBar) findViewById(R.id.loading_spinner);
		mDuration = getResources().getInteger(
				android.R.integer.config_shortAnimTime);
		mContent.setVisibility(View.GONE);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuItem item = menu.add(0, 1, 0, "toggle");
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == 1) {
			mContentLoaded = !mContentLoaded;
			showContentOrLoadingIndicator();
		} else if (item.getItemId() == android.R.id.home) {
			// NavUtils.navigateUpFromSameTask(this);
			NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
		}
		return super.onOptionsItemSelected(item);
	}

	private void showContentOrLoadingIndicator()
	{
		final View showView = mContentLoaded ? mContent : mIndicator;
		final View hideView = mContentLoaded ? mIndicator : mContent;

		showView.setVisibility(View.VISIBLE);
		showView.animate().alpha(1f).setDuration(mDuration).setListener(null);
		hideView.animate().alpha(0f).setDuration(mDuration)
				.setListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation)
					{
						hideView.setVisibility(View.GONE);
					}
				});
	}
}
