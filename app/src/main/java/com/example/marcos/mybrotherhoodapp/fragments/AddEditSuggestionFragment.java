package com.example.marcos.mybrotherhoodapp.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.marcos.mybrotherhoodapp.R;
import com.example.marcos.mybrotherhoodapp.SuggestionsDBHelper;
import com.example.marcos.mybrotherhoodapp.items.SuggestionItem;

/**
 * Class AddEditSuggestionFragment
 * Fragment shown when a suggestions is being added/edited
 */
public class AddEditSuggestionFragment extends Fragment {
    private static final String ARG_SUGGESTION_ID = "arg_suggestion_id";

    private String mSuggestionId;

    private SuggestionsDBHelper mLawyersDbHelper;

    private TextInputEditText mNameField;
    private TextInputEditText mMessageField;


    public AddEditSuggestionFragment() {
        // Required empty public constructor
    }

    public static AddEditSuggestionFragment newInstance(String suggestionId) {
        AddEditSuggestionFragment fragment = new AddEditSuggestionFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_suggestion, container, false);

        FloatingActionButton mSaveButton;

        // Referencias UI
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mNameField = (TextInputEditText) root.findViewById(R.id.et_name);
        mMessageField = (TextInputEditText) root.findViewById(R.id.et_message);

        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditSuggestion();
            }
        });

        mLawyersDbHelper = new SuggestionsDBHelper(getActivity());

        // Carga de datos
        if (mSuggestionId != null) {
            loadSuggestion();
        }

        return root;
    }

    private void loadSuggestion() {
        new GetLawyerByIdTask().execute();
    }

    private void addEditSuggestion() {

        String name = mNameField.getText().toString();
        String message = mMessageField.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(message)) {
            showAddEditError();
        } else{
            SuggestionItem suggestion = new SuggestionItem(name, message);
            new AddEditLawyerTask().execute(suggestion);
        }

    }

    private void showSuggestionsScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error at adding new information. Complete all fields", Toast.LENGTH_SHORT).show();
    }

    private void showSuggestion(SuggestionItem suggestion) {
        mNameField.setText(suggestion.getName());
        mMessageField.setText(suggestion.getMessage());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar sugerencia", Toast.LENGTH_SHORT).show();
    }

    private class GetLawyerByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mLawyersDbHelper.getSuggestionById(mSuggestionId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showSuggestion(new SuggestionItem(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditLawyerTask extends AsyncTask<SuggestionItem, Void, Boolean> {

        @Override
        protected Boolean doInBackground(SuggestionItem... suggestions) {
            if (mSuggestionId != null) {
                return mLawyersDbHelper.updateSuggestion(suggestions[0], mSuggestionId) > 0;

            } else {
                return mLawyersDbHelper.saveSuggestion(suggestions[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showSuggestionsScreen(result);
        }

    }

}
