package com.example.marcos.mybrotherhoodapp.fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marcos.mybrotherhoodapp.R;

/**
 * Created by Marcos on 10/11/2016.
 */

public class InitialFragment extends Fragment {

    private View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_initial, container, false);

        return v;
    }

    @Override
    public void onResume() {

        super.onResume();

        String textSize;
        boolean nightMode;
        int backgroundColor;
        int textColor;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //Set text size
        textSize = sharedPref.getString(SettingsFragment.KEY_PREF_TEXTSIZE,"");
        TextView textView = (TextView) v.findViewById(R.id.initial_text);
        textView.setTextSize(Integer.parseInt(textSize));

        //Set night or day mode
        nightMode = sharedPref.getBoolean(SettingsFragment.KEY_PREF_NIGHTMODE, false);
        if (nightMode){
            backgroundColor = getResources().getColor(R.color.colorPrimaryDark);
            textColor = Color.WHITE;
        } else{
            backgroundColor = Color.WHITE;
            textColor = Color.BLACK;
        }
        v.setBackgroundColor(backgroundColor);
        textView.setTextColor(textColor);
    }
}
