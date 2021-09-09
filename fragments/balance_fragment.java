package com.example.adi.rejsekortv1.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.adi.rejsekortv1.R;
import com.example.adi.rejsekortv1.database.UserDb;
import com.example.adi.rejsekortv1.model.User;

import io.realm.Realm;

public class balance_fragment extends Fragment {
    private static Realm realm;
    private static UserDb userDatabase;

    private static RadioButton Chbx_Hundred, Chbx_TwoHundred, Chbx_FiveHund, Chbx_Thousand;
    private static ImageButton AddBalance;
    private int Balance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.balance_fragment, container, false);
        userDatabase = UserDb.get(getActivity());
        Chbx_Hundred = (RadioButton) view.findViewById(R.id.hundred_chbx);
        Chbx_TwoHundred = (RadioButton) view.findViewById(R.id.twoH_chbx);
        Chbx_FiveHund = (RadioButton) view.findViewById(R.id.fiveH_chbx);
        Chbx_Thousand = (RadioButton) view.findViewById(R.id.thousand_chbx);
        AddBalance = (ImageButton) view.findViewById(R.id.addB_button);
        AddBalance.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        chargeBalance(v);
                    }
                }
        );
        return view;
    }

    private void chargeBalance(View view) {
        if (Chbx_Hundred.isChecked()) {
            Balance += 100;
        }
        if (Chbx_TwoHundred.isChecked()) {
            Balance += 200;
        }
        if (Chbx_FiveHund.isChecked()) {
            Balance += 500;
        }
        if (Chbx_Thousand.isChecked()) {
            Balance += 1000;
        }

        // For accessing UserName and Password saved on Login
        SharedPreferences sharedPref = getActivity().getSharedPreferences("UIdPrefsFile", Context.MODE_PRIVATE);
        String userCookie = sharedPref.getString("username", "");
        String Pswcookie = sharedPref.getString("password", "");

        if (userCookie != null && Pswcookie != null && userDatabase.addBalance(userCookie, Pswcookie, Balance)) {
            Toast.makeText(getActivity(), "Balance: " + Balance + " added!", Toast.LENGTH_LONG).show();
        }

           else Toast.makeText(getActivity(), "Not Enough Bank Balance", Toast.LENGTH_LONG).show();
        }
    }

