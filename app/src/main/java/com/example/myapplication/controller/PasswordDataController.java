package com.example.myapplication.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.example.myapplication.constants.DataConstants;
import com.example.myapplication.data.PasswordData;
import com.example.myapplication.model.PasswordModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PasswordDataController {

    private PasswordData passwordData;

    public PasswordDataController(Context context){
        passwordData = new PasswordData(context);
    }

    public long insertPassword(PasswordModel passwordModel){

        ContentValues values;
        long result;

        SQLiteDatabase db = passwordData.getWritableDatabase();
        values = new ContentValues();
        values.put(DataConstants.TITLE, passwordModel.getTitle());
        values.put(DataConstants.USERNAME, passwordModel.getUsername());
        values.put(DataConstants.PASSWORD, passwordModel.getPassword());
        values.put(DataConstants.DESCRIPTION, passwordModel.getDescription());
        values.put(DataConstants.DATE, passwordModel.getDate());

        result = db.insert(DataConstants.TABLE, null, values);
        db.close();

        return result;

    }

    public Cursor loadData(){
        Cursor cursor;

        String[] columns = {DataConstants.ID, DataConstants.TITLE, DataConstants.PASSWORD};

        SQLiteDatabase db = passwordData.getReadableDatabase();

        cursor = db.query(DataConstants.TABLE, columns, null, null, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        db.close();

        return cursor;

    }

    public Cursor loadDataById(int id){
        Cursor cursor;
        String[] columns =  {DataConstants.ID,DataConstants.TITLE,DataConstants.USERNAME,DataConstants.PASSWORD, DataConstants.DESCRIPTION};
        String where = DataConstants.ID + "=" + id;
        SQLiteDatabase db = passwordData.getReadableDatabase();
        cursor = db.query(DataConstants.TABLE,columns,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void updatePassword(int id, PasswordModel passwordModel){
        ContentValues values;
        String where;

        SQLiteDatabase db = passwordData.getWritableDatabase();

        where = DataConstants.ID + "=" + id;

        values = new ContentValues();
        values.put(DataConstants.TITLE, passwordModel.getTitle());
        values.put(DataConstants.USERNAME, passwordModel.getUsername());
        values.put(DataConstants.PASSWORD, passwordModel.getPassword());
        values.put(DataConstants.DESCRIPTION, passwordModel.getDescription());

        db.update(DataConstants.TABLE,values,where,null);
        db.close();
    }

    public void deletePassword(int id){
        String where = DataConstants.ID + "=" + id;
        SQLiteDatabase db = passwordData.getReadableDatabase();
        long x = db.delete(DataConstants.TABLE,where,null);
        db.close();
    }

}
