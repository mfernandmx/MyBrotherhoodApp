package com.example.marcos.mybrotherhoodapp.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.marcos.mybrotherhoodapp.R;

public class SettingsFragment extends PreferenceFragment {

	public static final String KEY_PREF_NIGHTMODE = "pref_nightmode";
    public static final String KEY_PREF_TEXTSIZE = "pref_textsize";

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}