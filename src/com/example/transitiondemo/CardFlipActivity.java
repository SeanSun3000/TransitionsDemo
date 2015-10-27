package com.example.transitiondemo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class CardFlipActivity extends Activity
{
	private boolean mShowingBack = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_flip);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new FrontFragment()).commit();
		} else {
			mShowingBack = getFragmentManager().getBackStackEntryCount() > 0 ? true
					: false;
		}

		getFragmentManager().addOnBackStackChangedListener(
				new FragmentManager.OnBackStackChangedListener() {
					@Override
					public void onBackStackChanged()
					{
						invalidateOptionsMenu();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		mShowingBack = getFragmentManager().getBackStackEntryCount() > 0 ? true
				: false;
		MenuItem item = menu.add(Menu.NONE, R.id.action_flip, Menu.NONE,
				mShowingBack ? R.string.action_phote : R.string.action_info);
		item.setIcon(mShowingBack ? R.drawable.ic_action_photo
				: R.drawable.ic_action_info);
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
			return true;
		case R.id.action_flip:
			clipCard();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void clipCard()
	{
		if (mShowingBack) {
			getFragmentManager().popBackStack();
		} else {
			getFragmentManager()
					.beginTransaction()
					.setCustomAnimations(R.animator.card_flip_right_in,
							R.animator.card_flip_right_out,
							R.animator.card_flip_left_in,
							R.animator.card_slip_left_out)
					.replace(R.id.container, new BackFragment())
					.addToBackStack(null).commit();
		}
	}

	public static class FrontFragment extends Fragment
	{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState)
		{
			View v = inflater.inflate(R.layout.fragment_card_front, container,
					false);
			return v;
		}
	}

	public static class BackFragment extends Fragment
	{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState)
		{
			View v = inflater.inflate(R.layout.fragment_card_back, container,
					false);
			return v;
		}
	}
}
