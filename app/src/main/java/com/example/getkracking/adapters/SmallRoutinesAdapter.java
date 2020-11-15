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

public class SmallRoutinesAdapter extends RecyclerView.Adapter<SmallRoutinesAdapter.SmallRoutineViewHolder> {
    ArrayList<RoutineVO> routinesList;
    RoutinesAdapter.OnRoutineListener onRoutineListener;
    String type;

    public SmallRoutinesAdapter(ArrayList<RoutineVO> routinesList, RoutinesAdapter.OnRoutineListener onRoutineListener, String type) {
        this.routinesList = routinesList;
        this.onRoutineListener = onRoutineListener;
        this.type = type;
    }

    @NonNull
    @Override
    public SmallRoutinesAdapter.SmallRoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.smallroutine_card, parent, false);
        return new SmallRoutinesAdapter.SmallRoutineViewHolder(view, onRoutineListener, type);
    }

    @Override
    public void onBindViewHolder(@NonNull SmallRoutinesAdapter.SmallRoutineViewHolder holder, int position) {
        holder.name.setText(routinesList.get(position).getName());
        holder.description.setText(routinesList.get(position).getDescription());
        holder.duration.setText(String.valueOf(routinesList.get(position).getDuration()));

        if(routinesList.get(position).isFavorited())
            holder.favourite.setImageResource(R.drawable.ic_favorite);
        else
            holder.favourite.setImageResource(R.drawable.ic_favorite_border);

        holder.category1.setRating(routinesList.get(position).getLevelCategory1());
    }

    @Override
    public int getItemCount() {
        return routinesList.size();
    }

    public static class SmallRoutineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, description, duration;
        RatingBar category1;
        ImageView favourite;        //FALTA LA OTRA IMAGEVIEW DE PESAS PERO NO LO SOPORTA LA API
        RoutinesAdapter.OnRoutineListener onRoutineListener;
        String type;

        public SmallRoutineViewHolder(View itemview, RoutinesAdapter.OnRoutineListener onRoutineListener, String type) {
            super(itemview);
            name = (TextView) itemview.findViewById(R.id.tv_name_routine_small);
            description = (TextView) itemview.findViewById(R.id.tv_description_routine_small);
            duration = (TextView) itemview.findViewById(R.id.tv_duration_routine_small);
            category1 = (RatingBar) itemview.findViewById(R.id.rb_category1_routine_small);
            favourite = (ImageView) itemview.findViewById(R.id.ivFavIconRoutine_small);

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
