package com.nlte.baidutakeaway.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Function：
 *
 * @author NLTE
 * @date 2016/7/13 0013 0:10
 */
public class ContainerPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public ContainerPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    public ContainerPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
