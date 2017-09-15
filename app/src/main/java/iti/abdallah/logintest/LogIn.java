package iti.abdallah.logintest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import model.ModelLogIn;
import rest.ApiClientLogin;
import rest.ApiInterfaceLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LogIn extends Activity {


    ProgressDialog p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        p = new ProgressDialog(this);
        p.setMessage("Wait To Log In");
        final EditText name = (EditText)findViewById(R.id.LogInName);
        final Button LogIn = (Button) findViewById(R.id.LogInNow);
        TextView signUp = (TextView)findViewById(R.id.LogInSignUp);

        signUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogIn.this,SignUp.class);
                LogIn.this.finish();
                startActivity(i);


            }
        });


        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String phone = name.getText().toString();




                if(phone.equals("")){
                    Toast.makeText(getApplicationContext(),"Please Enter Your Phone.",Toast.LENGTH_LONG).show();
                }

                else  {


                    try {
                        int num = Integer.parseInt(phone);

                        ApiInterfaceLogin apiLohIn = ApiClientLogin.getClient().create(ApiInterfaceLogin.class);
                        Call<ModelLogIn> call = apiLohIn.getAnsweres(phone);
                        p.show();


                        call.enqueue(new Callback<ModelLogIn>() {
                            @Override
                            public void onResponse(Call<ModelLogIn> call, Response<ModelLogIn> response) {
                                String status = response.body().getStatus();
                                p.dismiss();

                                if (response.isSuccessful()) {
                                    if (status.equals("SUCCESS")) {


                                        SharedPreferences SP = getApplicationContext().getSharedPreferences("LogPref", MODE_PRIVATE);
                                        SharedPreferences.Editor edit = SP.edit();
                                        edit.putBoolean("LogKey", true);
                                        edit.putString("PHONE", phone);
                                        edit.commit();
                                        Toast.makeText(getApplicationContext(), "Welcome :)", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(LogIn.this, Home.class);
                                        LogIn.this.finish();
                                        startActivity(i);

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Faild to log In", Toast.LENGTH_LONG).show();
                                        name.setText("");
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ModelLogIn> call, Throwable t) {
                                p.dismiss();
                                Toast.makeText(getApplicationContext(), "No INTERNET", Toast.LENGTH_LONG).show();

                            }
                        });



                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(),"You Can Enter numbers only.",Toast.LENGTH_LONG).show();
                    }



                }


            }
        });
    }
}
