package com.example.healthcareapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.healthcareapp.DashboardFragment.HomeFragment;
import com.squareup.picasso.Picasso;

import java.sql.Connection;

import de.hdodenhof.circleimageview.CircleImageView;

public class Call_Video_Call extends Fragment {


TextView Phones,Names,Gmails,ids;
CircleImageView imageView;
String name,phone,gmail,uri,id;
Button Call,VideoCall;

    public Call_Video_Call(String name, String phone, String gmail,String id,String uri) {
        this.name = name;
        this.phone = phone;
        this.gmail = gmail;
        this.id = id;
        this.uri = uri;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_call__video__call, container, false);
        Phones = view.findViewById(R.id.txtPhones);
        Gmails = view.findViewById(R.id.txtgmail);
        ids = view.findViewById(R.id.txtId);
        Names = view.findViewById(R.id.txtName);
       imageView = view.findViewById(R.id.drProfiles);
         Names.setText("Dr."+name);
         ids.setText(id);
         Phones.setText(phone);
        Gmails.setText(gmail);
         Glide.with(getContext()).load(uri).into(imageView);

         Call = view.findViewById(R.id.call);
         Call.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                String phone_number = Phones.getText().toString();
                callPhone(phone_number);
             }
         });


       return view;
    }

    private void callPhone(String phone_number)
    {
      if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)!=
              PackageManager.PERMISSION_GRANTED){
          ActivityCompat.requestPermissions((Activity) getContext(),new String[]{(Manifest.permission.CALL_PHONE)},101);
      }
      else{
          Intent i = new Intent();
          i.setAction(Intent.ACTION_CALL);
          i.setData(Uri.parse("tel: "+phone_number));
          i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(i);
      }

    }


    public void onBackPressed(){
        AppCompatActivity activity = (AppCompatActivity) getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,new HomeFragment())
                .addToBackStack(null).commit();

    }

}