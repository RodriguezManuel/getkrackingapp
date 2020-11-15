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
    OnRoutineListener onRoutineListener;
    public RoutinesAdapter(ArrayList<RoutineVO> routinesList, OnRoutineListener onRoutineListener) {
        this.routinesList = routinesList;
        this.onRoutineListener = onRoutineListener;
    }

    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bigroutine_card, parent, false);
        return new RoutineViewHolder(view, onRoutineListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {
        holder.setName(routinesList.get(position).getName());
        holder.setDescription(routinesList.get(position).getDescription());
        holder.setDuration(String.valueOf(routinesList.get(position).getDuration()));
        holder.setFavourite(routinesList.get(position).isFavorited());
        holder.setCategory1(routinesList.get(position).getLevelCategory1());
        holder.setCategory2(routinesList.get(position).getLevelCategory2());
    }

    @Override
    public int getItemCount() {
        return routinesList.size();
    }

    public interface OnRoutineListener {
        void onRoutineClick(int position);
    }

    public static class RoutineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, description, duration;
        RatingBar category1, category2;
        ImageView favourite;        //FALTA LA OTRA IMAGEVIEW DE PESAS PERO NO LO SOPORTA LA API
        OnRoutineListener onRoutineListener;

        public RoutineViewHolder(View itemView, OnRoutineListener onRoutineListener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_name_routine);
            description = (TextView) itemView.findViewById(R.id.tv_description_routine);
            duration = (TextView) itemView.findViewById(R.id.tv_duration_routine);
            category1 = (RatingBar) itemView.findViewById(R.id.rb_category1_routine);
            category2 = (RatingBar) itemView.findViewById(R.id.rb_category2_routine);
            favourite = (ImageView) itemView.findViewById(R.id.ivFavIconRoutine);

            this.onRoutineListener = onRoutineListener;
            itemView.setOnClickListener(this);
        }

        public void setName(String name) {
            this.name.setText(name);
        }

        public void setDescription(String description) {
            this.description.setText(description);
        }

        public void setDuration(String duration) {
            this.duration.setText(duration);
        }

        public void setCategory1(float category1) {
            this.category1.setRating(category1);
        }

        public void setCategory2(float category2) {
            this.category2.setRating(category2);
        }

        public void setFavourite(boolean favourite) {
            if (favourite)
                this.favourite.setImageResource(R.drawable.ic_favorite);
            else
                this.favourite.setImageResource(R.drawable.ic_favorite_border);
        }

        @Override
        public void onClick(View v) {
            onRoutineListener.onRoutineClick(getAdapterPosition());
        }
    }
}

