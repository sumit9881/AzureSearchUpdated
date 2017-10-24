package com.sadhika.bmwproject.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sadhika.bmwproject.model.pojos.LocationInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sadhika on 7/4/17.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = MySQLiteHelper.class.getSimpleName();

    public static final String TABLE_LOCATION = "location";
    public static final String KEY_ID = "id";

    public static final String COLUMN__LOCATION_NAME = "location";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_ARRIVAL = "arrivaltime";
    public static final String COLUMN_LATTITUDE = "lattitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    private static final String DATABASE_NAME = "location.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String CREATE_TABLE_LOCATION = "CREATE TABLE "
            + TABLE_LOCATION + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + COLUMN__LOCATION_NAME + " TEXT," + COLUMN_ADDRESS + " TEXT,"
            + COLUMN_ARRIVAL + " TEXT," + COLUMN_LATTITUDE + " DOUBLE,"
            + COLUMN_LONGITUDE + " DOUBLE" +")";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LOCATION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        onCreate(db);
    }

    public List<LocationInfo> getAllLocations() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_LOCATION;


        Cursor cursor = db.rawQuery(selectQuery, null);


        List<LocationInfo> list = new ArrayList<>();

        if (cursor != null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LocationInfo location = new LocationInfo();
            location.setID(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            location.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
            location.setName(cursor.getString(cursor.getColumnIndex(COLUMN__LOCATION_NAME)));
            location.setArrivalTime(cursor.getString(cursor.getColumnIndex(COLUMN_ARRIVAL)));
            location.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATTITUDE)));
            location.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE)));
            list.add(location);
            cursor.moveToNext();
        }

        return list;
    }

    public void putAllLocations(List<LocationInfo> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            for (LocationInfo location : list) {
                ContentValues values = new ContentValues();
                values.put(KEY_ID, location.getID());
                values.put(COLUMN__LOCATION_NAME, location.getName());
                values.put(COLUMN_ADDRESS, location.getAddress());
                values.put(COLUMN_ARRIVAL, location.getArrivalTime());
                values.put(COLUMN_LATTITUDE, location.getLatitude());
                values.put(COLUMN_LONGITUDE, location.getLongitude());


                long todo_id = db.insert(TABLE_LOCATION, null, values);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
