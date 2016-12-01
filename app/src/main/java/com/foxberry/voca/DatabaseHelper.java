package com.foxberry.voca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.foxberry.voca.dto.TranslateUnit;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "voca";
    private static final String TABLE_WORD_PAIRS = "translateUnits";

    private static final String KEY_ID = "id";
    private static final String KEY_WORD = "word";
    private static final String KEY_TRANSLATE = "tanslate";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORD_PAIRS_TAB = "CREATE TABLE " + TABLE_WORD_PAIRS + " ("
                + KEY_ID + " Integer PRIMARY KEY ASC, "
                + KEY_WORD + " TEXT, "
                + KEY_TRANSLATE + " TEXT " + ")";
        db.execSQL(CREATE_WORD_PAIRS_TAB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORD_PAIRS);
        onCreate(db);
    }

    public void addWordPair(TranslateUnit translateUnit) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_WORD, translateUnit.getWord());
        contentValues.put(KEY_TRANSLATE, translateUnit.getTranslate());

        database.insert(TABLE_WORD_PAIRS, null, contentValues);
        database.close();
    }

    public TranslateUnit getWordPair(int id) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TABLE_WORD_PAIRS, new String[]{KEY_ID,
                        KEY_WORD, KEY_TRANSLATE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        TranslateUnit translateUnit = new TranslateUnit(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        return translateUnit;
    }

    public List<TranslateUnit> getAllWordPairs() {
        List<TranslateUnit> translateUnits = new ArrayList<>();
        String selectAll = "SELECT * FROM " + TABLE_WORD_PAIRS;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectAll, null);
        if (cursor.moveToFirst())
            do {
                TranslateUnit translateUnit = new TranslateUnit();
                translateUnit.setId(Integer.parseInt(cursor.getString(0)));
                translateUnit.setWord(cursor.getString(1));
                translateUnit.setTranslate(cursor.getString(2));
                translateUnits.add(translateUnit);
            } while (cursor.moveToNext());
        return translateUnits;
    }

    public int getCount() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_WORD_PAIRS;
        Cursor cursor = database.rawQuery(query, null);
        cursor.close();
        return cursor.getCount();
    }

    public int updateWordPair(TranslateUnit translateUnit) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_WORD, translateUnit.getWord());
        values.put(KEY_TRANSLATE, translateUnit.getTranslate());

        // updating row
        return db.update(TABLE_WORD_PAIRS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(translateUnit.getId())});
    }

    public void deleteWordPair(TranslateUnit translateUnit) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORD_PAIRS, KEY_WORD + " = ?",
                new String[]{String.valueOf(translateUnit.getWord())});
        db.close();
    }


}
