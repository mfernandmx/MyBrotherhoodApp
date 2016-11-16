package com.example.marcos.mybrotherhoodapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.marcos.mybrotherhoodapp.items.SuggestionItem;

/**
 * Created by Marcos on 15/11/2016.
 */

public class SuggestionsDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Suggestions.db";

    //Sentencia SQL para crear la tabla de Usuarios
    String sqlCreate = "CREATE TABLE IF NOT EXISTS " + SuggestionsConstract.SuggestionEntry.TABLE_NAME + " ("
            + SuggestionsConstract.SuggestionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + SuggestionsConstract.SuggestionEntry.ID + " TEXT NOT NULL,"
            + SuggestionsConstract.SuggestionEntry.NAME + " TEXT NOT NULL,"
            + SuggestionsConstract.SuggestionEntry.MESSAGE + " TEXT NOT NULL,"
            + "UNIQUE (" + SuggestionsConstract.SuggestionEntry.ID + "))";

    public SuggestionsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sqlCreate);

        mockData(sqLiteDatabase);
    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockSuggestion(sqLiteDatabase, new SuggestionItem("Carlos Perez", "Cambia los colores"));
        mockSuggestion(sqLiteDatabase, new SuggestionItem("Daniel Samper", "Mete mas funcionalidades"));
        mockSuggestion(sqLiteDatabase, new SuggestionItem("Lucia Aristizabal", "Elimina la app"));
        mockSuggestion(sqLiteDatabase, new SuggestionItem("Marina Acosta", "Aprende a programar"));
    }

    public long mockSuggestion (SQLiteDatabase db, SuggestionItem suggestion) {
        return db.insert(
                SuggestionsConstract.SuggestionEntry.TABLE_NAME,
                null,
                suggestion.toContentValues());
    }

    public long saveSuggestion(SuggestionItem suggestion) {
        return getWritableDatabase().insert(
                SuggestionsConstract.SuggestionEntry.TABLE_NAME,
                null,
                suggestion.toContentValues());

    }

    public int deleteSuggestion(String suggestionId) {
        return getWritableDatabase().delete(
                SuggestionsConstract.SuggestionEntry.TABLE_NAME,
                SuggestionsConstract.SuggestionEntry.ID + " LIKE ?",
                new String[]{suggestionId});
    }

    public int updateSuggestion(SuggestionItem suggestion, String suggestionId) {
        return getWritableDatabase().update(
                SuggestionsConstract.SuggestionEntry.TABLE_NAME,
                suggestion.toContentValues(),
                SuggestionsConstract.SuggestionEntry.ID + " LIKE ?",
                new String[]{suggestionId}
        );
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
        Cursor c = getReadableDatabase().query(
                SuggestionsConstract.SuggestionEntry.TABLE_NAME,
                null,
                SuggestionsConstract.SuggestionEntry.ID + " LIKE ?",
                new String[]{suggestionId},
                null,
                null,
                null);
        return c;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }
}
