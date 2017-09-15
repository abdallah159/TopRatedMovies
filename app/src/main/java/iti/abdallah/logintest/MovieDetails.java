package iti.abdallah.logintest;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import model.Movies;
import rest.ApiClientMovies;
import rest.ApiInterfaceMovies;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetails extends AppCompatActivity {

    SQLiteDatabase moviesDB2;

    ImageView poster;
    ImageView profile;
    Integer MOVIE_NUMBER;
    TextView movieName;
    TextView year;
    TextView rate;
    TextView genre;

    RatingBar rt ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);
        poster = (ImageView) findViewById(R.id.poster);
        profile = (ImageView) findViewById(R.id.profile);
        movieName = (TextView) findViewById(R.id.movie_name);
        year = (TextView) findViewById(R.id.year);
        rate = (TextView) findViewById(R.id.rate);
        genre = (TextView) findViewById(R.id.genre);

        rt=(RatingBar)findViewById(R.id.ratingBar);







        try {
            moviesDB2 = this.openOrCreateDatabase("MoviesDB2", MODE_PRIVATE, null);
            moviesDB2.execSQL("CREATE TABLE IF NOT EXISTS MoviesList (title VARCHAR,rating DOUBLE,releasYear VARCHAR,image VARCHAR)");


        }catch (Exception e){

        }


        ApiInterfaceMovies apiService = ApiClientMovies.getClient().create(ApiInterfaceMovies.class);


        Call<ArrayList<Movies>> call = apiService.getTopRatedMovies();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        MOVIE_NUMBER = getIntent().getIntExtra("MOVIE_NUMBER", 0);
        progressDialog.show();

        call.enqueue(new Callback<ArrayList<Movies>>() {



            @Override
            public void onResponse(Call<ArrayList<Movies>> call, Response<ArrayList<Movies>> response) {
                ArrayList<Movies> movies = response.body();


                if (response.isSuccessful())
                {

                    String DELETE_QUERY ="DELETE FROM MoviesList";
                    moviesDB2.execSQL(DELETE_QUERY);

                    for (int i = 0; i < movies.size(); i++) {


                        String INSERT_QUERY = "INSERT INTO MoviesList VALUES (\"" + movies.get(i).getTitle() + "\"," + movies.get(i).getRating() + ",'" + movies.get(i).getReleaseYear() + "','" + movies.get(i).getImage() + "')";
                        moviesDB2.execSQL(INSERT_QUERY);

                        Log.v("+++++", INSERT_QUERY);

                    }

                    //Set Movie Profile Data....

                    Glide.with(getApplicationContext())
                            .load(movies.get(MOVIE_NUMBER).getImage())
                            .placeholder(R.drawable.placeholder)
                            .into(poster);

                    Glide.with(getApplicationContext())
                            .load(movies.get(MOVIE_NUMBER).getImage())
                            .placeholder(R.drawable.placeholder)
                            .into(profile);
                    rt.setIsIndicator(true);
                    rt.setRating(movies.get(MOVIE_NUMBER).getRating().floatValue());

                    movieName.setText(movies.get(MOVIE_NUMBER).getTitle());
                    year.setText("Release Year : " +movies.get(MOVIE_NUMBER).getReleaseYear());
                    rate.setText("Rating :  " + movies.get(MOVIE_NUMBER).getRating());

                    //Toast.makeText(getApplicationContext(),movies.get(MOVIE_NUMBER).getGenre().get(0).toString(),Toast.LENGTH_LONG).show();

                    if(MOVIE_NUMBER!=13) {

                        genre.setText("Genre: " + movies.get(MOVIE_NUMBER).getGenre().get(0).toString() + "," + movies.get(MOVIE_NUMBER).getGenre().get(1).toString());

                    }else if (MOVIE_NUMBER==13){

                        genre.setText("Genre: " + movies.get(MOVIE_NUMBER).getGenre().get(0).toString());

                    }
                    progressDialog.dismiss();

                    //Cashing data here to reuse it when it's offline






                    //Toast.makeText(getApplicationContext(), "Number : " + movies.size(), Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Failed To Connect Server.. " + response.code(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Movies>> call, Throwable t)
            {

                progressDialog.dismiss();
                ArrayList<Movies> moviesOffline = new ArrayList<Movies>();


                Cursor c = moviesDB2.rawQuery("SELECT * FROM MoviesList", null);


                c.moveToFirst();

                int titileIndex = c.getColumnIndex("title");
                int ratingIndex = c.getColumnIndex("rating");
                int realsYearIndex = c.getColumnIndex("releasYear");
                int imageIndex = c.getColumnIndex("image");


                while (c.moveToNext()) {

                    moviesOffline.add(new Movies(c.getString(titileIndex),c.getString(imageIndex),c.getDouble(ratingIndex),c.getString(realsYearIndex),null));
                }

                Glide.with(getApplicationContext())
                        .load(moviesOffline.get(MOVIE_NUMBER).getImage())
                        .placeholder(R.drawable.placeholder)
                        .into(poster);

                Glide.with(getApplicationContext())
                        .load(moviesOffline.get(MOVIE_NUMBER).getImage())
                        .placeholder(R.drawable.placeholder)
                        .into(profile);

                movieName.setText(moviesOffline.get(MOVIE_NUMBER).getTitle());
                year.setText("Release Year : " +moviesOffline.get(MOVIE_NUMBER).getReleaseYear());
                rate.setText("Rating :  " + moviesOffline.get(MOVIE_NUMBER).getRating());
                rt.setRating(moviesOffline.get(MOVIE_NUMBER).getRating().floatValue());


                //Toast.makeText(getApplicationContext(),movies.get(MOVIE_NUMBER).getGenre().get(0).toString(),Toast.LENGTH_LONG).show();






                  // Toast.makeText(getApplicationContext(), "Unable To Connect..", Toast.LENGTH_LONG).show();
            }
        });



    }
    }

