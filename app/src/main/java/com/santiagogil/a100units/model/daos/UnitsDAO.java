package com.santiagogil.a100units.model.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.santiagogil.a100units.model.pojos.Unit;
import com.santiagogil.a100units.utils.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class UnitsDAO {

    private DatabaseHelper databaseHelper;
    private Context context;

    public UnitsDAO(Context context) {

        this.context = context;
        databaseHelper = new DatabaseHelper(context);

    }

    public void createBlankUnitsInLocalDB(){

        for(Integer i = 1; i <= 100; i++){
            Unit unit = new Unit();
            unit.setID(i.toString());
            unit.setDescription("");
            addUnitToDatabase(unit);
        }
    }

    public void addUnitToDatabase(Unit unit){

        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues row = new ContentValues();

        row.put(DatabaseHelper.ID, unit.getID());
        row.put(DatabaseHelper.DESCRIPTION, unit.getDescription());

        database.insert(DatabaseHelper.TABLEUNITS, null, row);

        database.close();

    }

    public void getUnitsFromLocalDatabase(){

        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLEUNITS;

        Cursor cursor = database.rawQuery(selectQuery, null);

        List<Unit> units = new ArrayList();

        while(cursor.moveToNext()){

            Unit unit = new Unit();
            unit.setID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ID)));
            unit.setDescription(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DESCRIPTION)));

            units.add(unit);

        }

    }
}
