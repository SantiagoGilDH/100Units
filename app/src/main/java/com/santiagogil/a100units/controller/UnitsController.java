package com.santiagogil.a100units.controller;

import android.content.Context;

import com.santiagogil.a100units.model.daos.UnitsDAO;
import com.santiagogil.a100units.model.pojos.Unit;

import java.util.List;

public class UnitsController {


    public List<Unit> getUnits(Context context){

        UnitsDAO unitsDAO = new UnitsDAO(context);
        if(unitsDAO.getUnitsFromLocalDatabase().size() == 0){

            unitsDAO.createBlankUnitsInLocalDB();

        }

        return unitsDAO.getUnitsFromLocalDatabase();

    }

    public void updateUnit(Context context, Integer position, String description, Integer color) {

        UnitsDAO unitsDao = new UnitsDAO(context);
        unitsDao.updateUnit(position, description, color);

    }

    public void updateUnitColor(Context context, Integer position, Integer color) {

        UnitsDAO unitsDao = new UnitsDAO(context);
        unitsDao.updateUnitColor(position, color);
    }
}
