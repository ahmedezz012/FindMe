package com.ezz.findme.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ezz.findme.Adapters.RequestsAdapter;
import com.ezz.findme.Models.User;
import com.ezz.findme.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRequests extends Fragment {

    ArrayList<User> arrayList;
    FirebaseAuth auth;
    DatabaseReference database;
    RequestsAdapter ra;
    TextView textView;
    String id;
    public FragmentRequests() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();

        database= FirebaseDatabase.getInstance().getReference();

        database.keepSynced(true);

        id=auth.getCurrentUser().getUid();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_requests, container, false);
        arrayList=new ArrayList<>();
        textView = (TextView) v.findViewById(R.id.norequests);
        textView.setVisibility(TextView.VISIBLE);
        final ListView lv=(ListView)v.findViewById(R.id.listviewRequests);
        ra=new RequestsAdapter(getActivity(),arrayList,id);
        lv.setAdapter(ra);
        database.child("Users").child(auth.getCurrentUser().getUid()).child("requests").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User r=dataSnapshot.getValue(User.class);
                arrayList.add(r);
                ra.notifyDataSetChanged();
                Log.d("ahmedezz",arrayList.size()+" ");
                if(is_empty(arrayList))
                {
                    textView.setVisibility(TextView.VISIBLE);
                    arrayList.clear();
                }
                else
                    textView.setVisibility(TextView.INVISIBLE);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                User r=dataSnapshot.getValue(User.class);
                int position = -1;
                for(int i=0;i<arrayList.size();i++)
                {
                    if(arrayList.get(i).getEmail().equals(r.getEmail()))
                    {
                        position=i;
                        break;
                    }
                }
                if(position!=-1) {
                    arrayList.remove(position);
                    ra.notifyDataSetChanged();
                }
                show_disappear(arrayList);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }
    private boolean  is_empty(ArrayList<User> al)
    {
        return al.size()==0 ?true:false;
    }
    private void show_disappear(ArrayList<User> al)
    {
        if(is_empty(al))
        {
            textView.setVisibility(TextView.VISIBLE);
            al.clear();
        }
        else
            textView.setVisibility(TextView.INVISIBLE);
    }
}
