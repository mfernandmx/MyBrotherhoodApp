package com.example.marcos.mybrotherhoodapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.marcos.mybrotherhoodapp.AddEditSuggestionActivity;
import com.example.marcos.mybrotherhoodapp.R;
import com.example.marcos.mybrotherhoodapp.SuggestionDetailActivity;
import com.example.marcos.mybrotherhoodapp.SuggestionsConstract;
import com.example.marcos.mybrotherhoodapp.adapters.SuggestionsAdapter;
import com.example.marcos.mybrotherhoodapp.SuggestionsDBHelper;

/**
 * Created by Marcos on 11/11/2016.
 */

public class SuggestionsFragment extends Fragment {

    public static final int REQUEST_UPDATE_DELETE_SUGGESTION = 2;
    public static final String EXTRA_SUGGESTION_ID = "extra_suggestion_id";

    private SuggestionsDBHelper mSuggestionsDBHelper;
    private SuggestionsAdapter mSuggestionsAdapter;

    private View v;

    public SuggestionsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_suggestions, container, false);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.newSuggestion);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });

        // UI references
        ListView mSuggestionsList;
        mSuggestionsList = (ListView) v.findViewById(R.id.suggestions_list);
        mSuggestionsAdapter = new SuggestionsAdapter(getActivity(), null);

        // Setup
        mSuggestionsList.setAdapter(mSuggestionsAdapter);

        // Events
        mSuggestionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mSuggestionsAdapter.getItem(i);
                String currentSuggestionId = currentItem.getString(
                        currentItem.getColumnIndex(SuggestionsConstract.SuggestionEntry.ID));

                showDetailScreen(currentSuggestionId);
            }
        });

        getActivity().deleteDatabase(SuggestionsDBHelper.DATABASE_NAME);

        // Helper instance
        mSuggestionsDBHelper = SuggestionsDBHelper.getInstance(getActivity());

        // Data load
        loadSuggestions();

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditSuggestionActivity.REQUEST_ADD_SUGGESTION:
                    showSuccessfullSavedMessage();
                    loadSuggestions();
                    break;
                case REQUEST_UPDATE_DELETE_SUGGESTION:
                    loadSuggestions();
                    break;
            }
        }
    }

    private void loadSuggestions() {
        new SuggestionsLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Suggestion Saved", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditSuggestionActivity.class);
        startActivityForResult(intent, AddEditSuggestionActivity.REQUEST_ADD_SUGGESTION);
    }

    private void showDetailScreen(String suggestionId) {
       Intent intent = new Intent(getActivity(), SuggestionDetailActivity.class);
        intent.putExtra(SuggestionsFragment.EXTRA_SUGGESTION_ID, suggestionId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_SUGGESTION);
    }

    private class SuggestionsLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mSuggestionsDBHelper.getAllSuggestions();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mSuggestionsAdapter.swapCursor(cursor);
            }
        }
    }

    @Override
    public void onResume() {

        super.onResume();

        boolean nightMode;
        int backgroundColor;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //Set night or day mode
        nightMode = sharedPref.getBoolean(SettingsFragment.KEY_PREF_NIGHTMODE, false);
        if (nightMode){
            backgroundColor = getResources().getColor(R.color.colorPrimaryDark);
            mSuggestionsAdapter.setNightMode(true);
        } else{
            backgroundColor = Color.WHITE;
            mSuggestionsAdapter.setNightMode(false);
        }
        v.setBackgroundColor(backgroundColor);
    }

}
