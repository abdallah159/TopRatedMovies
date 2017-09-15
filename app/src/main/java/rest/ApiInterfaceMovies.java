package rest;

import java.util.ArrayList;

import model.Movies;
import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Created by abdallah on 08/09/17.
 */

public interface ApiInterfaceMovies {
    @GET("movies.json")
    Call<ArrayList<Movies>> getTopRatedMovies();



//    @GET("movie/{id}")
//    Call<MovieResponse>getMovieDetails(@Path("id") int id,@Query("api_key") String apiKey);


}
