package com.example.getkracking.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getkracking.R;
import com.example.getkracking.entities.RoutineVO;

import java.util.ArrayList;

public class RoutinesAdapter extends RecyclerView.Adapter<RoutinesAdapter.RoutineViewHolder> {
    ArrayList<RoutineVO> routinesList;

    public RoutinesAdapter(ArrayList<RoutineVO> routinesList) {
        this.routinesList = routinesList;
    }

    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bigroutine_card, parent, false);
        return new RoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {
        holder.name.setText(routinesList.get(position).getName());
        holder.description.setText(routinesList.get(position).getDescription());
        holder.duration.setText(String.valueOf(routinesList.get(position).getDuration()));

        if(routinesList.get(position).isFavorited())
            holder.favourite.setImageResource(R.drawable.ic_favorite);
        else
            holder.favourite.setImageResource(R.drawable.ic_favorite_border);

        holder.category1.setRating(routinesList.get(position).getLevelCategory1());
        holder.category2.setRating(routinesList.get(position).getLevelCategory2());
    }

    @Override
    public int getItemCount() {
        return routinesList.size();
    }

    public static class RoutineViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, duration;
        RatingBar category1, category2;
        ImageView favourite;        //FALTA LA OTRA IMAGEVIEW DE PESAS PERO NO LO SOPORTA LA API

        public RoutineViewHolder(View itemview) {
            super(itemview);
            name = (TextView) itemview.findViewById(R.id.tv_name_routine);
            description = (TextView) itemview.findViewById(R.id.tv_description_routine);
            duration = (TextView) itemview.findViewById(R.id.tv_duration_routine);
            category1 = (RatingBar) itemview.findViewById(R.id.rb_category1_routine);
            category2 = (RatingBar) itemview.findViewById(R.id.rb_category2_routine);
            favourite = (ImageView) itemview.findViewById(R.id.ivFavIconRoutine);
        }

    }
}

