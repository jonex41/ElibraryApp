package com.john.www.e_libraryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import java.util.ArrayList;
import java.util.List;


public class DatabaseFavourites extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "downloads_table";


    public static final String COLUMN_PDF_ID ="_id" ;
    public static final String PDF_NAME = "pdf_name";
    public static final String PDF_IMAGE = "pdf_image";
    public static final String PDF_URL = "pdf_url";

    public SQLiteDatabase db;

    // Database Version
    private static final int DATABASE_VERSION =23;

    // Database Name
    private static final String DATABASE_NAME = "PDF.db";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_PDF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PDF_NAME + " TEXT , " +
                PDF_IMAGE + " TEXT , " +
                PDF_URL + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    //drop beneficiary table
    private String DROP_BENEFICIARY_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseFavourites(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_BENEFICIARY_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    //Method to create beneficiary records


    public void addtoCart(BookModel contact) {


        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues contentValues = new ContentValues();

        contentValues.put(PDF_IMAGE, contact.getImageUrl());
        contentValues.put(PDF_NAME, contact.getImagename());
        contentValues.put(PDF_URL, contact.getPdfUrl());

        db.insert(TABLE_NAME, null, contentValues);


        db.close();
    }




    public boolean checkUser(String name) {

        // array of columns to fetch
        String[] columns = {
                PDF_NAME
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = PDF_NAME + " = ?";

        // selection argument
        String[] selectionArgs = {name};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public List<BookModel> getindivMessages(){
        List<BookModel> todos = new ArrayList<BookModel>();
        // array of columns to fetch
        String[] columns = {
                COLUMN_PDF_ID,
                PDF_URL,
                PDF_IMAGE,
                PDF_NAME,
        };
        SQLiteDatabase db = this.getReadableDatabase();



        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor c = db.query(TABLE_NAME, //Table to query
                columns,                    //columns to return
                null ,                  //columns for the WHERE clause
                null,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order

        if (c.moveToFirst()) {
            do {


                BookModel td = new BookModel();
                td.setImagename(c.getString(c.getColumnIndex(PDF_NAME)));
                td.setPdfUrl(c.getString(c.getColumnIndex(PDF_URL)));
                td.setImageUrl(c.getString(c.getColumnIndex(PDF_IMAGE)));

                // adding to todo list
                todos.add(td);
            } while (c.moveToNext());
        }

        c.close();

        db.close();

        return todos;
    }

    public void deleteRecord(String contact) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME, COLUMN_PDF_ID + " = ?", new String[]{contact});
        db.close();
    }


    public void deleteContact() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null, null);
        db.close();
    }

    public long getProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        return count;
    }
}

