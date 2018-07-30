package com.masajid.kacst.monirah.masjid.Utils.Mosque;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.mock.MockPackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.masajid.kacst.monirah.masjid.R;
import com.masajid.kacst.monirah.masjid.Utils.Mosque.Communication.FirstFragmentListenerMAP;
import com.masajid.kacst.monirah.masjid.Utils.Mosque.Communication.FragmentCommunicator;
import com.masajid.kacst.monirah.masjid.Utils.Utils.GPSTracker;

import java.util.List;


public class MosqueActivity extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
         {


    public static final String TAG = "MosqueActivity";

    //Variables  :
    private  View view;
    private ViewPager mViewPager;
    private MosqueSectoinsPageAdapter mosqueSectoinsPageAdapter;
    private TabLayout tabLayout;

    //Location:
    private double latitude ;
    private double longitude ;



    ///--------------MAP------------------------------
    Intent intentThatCalled;


  //-------------------GPS-------------------------------------
             private static final int REQUEST_CODE_PERMISSION = 2;
             String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

             // GPSTracker class
             protected GPSTracker gps;
//----------------------------------------------------


    public MosqueActivity(){
        super();
    }


    @SuppressLint("ValidFragment")
    public MosqueActivity(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       view = inflater.inflate(R.layout.activity_mosque,container,false);

       return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//---------------------------------------------------------------------------------------
        //-------------------------------------------
        //-------------------------------------------
        try {
            if (ActivityCompat.checkSelfPermission(getActivity(), mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //execute every time, else your else part will work
            } else {

                //Toast.makeText(getApplicationContext(), "الرجاء تفعيل الموقع", Toast.LENGTH_LONG).show();

                //System.out.println("Please Check Your Location");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // create class object
        gps = new GPSTracker(getContext());

        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
     //---------------------------------------------------------------------------------------

        mosqueSectoinsPageAdapter = new MosqueSectoinsPageAdapter(getChildFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        setupViewPager(mViewPager);

        //Tab:
        tabLayout =(TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        //MAP
        intentThatCalled = getActivity().getIntent();



    }

    public void setupViewPager(ViewPager viewPager){

        MosqueSectoinsPageAdapter adapter = new MosqueSectoinsPageAdapter(getChildFragmentManager());
        adapter.AddFragment(new MosqueMap(latitude,longitude), "خريطه");
        adapter.AddFragment(new MosqueList(latitude,longitude), "قائمة");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }




}
