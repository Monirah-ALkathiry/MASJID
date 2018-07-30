package com.masajid.kacst.monirah.masjid.Utils.Dawa;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.masajid.kacst.monirah.masjid.R;

/**
 * Created by Monirah on 7/24/2018.
 */

public class DawaList extends Fragment {

    public static final String TAG = "DawaLIST";
    private View view;

    //location:
    private double latitude ;
    private double longitude ;


    public DawaList(){
        super();
    }

    @SuppressLint("ValidFragment")
    public DawaList(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dawa_list,container,false);
        Toast.makeText(getContext(), "من القائمة "
                + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        return view;
    }
}
