package com.example.marcos.mybrotherhoodapp.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.example.marcos.mybrotherhoodapp.fragments.HistoryFragment;
import com.example.marcos.mybrotherhoodapp.fragments.SocialFragment;
import com.example.marcos.mybrotherhoodapp.fragments.ManagersFragment;

/**
 * Class MyFragmentPagerAdapter
 * Adapter used for making the the information section with a ViewPager format
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter{

    private String[] mTabTitles;

    public MyFragmentPagerAdapter(FragmentManager fm, String[] mTabTitles) {
        super(fm);
        this.mTabTitles = mTabTitles;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new HistoryFragment();
                break;
            case 1:
                fragment = new SocialFragment();
                break;
            case 2:
                fragment = new ManagersFragment();
                break;
        }

        return fragment;

    }

    @Override
    public int getCount() {
        return this.mTabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.mTabTitles[position];
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
