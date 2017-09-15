package iti.abdallah.logintest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import iti.abdallah.logintest.Adapter.MoviesAdapter;
import iti.abdallah.logintest.Adapter.RecyclerItemClickListener;
import model.Movies;
import rest.ApiClientMovies;
import rest.ApiInterfaceMovies;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesList extends AppCompatActivity {

    SQLiteDatabase moviesDB1;
    public int MOVIES_COUNT;
    ArrayList<Movies> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_list);


        ApiInterfaceMovies apiService = ApiClientMovies.getClient().create(ApiInterfaceMovies.class);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);

        try {
            moviesDB1 = this.openOrCreateDatabase("MoviesDB1", MODE_PRIVATE, null);
            moviesDB1.execSQL("CREATE TABLE IF NOT EXISTS MoviesList (title VARCHAR,rating DOUBLE,releasYear VARCHAR,image VARCHAR)");


        } catch (Exception e) {

            Log.e("Error", e.getMessage().toString());

        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));


        Call<ArrayList<Movies>> call = apiService.getTopRatedMovies();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // ON CLICK ITEAM HANDLE
                        Intent movieDetails = new Intent(getApplicationContext(), MovieDetails.class);
                        movieDetails.putExtra("MOVIE_NUMBER", position);
                        startActivity(movieDetails);
//                        Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_LONG).show();
                    }
                })
        );
        progressDialog.show();
        call.enqueue(new Callback<ArrayList<Movies>>() {

            @Override
            public void onResponse(Call<ArrayList<Movies>> call, Response<ArrayList<Movies>> response) {
                 movies = response.body();
                MOVIES_COUNT=movies.size();


                //Save in DateBase


                if (response.isSuccessful()) {


                    recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.movie_cell, getApplicationContext()));
                    progressDialog.dismiss();



                    String DELETE_QUERY ="DELETE FROM MoviesList";
                    moviesDB1.execSQL(DELETE_QUERY);

                    for (int i = 0; i < movies.size(); i++) {


                        String INSERT_QUERY = "INSERT INTO MoviesList VALUES (\"" + movies.get(i).getTitle() + "\"," + movies.get(i).getRating() + ",'" + movies.get(i).getReleaseYear() + "','" + movies.get(i).getImage() + "')";
                        moviesDB1.execSQL(INSERT_QUERY);

                        Log.v("+++++", INSERT_QUERY);

                    }

                    //Toast.makeText(getApplicationContext(), "Number : " + movies.size(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed To Connect Server.. " + response.code(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Movies>> call, Throwable t) {

                ArrayList<Movies> moviesOffline = new ArrayList<Movies>();


                Cursor c = moviesDB1.rawQuery("SELECT * FROM MoviesList", null);


                c.moveToFirst();

                
                int titileIndex = c.getColumnIndex("title");
                int ratingIndex = c.getColumnIndex("rating");
                int realsYearIndex = c.getColumnIndex("releasYear");
                int imageIndex = c.getColumnIndex("image");


                while (c.moveToNext()) {
                   // moviesOffline.add(new Movies(c.getString(titileIndex)));


                    moviesOffline.add(new Movies(c.getString(titileIndex),c.getString(imageIndex),c.getDouble(ratingIndex),c.getString(realsYearIndex),null));
                }
                progressDialog.dismiss();

                recyclerView.setAdapter(new MoviesAdapter(moviesOffline, R.layout.movie_cell, getApplicationContext()));


                //Toast.makeText(getApplicationContext(), "Unable To Connect..", Toast.LENGTH_LONG).show();


            }
        });


    }
}
