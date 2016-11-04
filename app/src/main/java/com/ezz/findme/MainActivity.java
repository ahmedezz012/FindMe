package com.ezz.findme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {


    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null)
        {
            Intent i = new Intent(MainActivity.this, UserActivity.class);
            startActivity(i);
            finish();
        }else {
            Intent i = new Intent(MainActivity.this, SignUp.class);
            startActivity(i);
            finish();
        }
        setContentView(R.layout.activity_main);



    }

}
