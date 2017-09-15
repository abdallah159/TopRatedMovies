package iti.abdallah.logintest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import model.ModelLogIn;
import rest.ApiClientLogin;
import rest.ApiInterfaceLogin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends Activity {

    TextView username;
    TextView userphone;
    TextView userid ;
    SharedPreferences SP;
    String phone="" ;
    ProgressDialog profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username=(TextView)findViewById(R.id.username);
        userphone=(TextView)findViewById(R.id.userphone);
        userid=(TextView)findViewById(R.id.userid);

        SP = getApplicationContext().getSharedPreferences("LogPref", MODE_PRIVATE);
        phone=SP.getString("PHONE",null);




        ApiInterfaceLogin apiLohIn = ApiClientLogin.getClient().create(ApiInterfaceLogin.class);
        Call<ModelLogIn> call = apiLohIn.getAnsweres(phone);
         profile=new ProgressDialog(this);
        profile.setMessage("Loading Your Data");
        profile.show();
        call.enqueue(new Callback<ModelLogIn>() {


            @Override
            public void onResponse(Call<ModelLogIn> call, Response<ModelLogIn> response) {
                ModelLogIn data =response.body();
                LoginAnswer answer= (LoginAnswer) data.getResult();

                if(response.isSuccessful()){

                    username.setText(answer.getName());
                    userphone.setText(phone);
                    userid.setText(Integer.toString(answer.getId()));
                    profile.dismiss();


                }


            }

            @Override
            public void onFailure(Call<ModelLogIn> call, Throwable t) {


                profile.dismiss();
                Toast.makeText(getApplicationContext(),"Please Connect to Internet",Toast.LENGTH_LONG).show();


            }
        });




    }
}
