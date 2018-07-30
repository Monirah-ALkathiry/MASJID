package com.masajid.kacst.monirah.masjid.Utils.Mosque;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.masajid.kacst.monirah.masjid.R;
import com.masajid.kacst.monirah.masjid.Utils.APIConnection.AdvanceSearchClint;
import com.masajid.kacst.monirah.masjid.Utils.APIConnection.ApiRetrofitClint;
import com.masajid.kacst.monirah.masjid.Utils.Mosque.MosqueAdvanceSearch.ImamaSearch;
import com.masajid.kacst.monirah.masjid.Utils.Mosque.MosqueAdvanceSearch.KhateebSearch;
import com.masajid.kacst.monirah.masjid.Utils.Mosque.MosqueAdvanceSearch.MosqueSearch;
import com.masajid.kacst.monirah.masjid.Utils.Utils.Sender;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Monirah on 7/29/2018.
 */

public class AdvanceSearch extends AppCompatActivity implements Sender {

    private static final String TAG = "Search dialog";

    private ViewPager mViewPager;
    private AdvanceSearchPageAdapter advanceSearchPageAdapter;

    protected Intent intent;
    protected String latitude;
    protected String longitude;
    protected   double lat;
    protected   double lon;


    private Button bSearch;
    private Button bExit;


    //Used To Update Map_Marker

    //-----------------------------------------------
    //Retrofit InterFace:
    private AdvanceSearchClint searchClient;
    //To get Mosque Information
    protected List<MosquesLatLng> mosquesLatLngs;

    protected String Mesage2;
    //-----------------------------------------------------------


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_advance_search);

        advanceSearchPageAdapter = new AdvanceSearchPageAdapter(getSupportFragmentManager());

        //set up the viewpager with Section adapter
        mViewPager = (ViewPager) findViewById(R.id.container);

        //Tab
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        intent=getIntent();
        latitude = intent.getStringExtra("LAT");
        longitude =  intent.getStringExtra("LON");


        bSearch =(Button) findViewById(R.id.search);
        bExit =(Button) findViewById(R.id.exit);

        System.out.print(latitude + "  LAT: \n Lone: " +longitude);

        bExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();

                finish();
            }
        });

        setUpViewPager(mViewPager);
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();

    }


    private void setUpViewPager(ViewPager viewPager) {

        AdvanceSearchPageAdapter adapter = new AdvanceSearchPageAdapter(getSupportFragmentManager());

        adapter.addFragment(new MosqueSearch(latitude , longitude), "إسم المسجد");
        adapter.addFragment(new ImamaSearch(latitude , longitude), "إسم الإمام" );
        adapter.addFragment(new KhateebSearch(latitude , longitude), "إسم  الخطيب");

        viewPager.setAdapter(adapter);
    }

    //-----------------------------------------------------------------


    @Override
    public void SendMassage(String Mesage) {

        Mesage2 = Mesage;

        if (Mesage2 == null){
            Toast.makeText(AdvanceSearch.this, "لايوجد مدخلات ", Toast.LENGTH_LONG).show();

        }



        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(AdvanceSearch.this,MosqueActivity.class);
                intent.putExtra("Query",Mesage2);
                startActivity(intent);


                //Toast.makeText(AdvanceSearch.this, Mesage2, Toast.LENGTH_SHORT).show();
                //Make A Connection With API :
                searchClient = ApiRetrofitClint.getApiRetrofitClint().create(AdvanceSearchClint.class);

                //Call SearchRequest interface
                Call<List<MosquesLatLng>> call = searchClient.getMosqueList2(25, latitude, longitude, Mesage2);
                //Create Response:
                call.enqueue(new Callback<List<MosquesLatLng>>() {
                    @Override
                    public void onResponse(Call<List<MosquesLatLng>> call, Response<List<MosquesLatLng>> response) {
                        mosquesLatLngs = response.body();
                        //Test Result and Print Data
                        System.out.println("Search Responce :");
                        System.out.println("Responce toString" + response.toString());
                        System.out.println("Responce body" + response.body());
                        System.out.println("Responce Headers" + response.headers());
                        System.out.print("URL" + response.isSuccessful());

                        Log.e("  URL KK : ", call.request().url().toString());

                        if (mosquesLatLngs.size() == 0) {
                            Toast.makeText(AdvanceSearch.this, "لايوجد بيانات", Toast.LENGTH_LONG).show();
                            return;

                        }
                        else {

                            String latitude = mosquesLatLngs.get(0).getLatitude();
                            String longitude = mosquesLatLngs.get(0).getLongitude();

                            System.out.print("latitude" + latitude + "\n");
                            System.out.print("longitude" + longitude + "\n");

                            lat = Double.parseDouble(latitude);
                            lon = Double.parseDouble(longitude);

                            System.out.println(latitude + " : lat \n lone : " +longitude);


                            //Map -----

                        }

                    }

                    @Override
                    public void onFailure(Call<List<MosquesLatLng>> call, Throwable t) {
                        System.out.print(":( :( \n");
                        Toast.makeText(AdvanceSearch.this, "الرجاء ادخال كلمات بحث اخرى", Toast.LENGTH_LONG).show();
                    }
                });



                finish();


            }
        });

    }//end Function


}//end Class

