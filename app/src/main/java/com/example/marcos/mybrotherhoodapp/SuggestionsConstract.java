package com.example.marcos.mybrotherhoodapp;

import android.provider.BaseColumns;

/**
 * Class SuggestionsConstract
 * Declares some static strings for use them in the DBHelper
 */
public class SuggestionsConstract {

    public static abstract class SuggestionEntry implements BaseColumns {
        public static final String TABLE_NAME ="suggestion";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String MESSAGE = "message";
    }
}
