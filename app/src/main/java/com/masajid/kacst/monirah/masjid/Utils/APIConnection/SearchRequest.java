package com.masajid.kacst.monirah.masjid.Utils.APIConnection;

import com.masajid.kacst.monirah.masjid.Utils.Mosque.MosquesLatLng;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Monirah on 7/24/2018.
 */

public interface SearchRequest {
    @GET("MobileApi/index.jsp?")
    Call<List<MosquesLatLng>>

    getMosqueList(@Query("lat") String latitude , @Query("lon") String longitude,
                  @QueryMap(encoded=true) Map<String, Object> map,
                  @Query("limit") int limit);
}
