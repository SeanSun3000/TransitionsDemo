package com.example.transitiondemo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ScreenSlideActivity extends Activity
{
	private TextView mTv;
	public static final String TAG = "hello";
	private static final int PAGE_NUM = 5;
	private ViewPager mViewPager;
	private int mAnimatonModeMenuItemId = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		ScreenSlidePagerAdapter adapter = new ScreenSlidePagerAdapter(
				getFragmentManager());
		mViewPager.setAdapter(adapter);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position)
					{
						invalidateOptionsMenu();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_screen_slide, menu);

		menu.findItem(R.id.previous).setEnabled(
				mViewPager.getCurrentItem() > 0 ? true : false);

		MenuItem next = menu
				.add(R.id.action_flip, R.id.next, Menu.NONE, "next");
		if (mViewPager.getCurrentItem() == PAGE_NUM - 1) {
			next.setTitle("finish");
		}
		next.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		if (mAnimatonModeMenuItemId != 0) {
			menu.findItem(mAnimatonModeMenuItemId).setChecked(true);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
			return true;
		case R.id.previous:
			mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
			return true;
		case R.id.next:
			mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
			return true;
		case R.id.action_zoom_out_page_transformer:
			mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
			setCheckStatus(item);
			return true;
		case R.id.action_depth_page_transformer:
			mViewPager.setPageTransformer(true, new DepthPageTransformer());
			setCheckStatus(item);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void setCheckStatus(MenuItem item)
	{
		mAnimatonModeMenuItemId = item.getItemId();
		item.setChecked(true);
	}

	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter
	{
		public ScreenSlidePagerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0)
		{
			return ScreenSlidePageFragment.newInstance(arg0);
		}

		@Override
		public int getCount()
		{
			return PAGE_NUM;
		}

	}
}

class ZoomOutPageTransformer implements ViewPager.PageTransformer
{
	private static final float MIN_SCALE = 0.85f;
	private static final float MIN_ALPHA = 0.5f;

	public void transformPage(View view, float position)
	{
		int pageWidth = view.getWidth();
		int pageHeight = view.getHeight();

		if (position < -1) { // [-Infinity,-1)
			// This page is way off-screen to the left.
			view.setAlpha(0);

		} else if (position <= 1) { // [-1,1]
			// Modify the default slide transition to shrink the page as well
			float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
			float vertMargin = pageHeight * (1 - scaleFactor) / 2;
			float horzMargin = pageWidth * (1 - scaleFactor) / 2;
			final String TAG = "hello";
			if (position < 0) {
				view.setTranslationX(horzMargin - vertMargin / 2);
				Log.d(TAG, "1: " + (horzMargin - vertMargin / 2));
			} else {
				view.setTranslationX(-horzMargin + vertMargin / 2);
				Log.d(TAG, "2: " + (-horzMargin + vertMargin / 2));
			}

			// Scale the page down (between MIN_SCALE and 1)
			view.setScaleX(scaleFactor);
			view.setScaleY(scaleFactor);

			// Fade the page relative to its size.
			view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
					/ (1 - MIN_SCALE) * (1 - MIN_ALPHA));

		} else { // (1,+Infinity]
			// This page is way off-screen to the right.
			view.setAlpha(0);
		}
	}
}

class DepthPageTransformer implements ViewPager.PageTransformer
{
	private static final float MIN_SCALE = 0.75f;

	public void transformPage(View view, float position)
	{
		int pageWidth = view.getWidth();

		if (position < -1) { // [-Infinity,-1)
			// This page is way off-screen to the left.
			view.setAlpha(0);

		} else if (position <= 0) { // [-1,0]
			// Use the default slide transition when moving to the left page
			view.setAlpha(1);
			view.setTranslationX(0);
			view.setScaleX(1);
			view.setScaleY(1);

		} else if (position <= 1) { // (0,1]
			// Fade the page out.
			view.setAlpha(1 - position);

			// Counteract the default slide transition
			view.setTranslationX(pageWidth * -position);

			// Scale the page down (between MIN_SCALE and 1)
			float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)
					* (1 - Math.abs(position));
			view.setScaleX(scaleFactor);
			view.setScaleY(scaleFactor);

		} else { // (1,+Infinity]
			// This page is way off-screen to the right.
			view.setAlpha(0);
		}
	}
}
