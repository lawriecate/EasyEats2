package com.lawriecate.apps.easyeats2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lawrie on 18/11/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "groceriesManager";

    // Contacts table name
    private static final String TABLE_GROCERIES = "groceries";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_BOUGHT = "bought";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GROCERIES_TABLE = "CREATE TABLE " + TABLE_GROCERIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_BOUGHT + " BOOLEAN" + ")";
        db.execSQL(CREATE_GROCERIES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROCERIES);

        // Create tables again
        onCreate(db);
    }

    public void addGrocery(Grocery grocery) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, grocery.getName());
        values.put(KEY_BOUGHT, grocery.getBought());
        // Inserting Row
        db.insert(TABLE_GROCERIES, null, values);
        db.close(); // Closing database connection
    }

    public Grocery getGrocery(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GROCERIES, new String[] { KEY_ID,
                        KEY_NAME, KEY_BOUGHT }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Grocery grocery = new Grocery(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), (cursor.getInt(2)==1));
        // return grocery
        return grocery;
    }

    public List<Grocery> getAllGroceries() {
        List<Grocery> groceryList = new ArrayList<Grocery>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_GROCERIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Grocery grocery = new Grocery(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        (cursor.getInt(2)==1)
                );
                // Adding contact to list
                groceryList.add(grocery);
            } while (cursor.moveToNext());
        }

        return groceryList;
    }



    public int getGroceriesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_GROCERIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public int updateGrocery(Grocery grocery) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, grocery.getName());
        values.put(KEY_BOUGHT, grocery.getBought());
        Log.i("DB","Updating grocery " + grocery.getID() + " bought: " + grocery.getBought());
        // updating row
        return db.update(TABLE_GROCERIES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(grocery.getID()) });
    }

    /*
         public int updateFitnessChallenge(FitnessChallenge fitnessChallenge) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, fitnessChallenge.getName());
        values.put(KEY_DESCRIPTION, fitnessChallenge.getDescription());
        values.put(KEY_LEVEL, fitnessChallenge.getLevel());

        values.put(KEY_COMPLETED, fitnessChallenge.getCompleted());
        values.put(KEY_PERFORM_NO, fitnessChallenge.getPerform_no());
        values.put(KEY_VIDEO, fitnessChallenge.getVideo());
        values.put(KEY_IMAGE, fitnessChallenge.getImage());


        // updating row
        return db.update(TABLE_FITNESS_CHALLENGES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(fitnessChallenge.getId())});
    }

     */

    public void deleteGrocery(Grocery grocery) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GROCERIES, KEY_ID + " = ?",
                new String[] { String.valueOf(grocery.getID()) });
        db.close();
    }

    public void deleteAllGrocery() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GROCERIES,null,null);
        db.close();
    }
}
