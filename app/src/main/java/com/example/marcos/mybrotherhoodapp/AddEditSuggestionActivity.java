package com.example.marcos.mybrotherhoodapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.marcos.mybrotherhoodapp.fragments.AddEditSuggestionFragment;
import com.example.marcos.mybrotherhoodapp.fragments.SuggestionsFragment;

/**
 * Class AddEditSuggestionActivity
 * Manage the activity for adding/editing a suggestion
 */
public class AddEditSuggestionActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_SUGGESTION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        String suggestionId = getIntent().getStringExtra(SuggestionsFragment.EXTRA_SUGGESTION_ID);

        setTitle(suggestionId == null ? "Set suggestion" : "Edit suggestion");

        AddEditSuggestionFragment addEditSuggestionFragment = (AddEditSuggestionFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_suggestion_container);
        if (addEditSuggestionFragment == null) {
            addEditSuggestionFragment = AddEditSuggestionFragment.newInstance(suggestionId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_suggestion_container, addEditSuggestionFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
