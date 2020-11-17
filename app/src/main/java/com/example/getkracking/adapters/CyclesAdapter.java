package com.example.getkracking.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getkracking.R;
import com.example.getkracking.entities.CycleVO;

import java.util.ArrayList;

public class CyclesAdapter extends RecyclerView.Adapter<CyclesAdapter.CycleViewHolder> {
    Activity activity;
    ArrayList<CycleVO> cycles;

    public CyclesAdapter(Activity activity, ArrayList<CycleVO> cycles) {
        this.activity = activity;
        this.cycles = cycles;
    }

    @NonNull
    @Override
    public CycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cycle, parent, false);
        return new CyclesAdapter.CycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CycleViewHolder holder, int position) {
        holder.tvName.setText(cycles.get(position).getName());
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        holder.rvExercises.setLayoutManager(layoutManager);
        holder.rvExercises.setAdapter(new ExercisesAdapter(cycles.get(position).getExercises()));
    }

    @Override
    public int getItemCount() {
        return cycles.size();
    }

    public class CycleViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        RecyclerView rvExercises;

        public CycleViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_cycleName);
            rvExercises = itemView.findViewById(R.id.rv_exercises);
        }
    }
}
