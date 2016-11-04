package com.ezz.findme.Adapters;

import android.content.Context;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.ezz.findme.Models.User;
import com.ezz.findme.R;
import com.ezz.findme.Util;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;


public class RequestsAdapter extends BaseAdapter  implements View.OnClickListener{

    int CurPos;

    String id;

    Context context;

    LayoutInflater ll;

    ArrayList<User> _requests;

    DatabaseReference db;

    private final String sended="sended_requests";

    private final String recivied="requests";

    private final String friends="friends";

    public RequestsAdapter() {

    }
    public RequestsAdapter(Context context, ArrayList<User> _requests, String id) {
        this.context=context;
        this.id=id;
        this._requests=_requests;
        ll= (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        db=FirebaseDatabase.getInstance().getReference();
    }
    @Override
    public int getCount() {
        return _requests.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CurPos=position;

        View v=ll.inflate(R.layout.itemfriendrequest,null);

        TextView name= (TextView) v.findViewById(R.id.textViewrequestername);

        name.setText(_requests.get(position).getName());

        final CircleImageView circleImageView=(CircleImageView)v.findViewById(R.id.imageViewRequester);

        Uri u=Uri.parse(_requests.get(position).getPhoto());

        Picasso
                .with(context)
                .load(u)
                .placeholder(R.drawable.avatar)
                .into(circleImageView);

        Button accept= (Button) v.findViewById(R.id.buttonAccept);
        accept.setOnClickListener(this);

        Button ignore=(Button)v.findViewById(R.id.buttonIgnore);
        ignore.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        User u = new User(_requests.get(CurPos).getId(),
                _requests.get(CurPos).getEmail(),
                _requests.get(CurPos).getName(),
                _requests.get(CurPos).getPhoto());
        if(v.getId()==R.id.buttonAccept) {
            removing(u);
            adding(u);
        }
        else if(v.getId()==R.id.buttonIgnore)
        {
             removing(u);
        }
    }

    private DatabaseReference reference(String Userid,String kind)
    {
        return db.child("Users").child(Userid).child(kind);
    }
    private void removing(User u)
    {
        //remove from sender sended requests
        reference(u.getId(),sended).child(id).removeValue();

        //remove from my requests
        reference(id,recivied).child(u.getId()).removeValue();
    }
    private void adding(User u)
    {
        //add to my friends
        reference(id,friends).child(u.getId()).setValue(u);

        //add me to his friends
        reference(u.getId(),friends).child(id).setValue(Util.user);
    }

}
