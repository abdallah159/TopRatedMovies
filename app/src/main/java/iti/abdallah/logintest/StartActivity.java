package iti.abdallah.logintest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import iti.abdallah.logintest.mvplogin.LogIn;

public class StartActivity extends Activity {

    SharedPreferences SP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        final Button MainSignIn = (Button) findViewById(R.id.mainSignIn);
        TextView MainSignUp = (TextView) findViewById(R.id.mainSignUp);


        SP = getApplicationContext().getSharedPreferences("LogPref", MODE_PRIVATE);
        MainSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Boolean LogInFlagMain = SP.getBoolean("LogKey", false);
                if (LogInFlagMain == true) {

                    Intent i = new Intent(StartActivity.this, Home.class);
                    startActivity(i);
                    Toast.makeText(getApplication(), "Welcome Back :)", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(StartActivity.this, LogIn.class);
                    startActivity(i);
                }

            }
        });
        MainSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(StartActivity.this, SignUp.class);
                startActivity(i);
            }
        });

    }


}
