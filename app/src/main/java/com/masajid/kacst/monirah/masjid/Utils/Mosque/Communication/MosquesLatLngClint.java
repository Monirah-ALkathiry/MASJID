package com.masajid.kacst.monirah.masjid.Utils.Mosque.Communication;

import com.masajid.kacst.monirah.masjid.Utils.Mosque.MosquesLatLng;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Monirah on 7/24/2018.
 */

public interface MosquesLatLngClint {


    @GET("MobileApi/index.jsp?")
    Call<List<MosquesLatLng>>
    getMosqueLatLng(@Query("lat") String latitude,
                    @Query("lon") String longitude,
                    @Query("limit") int limit);

}
