package com.example.healthcareapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healthcareapp.Model.DoctorModel;
import com.example.healthcareapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends FirebaseRecyclerAdapter<DoctorModel,RecyclerViewAdapter.myViewHolder>
{

    public RecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<DoctorModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull DoctorModel model) {
                  holder.name.setText(model.getName());
                  holder.phone.setText(model.getPhone());
                  holder.id.setText(model.getConferenceId());

        Glide.with(holder.imagesViews.getContext())
                .load(model.getImage())
                .into(holder.imagesViews);

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_xml,parent,false);
       return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView imagesViews;
        TextView name,phone,id;
    public myViewHolder(@NonNull View itemView) {
        super(itemView);
        imagesViews = itemView.findViewById(R.id.rec_image_profile);
        name = itemView.findViewById(R.id.drname);
        phone = itemView.findViewById(R.id.Drphone);
        id = itemView.findViewById(R.id.Drid);
    }
}
}
