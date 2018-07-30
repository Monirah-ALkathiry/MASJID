package com.masajid.kacst.monirah.masjid.Utils.Mosque;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.masajid.kacst.monirah.masjid.R;

/**
 * Created by Monirah on 7/25/2018.
 */

public class MosqueInformationActivity extends Fragment {

    public static final String TAG = "MosqueInformationActivity";

    //Variables  :
    private View view;
    private ViewPager mViewPager;
    private MosqueInformationAdapter mosqueInformationAdapter;
    private TabLayout tabLayout;

    //Adapter------------------------
    private TextView MasijedName ;

    Bundle intent;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_mosque_information,container,false);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Set Up the view Pager with Section Adapter:
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPager.setCurrentItem(0);


        //TabLayout
         tabLayout =(TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        MasijedName = (TextView) view.findViewById(R.id.MasijedName);


//Get Data From Mosque Adapter:
        intent = getArguments();

        if(intent != null) {
            MasijedName.setText(intent.getString("MOSQUE_NAME"));

            String data = intent.getString("MOSQUE_NAME");
            Toast.makeText(getContext(), data, Toast.LENGTH_LONG).show();

            setUpViewPager(mViewPager);

        }else {
            Toast.makeText(getContext(), "Null", Toast.LENGTH_LONG).show();

        }


    }

    private void setUpViewPager(ViewPager viewPager ){

        MosqueInformationAdapter adapter = new MosqueInformationAdapter(getChildFragmentManager());

        adapter.AddFragment(new MosqueInformation(intent), "عام " , getContext());
        adapter.AddFragment(new MosqueImage(intent),"الصور" ,getContext());
        adapter.AddFragment(new MosqueNote(),"ملاحظات" , getContext());

        viewPager.setAdapter(adapter);

    }
}
