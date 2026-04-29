package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseManager {

    static final String DATABASE_NAME = "SnacksDB";
    static final int DATABASE_VERSION = 1;

    static final String TABLE_SNACK = "snacks";
    static final String COLUMN_ID = "id";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_DESC = "description";
    static final String COLUMN_PRICE = "price";
    static final String COLUMN_IMAGE = "image";

    Context context;
    DBHelper helper;

    public DatabaseManager(Context context) {
        this.context = context;
    }

    public void open() {
        helper = new DBHelper(context);
    }

    public void close() {
        helper.close();
    }


    public ArrayList<Snack> getAllSnacks() {

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SNACK, null, null, null, null, null, null);

        ArrayList<Snack> snacks = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int image = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE));

                snacks.add(new Snack(image, name, desc, price, 0));

            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return snacks;
    }


    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String createTable = "CREATE TABLE " + TABLE_SNACK + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_DESC + " TEXT, " +
                    COLUMN_PRICE + " REAL, " +
                    COLUMN_IMAGE + " INTEGER)";

            db.execSQL(createTable);

            insertInitialData(db);
        }

        private void insertInitialData(SQLiteDatabase db) {

            insert(db, "Popcorn", "Large / Buttered", 8.99, R.drawable.popcorn);
            insert(db, "Nachos", "With Cheese Dip", 7.99, R.drawable.nachos);
            insert(db, "Soft Drink", "Large / Any Flavor", 5.99, R.drawable.drink);
            insert(db, "Candy Mix", "Assorted Candies", 6.99, R.drawable.candy);
        }

        private void insert(SQLiteDatabase db, String name, String desc, double price, int image) {

            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, name);
            cv.put(COLUMN_DESC, desc);
            cv.put(COLUMN_PRICE, price);
            cv.put(COLUMN_IMAGE, image);

            db.insert(TABLE_SNACK, null, cv);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SNACK);
            onCreate(db);
        }
    }
}