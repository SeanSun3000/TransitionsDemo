package com.example.transitiondemo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ScreenSlideActivity extends Activity
{
	private TextView mTv;
	public static final String TAG = "hello";
	private static final int PAGE_NUM = 5;
	private ViewPager mViewPager;

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

		MenuItem next = menu.add(Menu.NONE, R.id.next, Menu.NONE, "next");
		if (mViewPager.getCurrentItem() == PAGE_NUM - 1) {
			next.setTitle("finish");
		}
		next.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
			break;
		case R.id.previous:
			mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
			break;
		case R.id.next:
			mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
			break;
		}
		return super.onOptionsItemSelected(item);
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
