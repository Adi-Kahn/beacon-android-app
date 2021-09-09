package com.example.adi.rejsekortv1.model;


import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TravelHistory extends RealmObject {

    @PrimaryKey
    private String uID;
    private String Username;
    private String PsW;
    private int ZonesTraveled;
    private int Price;
    // Save starting and ending zone for travel
    private int TravelFrom;
    private int TravelTo;
    private String Date;

    public TravelHistory() {
    }

    public TravelHistory(String username, String psW) {
        Username = username;
        PsW = psW;
    }

    @Override
    public String toString(){
        return String.format("CheckedInZone:%s, CheckedOutZone:%s, Price:%s, ZonesTraveled:%s, Date/Time:%s", TravelFrom, TravelTo, Price,
                ZonesTraveled, Date);
    }

    public TravelHistory(String username, String psW, int zonesTraveled, int price, int travelFrom, int travelTo, String date) {
        Username = username;
        PsW = psW;
        ZonesTraveled = zonesTraveled;
        Price = price;
        //RemainingBalance = remainingBalance;
        TravelFrom = travelFrom;
        TravelTo = travelTo;
        Date = date;
        uID = UUID.randomUUID().toString();
    }

   public String getuID() {
        return uID;
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

    public int getZonesTraveled() {
        return ZonesTraveled;
    }

    public void setZonesTraveled(int zonesTraveled) {
        ZonesTraveled = zonesTraveled;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

        public int getTravelFrom() {
        return TravelFrom;
    }

    public void setTravelFrom(int travelFrom) {
        TravelFrom = travelFrom;
    }

    public int getTravelTo() {
        return TravelTo;
    }

    public void setTravelTo(int travelTo) {
        TravelTo = travelTo;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
