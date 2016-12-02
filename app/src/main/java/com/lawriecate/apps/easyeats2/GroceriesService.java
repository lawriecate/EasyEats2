package com.lawriecate.apps.easyeats2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by lawrie on 17/11/2016.
 */

public class GroceriesService extends SQLiteOpenHelper {

    public static final String TABLE_GROCERIES = "groceries";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "name";
    public static final String COLUMN_QUANTITY = "quantity";

    private static final String DATABASE_NAME = "commments.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_GROCERIES + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_TITLE
            + " text not null "+ COLUMN_QUANTITY
            + " integer not null);";

    public GroceriesService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(GroceriesService.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROCERIES);
        onCreate(db);
    }

}