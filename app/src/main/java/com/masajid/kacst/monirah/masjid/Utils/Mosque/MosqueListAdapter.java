package com.masajid.kacst.monirah.masjid.Utils.Mosque;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.masajid.kacst.monirah.masjid.R;
import com.masajid.kacst.monirah.masjid.Utils.AppNavigationDrawer;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * Created by Monirah on 7/25/2018.
 */

public class MosqueListAdapter extends RecyclerView.Adapter<MosqueListAdapter.MosqueViewList> {

    private List<MosquesLatLng> mosquesLatLngs;
    private double lat;
    private double log;

    double latd;
    double logd;

    String data;
    private Context context;

    //constructor
    public MosqueListAdapter(Context context, List<MosquesLatLng> latLngs, double lat , double log) {

        this.context = context;
        this.mosquesLatLngs = latLngs;
        this.lat =lat;
        this.log=log;


    }


    //constructor For Communication
    public MosqueListAdapter(Context context,List<MosquesLatLng> latLngs) {
        this.context = context;
        this.mosquesLatLngs = latLngs;

    }

    @Override
    public MosqueViewList onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mosque_item, parent, false);
        //Mosque Code :

        return new MosqueViewList(view);

    }


    @Override
    public void onBindViewHolder(final MosqueViewList holder, final int position) {



        holder.MosqueCode = (mosquesLatLngs.get(position).getCode());
        //System.out.println(holder.imageView + " holder.imageView ");

        // ((MosqueViewList) holder).FillList(position);

        holder.mTextView.setText(mosquesLatLngs.get(position).getMosqueName());
        holder.InfoTextView.setText(mosquesLatLngs.get(position).getCityVillage());
        holder.MosqueDistrict.setText(mosquesLatLngs.get(position).getDistrict());
        //-----------------------------Calc Distance --------------------------------
//Location Distance :
        Location locationA = new Location("point A");
        Location locationB = new Location("point B");
        //Used To calc Distance:
        locationA.setLatitude(lat);
        locationA.setLongitude(log);

        String latAPI= mosquesLatLngs.get(position).getLatitude();
        String logAPI= mosquesLatLngs.get(position).getLongitude();
        //  System.out.println(" Distance is :) :) :0  ******* " + logAPI  + "\n d by meeter :" +latAPI + "\n In Kilo **********: " );

        latd=Double.parseDouble(latAPI);
        logd= Double.parseDouble(logAPI);

        locationB.setLatitude(latd);
        locationB.setLongitude(logd);
        float distance = locationA.distanceTo(locationB);
        double dm =distance * Math.PI / 180.0;
        double dk = dm / 10.0;

        //rad * 180.0 / Math.PI
        //  System.out.println(" Distance is :) :) :0  ******* " + distance  + "\n d by meeter :" +dm + "\n In Kilo **********: " +dk );

        //Convert To String:
        dk = Math.floor(dk * 100) / 100;
        String Dstance= Double.toString(dk);
        holder.Distance.setText(Dstance + "كيلو");
//--------------------------------------------------------------------------------------------------
        //Onclick : Open New Activity
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            //USED in color
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#D3D3D3"));
                //view.setBackgroundColor(grey);
                //Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();
                //Create Object From Activity:
                Bundle intent = new Bundle();

                intent.putString("MOSQUE_CODE", mosquesLatLngs.get(position).getCode());
                //USED IN MAP
                intent.putString("MOSQUE_LAT", mosquesLatLngs.get(position).getLatitude());
                intent.putString("MOSQUE_LON", mosquesLatLngs.get(position).getLongitude());

                // Mosque Information:
                intent.putString("MOSQUE_NAME", mosquesLatLngs.get(position).getMosqueName());
                intent.putString("MOSQUE_TYPE", mosquesLatLngs.get(position).getMosqueCatogery());
                intent.putString("MOSQUE_REGION", mosquesLatLngs.get(position).getRegion());
                intent.putString("CITY_VILLAGE", mosquesLatLngs.get(position).getCityVillage());
                intent.putString("DISTRICT", mosquesLatLngs.get(position).getDistrict());
                intent.putString("STREET_NAME", mosquesLatLngs.get(position).getStreetName());
                intent.putString("IMAM_NAME", mosquesLatLngs.get(position).getImamName());
                intent.putString("KHATEEB_NAME", mosquesLatLngs.get(position).getKhateebName());
                intent.putString("MOATHEN_NAME", mosquesLatLngs.get(position).getMoathenName());
                intent.putString("OBSERVER_NAME", mosquesLatLngs.get(position).getObserverName());

                MosqueInformationActivity fragobj = new MosqueInformationActivity();
               fragobj.setArguments(intent);

               // fragobj.startActivity(intent);
               // context.startActivity(intent);
                //Test



               // MosqueInformationActivity newGamefragment = new MosqueInformationActivity();
                FragmentTransaction fragmentTransaction = ((AppNavigationDrawer) context).getSupportFragmentManager().beginTransaction();


              //  FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                       // .beginTransaction();
                fragmentTransaction.replace(R.id.container, fragobj);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        ParseHTML  ParseH = new ParseHTML();

        ParseH.mos(holder.MosqueCode,holder.imageView);
        ParseH.execute();

        // System.out.println(ParseH.a + " LINK ");
    }


    @Override
    public int getItemCount() {

        return mosquesLatLngs.size();
    }


//-----------------------------------------------------------------------------------------------------

    class ParseHTML extends AsyncTask<String,Void,String> {

        private Document doc = null;
        private String a;
        private String CodNumber;
        private ImageView imageView;
        private SpotsDialog mProgressDialog;
        private String imgurl;
        private Elements links;
        //  Bitmap bitmap;

        private void  mos( String MosquCode , ImageView img) {
            this.CodNumber = MosquCode;
            //System.out.println(CodNumber + " Mosque Code Number");
            this.imageView = img;
            // System.out.println(imageView + " holder.imageView ");
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();



            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... strings) {

            final StringBuilder builder = new StringBuilder();

            try {//+CodNumber+

                doc = Jsoup.connect("http://gis.moia.gov.sa/Mosques/Content/images/mosques/"+CodNumber+"/")
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                        .referrer("http://www.google.com")
                        .get();
                links = doc.getElementsByTag("a");



                if(links.size()>=2) {
                    if (links.get(1).attr("href").endsWith(".JPG")) {
                        a = links.get(1).attr("href");

                    }
                }
                else{
                    a = String.valueOf(R.drawable.ic_mosque);
                }


                imgurl= "http://gis.moia.gov.sa/"+a;

            } catch (IOException e) {
                builder.append("Error :( :(")
                        .append(e.getMessage())
                        .append("\n");
                //e.printStackTrace();
            }


            return "Execute";

        }

        @Override
        protected void onPostExecute(String s) {

            try {
                // if ((this.mProgressDialog != null) && this.mProgressDialog.isShowing()) {


                if(doc != null){
                    // Log.d("IMG :  ", "Download Image" +a);
                    // System.out.println(a + " New Link " +CodNumber+ "  CODE NUMBER\n" +imgurl);

                    Picasso.with(context)
                            .load(imgurl)
                            .placeholder(R.drawable.ic_mosque)
                            .into(imageView);
                }else {

                    imageView.setBackgroundResource(R.drawable.ic_mosque);
                }

                this.mProgressDialog.dismiss();
                //  }
            } catch (final IllegalArgumentException e) {
                // Handle or log or ignore
            } catch (final Exception e) {
                // Handle or log or ignore
            } finally {
                this.mProgressDialog = null;
            }

          /*  if(doc != null){
                Log.d("IMG :  ", "Download Image" +a);
                System.out.println(a + " New Link " +CodNumber+ "  CODE NUMBER\n" +imgurl);

               Picasso.with(context)
                        .load(imgurl)
                        .placeholder(R.drawable.mosqueicon)
                        .into(imageView);

                //imageView.setImageBitmap(bitmap);

                mProgressDialog.dismiss();

          }*/
            //super.onPostExecute(s);
        }



    }

    //------------------------------------------------------------------------------------------------------
    public class MosqueViewList extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Mosque List:

        private TextView mTextView;
        private TextView InfoTextView;
        private ImageView imageView;
        private TextView MosqueDistrict;
        private LinearLayout linearLayout;
        private TextView Distance;
        private CardView cardView;


        private String MosqueCode;//Form API To IMAGE VIEW


        public MosqueViewList(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.MosqueName);
            InfoTextView = (TextView) view.findViewById(R.id.MosqueInfo);
            imageView = (ImageView) view.findViewById(R.id.MosqueImage);
            MosqueDistrict =(TextView) view.findViewById(R.id.District);
            linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
            Distance = (TextView) view.findViewById(R.id.Distance);
            cardView = (CardView) view.findViewById(R.id.cardView);

        }


        @Override
        public void onClick(View view) {

        }


    }//end Inner Class
}// Mian Class
