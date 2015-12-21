package org.sana.android.db;

import org.sana.R;
import org.sana.android.LoginDataBaseAdapter;
import org.sana.api.task.Task;

import android.content.Context;
import android.util.Log;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * This class helps open, create, and upgrade the database file.
 * 
 * @author Sana Development Team
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context,String name,CursorFactory factory,int version) {
        super(context,name,factory,version);
    }



    @Override
    public void onCreate(SQLiteDatabase _db) {
    	_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE);
       // _db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE1);

    }


    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
        Log.w("TaskDBAdapter","Upgrading from version"+_oldVersion +"to"+ _newVersion +",which will"
                + " destroy old data");
        _db.execSQL("DROP TABLE IF EXISTS" + "TEMPLATE");
        onCreate(_db);


    }
}