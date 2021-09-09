
package com.example.adi.rejsekortv1.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.adi.rejsekortv1.R;
import com.example.adi.rejsekortv1.database.UserDb;
import com.example.adi.rejsekortv1.model.TravelHistory;

import io.realm.OrderedRealmCollection;

public class travelHistory_fragment extends Fragment {

    private static UserDb userDatabase;
    ListView ListTravel;
    private static ArrayAdapter listAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.travelhistory_fragment, container, false);

        //Database should be initialized for each fragement
        userDatabase = UserDb.get(getActivity());


        SharedPreferences sharedPref = getActivity().getSharedPreferences("UIdPrefsFile", Context.MODE_PRIVATE);
        String userCookie = sharedPref.getString("username", "");
        String Pswcookie = sharedPref.getString("password", "");

        ListTravel = (ListView) view.findViewById(R.id.travel_listview);
        listAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, userDatabase.showTravelHistory(userCookie, Pswcookie));
        ListTravel.setAdapter(listAdapter);
        return view;
    }


}

