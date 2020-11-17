package com.example.getkracking.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getkracking.R;
import com.example.getkracking.entities.ExerciseVO;

import java.util.ArrayList;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ExcerciseViewHolder> {

    ArrayList<ExerciseVO> excerciseList;

    public ExercisesAdapter(ArrayList<ExerciseVO> excerciseList) {
        this.excerciseList = excerciseList;
    }

    public static class ExcerciseViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView quantity;

        public ExcerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.ExcerciseImageInExcercise);
            name = (TextView) itemView.findViewById(R.id.NameValueInExcerciseCard);
            quantity = (TextView) itemView.findViewById(R.id.QuantityValueInExcerciseCard);
        }
    }

    @NonNull
    @Override
    public ExcerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.excercise_card, parent, false);
        return new ExcerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcerciseViewHolder holder, int position) {
        //holder.image.setImageResource(excerciseList.get(position).getImage());
        holder.name.setText(excerciseList.get(position).getName());
        if(excerciseList.get(position).getQuantity() > 0){
            holder.quantity.setText(String.valueOf(excerciseList.get(position).getQuantity()));
        }else
            holder.quantity.setText(String.valueOf(excerciseList.get(position).getDuration()));
    }

    @Override
    public int getItemCount() {
        return excerciseList.size();
    }
}
