package com.masajid.kacst.monirah.masjid.Utils;


import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.test.mock.MockPackageManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;
import com.masajid.kacst.monirah.masjid.R;
import com.masajid.kacst.monirah.masjid.Utils.APIConnection.AdvanceSearchClint;
import com.masajid.kacst.monirah.masjid.Utils.APIConnection.ApiRetrofitClint;
import com.masajid.kacst.monirah.masjid.Utils.APIConnection.SearchRequest;
import com.masajid.kacst.monirah.masjid.Utils.Dawa.DawaActivity;
import com.masajid.kacst.monirah.masjid.Utils.Mosque.AdvanceSearch;
import com.masajid.kacst.monirah.masjid.Utils.Mosque.Communication.FirstFragmentListenerMAP;
import com.masajid.kacst.monirah.masjid.Utils.Mosque.Communication.FragmentCommunicator;
import com.masajid.kacst.monirah.masjid.Utils.Mosque.MosqueActivity;
import com.masajid.kacst.monirah.masjid.Utils.Mosque.MosqueListAdapter;
import com.masajid.kacst.monirah.masjid.Utils.Mosque.MosqueMap;
import com.masajid.kacst.monirah.masjid.Utils.Mosque.MosquesLatLng;
import com.masajid.kacst.monirah.masjid.Utils.Utils.GPSTracker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AppNavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
    ,FirstFragmentListenerMAP
 {

    private Toolbar toolbar;
    private DrawerLayout drawer;

    // GPSTracker class
    private GPSTracker gps;
    private static final int REQUEST_CODE_PERMISSION = 2;
    private String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private double latitude;
    private double longitude;


     //--------------------------------------
     //Retrofit InterFace:
     private SearchRequest searchClient;
     //Retrofit InterFace:
     private AdvanceSearchClint AdvanceSearchClient;
     //To get Mosque Information
     public List<MosquesLatLng> mosquesLatLngs;
     public List<MosquesLatLng> AdvanceMosquesLatLngs;
    //-------------------------------------
    protected String SearchQuery;
     //Used To Update Map_Marker
     public FragmentCommunicator fragmentCommunicator;
     //-------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_navigation_drawer);

         toolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);



        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*check the drawer view is currently open or not
         boolean isOpen = drawer.isDrawerOpen(GravityCompat.END);
        Toast.makeText(this,String.valueOf(isOpen) ,Toast.LENGTH_LONG).show();
         */

        //Make Mosque Activity Default Activity
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new MosqueActivity()).commit();

        }


       //LOCATION:

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        // create class object
        gps = new GPSTracker(AppNavigationDrawer.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

             latitude = gps.getLatitude();
             longitude = gps.getLongitude();

                       // Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
             //       + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

//------------------------------------------
    }


    @Override
    public void onBackPressed() {
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //TODO:Search And Advance Search Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      // getMenuInflater().inflate(R.menu.app_navigation_drawer, menu);


//---------------------------Search----------------------------------------------

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_navigation_drawer, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if(null!=searchManager ) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        searchView.setIconifiedByDefault(false);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                try {
                    String Search_String = query;

                    if(Search_String != null){
                        Toast.makeText(AppNavigationDrawer.this, Search_String, Toast.LENGTH_LONG).show();
                        Search(Search_String);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        MenuItem menuItem = menu.findItem(R.id.search);
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Toast.makeText(MosqueActivity.this, "llll", Toast.LENGTH_LONG).show();

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                //Map -----
                fragmentCommunicator.passData(AdvanceMosquesLatLngs);
                return true;
            }
        });

        //---------------------------Search----------------------------------------------


        return super.onCreateOptionsMenu(menu);

    }

//---------------------------Search----------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            Toast.makeText(this," البحث",Toast.LENGTH_LONG).show();


                return true;
        }if (id == R.id.SearchFilter) {

            Toast.makeText(this,"البحث المتقدم",Toast.LENGTH_LONG).show();
             String lat = String.valueOf(latitude);
            String lon = String.valueOf(longitude);


           Intent intent = new Intent(AppNavigationDrawer.this, AdvanceSearch.class);
            intent.putExtra("LAT", lat);
            intent.putExtra("LON", lon);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



     public void Search(String query) {
         SearchQuery = query;
         //Convert latitude and longitude to String
         final String lat = Double.toString(latitude);
         final String lon = Double.toString(longitude);

         //New Test:
         final Map<String, Object> map = new HashMap<>();

         map.put("where", "Mosque_Name = N" + "\'" + SearchQuery + "\'");
         System.out.println(map + " MAP \n");

         //Make A Connection With API :
         searchClient = ApiRetrofitClint.getApiRetrofitClint().create(SearchRequest.class);

         //Call SearchRequest interface
         Call<List<MosquesLatLng>> call = searchClient.getMosqueList(lat, lon, map, 25);
         //  Create Response:
         call.enqueue(new Callback<List<MosquesLatLng>>() {
             @Override
             public void onResponse(Call<List<MosquesLatLng>> call, Response<List<MosquesLatLng>> response) {
                 mosquesLatLngs = response.body();
                 //Test Result and Print Data
                 //  System.out.println("Search Responce :");
                 // System.out.println("Responce toString" + response.toString());
                 //  System.out.println("Responce body" + response.body());
                 // System.out.println("Responce Headers" + response.headers());
                 // System.out.print("URL" + response.isSuccessful());

                 //  Log.e("  URL KK : ", call.request().url().toString());

                 if (mosquesLatLngs.size() == 0) {
                     // Log.e("  URL KK : ", "There is NO data ");
                     Toast.makeText(AppNavigationDrawer.this," لاتوجد بيانات " +SearchQuery,Toast.LENGTH_LONG).show();

                 } else {

                     String Newlatitude = mosquesLatLngs.get(0).getLatitude();
                     String Newlongitude = mosquesLatLngs.get(0).getLongitude();

                     // System.out.print("Newlatitude" + Newlatitude + "\n");
                     //  System.out.print("longitude" + Newlongitude + "\n");

                     //  System.out.print("MOSQU NAME ++++ 0000000000 " +mosquesLatLngs.get(0).getMosqueName() + "\n"
                     //   + "Array Size" + mosquesLatLngs.size() );

                     // for (int i=0 ; i < mosquesLatLngs.size() ; i++){

                     // System.out.print("New  NAME ++++ AAAAAA : " + mosquesLatLngs.get(i).getMosqueName());

                     // }
                     double latNew = Double.parseDouble(Newlatitude);
                     double lonNew = Double.parseDouble(Newlongitude);


                   //  recyclerView = (RecyclerView) findViewById(R.id.MosqueRecyclerView);
                   //  layoutManager = new LinearLayoutManager(MosqueActivity.this);
                   //  recyclerView.setLayoutManager(layoutManager);
                   //  recyclerView.setHasFixedSize(true);

                     //adapter = new MosqueListAdapter(MosqueActivity.this, mosquesLatLngs, latNew, lonNew);
//
                   //  recyclerView.setAdapter(adapter);

                     //Map -----
                     fragmentCommunicator.passData(mosquesLatLngs);

                 }
             }

             @Override
             public void onFailure(Call<List<MosquesLatLng>> call, Throwable t) {
                 System.out.print("لا يوجد اتصال \n");

             }
         });


     }

     //Here is new method-----------------------------------------------------
     public void passVal(FragmentCommunicator fragmentCommunicator) {
         this.fragmentCommunicator = fragmentCommunicator;

     }



     //-----------------------------------------------------------
     private MosqueListAdapter adapter;
     //Recycle View (Mosque List)
     private RecyclerView recyclerView;
     private RecyclerView.LayoutManager layoutManager;


     //send Data TO List -------------Idle------------------------
     @Override
     public void sendData(List<MosquesLatLng> newData) {
         recyclerView = (RecyclerView) findViewById(R.id.MosqueRecyclerView);

         layoutManager = new LinearLayoutManager(AppNavigationDrawer.this);
         recyclerView.setLayoutManager(layoutManager);
         recyclerView.setHasFixedSize(true);

         adapter = new MosqueListAdapter(this, newData);
         recyclerView.setAdapter(adapter);
     }

     //------------------------Drawer------------------------------
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();


        // set item as selected to persist highlight
        item.setChecked(true);
        drawer.closeDrawers();//Close Drawer After Select

        switch(id)
        {

            case R.id.login:

                // Toast.makeText(context, "LogIn",Toast.LENGTH_SHORT).show();
               // Intent LoginIntent = new Intent(context,LoginActivity.class);
               // context.startActivity(LoginIntent);
                break;


            case R.id.logOut:

                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this,R.style.MyDialogTheme);
                dlgAlert.setTitle("");
                dlgAlert.setMessage("قمت بتسجيل الخروج");
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
               // Intent LogoutIntent = new Intent(context,LogOut.class);
               // context.startActivity(LogoutIntent);

                //Toast.makeText(context, "logout",Toast.LENGTH_SHORT).show();
                break;

            case R.id.info:
                final String websiteurl= "http://www.moia.gov.sa/AboutMinistry/Pages/AboutMinistry.aspx";
               // Intent LinkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteurl));
               // context.startActivity(LinkIntent);
                //Toast.makeText(context, "Link",Toast.LENGTH_SHORT).show();

                break;


            case R.id.contactUs:
              //  Intent ContactUs = new Intent(context,ContactUsActivity.class);
             //   context.startActivity(ContactUs);
                //Toast.makeText(context, "Mosque",Toast.LENGTH_SHORT).show();

                break;


            /*case R.id.ic_Dawa:

                DawaActivity dawaGamefragment = new DawaActivity(latitude,longitude);
                FragmentTransaction dawafragmentTransaction = this.getSupportFragmentManager().beginTransaction();
                dawafragmentTransaction.replace(R.id.container, dawaGamefragment);
                dawafragmentTransaction.addToBackStack(null);
                dawafragmentTransaction.commit();


                break;*/

            case R.id.ic_favorit:
                // Toast.makeText(context, "Favorite",Toast.LENGTH_SHORT).show();
               // Intent FavoriteIntent = new Intent(context,FavoriteActivity.class);
               // context.startActivity(FavoriteIntent);
                break;

            case R.id.ic_masijed:


                MosqueActivity newGamefragment = new MosqueActivity(latitude,longitude);
                FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, newGamefragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;

            case R.id.aboutApp:
                // Toast.makeText(context, "About App",Toast.LENGTH_SHORT).show();

                break;

            case R.id.ic_praytime:
               // Intent intent4 = new Intent(context, PrayTime.class);//ACTIVITY_NUM = 1
               // context.startActivity(intent4);
                break;

            default:
                return true;
        }



        return false;
    }


}
