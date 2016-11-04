package com.ezz.findme;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import com.ezz.findme.Models.location;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyService extends Service {
    private final IBinder binder=new localbinder();

    FirebaseAuth auth;

    DatabaseReference dr;

    location ul;

    String id;

    public class localbinder extends Binder
    {
        MyService getService()
        {
            return MyService.this;
        }
    }

    public MyService() {

    }

    @Override
    public void onCreate() {

        super.onCreate();
        dr = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        id = auth.getCurrentUser().getUid();

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }

        if(auth.getCurrentUser()!=null) {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (location == null) {
                    } else {
                        ul = new location(location.getLatitude(), location.getLongitude());

                        dr.child("Users")
                                .child(id)
                                .child("location")
                                .setValue(ul);
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }

    }
    public location getLocation()
    {
        return ul;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
       return binder;
    }

}
