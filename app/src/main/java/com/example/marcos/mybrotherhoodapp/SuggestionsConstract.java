package com.example.marcos.mybrotherhoodapp;

import android.provider.BaseColumns;

/**
 * Created by Marcos on 15/11/2016.
 */

//database scheme
public class SuggestionsConstract {

    public static abstract class SuggestionEntry implements BaseColumns {
        public static final String TABLE_NAME ="suggestion";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String MESSAGE = "message";
    }
}
