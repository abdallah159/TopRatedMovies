package iti.abdallah.logintest.mvplogin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import iti.abdallah.logintest.Home;
import iti.abdallah.logintest.R;
import iti.abdallah.logintest.SignUp;
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





            }
        });
    }
}
