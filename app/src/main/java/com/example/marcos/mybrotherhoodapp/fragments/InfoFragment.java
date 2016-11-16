package com.example.marcos.mybrotherhoodapp.fragments;

import android.support.design.widget.TabLayout;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marcos.mybrotherhoodapp.R;
import com.example.marcos.mybrotherhoodapp.adapters.MyFragmentPagerAdapter;

/**
 * Created by Marcos on 11/11/2016.
 */

public class InfoFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_information, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mTabLayout = (TabLayout) getView().findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) getView().findViewById(R.id.view_pager);

        mViewPager.setAdapter(new MyFragmentPagerAdapter(getFragmentManager(), getResources().getStringArray(R.array.titles_tab)));
        mTabLayout.setupWithViewPager(mViewPager);

    }
}
