package com.masajid.kacst.monirah.masjid.Utils.Mosque;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.masajid.kacst.monirah.masjid.R;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Monirah on 7/25/2018.
 */

@SuppressLint("ValidFragment")
public class MosqueImage extends Fragment

    {

        private String mosqCode;
        private Context context = getContext();

        //Recycle View (Mosque List)
        private RecyclerView recyclerView;
        private RecyclerView.LayoutManager layoutManager;
        private MosqueImagAdapter adapter;
        private TextView Noimage;

        ArrayList<String> ImageUrls = new ArrayList<String>();

        View view;

        Bundle intent;

        public MosqueImage() {
        super();
        }

        @SuppressLint("ValidFragment")
        public MosqueImage(Bundle intent) {
            this.intent = intent;
        }

        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_mosque_image, container, false);
        Noimage = view.findViewById(R.id.NoImage);

        //intent = getActivity().getIntent();
        mosqCode = intent.getString("MOSQUE_CODE");

        System.out.print(mosqCode + " Mosque Code");


        //Recycler View
        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerViewImag);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);



            loodImage ParseH = new loodImage();

            ParseH.mos(mosqCode );
            ParseH.execute();
        return view;
    }

 //--------------------------------------------------------
        class loodImage extends AsyncTask<String,Void,String> {

            org.jsoup.nodes.Document doc = null;
            String CodNumber;
            Elements links;
            String myUrl;
            //ImageView imageView;
            // TextView textView;
            //, ImageView img, TextView textView
            //Constructor
            private void  mos( String MosquCode ){
                this.CodNumber = MosquCode;
                System.out.println(CodNumber + " CodNumber Is");
                //this.imageView = img;
                //   this.textView = textView;

            }

            @Override
            protected String doInBackground(String... strings) {

                final StringBuilder builder = new StringBuilder();
                // System.out.println(" :)0 ");

                try {

                    //  System.out.println(" :)1 ");
                    doc = Jsoup.connect("http://gis.moia.gov.sa/Mosques/Content/images/mosques/"+CodNumber)
                            .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                            .referrer("http://www.google.com")
                            .get();
                    Log.d("EXAMPLE ", doc.toString());
                    String title = doc.title();
                    links = doc.getElementsByTag("a");
                    builder.append(title).append(" title was\n");

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return "Execute";
            }

            @Override
            protected void onPostExecute(String s) {

                if(doc != null  ){

                    if(links.size() >1) {
                        for (org.jsoup.nodes.Element E : links) {
                            myUrl = E.attr("href");
                            System.out.println(" Link is : " + myUrl);

                            if (E.attr("href").endsWith(".JPG")) {
                                ImageUrls.add(E.attr("href"));
                                //Log.d("IMG :  ", "Download Image" + E.attr("href").endsWith(".JPG"));


                            }//end if

                        }//end for
                    }//end if
                    else {
                        Noimage.setVisibility(View.VISIBLE);
                    }


           /*  for (String imag: ImageUrls  ){
                    System.out.println(" size is  ()Mosque Image : " + ImageUrls.size());

                    System.out.println("Url Is" + imag);

                      /*
                      Picasso.with(context)
                                .load("http://gis.moia.gov.sa/"+imag)
                                .placeholder(R.drawable.mosqueicon)
                                .into(imageView);
                                * /
                }
               */

                }//end if
                adapter = new MosqueImagAdapter( getContext(),ImageUrls);
                recyclerView.setAdapter(adapter);
            }
        }//end Class


}