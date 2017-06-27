/*
 * Copyright (C) 2011 The Android Open Source Project
 * Copyright (C) 2011 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.plan.my.mytoolslibrary.view.indicator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.plan.my.mytoolslibrary.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


/**
 * This widget implements the dynamic action bar tab behavior that can change
 * across different configurations or circumstances.
 */
public class TabPageIndicator extends HorizontalScrollView implements
		PageIndicator {
	/** Title text used when no title is provided by the adapter. */
	private static final CharSequence EMPTY_TITLE = "";

	/**
	 * Interface for a callback when the selected tab has been reselected.
	 */
	public interface OnTabReselectedListener {
		/**
		 * Callback when the selected tab has been reselected.
		 * 
		 * @param position
		 *            Position of the current center item.
		 */
		void onTabReselected(int position);
	}

	private Runnable mTabSelector;

	@SuppressLint("ResourceAsColor")
	private final OnClickListener mTabClickListener = new OnClickListener() {
		public void onClick(View view) {
			TabView tabView = (TabView) view;
			final int oldSelected = mViewPager.getCurrentItem();
			final int newSelected = tabView.getIndex();
			mViewPager.setCurrentItem(newSelected);
			if (oldSelected == newSelected && mTabReselectedListener != null) {
				mTabReselectedListener.onTabReselected(newSelected);
			}
			changeView(newSelected);
		}
	};

	private final IcsLinearLayout mTabLayout;

	private ViewPager mViewPager;
	private ViewPager.OnPageChangeListener mListener;

	private int mMaxTabWidth;
	private int mSelectedTabIndex;

	private OnTabReselectedListener mTabReselectedListener;

	public TabPageIndicator(Context context) {
		this(context, null);
	}

	public TabPageIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		setHorizontalScrollBarEnabled(false);

		mTabLayout = new IcsLinearLayout(context,
				R.attr.vpiTabPageIndicatorStyle);
		addView(mTabLayout, new ViewGroup.LayoutParams(WRAP_CONTENT,
				MATCH_PARENT));
	}

	public void setOnTabReselectedListener(OnTabReselectedListener listener) {
		mTabReselectedListener = listener;
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
		setFillViewport(lockedExpanded);

		final int childCount = mTabLayout.getChildCount();
		if (childCount > 1
				&& (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST)) {
			if (childCount > 2) {
				mMaxTabWidth = (int) (MeasureSpec.getSize(widthMeasureSpec) * 0.4f);
			} else {
				mMaxTabWidth = MeasureSpec.getSize(widthMeasureSpec) / 2;
			}
		} else {
			mMaxTabWidth = -1;
		}

		final int oldWidth = getMeasuredWidth();
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int newWidth = getMeasuredWidth();

		if (lockedExpanded && oldWidth != newWidth) {
			// Recenter the tab display if we're at a new (scrollable) size.
			setCurrentItem(mSelectedTabIndex);
		}
	}

	private void animateToTab(final int position) {
		final View tabView = mTabLayout.getChildAt(position);
		if (mTabSelector != null) {
			removeCallbacks(mTabSelector);
		}
		mTabSelector = new Runnable() {
			public void run() {
				final int scrollPos = tabView.getLeft()
						- (getWidth() - tabView.getWidth()) / 2;
				smoothScrollTo(scrollPos, 0);
				mTabSelector = null;
			}
		};
		post(mTabSelector);
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (mTabSelector != null) {
			// Re-post the selector we saved
			post(mTabSelector);
		}
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mTabSelector != null) {
			removeCallbacks(mTabSelector);
		}
	}
  List<TabView> tabViewlist = new ArrayList<TabView>();
  @SuppressLint("ResourceAsColor")
private void changeView(int index){
	  /*
		switch (index) {
		case 0:
			for (TabView ew : tabViewlist) {
				if(ew.getIndex() == 0){
					ew.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_contact_list_normal, 0, 0);
					ew.setTextColor(R.color.color_black);
				}else if(ew.getIndex() == 1){
					ew.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_contact_calllog_undown, 0, 0);
					ew.setTextColor(R.color.txt_black);
				}else if(ew.getIndex() == 2){
					ew.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_contact_call_undown, 0, 0);
					ew.setTextColor(R.color.txt_black);
				}else if(ew.getIndex() == 3){
					ew.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_contact_find_undown, 0, 0);
					ew.setTextColor(R.color.txt_black);
				}
			}
			break;
		case 1:
			for (TabView ew : tabViewlist) {
				if(ew.getIndex() == 0){
					ew.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_contact_list_undown, 0, 0);
					ew.setTextColor(R.color.txt_black);
				}else if(ew.getIndex() == 1){
					ew.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_contact_calllog_normal, 0, 0);
					ew.setTextColor(R.color.color_black);
				}else if(ew.getIndex() == 2){
					ew.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_contact_call_undown, 0, 0);
					ew.setTextColor(R.color.txt_black);
				}else if(ew.getIndex() == 3){
					ew.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_contact_find_undown, 0, 0);
					ew.setTextColor(R.color.txt_black);
				}
			}
			break;
		case 2:
			
			for (TabView ew : tabViewlist) {
				if(ew.getIndex() == 0){
					ew.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_contact_list_undown, 0, 0);
					ew.setTextColor(R.color.txt_black);
				}else if(ew.getIndex() == 1){
					ew.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_contact_calllog_undown, 0, 0);
					ew.setTextColor(R.color.txt_black);
				}else if(ew.getIndex() == 2){
					ew.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_contact_call_normal, 0, 0);
					ew.setTextColor(R.color.color_black);
				}else if(ew.getIndex() == 3){
					ew.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_contact_find_undown, 0, 0);
					ew.setTextColor(R.color.txt_black);
				}
			}
			break;
		case 3:
			
			for (TabView ew : tabViewlist) {
				if(ew.getIndex() == 0){
					ew.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_contact_list_undown, 0, 0);
					ew.setTextColor(R.color.txt_black);
				}else if(ew.getIndex() == 1){
					ew.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_contact_calllog_undown, 0, 0);
					ew.setTextColor(R.color.txt_black);
				}else if(ew.getIndex() == 2){
					ew.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_contact_call_undown, 0, 0);
					ew.setTextColor(R.color.txt_black);
				}else if(ew.getIndex() == 3){
					ew.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_contact_find_normal, 0, 0);
					ew.setTextColor(R.color.color_black);
				}
			}
			break;
		default:
			break;
	}
	*/
  }
	@SuppressLint({ "NewApi", "ResourceAsColor" })
	private void addTab(int index, CharSequence text, int iconResId) {
		TabView tabView = new TabView(getContext());//
		tabView.mIndex = index;
		tabView.setFocusable(true);
		tabView.setOnClickListener(mTabClickListener);
		tabView.setText(text);
		tabView.setTextSize((float) 12.00);
//		tabView.setMinHeight(100);
//		tabView.setBottom(6);
//		tabView.setTop(6);
		tabView.setTextColor(R.color.txt_black_app);
		tabView.setCompoundDrawablePadding(4);
		tabView.setGravity(Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL);
//		tabView.setBackground(getResources().getDrawable(R.drawable.white_dark_seletor));
		if (iconResId != 0) {
			tabView.setCompoundDrawablesWithIntrinsicBounds(0, iconResId, 0, 0);
		}
		tabViewlist.add(tabView);
		TextView view = new TextView(getContext());
		view.setBackgroundColor(R.color.txt_dark_app);
		view.setBackground(getResources().getDrawable(R.drawable.black_bord_rect_bg));
		mTabLayout.addView(tabView, new LinearLayout.LayoutParams(0,
				WRAP_CONTENT, 1));//MATCH_PARENT

		mTabLayout.setGravity(Gravity.CENTER);
		mTabLayout.setBackground(getResources().getDrawable(R.drawable.black_bord_rect_bg));
		
		mTabLayout.addView(view, new LinearLayout.LayoutParams(2,
				MATCH_PARENT));//MATCH_PARENT
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		if (mListener != null) {
			mListener.onPageScrollStateChanged(arg0);
		}
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		if (mListener != null) {
			mListener.onPageScrolled(arg0, arg1, arg2);
		}
	}

	@Override
	public void onPageSelected(int arg0) {
		setCurrentItem(arg0);
		if (mListener != null) {
			mListener.onPageSelected(arg0);
			changeView(arg0);
		}
	}

	@Override
	public void setViewPager(ViewPager view) {
		if (mViewPager == view) {
			return;
		}
		if (mViewPager != null) {
			mViewPager.setOnPageChangeListener(null);
		}
		final PagerAdapter adapter = view.getAdapter();
		if (adapter == null) {
			throw new IllegalStateException(
					"ViewPager does not have adapter instance.");
		}
		mViewPager = view;
		view.setOnPageChangeListener(this);
		notifyDataSetChanged();
	}

	public void notifyDataSetChanged() {
		mTabLayout.removeAllViews();
		PagerAdapter adapter = mViewPager.getAdapter();
		IconPagerAdapter iconAdapter = null;
		if (adapter instanceof IconPagerAdapter) {
			iconAdapter = (IconPagerAdapter) adapter;
		}
		final int count = adapter.getCount();
		for (int i = 0; i < count; i++) {
			CharSequence title = adapter.getPageTitle(i);
			if (title == null) {
				title = EMPTY_TITLE;
			}
			int iconResId = 0;
			if (iconAdapter != null) {
				iconResId = iconAdapter.getIconResId(i);
			}
			addTab(i, title, iconResId);
		}
		if (mSelectedTabIndex > count) {
			mSelectedTabIndex = count - 1;
		}
		setCurrentItem(mSelectedTabIndex);
		requestLayout();
	}

	@Override
	public void setViewPager(ViewPager view, int initialPosition) {
		setViewPager(view);
		setCurrentItem(initialPosition);
	}

	@Override
	public void setCurrentItem(int item) {
		if (mViewPager == null) {
			throw new IllegalStateException("ViewPager has not been bound.");
		}
		mSelectedTabIndex = item;
		mViewPager.setCurrentItem(item);

		final int tabCount = mTabLayout.getChildCount();
		for (int i = 0; i < tabCount; i++) {
			final View child = mTabLayout.getChildAt(i);
			final boolean isSelected = (i == item);
			child.setSelected(isSelected);
			if (isSelected) {
				animateToTab(item);
			}
		}
	}

	@Override
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		mListener = listener;
	}

	private class TabView extends TextView {
		private int mIndex;

		public TabView(Context context) {
			super(context, null, R.attr.vpiTabPageIndicatorStyle);
		}

		@Override
		public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);

			// Re-measure if we went beyond our maximum size.
			if (mMaxTabWidth > 0 && getMeasuredWidth() > mMaxTabWidth) {
				super.onMeasure(MeasureSpec.makeMeasureSpec(mMaxTabWidth,
						MeasureSpec.EXACTLY), heightMeasureSpec);
			}
		}

		public int getIndex() {
			return mIndex;
		}
	}
}
