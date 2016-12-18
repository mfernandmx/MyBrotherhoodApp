package com.example.marcos.mybrotherhoodapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.marcos.mybrotherhoodapp.items.SuggestionItem;

/**
 * Class SuggestionsDBHelper
 * Manage all the interaction with the suggestions database, including its creation
 */
public class SuggestionsDBHelper extends SQLiteOpenHelper {

    private static SuggestionsDBHelper sInstance;

    private static final String TAG = "SuggestionsDBHelper";

    private static int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Suggestions.db";

    public static synchronized SuggestionsDBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SuggestionsDBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public SuggestionsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Sentencia SQL para crear la tabla de Usuarios
        String sqlCreate = "CREATE TABLE IF NOT EXISTS " + SuggestionsConstract.SuggestionEntry.TABLE_NAME + " ("
                + SuggestionsConstract.SuggestionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SuggestionsConstract.SuggestionEntry.ID + " TEXT NOT NULL,"
                + SuggestionsConstract.SuggestionEntry.NAME + " TEXT NOT NULL,"
                + SuggestionsConstract.SuggestionEntry.MESSAGE + " TEXT NOT NULL,"
                + "UNIQUE (" + SuggestionsConstract.SuggestionEntry.ID + "))";

        Log.v(TAG, "Creating the database");
        db.execSQL(sqlCreate);

        Log.v(TAG, "Adding initial data");
        mockData(db);
    }

    private void mockData(SQLiteDatabase db) {

        db.insert(
                SuggestionsConstract.SuggestionEntry.TABLE_NAME,
                null,
                new SuggestionItem("Carlos Perez", "Cambia los colores").toContentValues());
        db.insert(
                SuggestionsConstract.SuggestionEntry.TABLE_NAME,
                null,
                new SuggestionItem("Daniel Samper", "Mete mas funcionalidades").toContentValues());
        db.insert(
                SuggestionsConstract.SuggestionEntry.TABLE_NAME,
                null,
                new SuggestionItem("Lucia Aristizabal", "Elimina la app").toContentValues());
        db.insert(
                SuggestionsConstract.SuggestionEntry.TABLE_NAME,
                null,
                new SuggestionItem("Marina Acosta", "Aprende a programar").toContentValues());
    }

    public long saveSuggestion(SuggestionItem suggestion) {

        SQLiteDatabase db = this.getWritableDatabase();
        long success;

        success = db.insert(
                SuggestionsConstract.SuggestionEntry.TABLE_NAME,
                null,
                suggestion.toContentValues());

        db.close();
        return success;

    }

    public int deleteSuggestion(String suggestionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int success;

        success = db.delete(
                SuggestionsConstract.SuggestionEntry.TABLE_NAME,
                SuggestionsConstract.SuggestionEntry.ID + " LIKE ?",
                new String[]{suggestionId});

        db.close();
        return success;
    }

    public int updateSuggestion(SuggestionItem suggestion, String suggestionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int success;

        success = db.update(
                SuggestionsConstract.SuggestionEntry.TABLE_NAME,
                suggestion.toContentValues(),
                SuggestionsConstract.SuggestionEntry.ID + " LIKE ?",
                new String[]{suggestionId});

        db.close();
        return success;
    }

    public Cursor getAllSuggestions() {
        return getReadableDatabase()
                .query(
                        SuggestionsConstract.SuggestionEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getSuggestionById(String suggestionId) {
        return getReadableDatabase().query(
                SuggestionsConstract.SuggestionEntry.TABLE_NAME,
                null,
                SuggestionsConstract.SuggestionEntry.ID + " LIKE ?",
                new String[]{suggestionId},
                null,
                null,
                null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG, "onUpgrade");

        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }
}
