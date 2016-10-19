package com.example.antlerros95.chatroom;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by antlerros95 on 15/10/2016.
 */

public class UserListAdapter extends ArrayAdapter<User> {
    Context context;

    public UserListAdapter(Context context, int resourceID, List<User> items) {
        super(context, resourceID, items);
        this.context = context;
    }

    private class ViewHolder {
        ImageView userPortraitView;
        TextView userIDView;
        TextView userStatusView;

    }



    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        User user = getItem(position);

        LayoutInflater mInflater = LayoutInflater.from(context);

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_user_list, null);
            holder = new ViewHolder();
            holder.userPortraitView = (ImageView) convertView.findViewById(R.id.user_portrait);
            holder.userIDView = (TextView) convertView.findViewById(R.id.user_id);
            holder.userStatusView = (TextView) convertView.findViewById(R.id.user_status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            holder.userIDView.setText(user.getId());
            holder.userStatusView.setText(user.getStatus());

        } catch (Exception e) {
            String errorMan = user.getId();
            Log.d("User Item Error", errorMan);
        }

        return convertView;
    }

}
