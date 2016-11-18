package com.example.marcos.mybrotherhoodapp.fragments;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marcos.mybrotherhoodapp.AddEditSuggestionActivity;
import com.example.marcos.mybrotherhoodapp.SuggestionsDBHelper;
import com.example.marcos.mybrotherhoodapp.R;
import com.example.marcos.mybrotherhoodapp.items.SuggestionItem;

/**
 * Vista para el detalle del abogado
 */
public class SuggestionDetailFragment extends Fragment {
    private static final String ARG_SUGGESTION_ID = "suggestionId";

    private String mSuggestionId;

    private TextView mName;
    private TextView mMessage;

    private SuggestionsDBHelper mSuggestionsDBHelper;


    public SuggestionDetailFragment() {
        // Required empty public constructor
    }

    public static SuggestionDetailFragment newInstance(String suggestionId) {
        SuggestionDetailFragment fragment = new SuggestionDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SUGGESTION_ID, suggestionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mSuggestionId = getArguments().getString(ARG_SUGGESTION_ID);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_suggestion_detail, container, false);
        mName = (TextView) root.findViewById(R.id.tv_name);
        mMessage = (TextView) root.findViewById(R.id.tv_message);

        mSuggestionsDBHelper = new SuggestionsDBHelper(getActivity());

        loadSuggestion();

        return root;
    }

    private void loadSuggestion() {
        new GetSuggestionByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteSuggestionTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SuggestionsFragment.REQUEST_UPDATE_DELETE_SUGGESTION) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showSuggestion(SuggestionItem suggestion) {
        mName.setText(suggestion.getName());
        mMessage.setText(suggestion.getMessage());
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditSuggestionActivity.class);
        intent.putExtra(SuggestionsFragment.EXTRA_SUGGESTION_ID, mSuggestionId);
        startActivityForResult(intent, SuggestionsFragment.REQUEST_UPDATE_DELETE_SUGGESTION);
    }

    private void showSuggestionsScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error loading the information", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error deleting suggestion", Toast.LENGTH_SHORT).show();
    }

    private class GetSuggestionByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mSuggestionsDBHelper.getSuggestionById(mSuggestionId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showSuggestion(new SuggestionItem(cursor));
            } else {
                showLoadError();
            }
        }

    }

    private class DeleteSuggestionTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mSuggestionsDBHelper.deleteSuggestion(mSuggestionId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showSuggestionsScreen(integer > 0);
        }

    }

}
