package org.sana.android.activity;

/**
 * Created by arpitjaiswal on 11/21/15.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper implements CityListener {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "NotificationTab.db";
    private static final String TABLE_NAME = "Notification";
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "_name";
    private static final String KEY_STATE = "_state";
    private static final String KEY_DESCRIPTION = "_description";

    String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID +
            " INTEGER ," + KEY_NAME + " TEXT," + KEY_STATE + " TEXT," + KEY_DESCRIPTION + " TEXT unique)";


    String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }





    @Override
    public void addCity(City city) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ID, city.getId());
            values.put(KEY_NAME, city.getName());
            values.put(KEY_STATE, city.getState());
            values.put(KEY_DESCRIPTION, city.getDescription());
            db.insert(TABLE_NAME, null, values);
            db.close();
        } catch (Exception e) {
            Log.e("problem", e + "");
        }
    }

    @Override
    public ArrayList<City> getAllCity() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<City> cityList = null;
        ContentValues cv = new ContentValues();
        String flag = "1";
        try {
            cityList = new ArrayList<City>();
            String Query = "SELECT * FROM " + "Notification" + " WHERE "
                    + KEY_ID + " = 0";
            Cursor cursor = db.rawQuery(Query,null);

           // Cursor cursor = db.query(true, TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_STATE, KEY_DESCRIPTION}, null, null, KEY_DESCRIPTION, null, KEY_STATE + " ASC", null);

            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    City city = new City();
                    city.setId(cursor.getInt(0));
                    city.setName(cursor.getString(1));
                    city.setState(cursor.getString(2));
                    city.setDescription(cursor.getString(3));
                    cityList.add(city);
                }
            }
            cv.put(KEY_ID, flag);
            db.update(TABLE_NAME, cv, null, null);

            db.close();
        } catch (Exception e) {
            Log.e("error", e + "");
        }
        return cityList;
    }

}