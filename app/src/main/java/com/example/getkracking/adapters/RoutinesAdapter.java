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
    String type;        // para diferenciar entre distintos adapters
    public RoutinesAdapter(ArrayList<RoutineVO> routinesList, OnRoutineListener onRoutineListener, String type) {
        this.routinesList = routinesList;
        this.onRoutineListener = onRoutineListener;
        this.type = type;
    }

    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bigroutine_card, parent, false);
        return new RoutineViewHolder(view, onRoutineListener, type);
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

    public interface OnRoutineListener {
        void onRoutineClick(int position, String type);
    }

    public static class RoutineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, description, duration;
        RatingBar category1, category2;
        ImageView favourite;        //FALTA LA OTRA IMAGEVIEW DE PESAS PERO NO LO SOPORTA LA API
        OnRoutineListener onRoutineListener;
        String type;

        public RoutineViewHolder(View itemView, OnRoutineListener onRoutineListener, String type) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_name_routine);
            description = (TextView) itemView.findViewById(R.id.tv_description_routine);
            duration = (TextView) itemView.findViewById(R.id.tv_duration_routine);
            category1 = (RatingBar) itemView.findViewById(R.id.rb_category1_routine);
            category2 = (RatingBar) itemView.findViewById(R.id.rb_category2_routine);
            favourite = (ImageView) itemView.findViewById(R.id.ivFavIconRoutine);

            this.onRoutineListener = onRoutineListener;
            itemView.setOnClickListener(this);

            this.type = type;
        }

        @Override
        public void onClick(View v) {
            onRoutineListener.onRoutineClick(getAdapterPosition(), type);
        }
    }
}

