package com.example.marcos.mybrotherhoodapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.example.marcos.mybrotherhoodapp.fragments.SuggestionDetailFragment;
import com.example.marcos.mybrotherhoodapp.fragments.SuggestionsFragment;

public class SuggestionDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion_detail);

        String id = getIntent().getStringExtra(SuggestionsFragment.EXTRA_SUGGESTION_ID);

        SuggestionDetailFragment fragment = (SuggestionDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.suggestion_detail_container);
        if (fragment == null) {
            fragment = SuggestionDetailFragment.newInstance(id);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.suggestion_detail_container, fragment)
                    .commit();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_suggestion_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
