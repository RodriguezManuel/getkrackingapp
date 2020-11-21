package com.example.getkracking.adapters;

import android.app.Activity;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getkracking.R;
import com.example.getkracking.entities.CategoryVO;
import com.example.getkracking.entities.CycleVO;
import com.example.getkracking.entities.RoutineVO;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {
    Activity activity;
    ArrayList<Pair<CategoryVO,ArrayList<RoutineVO>>> categories;
    RoutinesAdapter.OnRoutineListener onRoutineListener;    //para cuando se tocan las rutinas

    public CategoriesAdapter(Activity activity, ArrayList<Pair<CategoryVO, ArrayList<RoutineVO>>> categories, RoutinesAdapter.OnRoutineListener onRoutineListener) {
        this.activity = activity;
        this.categories = categories;
        this.onRoutineListener = onRoutineListener;
    }

    @NonNull
    @Override
    public CategoriesAdapter.CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cycle, parent, false);    //uso el mismo layout de ciclos total necesito lo mismo(representar un nombre y un recyclerview
        return new CategoriesAdapter.CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        holder.tvName.setText(categories.get(position).first.getName());
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        holder.rvExercises.setLayoutManager(layoutManager);
        holder.rvExercises.setAdapter(new RoutinesAdapter(categories.get(position).second, onRoutineListener, null));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        RecyclerView rvExercises;
        RoutinesAdapter.OnRoutineListener onRoutineListener;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_cycleName);
            rvExercises = itemView.findViewById(R.id.rv_exercises);
        }

        @Override
        public void onClick(View v) {
            onRoutineListener.onRoutineClick(getAdapterPosition(), null);
        }
    }
}
