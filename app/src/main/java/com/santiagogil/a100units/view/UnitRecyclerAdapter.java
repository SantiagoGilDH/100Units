package com.santiagogil.a100units.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiagogil.a100units.R;
import com.santiagogil.a100units.pojos.Unit;

import java.util.ArrayList;
import java.util.List;

public class UnitRecyclerAdapter extends RecyclerView.Adapter {

    private List<Unit> units;
    private Context context;

    public List<Unit> getUnits() {
        return units;
    }

    public UnitRecyclerAdapter(Context context) {
        this.context = context;
        this.units = new ArrayList<>();
        loadUnits();
    }

    private void loadUnits(){
        for(Integer i = 0; i < 100; i++){
            units.add(new Unit());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.detail_unit, parent, false);
        return new UnitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return units.size();
    }

    static class UnitViewHolder extends RecyclerView.ViewHolder{

        public UnitViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemView.setBackgroundColor(Color.RED);
                }
            });
        }


    }

}
