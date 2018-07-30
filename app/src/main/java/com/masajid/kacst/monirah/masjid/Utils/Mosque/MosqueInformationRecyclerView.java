package com.masajid.kacst.monirah.masjid.Utils.Mosque;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.masajid.kacst.monirah.masjid.R;


/**
 * Created by Monirah on 7/25/2018.
 */

public class MosqueInformationRecyclerView extends RecyclerView.Adapter<MosqueInformationRecyclerView.MosqueViewInformationList>{

    Bundle intent;
    // Context context;

    //constructor
    public MosqueInformationRecyclerView( Bundle intent) {
        this.intent= intent;
        // this.context = context;
    }

    @Override
    public MosqueViewInformationList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mosque_information_list, parent, false);

        //Mosque Code :

        return new MosqueViewInformationList(view);
    }

    @Override
    public void onBindViewHolder(MosqueViewInformationList holder, int position) {

        String MOSQUE_TYPE ;
        MOSQUE_TYPE =intent.getString("MOSQUE_TYPE");

        int MTyop= Integer.parseInt(MOSQUE_TYPE);
        if(MTyop == 1){
            holder.MasijedType.setText("مسجد");
        }else if(MTyop == 2){
            holder.MasijedType.setText("مصلى");

        }
        else if(MTyop == 3){
            holder.MasijedType.setText("جامع");

        }
        else if(MTyop == 4){
            holder.MasijedType.setText("مصلى عيد");

        }else{
            holder.MasijedType.setText("لايوجد ");

        }

        String REGION =intent.getString("MOSQUE_REGION");
        //TEST
        System.out.println(REGION + " REGION");
        int RegionType= Integer.parseInt(REGION);

        if(RegionType == 1){
            holder.MOSQUE_REGION.setText("الرياض");

        }else
        if (RegionType ==  2){
            holder.MOSQUE_REGION.setText("مكة المكرمة");
        }else
        if (RegionType == 3){
            holder.MOSQUE_REGION.setText("الشرقية");
        }else
        if (RegionType == 4){
            holder.MOSQUE_REGION.setText("المدينة المنورة");
        }else
        if (RegionType == 5){
            holder.MOSQUE_REGION.setText("الجوف");
        }else
        if (RegionType == 6){
            holder.MOSQUE_REGION.setText("الباحة");
        }else
        if (RegionType == 7){
            holder.MOSQUE_REGION.setText("عسير");
        }else
        if (RegionType == 8){
            holder.MOSQUE_REGION.setText("القصيم");
        }else
        if (RegionType == 9){
            holder.MOSQUE_REGION.setText("حائل");
        }else
        if (RegionType == 10){
            holder.MOSQUE_REGION.setText("تبوك");
        }else
        if (RegionType == 11){
            holder.MOSQUE_REGION.setText("الحدود الشمالية");
        }else
        if (RegionType == 12){
            holder.MOSQUE_REGION.setText("جازان");
        }
        else if (RegionType == 13){
            holder.MOSQUE_REGION.setText("نجران");
        }

        //String CITY_VILLAGE =intent.getStringExtra("CITY_VILLAGE");
        holder.CITY_VILLAGE.setText(intent.getString("CITY_VILLAGE"));



        holder.DISTRICT.setText(intent.getString("DISTRICT"));
        holder.IMAM_NAME.setText(intent.getString("IMAM_NAME"));
        holder.STREET_NAME.setText(intent.getString("STREET_NAME"));
        holder.KHATEEB_NAME.setText(intent.getString("KHATEEB_NAME"));
        holder.MOATHEN_NAME.setText( intent.getString("MOATHEN_NAME"));
        holder.OBSERVER_NAME.setText(intent.getString("OBSERVER_NAME"));

        //holder.Masijed_Type.setText(mosquesLatLngs.get(position).getMosqueCatogery());
        // holder.MOSQUE_REGION.setText(mosquesLatLngs.get(position).getRegion());



    }

    @Override
    public int getItemCount() {
        return 1;

    }

    public class MosqueViewInformationList extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView MasijedType;
        private TextView MOSQUE_REGION;
        private TextView CITY_VILLAGE;
        private TextView DISTRICT;
        private TextView STREET_NAME;
        private TextView IMAM_NAME;
        private TextView KHATEEB_NAME;
        private TextView MOATHEN_NAME;
        private TextView OBSERVER_NAME;
        private LinearLayout linearLayout;


        public MosqueViewInformationList(View itemView) {
            super(itemView);


            MasijedType = (TextView) itemView.findViewById(R.id.MosqueType);
            DISTRICT = (TextView) itemView.findViewById(R.id.District);
            MOSQUE_REGION  = (TextView) itemView.findViewById(R.id.MosqueRegion);
            CITY_VILLAGE = (TextView) itemView.findViewById(R.id.CityVillage);
            STREET_NAME =(TextView) itemView.findViewById(R.id.StreetName);
            IMAM_NAME = (TextView) itemView.findViewById(R.id.ImamName);
            KHATEEB_NAME = (TextView) itemView.findViewById(R.id.KhateebName);
            MOATHEN_NAME = (TextView) itemView.findViewById(R.id.MoathenName);
            OBSERVER_NAME = (TextView) itemView.findViewById(R.id.ObserverName);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutInfo);


        }

        @Override
        public void onClick(View view) {

        }
    }
}