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
 * Class InfoFragment
 * Fragment shown when in the information section
 * Shows a ViewPager with several tabs and its correspondent fragments
 */
public class InfoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        TabLayout mTabLayout;
        ViewPager mViewPager;

        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);

        mViewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), getResources().getStringArray(R.array.titles_tab)));
        mTabLayout.setupWithViewPager(mViewPager);

    }
}
