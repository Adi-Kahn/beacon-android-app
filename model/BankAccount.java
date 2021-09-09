package com.example.adi.rejsekortv1.model;


import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BankAccount extends RealmObject {
    @PrimaryKey
    private String uID;
    private String Name;
    private String Email;
    private String Username;
    private String PsW;
    private int Balance;
    private int CardNumber;


    public BankAccount() {
    }

    public BankAccount(String userBaccount, String psW) {
        Username = userBaccount;
        PsW = psW;

    }

    public BankAccount(String name, String email, String username, String psW, int balance, int cardNumber) {
        Name = name;
        Email = email;
        Username = username;
        PsW = psW;
        Balance = balance;
        CardNumber = cardNumber;
        uID = UUID.randomUUID().toString();

    }

    public String getuID() {
        return uID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPsW() {
        return PsW;
    }

    public void setPsW(String psW) {
        PsW = psW;
    }

    public int getBalance() {
        return Balance;
    }

    public void setBalance(int balance) {
        Balance = balance;
    }

    public int getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(int cardNumber) {
        CardNumber = cardNumber;
    }

}
