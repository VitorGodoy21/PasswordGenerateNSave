package com.example.myapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;

import com.example.myapplication.constants.DataConstants;

public class PasswordData extends SQLiteOpenHelper {

    public PasswordData(Context context){
        super(context, DataConstants.DATABASE_NAME,null, DataConstants.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataConstants.CreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DataConstants.DropTable);
        onCreate(db);
    }
}
