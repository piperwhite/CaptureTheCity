package com.whiteandc.capture.fragments.notcaptured;

import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.CirclePageIndicator;
import com.whiteandc.capture.R;
import com.whiteandc.capture.fragments.BasicFragment;
import com.whiteandc.capture.fragments.captured.ViewPagerAdapter;
import com.whiteandc.capture.data.Monument;
import com.whiteandc.capture.data.MonumentList;


public class FragmentNotCaptured extends BasicFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private static final String CLASS = "FragmentNotCaptured";
    private Monument monument;
    private ViewPagerAdapter adapter;
    private int currentPicture = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        monument = MonumentList.getMonument(monumentActivity.getCurrentMonumentId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_monument_not_captured, container, false);

        monumentActivity.setFullScreen(false);
        monumentActivity.setToolBarVisibility(true);
        monumentActivity.setHomeButtonVisibility(true);
        monumentActivity.setSelectedFragment(this);

        adapter = new ViewPagerAdapter(monumentActivity, monument.getPhotos());
        adapter.notifyDataSetChanged();
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.photo_pager);
        viewPager.setAdapter(adapter);

        //Bind the title indicator to the adapter
        CirclePageIndicator circleIndicator = (CirclePageIndicator)rootView.findViewById(R.id.indicator);
        circleIndicator.setViewPager(viewPager);
        circleIndicator.setOnPageChangeListener(this);
        rootView.findViewById(R.id.camera_fab).setOnClickListener(this);

        monumentActivity.setToolbarTitle(monument.getName());

        return rootView;
    }

    @Override
    public void onClick(View v) {
        monumentActivity.switchToFragmentCamera(monument.getPhotos()[currentPicture]);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        currentPicture = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}