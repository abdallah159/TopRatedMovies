package iti.abdallah.logintest;

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

import iti.abdallah.logintest.mvplogin.LogIn;
import model.ModelSignUp;
import rest.ApiClientSginUp;
import rest.ApiInterfaceSginUp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends Activity implements View.OnClickListener {

    EditText name ;
    EditText phone;
    Button reg;
    TextView sginin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name=(EditText)findViewById(R.id.SignUpName);
        phone=(EditText)findViewById(R.id.SignUpPhone);
        sginin=(TextView)findViewById(R.id.sginin);
        sginin.setOnClickListener(this);
        reg=(Button)findViewById(R.id.SignUoNow);
        reg.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {


        if(view.getId()==R.id.sginin){
            Intent i = new Intent(SignUp.this,LogIn.class);
            SignUp.this.finish();
            startActivity(i);
        }

        //Get User Data from EditText..
        String userName=name.getText().toString();
        String userPhone=phone.getText().toString();

        if(userName.equals("")||userPhone.equals("")){
            Toast.makeText(getApplicationContext(),"Please Fill All Fields.",Toast.LENGTH_LONG).show();
        }

        else {

            try {
                int num = Integer.parseInt(userPhone);

                ApiInterfaceSginUp apiService =
                        ApiClientSginUp.getClient().create(ApiInterfaceSginUp.class);

                final ProgressDialog load=new ProgressDialog(this);
                load.setMessage("Signing Up..");


                if(view.getId()==R.id.SignUoNow) {
                    Call<ModelSignUp> call = apiService.getAnsweres(userName, userPhone);
                    load.show();
                    call.enqueue(new Callback<ModelSignUp>() {
                        @Override
                        public void onResponse(Call<ModelSignUp> call, Response<ModelSignUp> response) {
                            //Get Response from json..
                            String status = response.body().getStatus();
                            String result = response.body().getResult();
                            if (response.isSuccessful()) {
                                load.dismiss();
                                if (status.equals("SUCCESS")) {


                                    SharedPreferences SP = getApplicationContext().getSharedPreferences("LogPref", MODE_PRIVATE);
                                    SharedPreferences.Editor edit = SP.edit();
                                    edit.putBoolean("LogKey", true);
                                    edit.commit();
                                    Toast.makeText(getApplicationContext(), "Successfuly Log In.", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(SignUp.this, LogIn.class);
                                    SignUp.this.finish();
                                    startActivity(i);

                                } else if (status.equals("FAILING")) {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                    name.setText("");
                                    phone.setText("");
                                }
                            }


                        }

                        @Override
                        public void onFailure(Call<ModelSignUp> call, Throwable t) {
                            load.dismiss();
                            Toast.makeText(getApplicationContext(), "No INTERNET..", Toast.LENGTH_LONG).show();


                        }
                    });

                }



            } catch (NumberFormatException e) {

                Toast.makeText(getApplicationContext(),"Your Phone Must be Number.",Toast.LENGTH_LONG).show();
            }



        }



    }
}
