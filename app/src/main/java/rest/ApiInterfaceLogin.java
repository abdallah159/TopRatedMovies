package rest;

import model.ModelLogIn;
import model.ModelSignUp;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by abdallah on 10/09/17.
 */

public interface ApiInterfaceLogin {
    @GET("user/findUser")
    Call<ModelLogIn> getAnsweres(@Query("phone") String phone);

}
