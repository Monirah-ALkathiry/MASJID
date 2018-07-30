package com.masajid.kacst.monirah.masjid.Utils.Mosque;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monirah on 7/29/2018.
 */

public class AdvanceSearchPageAdapter extends FragmentPagerAdapter {
    //Keeping Trace fro fragment and Title

    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> mFragmentTitle = new ArrayList<>();



    public AdvanceSearchPageAdapter(FragmentManager fm) {
        super(fm);
    }

    //used to Add Fragment To Fragment List
    public void addFragment(Fragment fragment, String title){

        fragments.add(fragment);
        mFragmentTitle.add(title);

    }



    @Override
    public CharSequence getPageTitle(int position) {

        //return Page Title
        return mFragmentTitle.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        //return Fragment
        return fragments.get(position);
    }

    @Override
    public int getCount() {

        //return Fragment Size
        return fragments.size();
    }
}
