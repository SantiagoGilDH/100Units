package com.santiagogil.a100units.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiagogil.a100units.R;
import com.santiagogil.a100units.controller.UnitsController;
import com.santiagogil.a100units.model.pojos.Unit;

public class FragmentUnitRecyclerView extends Fragment {

    private RecyclerView recyclerView;
    private ActivityCommunicator activityCommunicator;
    private UnitRecyclerAdapter unitRecyclerAdapter;
    private Boolean onListViewMode;
    private Boolean onGridViewMode;

    public void setActivityCommunicator(ActivityCommunicator activityCommunicator) {
        this.activityCommunicator = activityCommunicator;
    }

    public FragmentUnitRecyclerView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unit_recycler_view, container, false);

        onListViewMode = false;
        onGridViewMode = false;

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        switchToGridView();

        return view;
    }

    public void updateUnit(Integer position, String description, Integer color) {

            UnitsController unitsController = new UnitsController();
            unitsController.updateUnit(getContext(), position, description, color);
            unitRecyclerAdapter.setUnits(unitsController.getUnits(getContext()));
            unitRecyclerAdapter.notifyDataSetChanged();
    }

    public void switchViewMode() {

        if(onListViewMode){
            switchToGridView();
        } else {
            switchToListView();
        }

    }

    private void switchToListView() {

        onGridViewMode = false;
        onListViewMode = true;
        unitRecyclerAdapter = new UnitListRecyclerAdapter(getContext(), activityCommunicator);
        loadUnitsInRecyclerAdapter();
        recyclerView.setAdapter(unitRecyclerAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void switchToGridView() {

        onGridViewMode = true;
        onListViewMode = false;
        unitRecyclerAdapter = new UnitGridRecyclerAdapter(getContext(), activityCommunicator);
        loadUnitsInRecyclerAdapter();
        recyclerView.setAdapter(unitRecyclerAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 10);
        recyclerView.setLayoutManager(gridLayoutManager);

    }

    public void updateUnitColor(Integer position, Integer color) {

        UnitsController unitsController = new UnitsController();
        unitsController.updateUnitColor(getContext(), position, color);
        unitRecyclerAdapter.setUnits(unitsController.getUnits(getContext()));
        unitRecyclerAdapter.notifyDataSetChanged();
    }

    public interface ActivityCommunicator{
        void onUnitTouched(Unit unit, Integer position);
    }

    private void loadUnitsInRecyclerAdapter(){

        UnitsController unitsController = new UnitsController();
        unitRecyclerAdapter.setUnits(unitsController.getUnits(getContext()));

    }
}
