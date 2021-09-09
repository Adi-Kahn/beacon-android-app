package com.example.adi.rejsekortv1;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adi.rejsekortv1.database.UserDb;
import com.example.adi.rejsekortv1.model.User;

import io.realm.Realm;

public class LoginActivity extends AppCompatActivity  {

    private static Realm realm;
    private static UserDb userDatabase;

    private static EditText Username;
    private static EditText PsW;
    private static Button Login, signUp, ListRealm;
    private static TextView List;
    SharedPreferences mSharedPreferences;
    public static final String UID_PREFS_NAME = "UIdPrefsFile";
    public static String User_Name_Key = "username";
    public static String Password_Key = "password";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        userDatabase = UserDb.get(this);

        Username = (EditText) findViewById(R.id.user_input);
        PsW = (EditText) findViewById(R.id.psw_input);
        Login = (Button) findViewById(R.id.login_button);
        signUp = (Button) findViewById(R.id.signup_button);


        signUp.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), SignupActivity.class);
                        startActivity(i);
                    }
                }
        );

        Login.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        if ((Username.getText().length() > 0)
                                && (PsW.getText().length() > 0)){

                            SharedPreferences sharedPref = getSharedPreferences("UIdPrefsFile", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("username", Username.getText().toString().trim());
                            editor.putString("password", PsW.getText().toString().trim());
                            editor.apply();

                           // User user = new User(Username.getText().toString().trim(),PsW.getText().toString().trim());

                            User user = new User();
                            user.setUsername(Username.getText().toString().trim());
                            user.setPsW(PsW.getText().toString().trim());
                            if(userDatabase.LoginUserPr(user)){
                                Intent i = new Intent(v.getContext(), NavigationActivity.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(v.getContext(), "Please Enter correct username and password !",Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                }
        );


    }

}



