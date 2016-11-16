package com.example.marcos.mybrotherhoodapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.marcos.mybrotherhoodapp.LoginActivity;
import com.example.marcos.mybrotherhoodapp.R;

/**
 * Created by Marcos on 12/11/2016.
 */

public class UnloggedFragment extends Fragment {

    static private final int LOGIN_REQUEST = 0;
    static private boolean logged = false;

    loginChangedListener mCallback;

    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_unlogged, container, false);

        Button button = (Button) v.findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showLoginPage();
            }
        });

        return v;
    }

    // Container Activity must implement this interface
    public interface loginChangedListener {
        void loginChanged(boolean isLogged);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (loginChangedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public void showLoginPage() {

        Intent actLoginIntent = new Intent (getActivity(),LoginActivity.class);
        startActivityForResult(actLoginIntent, LOGIN_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check which request we're responding to
        if (requestCode == LOGIN_REQUEST) {
            // Make sure the request was successful
            if (resultCode == getActivity().RESULT_OK) {
                logged = data.getBooleanExtra("logged", false);
                mCallback.loginChanged(logged);
            }
        }
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
        TextView textView = (TextView) v.findViewById(R.id.unlogged_text);
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
