package com.atguigu.shappingmall_1020.community.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.atguigu.shappingmall_1020.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by 张永卫on 2017/3/4.
 */

public class CommunityViewPagerAdapter extends FragmentPagerAdapter {


    private final ArrayList<BaseFragment> fragments;

    private String[] titles = new String[]{"新帖","热帖"};

    public CommunityViewPagerAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
