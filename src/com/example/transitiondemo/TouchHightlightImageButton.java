package com.example.transitiondemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class TouchHightlightImageButton extends ImageButton
{
	private Drawable mForegroundDrawable;
	private Rect mCacheBounds = new Rect();

	public TouchHightlightImageButton(Context context, AttributeSet attrs,
			int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init();
	}

	public TouchHightlightImageButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public TouchHightlightImageButton(Context context)
	{
		super(context);
		init();
	}

	private void init()
	{
		setBackgroundColor(Color.CYAN);
		setPadding(0, 0, 0, 0);

		TypedArray a = getContext().obtainStyledAttributes(
				new int[] { android.R.attr.selectableItemBackground });
		mForegroundDrawable = a.getDrawable(0);
		mForegroundDrawable.setCallback(this);
		a.recycle();
	}

	@Override
	protected void drawableStateChanged()
	{
		super.drawableStateChanged();
		
		if (mForegroundDrawable.isStateful()) {
			mForegroundDrawable.setState(getDrawableState());
		}
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		mForegroundDrawable.setBounds(mCacheBounds);
		mForegroundDrawable.draw(canvas);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		mCacheBounds.set(0, 0, w, h);
	}
}
