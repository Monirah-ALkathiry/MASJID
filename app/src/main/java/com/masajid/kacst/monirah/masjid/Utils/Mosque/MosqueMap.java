package com.masajid.kacst.monirah.masjid.Utils.Mosque;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.MarkerManager;
import com.masajid.kacst.monirah.masjid.R;
import com.masajid.kacst.monirah.masjid.Utils.APIConnection.ApiRetrofitClint;
import com.masajid.kacst.monirah.masjid.Utils.AppNavigationDrawer;
import com.masajid.kacst.monirah.masjid.Utils.Mosque.Communication.FirstFragmentListenerMAP;
import com.masajid.kacst.monirah.masjid.Utils.Mosque.Communication.FragmentCommunicator;
import com.masajid.kacst.monirah.masjid.Utils.Mosque.Communication.MosquesLatLngClint;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Monirah on 7/17/2018.
 */

public class MosqueMap extends Fragment implements OnMapReadyCallback
        , GoogleMap.InfoWindowAdapter
        , GoogleMap.OnInfoWindowClickListener
        , FragmentCommunicator
        ,GoogleMap.OnCameraIdleListener,
        GoogleMap.OnCameraMoveStartedListener
     {

    // the fragment initialization

    private static final String TAG = "MosqueMap";

    //location:
    private double latitude ;
    private double longitude ;



    //Initialize Map variables :
    protected MapView mapView;
    protected GoogleMap MgoogleMap;
    private View mView;
    // SupportMapFragment mapFragment;
    protected Marker marker;
    //----------------------------------



    private String Slatitude;
    private String Slongitude;

    protected Marker MosqueMarker;


    //Used If Map Marker Clicked Open this Activity
    //protected MosqueListAdapter adapter;

    //Location Distance :
    protected Location locationA = new Location("point A");
    protected Location locationB = new Location("point B");

    //Retrofit InterFace:
    protected MosquesLatLngClint mosquesLatLngClint;
    //To get Mosque Information
    protected List<MosquesLatLng> mosquesLatLngs;
    protected List<MosquesLatLng> NewmosquesLatLngs;


    //User Location:
    protected LatLng latLng;


    //Communication'
    FirstFragmentListenerMAP firstFragmentListenerMAP;


    //
    protected String Newlat;
    protected String Newlon;

    private TextView MosqueName;
    private String MosquName;
    private List<MosquesLatLng> mosquesLatLngs2;


//Oncamera Idle
    private String nlat;
    private String nlng;

//Used To update marker Marker
         private  boolean flge;

         public MosqueMap(){
        super();
    }


    @SuppressLint("ValidFragment")
    public MosqueMap(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof FirstFragmentListenerMAP){
            firstFragmentListenerMAP = (FirstFragmentListenerMAP)context;
        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            mView = inflater.inflate(R.layout.fragment_mosque_map, container, false);
            //Maps Initializer
            MapsInitializer.initialize(this.getActivity());
            mapView = (MapView) mView.findViewById(R.id.mapView);
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(this);
            Slatitude = Double.toString(latitude);
            Slongitude = Double.toString(longitude);

            flge = true;

        }catch (InflateException e)
             {
            Log.e(TAG, "Inflate exception");

             }
            return mView;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Set Map View By Id
        //check if Not Null

        //TODO: Update lis :
        //ToDO: Search ANd Advance search

       ((AppNavigationDrawer) getActivity()).passVal(new FragmentCommunicator() {
            @Override
            public void passData(List<MosquesLatLng> mosquesLatLngs) {
                NewmosquesLatLngs = mosquesLatLngs;
                Toast.makeText(getContext(), "لا يوجد بيانات ", Toast.LENGTH_SHORT).show();
                onResume();

                // addMoreMarker(NewmosquesLatLngs);
            }});


    }




    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();


    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

        if (NewmosquesLatLngs != null) {

            MgoogleMap.clear();
            onMapReady(MgoogleMap);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }

//--------------------------------------

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //Get Data From API :
        MgoogleMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            return;
        }
        MgoogleMap.setMyLocationEnabled(true);

        // Set a listener for marker click.

        //Initialize Map:
        MapsInitializer.initialize(getContext());
        MgoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Add Marker and Map Properties (User Location)
        latLng = new LatLng(latitude, longitude);

        //Set Camera Position:
        CameraPosition CameraMosque = CameraPosition.builder().target(latLng)
                .zoom(13)
                .bearing(0)
                .tilt(40)
                .build();

        //Google Map Zoom in zoom out
        //Zoom controller position:
        //leftPadding, topPadding, rightPadding, bottomPadding
        MgoogleMap.setPadding(0, 0, 0, 100);
        MgoogleMap.getUiSettings().setZoomControlsEnabled(true);
        MgoogleMap.getUiSettings().setRotateGesturesEnabled(true);
        MgoogleMap.getUiSettings().setScrollGesturesEnabled(true);
        MgoogleMap.getUiSettings().setTiltGesturesEnabled(true);

        //USER LOCATION :
        MgoogleMap.setMyLocationEnabled(true);
    // MgoogleMap.setOnMarkerClickListener(this);

        //  Google Map Onclick:
        MgoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraMosque));

        //MapMove---
        MgoogleMap.setOnCameraIdleListener(this);
        MgoogleMap.setOnCameraMoveStartedListener(this);



        if (NewmosquesLatLngs == null) {
            //System.out.println("NULLL Datat : ");
            AddOtherLocation(Slatitude, Slongitude);
            //Change Position:

        } else {

            addMoreMarker(NewmosquesLatLngs);
        }

    }



    public void AddOtherLocation(String lat, String lon) {

        if (marker != null) {
            marker.remove();
        }
        Newlat = lat;
        Newlon = lon;

        //Make A Connection With API :
        mosquesLatLngClint = ApiRetrofitClint.getApiRetrofitClint().create(MosquesLatLngClint.class);
        //Call Function form Inter Face And Send Parameter to it


        Call<List<MosquesLatLng>> call = mosquesLatLngClint.getMosqueLatLng(Newlat, Newlon, 25);
        //  Create Response:
        call.enqueue(new Callback<List<MosquesLatLng>>() {
            @Override
            public void onResponse(Call<List<MosquesLatLng>> call, Response<List<MosquesLatLng>> response) {

                if (response.isSuccessful()) {
                    mosquesLatLngs = response.body();
                    System.out.println(mosquesLatLngs.size() + " SIZE IS");
                    //Send Data To Fragment List---
                    //  adapter = new MosqueListAdapter(getContext(),mosquesLatLngs);

                    ///recyclerView.setAdapter(adapter);
                    //-------

                    //Test Result and Print Data
                     System.out.println("Responce toString" + response.toString());
                     System.out.println("Responce body" + response.body());
                    //  System.out.println("Responce headers" + response.headers());
                    //  System.out.println("Responce errorBody" + response.errorBody());
                    //  System.out.print("URL" + response.isSuccessful());
                    //Storing the data in our list

                      System.out.println("Size Is onResponce :----" + mosquesLatLngs.size());
                    //-----------------------------------------------------------------------

                    //firstFragmentListenerMAP.sendData(mosquesLatLngs);
                   addMoreMarker(mosquesLatLngs);

                    //System.out.println("Size Is onResponce :----" + mosquesLatLngs.size());
                } else {


                }

            }

            @Override
            public void onFailure(Call<List<MosquesLatLng>> call, Throwable t) {
                System.out.println("Error bad  ):-----------------------");
            }
        });

        // } else {

        //  Toast.makeText(getContext(), "لا يوجد إتصال ", Toast.LENGTH_SHORT).show();


        //}
    }


    public void addMoreMarker(List<MosquesLatLng> mosques) {

        if (marker != null) {
            //to remove All marker from Map when user change camera position
            MgoogleMap.clear();
            //Map Movment Marker
        }

        mosquesLatLngs2 = mosques;
        System.out.println(mosquesLatLngs2 + "\n Test Mosque List\n");
        //Used To calc Distance:
        locationA.setLatitude(latitude);
        locationA.setLongitude(longitude);


        //Add All mosqu
        for (int i = 0; i < mosquesLatLngs2.size(); i++) {


            String latAPI = mosquesLatLngs2.get(i).getLatitude();
            String logAPI = mosquesLatLngs2.get(i).getLongitude();

            double latd = Double.parseDouble(latAPI);
            final double logd = Double.parseDouble(logAPI);
            LatLng latLngAPI = new LatLng(latd, logd);

            System.out.println(" Distance is :) :) :0  " + latLngAPI);

            System.out.println(latLngAPI + "  Id " + i + mosquesLatLngs2.size());
            MosquName = mosquesLatLngs2.get(i).getMosqueName();

//-----------------------------Calc Distance --------------------------------
            locationB.setLatitude(latd);
            locationB.setLongitude(logd);
            float distance = locationA.distanceTo(locationB);
            double dm = distance * Math.PI / 180.0;
            double dk = dm / 10.0;

            //rad * 180.0 / Math.PI
            // System.out.println(" Distance is :) :) :0  " + distance + "\n d by meeter :" + dm + "\n In Kilo : " + dk);


//--------------------------------------------------------------------------------------------------

            if (MgoogleMap != null) {
                //addItems();

                marker = MgoogleMap.addMarker(
                        new MarkerOptions()
                                .position(latLngAPI)
                                .title(MosquName)////title on the marker
                                .snippet("")//Description
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map)));

                // System.out.println(MosquName + " Name");
                marker.setTag(mosquesLatLngs2.get(i));


                //Info Window
                MgoogleMap.setInfoWindowAdapter(this);
                //Onclick Info Window

                MgoogleMap.setOnInfoWindowClickListener(
                        new GoogleMap.OnInfoWindowClickListener() {

                    @Override
                    public void onInfoWindowClick(Marker marker) {


                        MosquesLatLng infoAttached = (MosquesLatLng) marker.getTag();

                        Bundle intent = new Bundle();


                        intent.putString("MOSQUE_CODE", infoAttached.getCode());
                        //USED IN MAP
                        intent.putString("MOSQUE_LAT", infoAttached.getLatitude());
                        intent.putString("MOSQUE_LON", infoAttached.getLongitude());

                        // Mosque Information:
                        intent.putString("MOSQUE_NAME", infoAttached.getMosqueName());
                        intent.putString("MOSQUE_TYPE", infoAttached.getMosqueCatogery());
                        intent.putString("MOSQUE_REGION", infoAttached.getRegion());
                        intent.putString("CITY_VILLAGE", infoAttached.getCityVillage());
                        intent.putString("DISTRICT", infoAttached.getDistrict());
                        intent.putString("STREET_NAME", infoAttached.getStreetName());
                        intent.putString("IMAM_NAME", infoAttached.getImamName());
                        intent.putString("KHATEEB_NAME", infoAttached.getKhateebName());
                        intent.putString("MOATHEN_NAME", infoAttached.getMoathenName());
                        intent.putString("OBSERVER_NAME", infoAttached.getObserverName());


                       // getContext().startActivity(intent);

                       // FragmentManager fm = getFragmentManager();
                       // fm.beginTransaction().replace(R.id.container,fragobj).commit();



                        MosqueInformationActivity fragobj = new MosqueInformationActivity();
                        fragobj.setArguments(intent);
                       // MosqueInformationActivity newGamefragment = new MosqueInformationActivity();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                                .beginTransaction();
                        fragmentTransaction.replace(R.id.container, fragobj);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    }
                });
            }//end if
            else {
                Toast.makeText(getContext(), "/n لا توجد بيانات /n", Toast.LENGTH_SHORT).show();


            }

        }//end for


    }
//-------------------end Add Marker-----------------------------
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = getLayoutInflater().inflate(R.layout.map_info_window, null, false);


        MosqueName = (TextView) view.findViewById(R.id.MosqueName);
        MosqueName.setText(marker.getTitle());

        return view;

    }

    @Override
    public void onCameraIdle() {


        LatLngBounds bounds = MgoogleMap.getProjection().getVisibleRegion().latLngBounds;

        LatLng nl= bounds.getCenter();
        double lon= nl.longitude;
        double lat = nl.latitude;

        nlat= String.valueOf(lat);
        nlng = String.valueOf(lon);

        if(NewmosquesLatLngs == null ) {
            if (flge) {
                AddOtherLocation(nlat, nlng);
            }
        }

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(getContext(), "Ck",
                Toast.LENGTH_SHORT).show();
    }


    //-----------------Communication with Fragment (Search) -----------------------
    @Override
    public void passData(List<MosquesLatLng> mosquesLatLngs) {
        Toast.makeText(getContext(), "From Inter Face\n "
                +mosquesLatLngs.get(0).getMosqueName(), Toast.LENGTH_SHORT).show();

    }

//------------------------------------------------------------


    @Override
    public void onCameraMoveStarted(int reason) {


        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {

            //Toast.makeText(getContext(), "The user gestured on the map.",
            //    Toast.LENGTH_SHORT).show();

        } else
        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_API_ANIMATION) {

            //   Toast.makeText(getContext(), "The user tapped something on the map.",
            //  Toast.LENGTH_SHORT).show();


            if (marker.equals(marker)) {
                //  Toast.makeText(getContext(), "The  MARKER.",
                //   Toast.LENGTH_SHORT).show();
                if (!marker.isInfoWindowShown()) {
                    // Toast.makeText(getContext(), "The window",
                    //      Toast.LENGTH_SHORT).show();
                    flge = false;

                }
            }

            MgoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {

                    //Toast.makeText(getContext(), "Map Clicked.",Toast.LENGTH_SHORT).show();

                    flge = true;

                }
            });



        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {

           // Toast.makeText(getContext(), "The  the camera.",
                  //  Toast.LENGTH_SHORT).show();
        }


    }


}
