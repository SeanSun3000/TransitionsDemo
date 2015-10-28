package com.example.transitiondemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class ZoomActivity extends Activity
{
	private int mAnimationDuration;
	private Animator mCurrentAnimator;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zoom);

		final View thumb1View = findViewById(R.id.thumb_button_1);
		thumb1View.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				zoomImageFromThumb(thumb1View, R.drawable.image1);
			}
		});

		final View thumb2View = findViewById(R.id.thumb_button_2);
		thumb2View.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				zoomImageFromThumb(thumb2View, R.drawable.image2);
			}
		});

		mAnimationDuration = getResources().getInteger(
				android.R.integer.config_longAnimTime);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void zoomImageFromThumb(final View thumbView, int imageResId)
	{
		if (mCurrentAnimator != null) {
			mCurrentAnimator.cancel();
		}

		final ImageView expandedImageView = (ImageView) findViewById(R.id.expanded_image);
		expandedImageView.setImageResource(imageResId);

		final Rect startBound = new Rect();
		Rect finalBound = new Rect();
		Point globalOffset = new Point();
		thumbView.getGlobalVisibleRect(startBound);
		findViewById(R.id.container).getGlobalVisibleRect(finalBound,
				globalOffset);
		startBound.offset(-globalOffset.x, -globalOffset.y);
		finalBound.offset(-globalOffset.x, -globalOffset.y);

		float scale;

		if ((float)finalBound.width() / startBound.width() > finalBound.height()
				/ startBound.height()) {
			scale = (float) startBound.height() / finalBound.height();
			float startWidth = finalBound.width() * scale;
			int deltaWidth = (int) ((startWidth - startBound.width()) / 2);
			startBound.left -= deltaWidth;
			startBound.right += deltaWidth;
		} else {
			scale = (float) startBound.width() / finalBound.width();
			float startHeight = finalBound.height() * scale;
			int deltaHeight = (int) ((startHeight - startBound.height()) / 2);
			startBound.top -= deltaHeight;
			startBound.bottom += deltaHeight;
		}

		expandedImageView.setVisibility(View.VISIBLE);
		thumbView.setAlpha(0);

		expandedImageView.setPivotX(0);
		expandedImageView.setPivotY(0);

		AnimatorSet set = new AnimatorSet();
		set.play(
				ObjectAnimator.ofFloat(expandedImageView, View.X,
						startBound.left, finalBound.left))
				.with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
						startBound.top, finalBound.top))
				.with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
						scale, 1.0f))
				.with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y,
						scale, 1.0f));
		set.setDuration(mAnimationDuration);
		set.setInterpolator(new DecelerateInterpolator());
		set.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation)
			{
				super.onAnimationEnd(animation);
				mCurrentAnimator = null;
			}

			@Override
			public void onAnimationCancel(Animator animation)
			{
				super.onAnimationCancel(animation);
				mCurrentAnimator = null;
			}
		});
		set.start();

		mCurrentAnimator = set;
		final float startScaleFinal = scale;

		expandedImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				if (mCurrentAnimator != null) {
					mCurrentAnimator.cancel();
				}

				AnimatorSet set = new AnimatorSet();
				set.play(
						ObjectAnimator.ofFloat(expandedImageView, View.X,
								startBound.left))
						.with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
								startBound.top))
						.with(ObjectAnimator.ofFloat(expandedImageView,
								View.SCALE_X, startScaleFinal))
						.with(ObjectAnimator.ofFloat(expandedImageView,
								View.SCALE_Y, startScaleFinal));
				set.setDuration(mAnimationDuration);
				set.setInterpolator(new DecelerateInterpolator());
				set.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationCancel(Animator animation)
					{
						super.onAnimationCancel(animation);
						mCurrentAnimator = null;
						thumbView.setAlpha(1);
						expandedImageView.setVisibility(View.GONE);
					}

					@Override
					public void onAnimationEnd(Animator animation)
					{
						super.onAnimationEnd(animation);
						mCurrentAnimator = null;
						thumbView.setAlpha(1);
						expandedImageView.setVisibility(View.GONE);
					}
				});
				set.start();

				mCurrentAnimator = set;
			}
		});

	}
}
