package com.masajid.kacst.monirah.masjid.Utils.Mosque;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.masajid.kacst.monirah.masjid.R;
import com.masajid.kacst.monirah.masjid.Utils.APIConnection.ApiRetrofitClint;
import com.masajid.kacst.monirah.masjid.Utils.APIConnection.MosquesLatLngClint;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Monirah on 7/17/2018.
 */

public class MosqueList extends Fragment {

    // the fragment initialization

    public static final String TAG = "MosqueLIST";
    private View view;

    //Location:
    private double latitude ;
    private double longitude ;

    //-----------------------------------------------------------------

    //Recycle View (Mosque List)
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MosqueListAdapter adapter;

    //Retrofit InterFace:
    private MosquesLatLngClint mosquesLatLngClint;
    //To get Mosque Information
    private List<MosquesLatLng> mosquesLatLngs;



    private  double lat;
    private  double log;

    protected String Slatitude;
    protected String Slongitude;

    //Connect With API :
    private double New_lat;
    private double New_log;
    private   String lat2;
    private String long2;



    public MosqueList(){
        super();
    }


    @SuppressLint("ValidFragment")
    public MosqueList(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mosque_list,container,false);

        Toast.makeText(getContext(), "من القائمة "
                + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();


        Slatitude= Double.toString(latitude);
        Slongitude= Double.toString(longitude);


        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //Recycler View
        recyclerView = (RecyclerView) view.findViewById(R.id.MosqueRecyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        ConnectWithAPI(Slatitude,Slongitude);

    }

    public void ConnectWithAPI(String latitude , String longitude){

        lat2 = latitude;
        long2 = longitude;
        //----------------------------------
        //  System.out.println("----:" + lat2 + ")P)----------------" + long2);

        //Make A Connection With API :
        mosquesLatLngClint = ApiRetrofitClint.getApiRetrofitClint().create(MosquesLatLngClint.class);
        //Call Function form Inter Face And Send Parameter to it
        Call<List<MosquesLatLng>> call = mosquesLatLngClint.getMosqueLatLng(lat2,long2,25);
        //  Create Response:
        call.enqueue(new Callback<List<MosquesLatLng>>() {
            @Override
            public void onResponse(Call<List<MosquesLatLng>> call, Response<List<MosquesLatLng>> response) {

                mosquesLatLngs = response.body();

                New_lat= Double.parseDouble(lat2);
                New_log=Double.parseDouble(long2);

                //  System.out.print("lat : "+ New_lat);
                //System.out.print("long: "+ New_log + " \n CONTEXT IS : " +getContext() +"\n");

                //Send Data To Fragment List---
                if(getContext()  != null) {
                    adapter = new MosqueListAdapter(getContext(), mosquesLatLngs, New_lat, New_log);
                    recyclerView.setAdapter(adapter);
                }else{
                    System.out.println("لا يوجد بيانات\n" );
                }
                //---------------------------------------------------------------------------------------------
                //Test Result and Print Data
                //System.out.println("Responce toString: " + response.toString());
                //System.out.println("Responce body : "+ response.body());
                //System.out.println("Responce headers : "+ response.headers());
                //System.out.println("Responce errorBody : "+ response.errorBody());
                //System.out.print("URL : " + response.isSuccessful());
                //Storing the data in our list

                //System.out.println("Size Is on Responce :----" +mosquesLatLngs.size());
                //-----------------------------------------------------------------------

            }

            @Override
            public void onFailure(Call<List<MosquesLatLng>> call, Throwable t) {
                System.out.println("Error bad  connection\n");
            }
        });
    }


}
