package com.masajid.kacst.monirah.masjid.Utils.APIConnection;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Monirah on 7/24/2018.
 */

public class ApiRetrofitClint {

    public static final String BASE_URL="http://gis.moia.gov.sa/";
    public static Retrofit retrofit = null;

    public static Retrofit getApiRetrofitClint(){

        if (retrofit == null){

            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
