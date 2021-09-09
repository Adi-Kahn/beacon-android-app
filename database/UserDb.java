package com.example.adi.rejsekortv1.database;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.adi.rejsekortv1.model.BankAccount;
import com.example.adi.rejsekortv1.model.TravelHistory;
import com.example.adi.rejsekortv1.model.User;

import java.sql.SQLOutput;
import java.util.List;
import java.util.UUID;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

public class UserDb extends AppCompatActivity {
    private static Realm realm;
    private static UserDb sUserDb;
    public static SharedPreferences mPreferences;
    public static final String UID_PREFS_NAME = "UIdPrefsFile";

    public static UserDb get(Context context) {
        if (sUserDb == null) {
            realm = Realm.getDefaultInstance();
            sUserDb = new UserDb(context);

           }
        return sUserDb;
    }

    public void saveUser(User user, BankAccount bankacc) {
        final User uprofile = user;
        final BankAccount bank = bankacc;
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(uprofile);
                realm.copyToRealm(bank);
            }});
    }

    public User showUserProfile(String username, String pass) {
        User user = new User();
        User rows = realm.where(User.class).equalTo("Username", username).equalTo("PsW", pass).findFirst();
        if (rows != null){
            try {
                user.setName(rows.getName());
                user.setEmail(rows.getEmail());
                user.setCardnumber(rows.getCardnumber());
                user.setUsername(rows.getUsername());
                user.setPsW(rows.getPsW());
                user.setBalance(rows.getBalance());
                return user;
            }
            catch (RealmException ex) {
                System.out.println("Realm Error!" + ex.toString());
            }
        }
        else  Log.v("database", ">>>>>>>>>>Problem<<<<");
        return rows;
    }

    public boolean LoginUserPr(User SignIn){
        final User login = SignIn;
        boolean exist= false;

        RealmResults<User> rows=realm.where(User.class).equalTo("Username", login.getUsername().trim()).equalTo("PsW", login.getPsW().trim()).findAll();
        if (rows.size() > 0) {
            Log.v("database", ">>>>>>>>>>stored successfully<<<<");
            exist = true;
        }
        return exist;
    }

    public BankAccount showBankAccount(String username, String pass){
        BankAccount accountBank = new BankAccount();
        BankAccount rows = realm.where(BankAccount.class).equalTo("Username", username).equalTo("PsW", pass).findFirst();
        if (rows != null){
            //accountBank = rows;
            accountBank.setName(rows.getName());
            accountBank.setEmail(rows.getEmail());
            accountBank.setUsername(rows.getUsername());
            accountBank.setPsW(rows.getPsW());
            accountBank.setCardNumber(rows.getCardNumber());
            accountBank.setBalance(rows.getBalance());
            return accountBank;
        }
        else return null;
    }

    //Method for Balance Fragment and adding Balance to User's Travel Account
    public boolean addBalance(String us, String psw, int balance){
        User user = new User();
        BankAccount bankaccount = new BankAccount();
        int result, travelBalance, bankbalance, NewBankBalanace  = 0;
        boolean added = false;

        User rows =realm.where(User.class).equalTo("Username", us).equalTo("PsW", psw).findFirst();
        BankAccount Baccount = realm.where(BankAccount.class).equalTo("Username", us).findFirst();

            if (rows != null && Baccount != null) {
                            // take user balance for travel
                            travelBalance = rows.getBalance();
                            user.setBalance(travelBalance);
                            // take user balance from bank
                            bankbalance = Baccount.getBalance();
                            bankaccount.setBalance(bankbalance);

                    if (balance<= bankbalance) {
                        // Add money to user travel account
                         result = travelBalance + balance;
                        // Withdraw money from Bank account
                         NewBankBalanace = bankbalance - balance;
                        realm.beginTransaction();
                        // Update Balance in User and Bank Account
                        rows.setBalance(result);
                        Baccount.setBalance(NewBankBalanace);
                        realm.commitTransaction();
                        Log.v("database", ">>>>>>>>>>stored successfully<<<<");
                        added = true;
                    }
                }
                else Log.v("User", ">>>>>>>>>>Not match<<<<");
                return added;
            }

    // Method for saving travel history of user, used with in travel_fragment
    public boolean saveTravelHis(String user, String pw, int zp, TravelHistory travelH)
    {
        TravelHistory travelHis = travelH;
        User userModel = new User();
        int travelBalance, result = 0;
        boolean update= false;

       // TravelHistory traveled =realm.where(TravelHistory.class).equalTo("Username", user).equalTo("PsW", pw).findFirst();
        User rows =realm.where(User.class).equalTo("Username", user).equalTo("PsW", pw).findFirst();
        if (rows != null ) {
            travelBalance = rows.getBalance();
              if (zp < travelBalance ) {
                result = travelBalance - zp;
                realm.beginTransaction();
                realm.copyToRealm(travelHis);
                //travelHis.setDate(date);
                rows.setBalance(result);
                realm.commitTransaction();
                update = true;
                Log.v("Zones", ">>>>>>>>>>stored successfully<<<<");
            }
            else return update;
        }
        return update;
    }

    // Method for showing list of travel history for specific user
    public List<TravelHistory> showTravelHistory(String username, String pass) {
        TravelHistory travelhis = new TravelHistory();
        RealmResults<TravelHistory> result = realm.where(TravelHistory.class).equalTo("Username", username).equalTo("PsW", pass).findAll();
        if (result.size() > 0){
            Log.v("List", ">>>>>>>>>>Views<<<<");
            return result;
        }
        else
            Log.v("Not", ">>>>>>>>>>Accessible<<<<");
            return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public UserDb(Context context) {

    }
}


