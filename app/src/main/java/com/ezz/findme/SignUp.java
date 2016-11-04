package com.ezz.findme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    FirebaseAuth Auth;

    DatabaseReference db;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        Auth= FirebaseAuth.getInstance();

        db= FirebaseDatabase.getInstance().getReference();

        pd=new ProgressDialog(this);

        pd.setTitle("loading");
    }
    public void SignUp(View v)
    {
        boolean problem=false;
        final String Mail=((EditText)findViewById(R.id.editTextmail)).getText().toString();
        String pass=((EditText)findViewById(R.id.editTextpass)).getText().toString();
        String cpass=((EditText)findViewById(R.id.editTextconfpass)).getText().toString();
        final String name=((EditText)findViewById(R.id.editTextName)).getText().toString();

        if(!pass.equals(cpass)){
           Toast.makeText(this,"passwords not matched",Toast.LENGTH_SHORT).show();
            problem=true;
        }
        if(TextUtils.isEmpty(Mail))
        {
            Toast.makeText(this,"u have to write email",Toast.LENGTH_SHORT).show();
            problem=true;
        }
        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this,"u have to write name",Toast.LENGTH_SHORT).show();
            problem=true;
        }
        if(!problem)
        {
            pd.show();
            Auth.createUserWithEmailAndPassword(Mail,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    pd.dismiss();
                    reference(authResult.getUser().getUid(),"name").setValue(name);

                    reference(authResult.getUser().getUid(),"email").setValue(Mail);

                    Toast.makeText(SignUp.this,"Signed up",Toast.LENGTH_LONG).show();

                    startActivity(new Intent(SignUp.this,ActivityPhoto.class));
                    finish();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(SignUp.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    public void Login(View v)
    {
        startActivity(new Intent(SignUp.this, Login.class));
        finish();
    }
    private DatabaseReference reference(String id,String child)
    {
        return db.child("Users").child(id).child(child);
    }
}
