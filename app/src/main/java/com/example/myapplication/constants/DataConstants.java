package com.example.myapplication.constants;

public class DataConstants {

    public static final String DATABASE_NAME = "data.db";
    public static final String TABLE = "password";
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String DESCRIPTION = "description";
    public static final String DATE = "date";
    public static final int VERSION = 2;

    public static final String CreateTable = "CREATE TABLE " + TABLE + " ( "
            + ID + " integer primary key autoincrement,"
            + TITLE + " text, "
            + USERNAME + " text, "
            + PASSWORD + " text, "
            + DESCRIPTION + " text, "
            + DATE + " text"
            +")";

    public static final String DropTable = "DROP TABLE IF EXISTS " + TABLE;

}
