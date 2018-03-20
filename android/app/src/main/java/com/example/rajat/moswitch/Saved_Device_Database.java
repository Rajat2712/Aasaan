package com.example.rajat.moswitch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajat on 4/7/2017.
 */

public class Saved_Device_Database extends SQLiteOpenHelper {


    private static final String Key_Id="id";
    private static final String Key_SSID="SSID";
    private static final String Key_BSSID="BSSID";
    private static final String Key_Pass="Pass";
    private static final String Key_Level="Level";
    private static final String Key_State="State";
    private static final String Key_Name="name";
    private static final String Key_Area="area";
    private static final String Key_Photo="photo";



    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "MoSwitch";
    private static final String TABLE_NAME = "snetwork";


    public Saved_Device_Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE  IF NOT EXISTS  " + TABLE_NAME + "("
                + Key_Id + " INTEGER PRIMARY KEY AUTOINCREMENT," + Key_SSID + " TEXT," + Key_BSSID + " TEXT,"
                + Key_Pass + " TEXT," + Key_Level +" INTEGER," + Key_Name + " TEXT,"  + Key_Area + " TEXT,"  + Key_Photo +" BLOB," + Key_State +" INTEGER" + ")";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }


    public long addDevice(Wifi_list wifi_list){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Key_SSID, wifi_list.getSSID());
        values.put(Key_BSSID, wifi_list.getBSSID());
        values.put(Key_Pass, wifi_list.getPass());
        values.put(Key_Level,wifi_list.getLevel());
        values.put(Key_Name, wifi_list.getName());
        values.put(Key_Area,wifi_list.getArea());
        values.put(Key_Photo,wifi_list.getPhoto());
        values.put(Key_State,wifi_list.getState());
        long ri=db.insert(TABLE_NAME, null, values);

        db.close();

        return ri;
    }


    public List<Wifi_list> getAllDevice() {
        List<Wifi_list> DeviceArrayList = new ArrayList<Wifi_list>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {
                Wifi_list wifi_list = new Wifi_list();
                //pgContact.setID(Integer.parseInt(cursor.getString(0)));
                wifi_list.setId(cursor.getInt(0));
                wifi_list.setSSID(cursor.getString(1));
                wifi_list.setBSSID(cursor.getString(2));
                wifi_list.setPass(cursor.getString(3));
                wifi_list.setLevel(cursor.getInt(4));
                wifi_list.setName(cursor.getString(5));
                wifi_list.setArea(cursor.getString(6));
                wifi_list.setPhoto(cursor.getBlob(7));
                wifi_list.setState(cursor.getInt(8));
                DeviceArrayList.add(wifi_list);
            } while (cursor.moveToNext());
        }else {DeviceArrayList=null; }
        cursor.close();
        db.close();


        return DeviceArrayList;
    }



    public int getDeviceCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }


    public int getCoumnCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getColumnCount();
    }


    public void deleteDevice(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, Key_Id + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }



    public Wifi_list getDevice(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true,TABLE_NAME, new String[]{ Key_Id,
                        Key_SSID,Key_BSSID,Key_Pass,Key_Level,Key_Name,Key_Area,Key_Photo,Key_State }, Key_Id + "=?",
                new String[]{ String.valueOf(id) }, null, null, null, null);



        if (cursor != null)
            cursor.moveToFirst();

        /*PgContact pgContact = new PgContact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5), cursor.getBlob(6),cursor.getString(7));
        */

        Wifi_list   wifi_list = new Wifi_list(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)), cursor.getString(5),cursor.getString(6), cursor.getBlob(7));
        db.close();
        return wifi_list;
    }



    public int updateDevice(Wifi_list wifi_list,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Key_SSID, wifi_list.getSSID());
        values.put(Key_BSSID, wifi_list.getBSSID());
        values.put(Key_Pass, wifi_list.getPass());
        values.put(Key_Level,wifi_list.getLevel());
        values.put(Key_Name, wifi_list.getName());
        values.put(Key_Area,wifi_list.getArea());
        values.put(Key_Photo,wifi_list.getPhoto());
        values.put(Key_State,wifi_list.getState());

        return db.update(TABLE_NAME, values, Key_Id + " = ?",
                new String[]{String.valueOf(id)});
    }

}
