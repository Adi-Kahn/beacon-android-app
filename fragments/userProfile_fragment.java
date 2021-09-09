package com.example.adi.rejsekortv1.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.adi.rejsekortv1.R;
import com.example.adi.rejsekortv1.database.UserDb;
import com.example.adi.rejsekortv1.model.User;

import io.realm.Realm;

public class userProfile_fragment extends Fragment{

    private static UserDb userDatabase;
    private static User user;
    private static TextView Name, Email, CardNumber, UserName, Balance;
    private static Button SignUp;
    String CrdN, Bal;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userprofile_fragment, container, false);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("UIdPrefsFile", Context.MODE_PRIVATE);
        String userCookie = sharedPref.getString("username", "");
        String Pswcookie = sharedPref.getString("password", "");
        //Database should be initialized for each fragement
        userDatabase = UserDb.get(getActivity());
        user = userDatabase.showUserProfile(userCookie,Pswcookie );

        Name = (TextView) view.findViewById(R.id.input_name);
        Email = (TextView) view.findViewById(R.id.input_email);
        CardNumber = (TextView) view.findViewById(R.id.input_cardnumber);
        UserName = (TextView) view.findViewById(R.id.input_Username);
        Balance = (TextView) view.findViewById(R.id.input_balance);
        SignUp = (Button) view.findViewById(R.id.signup_button);


        CrdN = Integer.toString(user.getCardnumber());
        Bal = Integer.toString(user.getBalance());

        Name.setText("NAME: " + user.getName().toString());
        Email.setText("EMAIL: " + user.getEmail().toString());
        CardNumber.setText("CARDNUMBER: " + CrdN);
        UserName.setText("USERNAME: " + user.getUsername().toString());
        Balance.setText("BALANCE: " + Bal);

        return view;
    }
}
