package com.example.spendcelebsmoney.ui.tabs;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.spendcelebsmoney.TopWorth.MidPaidFragment;
import com.example.spendcelebsmoney.MyWorth.MyWorthFragment;
import com.example.spendcelebsmoney.TopWorth.TopPaidFragment;
import com.example.spendcelebsmoney.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        //in here is the logic responsible for passing in my fragments
        Fragment fragmentInstantiate = null;
        switch (position) {
            case 0:
                fragmentInstantiate = new TopPaidFragment();
                break;
            case 1:
                fragmentInstantiate = new MidPaidFragment();
                break;
            case 2:
                fragmentInstantiate = new MyWorthFragment();
                break;
        }
        return fragmentInstantiate;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}