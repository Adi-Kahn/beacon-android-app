package com.example.adi.rejsekortv1.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.adi.rejsekortv1.R;
import com.example.adi.rejsekortv1.database.UserDb;
import com.example.adi.rejsekortv1.model.BankAccount;
import com.example.adi.rejsekortv1.model.User;

public class bankAccount_fragment extends Fragment{

    private static UserDb userDatabase;
    private static BankAccount bankAccount;
    private static TextView NameTxtv, CardNumberTxtv, BalanceTxtv;
    private static TextView Name, CardNumber, Balance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bankaccount_fragment, container, false);
        //Database should be initialized for each fragement
        userDatabase = UserDb.get(getActivity());

        Name = (TextView) view.findViewById(R.id.name_edttxt);
        CardNumber = (TextView) view.findViewById(R.id.cardnu_edttxt);
        Balance = (TextView) view.findViewById(R.id.balance_edtxt);
        NameTxtv = (TextView) view.findViewById(R.id.balance_txtv);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("UIdPrefsFile", Context.MODE_PRIVATE);
        String userCookie = sharedPref.getString("username", "");
        String Pswcookie = sharedPref.getString("password", "");
        if (userCookie != null && Pswcookie != null) {
            bankAccount = userDatabase.showBankAccount(userCookie, Pswcookie);
            String crdnum = Integer.toString(bankAccount.getCardNumber());
            String bal = Integer.toString(bankAccount.getBalance());
            Name.setText(bankAccount.getName().toString());
            CardNumber.setText(crdnum);
            Balance.setText(bal);
        }
        return view;
    }
}

