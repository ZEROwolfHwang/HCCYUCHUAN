package com.ruitong.yuchuan.maptest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ruitong.yuchuan.maptest.fragment.LineFragment;
import com.ruitong.yuchuan.maptest.fragment.PointFragment;


/**
 * Created by Administrator on 2017/7/17.
 */

public class CollectViewPagerAdapter extends FragmentPagerAdapter {

    public CollectViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = LineFragment.newInstance();
                break;
            case 1:
                fragment = PointFragment.newInstance();
                break;
            default:
                fragment = LineFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "航线";
            case 1:
                return "航点";

        }
        return null;
    }
}
