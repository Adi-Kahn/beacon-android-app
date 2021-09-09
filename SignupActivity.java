package com.example.adi.rejsekortv1;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adi.rejsekortv1.database.UserDb;
import com.example.adi.rejsekortv1.model.BankAccount;
import com.example.adi.rejsekortv1.model.User;

public class SignupActivity extends AppCompatActivity {

    private static EditText Name, Email, CardNumber, UserName, PsW;
    private static Button SignUp;
    private static UserDb userDatabase;
    int travelbalance = 0;
    int bankBalance = 6000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        userDatabase = UserDb.get(this);

        Name = (EditText) findViewById(R.id.input_name);
        Email = (EditText) findViewById(R.id.input_email);
        CardNumber = (EditText) findViewById(R.id.input_cardnumber);
        UserName = (EditText) findViewById(R.id.input_Username);
        PsW = (EditText) findViewById(R.id.input_password);
        SignUp = (Button) findViewById(R.id.signup_button);

        SignUp.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){

                        if ((Name.getText().length() > 0) && (Email.getText().length() > 0)
                                && (CardNumber.getText().length() > 0) && (UserName.getText().length() > 0)
                                && (PsW.getText().length() > 0)) {

                            // For Creating new User
                            User user = new User(
                                    Name.getText().toString().trim(),
                                    Email.getText().toString().trim(),Integer.parseInt(CardNumber.getText().toString().trim()),
                                    UserName.getText().toString().trim(),PsW.getText().toString().trim(),travelbalance);

                            // Saving Bank account for the same user on Signup
                            BankAccount bankacc = new BankAccount(
                                    Name.getText().toString().trim(), Email.getText().toString().trim(),
                                    UserName.getText().toString().trim(),PsW.getText().toString().trim(),
                                    bankBalance, Integer.parseInt(CardNumber.getText().toString().trim())
                                    );

                            SharedPreferences sharedPref = getSharedPreferences("UIdPrefsFile", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("username", UserName.getText().toString().trim());
                            editor.putString("password", PsW.getText().toString().trim());
                            editor.apply();

                            userDatabase.saveUser(user, bankacc);
                            Toast.makeText(v.getContext(), "User Saved!", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(v.getContext(), NavigationActivity.class);
                            startActivity(i);
                        }
                        else Toast.makeText(v.getContext(), "Please Enter all fields !",
                                Toast.LENGTH_LONG).show();
                    }
    });
    }
}