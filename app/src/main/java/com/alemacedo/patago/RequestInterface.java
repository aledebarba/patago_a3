package com.alemacedo.patago;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Alexandre on 13/09/2017.
 */



public interface RequestInterface {

    public static final String BASE_URL = "http://www.mocky.io/v2/";

    @GET("59bb7ea80f00007301ff85c4")
    Call<JSONResponse> getJSON();

}
