package com.santiagogil.a100units.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database
    private static final String DATABASENAME = "100UnitsDB";
    private static final Integer DATABASEVERSION = 1;

    //Tables
    public static final String TABLEUNITS = "Units";

    //Columns
    public static final String ID = "ID";


    //Values

    public DatabaseHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


    }

    private static final String CREATE_TABLE_ITEMS = "CREATE TABLE " +  "("
            + ID + " STRING PRIMARY KEY,"

            + ")";


    private static final String CREATE_TABLE_CONSUMPTIONS = "CREATE TABLE " +  "("
            + ID + " STRING PRIMARY KEY,"

            + ")";



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
