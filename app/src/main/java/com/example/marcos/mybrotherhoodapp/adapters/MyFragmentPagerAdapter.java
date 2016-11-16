package com.example.marcos.mybrotherhoodapp.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.example.marcos.mybrotherhoodapp.fragments.HistoryFragment;
import com.example.marcos.mybrotherhoodapp.fragments.SocialFragment;
import com.example.marcos.mybrotherhoodapp.fragments.ManagersFragment;

/**
 * Created by Marcos on 09/11/2016.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{

    private String[] mTabTitles;


    public MyFragmentPagerAdapter(FragmentManager fm, String[] mTabTitles) {
        super(fm);
        this.mTabTitles = mTabTitles;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new HistoryFragment();
            case 1:
                return new SocialFragment();
            case 2:
                return new ManagersFragment();
            default:
                return null;
        }

    }

    /**
     * @return number of views available
     */
    @Override
    public int getCount() {
        return this.mTabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.mTabTitles[position];
    }
}
