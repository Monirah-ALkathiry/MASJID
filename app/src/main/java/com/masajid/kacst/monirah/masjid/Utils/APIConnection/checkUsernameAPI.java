package com.masajid.kacst.monirah.masjid.Utils.APIConnection;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Monirah on 7/24/2018.
 */

public interface checkUsernameAPI {

    @FormUrlEncoded
    @POST("mosques/users.jsp")
    Call<JsonObject> check(
            @Field("option")int optioncheck,
            @Field("username")String username);
}
