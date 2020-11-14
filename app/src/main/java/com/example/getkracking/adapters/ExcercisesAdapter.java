package com.example.getkracking.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getkracking.R;
import com.example.getkracking.entities.ExcerciseVO;

import java.util.ArrayList;

public class ExcercisesAdapter extends RecyclerView.Adapter<ExcercisesAdapter.ExcerciseViewHolder> {

    ArrayList<ExcerciseVO> excerciseList;

    public ExcercisesAdapter(ArrayList<ExcerciseVO> excerciseList) {
        this.excerciseList = excerciseList;
    }

    public class ExcerciseViewHolder extends RecyclerView.ViewHolder {

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
        holder.quantity.setText(excerciseList.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return excerciseList.size();
    }
}
