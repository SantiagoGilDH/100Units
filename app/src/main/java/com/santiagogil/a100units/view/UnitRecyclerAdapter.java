package com.santiagogil.a100units.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.santiagogil.a100units.model.pojos.Unit;

import java.util.ArrayList;
import java.util.List;

public class UnitRecyclerAdapter extends RecyclerView.Adapter{

    private List<Unit> units;
    private Context context;
    private FragmentUnitRecyclerView.ActivityCommunicator activityCommunicator;

    public UnitRecyclerAdapter(Context context, FragmentUnitRecyclerView.ActivityCommunicator activityCommunicator) {
        this.units = new ArrayList<>();
        this.context = context;
        this.activityCommunicator = activityCommunicator;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }
    public List<Unit> getUnits() {
        return units;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public FragmentUnitRecyclerView.ActivityCommunicator getActivityCommunicator() {
        return activityCommunicator;
    }

    public void setActivityCommunicator(FragmentUnitRecyclerView.ActivityCommunicator activityCommunicator) {
        this.activityCommunicator = activityCommunicator;
    }
}
