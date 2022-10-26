package com.example.healthcareapp;

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
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcareapp.Model.DoctorResumeModel;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class DoctorResumeUpload extends AppCompatActivity {
TextView text;
FloatingActionButton upload,browse;
PDFView pdfView;
private Uri pdfUri;
FirebaseStorage firebaseStorage;
StorageReference storageReference;
FirebaseDatabase database;
DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_resume_upload);
        text = findViewById(R.id.title);
        String Name = getIntent().getStringExtra("Name");
        text.setText(Name);


        pdfView = findViewById(R.id.pdf);
        upload = findViewById(R.id.upload);
        browse = findViewById(R.id.browse);


        database =FirebaseDatabase.getInstance();
        databaseReference =database.getReference("Doctor's Resume");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePdf();
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               uploadPdf();
            }
        });
    }

    private void uploadPdf()
    {
       if (pdfUri!=null){
           ProgressDialog pd = new ProgressDialog(this);
           pd.show();
           pd.setMessage("Please wait...");
           StorageReference ref = storageReference.child("Doctor_Resume/*"+ UUID.randomUUID().toString());
           try {
               ref.putFile(pdfUri)
                       .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                           @Override
                           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                  ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                      @Override
                                      public void onSuccess(Uri uri) {
                                       pd.dismiss();
                                       String PDFURI = uri.toString();
                                       Pdf(PDFURI);
                                          Toast.makeText(DoctorResumeUpload.this, "Upload Succeessful !!", Toast.LENGTH_SHORT).show();
                                          Intent i = new Intent(DoctorResumeUpload.this,DoctorProfile.class);
                                          startActivity(i);
                                      }
                                  });

                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(DoctorResumeUpload.this, "Failed !!", Toast.LENGTH_SHORT).show();
                           }
                       })
                       .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                           @Override
                           public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                               double progress = (100.0*snapshot.getBytesTransferred()/snapshot
                                       .getTotalByteCount());
                               pd.setMessage("Uploaded "+(int)progress+"%");
                           }
                       });
           }catch (Exception e){
               e.printStackTrace();
           }
       }
    }

    private void Pdf(String pdfUri)
    {
       String Name = text.getText().toString();

       DoctorResumeModel doctorResumeModel = new DoctorResumeModel(Name,pdfUri);
       databaseReference.child(Name).setValue(doctorResumeModel);

    }

    private void choosePdf()
    {
        Intent i = new Intent();
        i.setType("application/pdf");
        i.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLaunch.launch(i);
    }
    ActivityResultLauncher<Intent>someActivityResultLaunch = registerForActivityResult(new
            ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
           if (result.getResultCode()== Activity.RESULT_OK){
               pdfUri = result.getData().getData();
               pdfView.fromUri(pdfUri).load();
           }
        }
    });
}