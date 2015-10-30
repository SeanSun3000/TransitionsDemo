package com.example.transitiondemo;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ScreenSlidePageFragment extends Fragment
{
	public static final String ARG_PAGE = "page";
	private int mPageNumber = 0;

	public static ScreenSlidePageFragment newInstance(int pageNum)
	{
		ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(ARG_PAGE, pageNum);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			mPageNumber = (Integer) getArguments().get(ARG_PAGE);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.except_content, container, false);
		View root = v.findViewById(R.id.content);
		((TextView) v.findViewById(R.id.txt)).setText(getString(
				R.string.tile_template_step, mPageNumber + 1));
		
		int backgoungColor = Color.BLACK;

		switch (mPageNumber) {
		case 0:
			backgoungColor = Color.RED;
			break;
		case 1:
			backgoungColor = Color.BLUE;
			break;
		case 2:
			backgoungColor = Color.GREEN;
			break;
		case 3:
			backgoungColor = Color.CYAN;
			break;
		case 4:
			backgoungColor = Color.MAGENTA;
			break;
		}
		root.setBackgroundColor(backgoungColor);

		return v;
	}
}
