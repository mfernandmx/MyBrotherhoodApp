package com.example.marcos.mybrotherhoodapp.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.marcos.mybrotherhoodapp.R;

/**
 * Class ShareFragment
 * Fragment shown when in the share section
 */
public class ShareFragment extends Fragment {

    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.fragment_share, container, false);

        Button button = (Button) v.findViewById(R.id.shareButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                share();
            }
        });

        return v;
    }

    private void share() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Brotherhood App");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey! I just tried a new app called 'My Brotherhood App', and i want you to try it!");
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    @Override
    public void onResume() {
        super.onResume();

        boolean nightMode;
        int backgroundColor;
        int textColor;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        TextView textView = (TextView) v.findViewById(R.id.share_text);

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
