package com.ezz.findme;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.IBinder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Polyline;
import com.ezz.findme.Models.Directions.TheDirections;
import com.ezz.findme.Models.location;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ezz.findme.MyService.localbinder;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,View.OnClickListener {

    private GoogleMap mMap;
    String userid,username;
    location userLocation,mylocation;
    DatabaseReference databaseReference;
    MyService myService;
    boolean isBound=false;
    Button showDirections;
    RequestQueue requestQueue;
    ProgressDialog pd;
    Gson gson;
    TheDirections theDirections;
    ArrayList<Polyline> polylinePaths;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbarmaps);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pd=new ProgressDialog(this);
        pd.setTitle("loading");

        gson=new Gson();

        requestQueue= Volley.newRequestQueue(this);

        showDirections=(Button)findViewById(R.id.showdirections);
        showDirections.setOnClickListener(this);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        Intent intent=new Intent(this,MyService.class);

        bindService(intent,connection, Context.BIND_ABOVE_CLIENT);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        userid = getIntent().getStringExtra("userid");

        username = getIntent().getStringExtra("username");

        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        switch (id)
        {
            case R.id.normal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.satellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.terrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.hybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        databaseReference.child("Users").child(userid).child("location")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        userLocation=dataSnapshot.getValue(location.class);
                        LatLng friendloc = new LatLng(userLocation.getLat(),userLocation.getLon());

                        mMap.clear();

                        mMap.addMarker(new MarkerOptions().position(friendloc).title(username)).showInfoWindow();

                        LatLng mylocationlatlng = new LatLng(mylocation.getLat(),mylocation.getLon());

                        mMap.addMarker(new MarkerOptions().position(mylocationlatlng).
                                                           title("You").
                                                           icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(friendloc));
                        mMap.moveCamera(CameraUpdateFactory.zoomTo(14));

                        mMap.getUiSettings().setMapToolbarEnabled(false);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
    }

    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
           localbinder b=(localbinder) service;
            myService=b.getService();
            isBound=true;
            mylocation=myService.getLocation();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound=false;
        }
    };

    @Override
    public void onClick(View v) {
        pd.show();
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                "https://maps.googleapis.com/maps/api/directions/json?origin=" +
                        convertlatlng(mylocation) +
                        "&destination=" + convertlatlng(userLocation) +
                        "&key=" + getString(R.string.DirectionKey)
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                theDirections=gson.fromJson(response,TheDirections.class);
                DrawPath(decodePolyLine(theDirections.getRoutes().get(0).getOverview_polyline().getPoints()));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(MapsActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    private String convertlatlng(location l)
    {
        return l.getLat()+","+l.getLon();
    }

    private List<LatLng> decodePolyLine(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d, lng / 100000d
            ));
        }

        return decoded;
    }

    private void DrawPath(List<LatLng> listlatlng)
    {
        polylinePaths=new ArrayList<>();
        PolylineOptions polylineOptions = new PolylineOptions().
                geodesic(true).
                color(Color.BLUE).
                width(10);

        for (int i = 0; i < listlatlng.size(); i++)
            polylineOptions.add(listlatlng.get(i));

        polylinePaths.add(mMap.addPolyline(polylineOptions));
    }
}
