package com.masajid.kacst.monirah.masjid.Utils.Dawa;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monirah on 7/24/2018.
 */

public class DawaSectoinsPageAdapter extends FragmentPagerAdapter {

    //Keep Track Fragment And Fragment Title:
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();


    public DawaSectoinsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    //Fun : Add Fragment To Fragment List:
    public void AddFragment (Fragment fragment, String title ){

        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);


    }//end AddFragment Fun


    /**
     * This method may be called by the ViewPager to obtain a title string
     * to describe the specified page. This method may return null
     * indicating no title for this page. The default implementation returns
     * null.
     *
     * @param position The position of the title requested
     * @return A title for the requested page
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);

    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        return  mFragmentList.get(position);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
