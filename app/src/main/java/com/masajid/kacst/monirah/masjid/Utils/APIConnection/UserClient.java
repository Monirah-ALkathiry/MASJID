package com.masajid.kacst.monirah.masjid.Utils.APIConnection;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Monirah on 7/24/2018.
 */

public interface UserClient {
    @FormUrlEncoded
    @POST("mosques/users.jsp")
    Call<JsonObject> login(
            @Field("option")int option,
            @Field("username")String username,
            @Field("password")String password
    );

    @GET("getuserinfo")
    Call<ResponseBody> getinfo(@Header("Authorization") String authToken);

}
