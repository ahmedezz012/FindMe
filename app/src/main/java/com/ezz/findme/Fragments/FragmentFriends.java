package com.ezz.findme.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.ezz.findme.Adapters.FriendsAdapter;
import com.ezz.findme.MapsActivity;
import com.ezz.findme.Models.User;
import com.ezz.findme.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;


public class FragmentFriends extends Fragment {

    FirebaseAuth auth;

    DatabaseReference database;

    ArrayList<User> arrayList;

    FriendsAdapter fa;

    AlertDialog.Builder builder;

    Dialog d;

    TextView textView;

    public FragmentFriends() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        builder=new AlertDialog.Builder(getActivity());

        builder.setTitle("Delete");

        builder.setIcon(android.R.drawable.ic_dialog_alert);

        auth=FirebaseAuth.getInstance();

        database=FirebaseDatabase.getInstance().getReference();

        database.keepSynced(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_friends, container, false);

        textView = (TextView) v.findViewById(R.id.nofriends);
        textView.setVisibility(TextView.VISIBLE);

        final FirebaseUser u=auth.getCurrentUser();

        arrayList=new ArrayList<>();

        fa=new FriendsAdapter(getActivity(),arrayList);

        ListView lv= (ListView) v.findViewById(R.id.ListViewFriends);
        lv.setAdapter(fa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getActivity(), MapsActivity.class);
                i.putExtra("userid",  arrayList.get(position).getId());
                i.putExtra("username",arrayList.get(position).getName());
                startActivity(i);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final int pos=position;
                final String u_id=arrayList.get(pos).getId();
                builder.setMessage("are you sure you want to delete "+arrayList.get(position).getName());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database
                                .child("Users")
                                .child(u_id)
                                .child("friends")
                                .child(u.getUid()).removeValue();

                        database
                                .child("Users")
                                .child(u.getUid())
                                .child("friends")
                                .child(u_id)
                                .removeValue();
                        
                        Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dismiss();
                    }
                });

                d=builder.create();

                d.show();

                return false;
            }
        });
        database.child("Users").child(u.getUid()).child("friends").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                      User f=dataSnapshot.getValue(User.class);
                      arrayList.add(f);
                      fa.notifyDataSetChanged();
                      show_disappear(arrayList);
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
                    fa.notifyDataSetChanged();
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

    private void dismiss()
    {
        d.dismiss();
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
