package com.example.marcos.mybrotherhoodapp.items;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.marcos.mybrotherhoodapp.SuggestionsConstract;

import java.io.Serializable;
import java.util.UUID;

/**
 * Class SuggestionItem
 * Declares the attributes needed to manage the suggestions in the suggestions section
 */
public class SuggestionItem implements Serializable {

    private String id;
    private String name;
    private String message;

    public SuggestionItem(String name, String message){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.message = message;
    }

    public SuggestionItem(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(SuggestionsConstract.SuggestionEntry.ID));
        name = cursor.getString(cursor.getColumnIndex(SuggestionsConstract.SuggestionEntry.NAME));
        message = cursor.getString(cursor.getColumnIndex(SuggestionsConstract.SuggestionEntry.MESSAGE));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String suggestion) {
        this.message = suggestion;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(SuggestionsConstract.SuggestionEntry.ID, id);
        values.put(SuggestionsConstract.SuggestionEntry.NAME, name);
        values.put(SuggestionsConstract.SuggestionEntry.MESSAGE, message);
        return values;
    }
}
