package com.example.lenovo.Location4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenovo on 21-09-2016.
 */
public class Database extends SQLiteOpenHelper {
    public static final String Database_name="location";
    public static final String Table_name="coordinates";
    public static final String col_1="ID";
    public static final String col_2="lat";
    public static final String col_3="lon";
    SQLiteDatabase db;

    public Database(Context context) {
        super(context, Database_name, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+ Table_name+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,lat TEXT,lon TEXT)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ Table_name);
        onCreate(sqLiteDatabase);

    }
    public boolean insertData(double lat, double lon){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentvalues=new ContentValues();
        contentvalues.put(col_2,lat);
        contentvalues.put(col_3,lon);
        long result=  sqLiteDatabase.insert(Table_name,null,contentvalues);
        if(result==-1)
            return false;
        else
            return true;

    }
    public Cursor getAlldata(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from coordinates",null);
        return res;

    }
   public void DeleteAll()
   {    db=this.getWritableDatabase();
       db.delete("coordinates", null, null);
   }

}
