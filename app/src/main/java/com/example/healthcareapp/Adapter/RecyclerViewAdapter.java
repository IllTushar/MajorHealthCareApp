package com.example.healthcareapp.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healthcareapp.Activity.Call;
import com.example.healthcareapp.Call_Video_Call;
import com.example.healthcareapp.Model.DoctorModel;
import com.example.healthcareapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.core.Context;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends FirebaseRecyclerAdapter<DoctorModel,RecyclerViewAdapter.myViewHolder>
{

static int PERMISSION_CODE=101;
    View mView;
    Context mContext;
    public RecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<DoctorModel> options) {
        super(options);
//        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull DoctorModel model) {
                  holder.name.setText("Dr."+model.getName());
                  holder.phone.setText(model.getPhone());
                  holder.id.setText(model.getConferenceId());
                  holder.email.setText(model.getGmail());
        Glide.with(holder.imagesViews.getContext())
                .load(model.getImage())
                .into(holder.imagesViews);
           holder.call.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   try {
                       AppCompatActivity activity = (AppCompatActivity) v.getContext();
                       activity.getSupportFragmentManager().beginTransaction()
                               .replace(R.id.FrameLayout,new Call_Video_Call(model.getName(), model.getPhone(), model.getGmail(),
                                       model.getConferenceId(), model.getImage()))
                               .addToBackStack(null).commit();
                   }catch (Exception e){
                       e.printStackTrace();
                   }
               }
           });

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
        TextView name,phone,id,email;
        TextView call;
    public myViewHolder(@NonNull View itemView) {
        super(itemView);
        imagesViews = itemView.findViewById(R.id.rec_image_profile);
        name = itemView.findViewById(R.id.drname);
        phone = itemView.findViewById(R.id.Drphone);
        id = itemView.findViewById(R.id.Drid);
        email = itemView.findViewById(R.id.drgmail);
        call =itemView.findViewById(R.id.drcall);
    }
}
}
