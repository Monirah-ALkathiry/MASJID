package com.masajid.kacst.monirah.masjid.Utils.APIConnection;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Monirah on 7/24/2018.
 */

public interface LoginRequest {
    @FormUrlEncoded
    @Headers({
            "Content-type: application/x-www-form-urlencoded",
            "APIsKey:M0i@#@pI#2oI7#GiS",
            "userType:1"
    })
    @POST("token")
    Call<JsonObject> login(
            @Field("grant_type")String grant_type,
            @Field("username")String username,
            @Field("password")String password
    );
}
