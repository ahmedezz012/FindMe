package com.ezz.findme.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.ezz.findme.Models.User;
import com.ezz.findme.R;
import com.ezz.findme.UserStates;
import com.ezz.findme.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSearch extends Fragment implements View.OnClickListener {


    private final String sended="sended_requests";
    private final String recivied="requests";
    private final String friends="friends";

    FirebaseAuth auth;

    DatabaseReference db;

    Dialog d;

    TextView tv;

    CircleImageView civ;

    AlertDialog.Builder builder;

    View viewdialog;

    Button addbutton,search;

    String id;

    EditText email;

    User user;

    boolean is_sended_requests=false;

    boolean is_request_to_me=false;

    boolean is_friend=false;

    UserStates userStates;

    public FragmentSearch() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();

        id=auth.getCurrentUser().getUid();

        db=FirebaseDatabase.getInstance().getReference();

        db.keepSynced(true);

        userStates=UserStates.addfriend;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search, container, false);

        email= (EditText) view.findViewById(R.id.editTextsearchedmail);
        email.setHintTextColor(getResources().getColor(R.color.MyBackGround));

        search= (Button) view.findViewById(R.id.buttonSearch);
        search.setOnClickListener(this);

        viewdialog = ((LayoutInflater)(getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))).inflate(R.layout.dialog_add,null);

        addbutton= (Button) viewdialog.findViewById(R.id.buttonAdd);
        addbutton.setOnClickListener(this);

        builder=new AlertDialog.Builder(getActivity());
        tv = (TextView) viewdialog.findViewById(R.id.textViewsearchedname);
        civ = (CircleImageView) viewdialog.findViewById(R.id.imageviewsearched);

        builder.setView(viewdialog);

        d=builder.create();

        d.setCanceledOnTouchOutside(true);


        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonSearch)
        {
            if(!(email.getText().toString().equals(auth.getCurrentUser().getEmail()))) {
                userStates = UserStates.addfriend;
                addbutton.setText("Add");
                //search in sended requests
                ValueEventListener valueEventListenersended = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            User u = ds.getValue(User.class);
                            if (u.getEmail().equals(email.getText().toString())) {
                                user = u;
                                is_sended_requests = true;
                                addbutton.setText("Cancel Request");
                                userStates = UserStates.cancelrequest;
                                tv.setText(u.getName());
                                Picasso.with(getActivity())
                                        .load(Uri.parse(u.getPhoto()))
                                        .placeholder(R.drawable.avatar)
                                        .error(R.drawable.avatar)
                                        .into(civ);
                                d.show();
                                email.setText("");
                                break;
                            }
                        }
                        if (!is_sended_requests) {
                            ValueEventListener valueEventListenerrequested = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        User u = ds.getValue(User.class);
                                        if (u.getEmail().equals(email.getText().toString())) {
                                            is_request_to_me = true;
                                            user = u;
                                            userStates = UserStates.accept;
                                            addbutton.setText("Accept");
                                            tv.setText(u.getName());
                                            Picasso.with(getActivity())
                                                    .load(Uri.parse(u.getPhoto()))
                                                    .placeholder(R.drawable.avatar)
                                                    .error(R.drawable.avatar)
                                                    .into(civ);
                                            d.show();
                                            email.setText("");
                                            break;
                                        }
                                    }
                                    if (!is_request_to_me) {
                                        ValueEventListener valueEventListenerfriends = new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                    User u = ds.getValue(User.class);
                                                    if (u.getEmail().equals(email.getText().toString())) {
                                                        is_friend = true;
                                                        user = u;
                                                        addbutton.setText("Delete");
                                                        userStates = UserStates.delete;
                                                        tv.setText(u.getName());
                                                        Picasso.with(getActivity())
                                                                .load(Uri.parse(u.getPhoto()))
                                                                .placeholder(R.drawable.avatar)
                                                                .error(R.drawable.avatar)
                                                                .into(civ);
                                                        d.show();
                                                        email.setText("");
                                                        break;
                                                    }
                                                }
                                                if (!is_friend) {
                                                    db.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {

                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            boolean b = false;
                                                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                                User u = ds.getValue(User.class);
                                                                u.setId(ds.getKey());
                                                                if (u.getEmail().equals(email.getText().toString())) {
                                                                    user = u;
                                                                    tv.setText(u.getName());
                                                                    Picasso.with(getActivity())
                                                                            .load(Uri.parse(u.getPhoto()))
                                                                            .placeholder(R.drawable.avatar)
                                                                            .error(R.drawable.avatar)
                                                                            .into(civ);
                                                                    d.show();
                                                                    email.setText("");
                                                                    b = true;
                                                                    break;
                                                                }
                                                            }
                                                            if (!b) {
                                                                Toast.makeText(getActivity(), "not exists", Toast.LENGTH_LONG).show();
                                                                email.setText("");
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });
                                                } else
                                                    is_friend = false;
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        };
                                        DatabaseReference databaseReferencefriends = reference(id, friends);
                                        databaseReferencefriends.addListenerForSingleValueEvent(valueEventListenerfriends);
                                    } else
                                        is_request_to_me = false;
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            };
                            DatabaseReference databaseReferencerequests = reference(id, recivied);
                            databaseReferencerequests.addListenerForSingleValueEvent(valueEventListenerrequested);
                        } else
                            is_sended_requests = false;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                DatabaseReference databaseReferencesended = reference(id, sended);
                databaseReferencesended.addListenerForSingleValueEvent(valueEventListenersended);
            }
            else
            {
                Toast.makeText(getActivity(),"You Can't search for yourself",Toast.LENGTH_SHORT).show();
            }
        }
        else if(v.getId()==R.id.buttonAdd)
        {
           switch (userStates)
           {
               case addfriend:
                   //add user to my sended
                   reference(id,sended).child(user.getId()).setValue(user);

                   //add me to user requests
                   reference(user.getId(),recivied).child(id).setValue(Util.user);

                   Toast.makeText(getActivity(),"request sended successfully",Toast.LENGTH_SHORT).show();

                   d.dismiss();
                   break;

               case delete:
                   //remove from my friends
                   reference(id,friends).child(user.getId()).removeValue();

                   //remove from his friends
                   reference(user.getId(),friends).child(id).removeValue();

                   Toast.makeText(getActivity(),user.getName()+" deleted from friends",Toast.LENGTH_SHORT).show();

                   d.dismiss();
                   break;

               case cancelrequest:
                   // remove user from my sended
                   reference(id,sended).child(user.getId()).removeValue();

                   //remove me from user requests
                   reference(user.getId(),recivied).child(id).removeValue();

                   Toast.makeText(getActivity(),"request canceled successfully",Toast.LENGTH_SHORT).show();

                   d.dismiss();
                   break;

               case accept:
                   //delete from sended of the sender
                   reference(user.getId(),sended).child(id).removeValue();

                   //delete from my requests
                   reference(id,recivied).child(user.getId()).removeValue();

                   //add to my friends
                   reference(id,friends).child(user.getId()).setValue(user);

                   //add to his friends
                   reference(user.getId(),friends).child(id).setValue(Util.user);

                   Toast.makeText(getActivity(),"Accepted successfully",Toast.LENGTH_SHORT).show();

                   d.dismiss();

                   break;
               default:
                   break;
           }
        }
    }

    private DatabaseReference reference(String Userid,String kind)
    {
        return db.child("Users").child(Userid).child(kind);
    }
}
