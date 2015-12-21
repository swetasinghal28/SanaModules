package org.sana.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import org.sana.android.db.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;


public class LoginDataBaseAdapter {
    public static final String DATABASE_NAME="Patient.db";
    public static final int DATABASE_VERSION= 1;
    public static final int NAME_COLUMN=1;
    public static final String  PATIENT_ID="ID";
    public static final String  PATIENT_FIRSTNAME="FIRSTNAME";
    public static final String PATIENT_LASTNAME="LASTNAME";
    public static final String PATIENT_PASSWORD="PASSWORD";
    public static final String PATIENT_WEIGHT="WEIGHT";
    public static final String PATIENT_HEIGHT="HEIGHT";
    public static final String PATIENT_HAEMOGLOBIN="HAEMOGLOBIN";
    public static final String PATIENT_BLOODGROUP="BLOODGROUP";
    public static final String PATIENTS_TABLE_NAME= "LOGIN";



    public static final String DATABASE_CREATE = "create table "+"LOGIN "+"("+"ID "+"INTEGER PRIMARY KEY AUTOINCREMENT, " + "FIRSTNAME  text, LASTNAME text, PASSWORD text, HAEMOGLOBIN text, BLOODGROUP text, WEIGHT text, HEIGHT text);";
    //public static final String DATABASE_CREATE1 = "create table "+"NOTIFICATIONS "+"("+"ID " + "DATE  text, NOTIFICATION text, ACTIONTAKEN text, FOLLOWUP text);";

    public SQLiteDatabase db;
    private final Context context;
    private DatabaseHelper dbhelper;
    public LoginDataBaseAdapter(Context _context)
    {
        context=_context;
        dbhelper=new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    public LoginDataBaseAdapter open()throws SQLException{
        db = dbhelper.getWritableDatabase();
        return this;

    }
    public void close(){
        db.close();
    }
    public SQLiteDatabase getDataBaseInstance(){
        return db;

    }
    public void insertEntry(String firstname, String lastname, String password,String haemoglobin,String bloodgroup,String weight,String height ){
        ContentValues newValues = new ContentValues();
        newValues.put("FIRSTNAME",firstname);
        newValues.put("LASTNAME", lastname);
        newValues.put("PASSWORD", password);
        newValues.put("BLOODGROUP", bloodgroup);
        newValues.put("WEIGHT", weight);
        newValues.put("HEIGHT", height);
        newValues.put("HAEMOGLOBIN", haemoglobin);

        db.insert("LOGIN",null,newValues);

    }


    public int deleteEntry(String firstname){
        String where="FIRSTNAME=?";
        int numberOFentriesDeleted = db.delete("LOGIN",where,new String[]{firstname});
        return numberOFentriesDeleted;
    }
    public String getSingleEntry(String firstname){
        Cursor cursor;
        cursor = db.query("LOGIN",null ,"FIRSTNAME = ?" , new String[]{firstname},null, null, null);
        if(cursor.getCount()<1)
        {
            cursor.close();
            return "Not Exist";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return  password;
    }

    public void updateEntry(String firstname, String lastname, String password,String haemoglobin,String bloodgroup,String weight,String height) {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put("FIRSTNAME", firstname);
        updatedValues.put("LASTNAME", lastname);
        updatedValues.put("PASSWORD", password);
        updatedValues.put("BLOODGROUP", bloodgroup);
        updatedValues.put("WEIGHT", weight);
        updatedValues.put("HEIGHT", height);
        updatedValues.put("HAEMOGLOBIN", haemoglobin);

        String where="FIRSTNAME=?";
        db.update("LOGIN", updatedValues, where, new String[]{firstname});

    }

    public Cursor getPatientDetail( ){
        String[] projections = {"FIRSTNAME", "LASTNAME", "PASSWORD", "BLOODGROUP" , "WEIGHT","HEIGHT",
        "HAEMOGLOBIN"};
        Cursor cursor = db.query("LOGIN",projections,null,null,null,null,null);
        if (cursor!=null){
            cursor.moveToLast();
        }
        return cursor;
    }

}
