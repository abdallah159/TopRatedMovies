package rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abdallah on 08/09/17.
 */

public class ApiClientMovies {
    public static final String BASE_URL="https://api.androidhive.info/json/";
    private static Retrofit retrofit=null;

    public static Retrofit getClient(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL).
                            addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retrofit;
    }
}
