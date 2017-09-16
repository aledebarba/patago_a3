package com.alemacedo.patago;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Alexandre on 14/09/2017.
 */

public interface DBRotinasRequestInterface {

    @GET("59baf2a90f00006505622a47")
    Call<DBRotinasJsonResponse> getJSON();

}
