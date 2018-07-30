package com.masajid.kacst.monirah.masjid.Utils.Mosque;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.masajid.kacst.monirah.masjid.R;

import java.util.Locale;

/**
 * Created by Monirah on 7/25/2018.
 */

public class MosqueInformation extends Fragment implements OnMapReadyCallback
        ,GoogleMap.OnMarkerClickListener
{

    //Recycle View (Mosque List)
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MosqueInformationRecyclerView adapter;
    //--------------------

    //Google Map
    private String Latitude;
    private String Longitude;
    private String MosqueName;

    private Double Lat;
    private Double Lon;


    private Button Back;
    GoogleMap MgoogleMap;
    MapView mapView;
    View view;
    Marker marker;
    Bundle intent;

    //Constructor:
    public MosqueInformation() {

    }


    @SuppressLint("ValidFragment")
    public MosqueInformation(Bundle b) {

        this.intent =b;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_mosque_information, container, false);




        //Set Lat  and Long :
        Latitude = intent.getString("MOSQUE_LAT");
        Longitude = intent.getString("MOSQUE_LON");
        MosqueName = intent.getString("MOSQUE_NAME");
        //Convert To Double
        Lat = Double.parseDouble(Latitude);
        Lon = Double.parseDouble(Longitude);


        //Recycler View
        recyclerView = (RecyclerView) view.findViewById(R.id.MosqueRecyclerViewINFO);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

    /* Send Data To Fragment List--- */
        //adapter = new MosqueInformationRecyclerView(getContext(), intent);
        adapter = new MosqueInformationRecyclerView(intent);

        recyclerView.setAdapter(adapter);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) view.findViewById(R.id.mapView);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        MgoogleMap = googleMap;

        MgoogleMap.setMapType(googleMap.MAP_TYPE_NORMAL);

        //Add Marker and Map Properties (User Location)

        LatLng latLng = new LatLng(Lat, Lon);

        marker= MgoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(Lat, Lon))
                .title(MosqueName)////title on the marker
                .snippet("موقعي")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icons))//Description
        );

        //-----------------------
        //Set Camera Position:
        CameraPosition CameraMosque = CameraPosition.builder().target(latLng)
                .zoom(15)
                .bearing(0)
                .tilt(15)
                .build();
        //Move Camera
        MgoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraMosque));
        MgoogleMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);


    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        if(marker.equals(marker)){

            marker.getPosition();

            Log.w("Click", "test");
            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Lat, Lon);

            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            mapIntent.setPackage("com.google.android.apps.maps");
            this.startActivity(mapIntent);

            return true;

        }



        return false;
    }
}
