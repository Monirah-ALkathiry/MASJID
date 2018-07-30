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

public class DawaMap extends Fragment {

    private static final String TAG = "DawaMap";
    private View view;

    //location:
    private double latitude ;
    private double longitude ;


    public DawaMap() {
        super();
    }

    @SuppressLint("ValidFragment")
    public DawaMap(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dawa_map,container,false);

        Toast.makeText(getContext(), "من map "
                + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

        return view;
    }
}
