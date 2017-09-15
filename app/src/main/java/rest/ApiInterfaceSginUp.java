package rest;

import model.ModelSignUp;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by abdallah on 10/09/17.
 */

public interface ApiInterfaceSginUp {
    @GET("user/register")
    Call<ModelSignUp> getAnsweres(@Query("name") String name , @Query("phone") String phone);

}
