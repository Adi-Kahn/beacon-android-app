package com.example.adi.rejsekortv1.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
    private String Name;
    private String Email;
    private int Cardnumber;
    private String Username;
    private String PsW;
    @PrimaryKey
    private String uID;
    private int Balance;

    public User() {
    }

    public User(String username, String psW, int balance) {
        Username = username;
        PsW = psW;
        Balance = balance;
    }

    public User(String username, String psW) {
        Username = username;
        PsW = psW;
    }

    //Constructor to be used for signup_fragment
    public User(String name, String email, int Cardnumber, String username, String psW, int balance) {
        Name = name;
        Email = email;
        this.Cardnumber = Cardnumber;
        Username = username;
        PsW = psW;
        Balance = balance;
        uID = UUID.randomUUID().toString();
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

    public int getCardnumber() {
        return Cardnumber;
    }

    public void setCardnumber(int cardnumber) {
        this.Cardnumber = cardnumber;
    }

    public String getuID() {
        return uID;
    }

    public int getBalance() {
        return Balance;
    }

    public void setBalance(int balance) {
        Balance = balance;
    }
}
