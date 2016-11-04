package com.ezz.findme.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ezz.findme.Models.User;
import com.ezz.findme.R;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class FriendsAdapter extends BaseAdapter {
    Context c;

    LayoutInflater ll;

    ArrayList<User> friends;

    public FriendsAdapter() {

    }
    public FriendsAdapter(Context c,ArrayList<User> friends) {
        this.c=c;
        this.friends=friends;
        ll= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return friends.size();
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

        View v=ll.inflate(R.layout.itemfriend,null);

        TextView tv=(TextView)v.findViewById(R.id.friendname);

        tv.setText(friends.get(position).getName());

        final CircleImageView circleImageView= (CircleImageView) v.findViewById(R.id.profile_image);

        Uri u= Uri.parse(friends.get(position).getPhoto());

        Picasso.with(c).load(u).placeholder(R.drawable.avatar).into(circleImageView);

        return v;
    }
}
