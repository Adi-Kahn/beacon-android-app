package com.example.adi.rejsekortv1.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.example.adi.rejsekortv1.R;
import com.example.adi.rejsekortv1.database.UserDb;
import com.example.adi.rejsekortv1.model.BankAccount;
import com.example.adi.rejsekortv1.model.TravelHistory;
import com.example.adi.rejsekortv1.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class travel_fragment extends Fragment {

    private BeaconManager beaconManager;
    private Region region;
    private static UserDb userDatabase;
    private static User user;
    private static EditText Checkin_zone, Checkin_station;
    private static TextView TraveledZones, TotalZones, Checkout_zone, Checkout_station;
    private static Button CheckinButt, CheckoutButt;
    private String checkInZone, ckin_station, CheckedInStation;
    private int CheckedInZone, CheckedOutz, totalPrice, travelBalance, Bal;
    private int zonePrice = 12;
    int minimumBalance = 60;

    List<Integer> ZoneCount = new ArrayList<Integer>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.travel_fragment, container, false);
        // Initializing Database
        userDatabase = UserDb.get(getActivity());
        SharedPreferences sharedPref = getActivity().getSharedPreferences("UIdPrefsFile", Context.MODE_PRIVATE);
        String userCookie = sharedPref.getString("username", "");
        String Pswcookie = sharedPref.getString("password", "");

        Checkin_zone = (EditText) view.findViewById(R.id.ckin_zone);
        Checkin_station = (EditText) view.findViewById(R.id.chkin_station);
        Checkout_zone = (TextView) view.findViewById(R.id.chkout_zone);
        Checkout_station = (TextView) view.findViewById(R.id.chkout_station);
        TraveledZones = (TextView) view.findViewById(R.id.travel_txtv);
        TotalZones = (TextView) view.findViewById(R.id.totalZones_txtv);

        CheckinButt = (Button) view.findViewById(R.id.chkin_button);
        CheckoutButt = (Button) view.findViewById(R.id.chkout_button);
        CheckoutButt.setVisibility(View.GONE);

        user = userDatabase.showUserProfile(userCookie,Pswcookie);
        Bal = user.getBalance();
        User user = new User();
        travelBalance = user.getBalance();
               CheckinButt.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {

                            if (checkInZone != "" && ckin_station != "" && Bal > minimumBalance) {
                                getCheckinZone(v);
                                CheckoutButt.setVisibility(View.VISIBLE);
                            } else
                                Toast.makeText(getActivity(), "Kindly Chargeup Travel Balance First!", Toast.LENGTH_LONG).show();
                        }
                    }
            );

        CheckoutButt.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        if (checkInZone != "" && ckin_station != "" && Checkout_zone != null && Checkout_station != null) {

                            if (getCheckOutZone(v)) {
                                Toast.makeText(getActivity(), "CHECKED OUT", Toast.LENGTH_LONG).show();

                                if (CheckoutButt.getVisibility() == View.VISIBLE && CheckinButt.getVisibility() == View.VISIBLE) {
                                    CheckinButt.setVisibility(View.GONE);
                                    CheckoutButt.setVisibility(View.GONE);
                                } else {
                                    CheckoutButt.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                        else Toast.makeText(getActivity(), "Kindly CheckIn First", Toast.LENGTH_LONG).show();
                    }
                }
        );

        beaconManager = new BeaconManager(getActivity());
        region = new Region("ranged region", UUID.fromString("E3B54450-AB73-4D79-85D6-519EAF0F45D9"), null, null);

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, final List<Beacon> list) {

                if (!list.isEmpty()) {
                    // Showing Major and Minor value
                    checkInZone =  String.format(""+list.get(0).getMajor());
                    ckin_station =  String.format(""+list.get(0).getMinor());

                    final Beacon nearestBeacon = list.get(0);
                    int newZone = nearestBeacon.getMajor();

                    int sameZone= 0;
                    if (newZone != sameZone) {
                        sameZone = newZone;
                        ZoneCount.add(newZone);
                    }
                    Checkin_zone.setText("Zone: " + checkInZone);
                    Checkin_station.setText("St: " + ckin_station);
                   Log.d("ITU", "Nearest places: " + list.get(0).getMajor() + list.get(0).getMinor() );
                }
            }
        });
        return view;    }

    @Override
    public void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(getActivity());
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }

    @Override
    public void onPause() {
        beaconManager.stopRanging(region);
        super.onPause();
    }

    public void getCheckinZone(View view){
        Checkout_zone.setText("Zone: "+ checkInZone);
        Checkout_station.setText("St: "+ ckin_station);
    }

    public boolean getCheckOutZone(View view){
        boolean result = false;
        // For accessing UserName and Password saved on Login
        SharedPreferences sharedPref = getActivity().getSharedPreferences("UIdPrefsFile", Context.MODE_PRIVATE);
        String userCookie = sharedPref.getString("username", "");
        String Pswcookie = sharedPref.getString("password", "");

        Checkin_zone.setText(checkInZone);
        Checkin_station.setText(ckin_station);
        String zoneinput = Checkin_zone.getText().toString();
        String stationinput = Checkin_station.getText().toString();
        CheckedInZone = Integer.parseInt(checkInZone);
        List<Integer> DistinctZones = new ArrayList<Integer>();

        for (int Zone: ZoneCount) {
            // To filter Zone and remove the duplicate zone entry
            if(!DistinctZones.contains(Zone))
            DistinctZones.add(Zone);
        }

        for (int DZone: DistinctZones) {
           System.out.println(DZone);
        }

        int zonesTraveled = DistinctZones.size();
        CheckedOutz =DistinctZones.get(DistinctZones.size()-1);
        totalPrice = zonesTraveled * zonePrice;
        TraveledZones.setText("CheckedOut Zone: " + zoneinput + " Station: " + stationinput );
        TotalZones.setText("Total Zones Traveled: " + zonesTraveled + " Price: " + totalPrice);
        String date = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        TravelHistory travelHis = new TravelHistory(userCookie, Pswcookie, zonesTraveled, totalPrice, CheckedInZone, CheckedOutz, date);

        if(userDatabase.saveTravelHis(userCookie, Pswcookie, totalPrice, travelHis)){
            Toast.makeText(getActivity(), "Zones Traveled Saved!",Toast.LENGTH_LONG).show();
            return result = true;
        }
        else{
            return result;
        }
    }

}





