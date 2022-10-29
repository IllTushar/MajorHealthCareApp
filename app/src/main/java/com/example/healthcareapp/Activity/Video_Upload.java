package com.example.healthcareapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.healthcareapp.R;
import com.example.healthcareapp.Model.filemodel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Video_Upload extends AppCompatActivity {
VideoView videoView;
Button upload,browse;
MediaController mediaController;
EditText description;
Uri videoUri;
StorageReference storageReference;
DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_upload);
        description = findViewById(R.id.description);
        upload = findViewById(R.id.upload);
        browse = findViewById(R.id.browser);
        videoView = findViewById(R.id.video_view);
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);


        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("myvideo");
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.setType("video/*");
                    startActivityForResult(i,1);

            }

        });
          upload.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                 String Description = description.getText().toString();
                 if (Description.isEmpty()){
                     Toast.makeText(Video_Upload.this, "Enter Description!!", Toast.LENGTH_SHORT).show();
                     return;
                 }
                 else{
                     processVideoUploading();
                 }
              }
          });
    }
   public String getExtension(){
       MimeTypeMap mimeTypeMap =  MimeTypeMap.getSingleton();
       return mimeTypeMap.getMimeTypeFromExtension(getContentResolver().getType(videoUri));
   }
    private void processVideoUploading() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Media Uploader");
        pd.show();
       final StorageReference uploader = storageReference.child("myvideo/"+System.currentTimeMillis()+"."+getExtension());
        uploader.putFile(videoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                         uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                             @Override
                             public void onSuccess(Uri uri) {
                               filemodel obj = new filemodel(description.getText().toString(),uri.toString());
                               databaseReference.child(databaseReference.push().getKey()).setValue(obj)
                                       .addOnSuccessListener(new OnSuccessListener<Void>() {
                                           @Override
                                           public void onSuccess(Void unused) {
                                               pd.dismiss();
                                               Toast.makeText(Video_Upload.this, "Successfully Uploaded !!", Toast.LENGTH_SHORT).show();
                                           }
                                       })
                                       .addOnFailureListener(new OnFailureListener() {
                                           @Override
                                           public void onFailure(@NonNull Exception e) {
                                               pd.dismiss();
                                               Toast.makeText(Video_Upload.this, "Failed!!", Toast.LENGTH_SHORT).show();
                                           }
                                       });

                             }
                         });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float per = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        pd.setMessage("Uploaded: "+(int)per+"%");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 ){
            videoUri = data.getData();
            videoView.setVideoURI(videoUri);
            videoView.start();
        }
    }
}