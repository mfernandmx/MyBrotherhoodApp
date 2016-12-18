package com.example.marcos.mybrotherhoodapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.marcos.mybrotherhoodapp.R;
import com.example.marcos.mybrotherhoodapp.SuggestionsConstract;

/**
 * Class SuggestionsAdapter
 * Adapter used for showing the suggestions in the suggestions section
 */
public class SuggestionsAdapter extends CursorAdapter {

    private boolean nightMode = false;

    public SuggestionsAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.item_suggestion, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        TextView nameText = (TextView) view.findViewById(R.id.nameText);
        TextView messageText = (TextView) view.findViewById(R.id.suggestionText);

        // Get valors.
        String name = cursor.getString(cursor.getColumnIndex(SuggestionsConstract.SuggestionEntry.NAME));
        String message = cursor.getString(cursor.getColumnIndex(SuggestionsConstract.SuggestionEntry.MESSAGE));

        // Setup.
        nameText.setText(name);
        messageText.setText(message);

        // Change text color if the night mode is active
        int textColor;
        if (!nightMode){
            textColor = Color.BLACK;
        } else{
            textColor = Color.WHITE;
        }

        nameText.setTextColor(textColor);
        messageText.setTextColor(textColor);
    }

    public void setNightMode(boolean nightMode){
        this.nightMode = nightMode;
    }
}
