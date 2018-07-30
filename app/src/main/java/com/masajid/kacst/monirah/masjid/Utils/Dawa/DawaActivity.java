package com.masajid.kacst.monirah.masjid.Utils.Dawa;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.masajid.kacst.monirah.masjid.R;
import com.masajid.kacst.monirah.masjid.Utils.Mosque.MosqueSectoinsPageAdapter;


public class DawaActivity extends Fragment {


    public static final String TAG = "DawaActivity";

    //Variables  :
    private  View view;
    private ViewPager mViewPager;
    private DawaSectoinsPageAdapter DawaSectoinsPageAdapter;
    private TabLayout tabLayout;

    //location:
    private double latitude ;
    private double longitude ;


    public DawaActivity() {
        super();
    }

    @SuppressLint("ValidFragment")
    public DawaActivity(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      view = inflater.inflate(R.layout.dawa_activity,container,false);

        Toast.makeText(getContext(), "من ddddddddddddd"
                + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
      return view;
    }


    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        DawaSectoinsPageAdapter = new DawaSectoinsPageAdapter(getChildFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        SetUpViewPager(mViewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    public  void SetUpViewPager(ViewPager viewPager){
        DawaSectoinsPageAdapter adapter = new DawaSectoinsPageAdapter(getChildFragmentManager());
        adapter.AddFragment(new DawaMap(latitude,longitude), "خريطة");
        adapter.AddFragment(new DawaList(latitude,longitude),"قائمة");

        viewPager.setAdapter(adapter);


    }
}
