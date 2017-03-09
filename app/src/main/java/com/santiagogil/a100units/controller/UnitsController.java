package com.santiagogil.a100units.controller;

import android.content.Context;

import com.santiagogil.a100units.model.daos.UnitsDAO;
import com.santiagogil.a100units.model.pojos.Unit;

import java.util.ArrayList;
import java.util.List;

public class UnitsController {


    public List<Unit> getUnits(Context context){

        UnitsDAO unitsDAO = new UnitsDAO(context);
        if(unitsDAO.getUnitsFromLocalDatabase().size() == 0){

            unitsDAO.createBlankUnitsInLocalDB();

        }

        return unitsDAO.getUnitsFromLocalDatabase();

    }

    public void updateUnitDescription(Context context, Integer position, String description) {

        UnitsDAO unitsDao = new UnitsDAO(context);
        unitsDao.updateUnitDescription(position, description);

    }
}
