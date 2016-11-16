package com.example.marcos.mybrotherhoodapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.marcos.mybrotherhoodapp.fragments.SettingsFragment;

/**
 * Created by Marcos on 09/11/2016.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Declare and setup Login Button
        Button loginButton = (Button) findViewById(R.id.loginButtonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {

            // Call login() when pressed
            @Override
            public void onClick(View v) {

                login();

            }
        });

        // Declare and setup Cancel Button
        Button cancelButton = (Button) findViewById(R.id.loginButtonCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            // Call cancel() when pressed
            @Override
            public void onClick(View v) {

                cancel();

            }
        });

    }

    public void login() {

        Intent resultData = new Intent();
        resultData.putExtra("logged", true);
        setResult(Activity.RESULT_OK, resultData);
        finish();
    }

    public void cancel() {

        Intent resultData = new Intent();
        resultData.putExtra("logged", false);
        setResult(Activity.RESULT_OK, resultData);
        finish();
    }

    @Override
    public void onResume() {

        super.onResume();

        boolean nightMode;
        int backgroundColor;
        int textColor;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        View v = findViewById(R.id.Login).getRootView();

        TextView textView = (TextView) v.findViewById(R.id.editText);
        TextView textView2 = (TextView) v.findViewById(R.id.editText2);

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
        textView.setHintTextColor(textColor);
        textView2.setTextColor(textColor);
        textView2.setHintTextColor(textColor);

    }
}
