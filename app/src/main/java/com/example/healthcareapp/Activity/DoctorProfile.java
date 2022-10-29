package com.example.healthcareapp.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthcareapp.Model.DoctorModel;
import com.example.healthcareapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorProfile extends AppCompatActivity {
private EditText name,phone,gmail,meetingId;
private CircleImageView imageView;
private Uri imguri;
private int REQUEST_CODE=22;

Button save;
FirebaseDatabase database;
DatabaseReference databaseReference;
FirebaseStorage firebaseStorage;
StorageReference storageReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        //FindView by Id..
        name  = findViewById(R.id.DrName);
        phone = findViewById(R.id.DrPhone);
        gmail = findViewById(R.id.DrEmail);
        meetingId = findViewById(R.id.ConferenceId);
        imageView = findViewById(R.id.drimg);

        save = findViewById(R.id.save_profile);


        //Firebase..
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Doctor's Details");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseImage();
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              imageUpload();
            }
        });

    }



    private void uploadDetials(String imagesUri)
    {
       String Name = name.getText().toString();
       String Phone = phone.getText().toString();
       String Gmail = gmail.getText().toString();
       String ConfirenceID = meetingId.getText().toString();
       imagesUri  = imagesUri.toString();
       DoctorModel doctorModel = new DoctorModel(Name,Gmail,Phone,ConfirenceID,imagesUri);
       databaseReference.child(Phone).setValue(doctorModel);
       name.setText("");
       phone.setText("");
       gmail.setText("");
       meetingId.setText("");
       imageView.setImageResource(R.drawable.profile);

    }

    private void imageUpload()
    {
       if(imguri!=null)
       {
           ProgressDialog pd = new ProgressDialog(this);
           pd.setMessage("Please wait..");
           pd.show();
           StorageReference ref = storageReference.child("images/*"+ UUID.randomUUID().toString());
           try
           {
             ref.putFile(imguri)
                     .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                         @Override
                         public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                          ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                              @Override
                              public void onSuccess(Uri uri) {
                               pd.dismiss();
                               String imageUri = uri.toString();
                               String Name = name.getText().toString();
                               uploadDetials(imageUri);
                               Toast.makeText(DoctorProfile.this, "Upload Succeessful !!", Toast.LENGTH_SHORT).show();
                               Intent i = new Intent(DoctorProfile.this, DoctorResumeUpload.class);
                               i.putExtra("Name",Name);
                               startActivity(i);
                              }
                          });
                         }
                     }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Toast.makeText(DoctorProfile.this, "Failed !!", Toast.LENGTH_SHORT).show();
                         }
                     }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                         @Override
                         public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                             double progress = (100.0*snapshot.getBytesTransferred()/snapshot
                                     .getTotalByteCount());
                             pd.setMessage("Uploaded "+(int)progress+"%");
                         }
                     });
           }
           catch (Exception e)
           {
               e.printStackTrace();
           }
       }
    }


    private void choseImage()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
       someActivityResultLauncher.launch(i);
    }

    ActivityResultLauncher<Intent>someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()== Activity.RESULT_OK){
                        imguri = result.getData().getData();
                        imageView.setImageURI(imguri);
                    }
                }
            }
    );


}