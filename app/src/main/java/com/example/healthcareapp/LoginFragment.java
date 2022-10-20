package com.example.healthcareapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginFragment extends Fragment {
    EditText loginPassword,loginEmail;
    TextView forgetPassword;
    Button login;
    FirebaseAuth mAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_login, container, false);
        forgetPassword = root.findViewById(R.id.forgetPassword);
        loginEmail = root.findViewById(R.id.loginEmail);
        loginPassword = root.findViewById(R.id.loginPassword);
        login =root.findViewById(R.id.btnlogin);
        mAuth = FirebaseAuth.getInstance();

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bottom_sheet bottomSheet = new Bottom_sheet();
                bottomSheet.show(getFragmentManager(), bottomSheet.getTag());
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 ProgressDialog pd = new ProgressDialog(getContext());
                pd.setTitle("Login");
                pd.setMessage("Please wait");
                pd.show();
                String Emails = loginEmail.getText().toString();
                String Password =loginPassword.getText().toString();
                if(Emails.isEmpty()){
                    pd.dismiss();
                    Toast.makeText(getContext(), "Enter Email!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(Password.isEmpty()){
                    pd.dismiss();
                    Toast.makeText(getContext(), "Enter Valid Password!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    mAuth.signInWithEmailAndPassword(Emails, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                pd.dismiss();
                                Intent i = new Intent(getActivity().getApplication(), DashBoard.class);
                                i.putExtra("email",Emails);
                                i.putExtra("password",Password);
                                startActivity(i);
                            } else {
                                pd.dismiss();
                                Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        return root;
    }
}