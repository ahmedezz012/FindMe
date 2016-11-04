package com.ezz.findme.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ezz.findme.Fragments.FragmentFriends;
import com.ezz.findme.Fragments.FragmentRequests;
import com.ezz.findme.Fragments.FragmentSearch;


public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new FragmentRequests();
            case 1:
                return new FragmentFriends();
            case 2:
                return new FragmentSearch();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "Friend Requests";
            case 1:
                return "Friends";
            case 2:
                return "Search";
        }
        return super.getPageTitle(position);
    }
}
