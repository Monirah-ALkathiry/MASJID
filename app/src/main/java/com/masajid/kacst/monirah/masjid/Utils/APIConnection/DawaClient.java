package com.masajid.kacst.monirah.masjid.Utils.APIConnection;

import com.masajid.kacst.monirah.masjid.Utils.Dawa.DawaLatLng;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Monirah on 7/24/2018.
 */

public interface DawaClient {
    @GET("MobileApi/DawaActivity.jsp?")
    Call<List<DawaLatLng>>
    getDawaLatLng(@Query("lat") String locYCoord , @Query("lon") String locXCoord , @Query("limit") int limit);
}
