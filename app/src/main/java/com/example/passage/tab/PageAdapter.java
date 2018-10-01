package com.example.passage.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.HashMap;

public class PageAdapter extends FragmentPagerAdapter {
    private int num;
    private HashMap<Integer, Fragment> mfragments = new HashMap<>();
    private Fragment[] fragments;

    public PageAdapter(FragmentManager fm, int num) {
        super(fm);
        this.num = num;
    }

    @Override
    public int getCount() {
        return num;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public Fragment getItem(int position) {
        return createFragment(position);
    }

    private Fragment createFragment(int pos) {
        Fragment fragment = mfragments.get(pos);
        fragments = FragmentGenerator.getFragments();
        if (fragment == null) {
            switch (pos) {
                case 0:
                    fragment = fragments[0];
                    break;
                case 1:
                    fragment = fragments[1];
                    break;
                case 2:
                    fragment = fragments[2];
                    break;
                case 3:
                    fragment = fragments[3];
                    break;
            }
            mfragments.put(pos, fragment);
        }
        return fragment;
    }
}
