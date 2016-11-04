package com.ezz.findme;

import android.app.ActivityManager;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ezz.findme.Adapters.ViewPagerAdapter;
import com.ezz.findme.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {

    String TheService=".MyService";

    FirebaseAuth auth;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        auth = FirebaseAuth.getInstance();

        final String id = auth.getCurrentUser().getUid();

        databaseReference.child("Users").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Util.user = dataSnapshot.getValue(User.class);
                Util.user.setId(id);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager vp=(ViewPager)findViewById(R.id.viewpager);
        vp.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        vp.setPageTransformer(true,new CubeOutTransformer());

        TabLayout tl= (TabLayout) findViewById(R.id.tab);
        tl.setupWithViewPager(vp);
        vp.setCurrentItem(1);

        if(!isServiceRunning())
        {
            startService(new Intent(UserActivity.this,MyService.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.signout:
                stopService(new Intent(this, MyService.class));
                auth.signOut();
                startActivity(new Intent(this, SignUp.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean isServiceRunning()
    {
        ActivityManager am= (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service:am.getRunningServices(Integer.MAX_VALUE))
        {
               if(TheService.equals(service.service.getClassName()))
               {
                   return true;
               }
        }
        return  false;
    }
}
