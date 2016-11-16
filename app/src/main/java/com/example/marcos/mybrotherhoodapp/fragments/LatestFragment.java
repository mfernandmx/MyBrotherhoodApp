package com.example.marcos.mybrotherhoodapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marcos.mybrotherhoodapp.items.LatestItem;
import com.example.marcos.mybrotherhoodapp.R;
import com.example.marcos.mybrotherhoodapp.adapters.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcos on 10/11/2016.
 */

public class LatestFragment extends Fragment {

    private List<String> headlines;
    private List<String> links;
    private List<LatestItem> data;
    private RecyclerView rvLatest;
    public RecyclerViewAdapter adapter;

    private static final String TAG = "LatestFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v(TAG, "onCreateView");

        View v = inflater.inflate(R.layout.recycler_view, container, false);

        rvLatest = (RecyclerView) v.findViewById(R.id.recyclerView);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvLatest.setLayoutManager(llm);

        initializeData();
        initializeAdapter();

        rvLatest.setItemAnimator(new DefaultItemAnimator());

        return v;
    }

    public void initializeData(){

        Log.v(TAG, "initializeData");

        headlines = new ArrayList<>();
        links = new ArrayList<>();
        data = new ArrayList<>();

        //Random headlines

        headlines.add(0, "CampusVirtual");
        headlines.add(1, "UNEx");
        headlines.add(2, "Android Developers");
        headlines.add(3, "Android");
        headlines.add(4, "Google Translate");

        for (int i = 0; i < 10; i++){
            headlines.add(i+5,"News " + (i+1));
        }

        // Random links
        links.add(0,"https://campusvirtual.unex.es/portal/");
        links.add(1,"http://www.unex.es/");
        links.add(2,"https://developer.android.com/index.html");
        links.add(3,"https://www.android.com/intl/es_es/");
        links.add(4,"https://translate.google.com/?hl=es");

        for (int i = 0; i < 10; i++){
            links.add(i+5,"http://www.unex.es/");
        }

        for (int i = 0; i < 15; i++){
            data.add(i, new LatestItem(headlines.get(i), links.get(i)));
        }
    }

    public void initializeAdapter(){

        Log.v(TAG, "initializeAdapter");

        adapter = new RecyclerViewAdapter(data, new RecyclerViewAdapter.OnItemClickListener(){
            @Override public void onItemClick(LatestItem item) {
                Uri uri = Uri.parse(item.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        rvLatest.setAdapter(adapter);
    }
}
