package com.example.healthcareapp.LoginRegistrationFragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthcareapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;

public class Bottom_sheet extends BottomSheetDialogFragment {
    EditText email;
    FirebaseAuth mAuth;
    Button btnreset;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
        email = root.findViewById( R.id.resetEmail );
        btnreset = root.findViewById( R.id.btnReset );
        mAuth = FirebaseAuth.getInstance();
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                boolean check = validation(Email);
                ProgressDialog pd = new ProgressDialog(getContext());
                pd.setTitle("Reset Password");
                pd.setMessage("Please wait..");
                pd.show();
                if (check==true){
                    mAuth.sendPasswordResetEmail(Email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        pd.dismiss();
//
                                        Toast.makeText(getContext(),"Please check your EmailID !!",Toast.LENGTH_SHORT ).show();
                                        return;
                                    } else {
                                        pd.dismiss();
                                        Toast.makeText(getContext(),"Enter valid EmailID !!",Toast.LENGTH_SHORT ).show();
                                        return;
                                    }
                                }
                            });
                }
                else {
                    pd.dismiss();

                }

            }
        });
    return root;
    }
    private boolean validation(String Email) {
        if (TextUtils.isEmpty( Email )) {
            email.requestFocus();
            email.setError( "Enter Email !!" );
            return false;
        } else {
            return true;
        }
    }
}