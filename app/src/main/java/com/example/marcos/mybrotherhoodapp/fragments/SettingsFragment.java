package com.example.marcos.mybrotherhoodapp.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.example.marcos.mybrotherhoodapp.R;

/**
 * Class SettingsFragment
 * Fragment shown when in the settings section, as well as in settings in the option menu
 * Shows all the preferences that can be modified in the app
 */
public class SettingsFragment extends PreferenceFragment {

	public static final String KEY_PREF_NIGHTMODE = "pref_nightmode";
    public static final String KEY_PREF_TEXTSIZE = "pref_textsize";
    public static final String KEY_PREF_NUMPHOTOS = "pref_numphotos";

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, false);

        addPreferencesFromResource(R.xml.preferences);
    }
}