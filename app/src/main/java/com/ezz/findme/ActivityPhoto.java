package com.ezz.findme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ActivityPhoto extends AppCompatActivity {

    DatabaseReference db;

    ImageView photo;

    ImageButton gallery,camera;


    StorageReference storage;

    FirebaseAuth auth;

    String uid;

    Uri uri;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        pd=new ProgressDialog(ActivityPhoto.this);
        pd.setTitle("loading");

        db=FirebaseDatabase.getInstance().getReference();
        storage=FirebaseStorage.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        uid=auth.getCurrentUser().getUid();

        photo= (ImageView) findViewById(R.id.imageViewPhoto);

        camera= (ImageButton) findViewById(R.id.imageButtonPickfromcamera);

        gallery=(ImageButton)findViewById(R.id.imageButtonPickfromgallery);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,100);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,200);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if(requestCode==100 && resultCode==RESULT_OK)
            {
                Bitmap bm=(Bitmap) data.getExtras().get("data");
                photo.setImageBitmap(bm);
                uri=data.getData();

            }else if(requestCode==200 && resultCode==RESULT_OK)
            {
                uri=data.getData();
                String[] p={MediaStore.Images.Media.DATA};
                Cursor c=getContentResolver().query(uri,p,null,null,null);
                c.moveToFirst();
                int index=c.getColumnIndex(p[0]);
                String filepath=c.getString(index);
                c.close();
                photo.setImageBitmap(BitmapFactory.decodeFile(filepath));
            }
    }
    public void Confirm(View v)
    {
      if(uri!=null) {
          pd.show();
          putfile(uri);
      }
        else
          Toast.makeText(ActivityPhoto.this,"upload photo first",Toast.LENGTH_LONG).show();
    }
    private void putfile(Uri u)
    {
        storage.child("photos").child(uid).child(u.getLastPathSegment()).putFile(u)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        db.child("Users").child(uid).child("photo").setValue(taskSnapshot.getDownloadUrl().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                pd.dismiss();
                                Toast.makeText(ActivityPhoto.this,"photo updated successfully",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ActivityPhoto.this,UserActivity.class));
                                finish();
                            }
                        });
                    }
                })
                 .addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         pd.dismiss();
                         Toast.makeText(ActivityPhoto.this,e.getMessage(),Toast.LENGTH_LONG).show();
                     }
                 });
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this,"choose photo first",Toast.LENGTH_LONG).show();
    }
}
