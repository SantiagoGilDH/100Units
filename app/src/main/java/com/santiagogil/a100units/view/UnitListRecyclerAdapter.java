package com.santiagogil.a100units.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.santiagogil.a100units.R;
import com.santiagogil.a100units.model.pojos.Unit;

import java.util.ArrayList;
import java.util.List;

public class UnitListRecyclerAdapter extends UnitRecyclerAdapter {

    public UnitListRecyclerAdapter(Context context, FragmentUnitRecyclerView.ActivityCommunicator activityCommunicator) {
        super(context, activityCommunicator);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.unit_detail_card_view, parent, false);
        return new UnitViewHolder(view, getActivityCommunicator());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        Unit unit = getUnits().get(position);
        UnitViewHolder unitViewHolder = (UnitViewHolder) holder;
        unitViewHolder.loadUnit(unit);
        unitViewHolder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return getUnits().size();
    }

    static class UnitViewHolder extends RecyclerView.ViewHolder{

        private Unit unit;
        private Integer position;

        public void setPosition(Integer position) {
            this.position = position;
        }

        private TextView textView;
        private TextView textViewUnitDescription;
        private FragmentUnitRecyclerView.ActivityCommunicator activityCommunicator;

        public UnitViewHolder(final View itemView, final FragmentUnitRecyclerView.ActivityCommunicator activityCommunicator) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_view_unit_id);
            textViewUnitDescription = (TextView) itemView.findViewById(R.id.text_view_unit_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    activityCommunicator.onUnitTouched(unit, position);
                }
            });

        }

        public void loadUnit(Unit unit) {
            this.unit = unit;
            textView.setText(unit.getID() + ": ");
            if(unit.getDescription().equals("")){
                textViewUnitDescription.setText("Add a Description");
            } else {
                textViewUnitDescription.setText(unit.getDescription());
            }
        }
    }
}


