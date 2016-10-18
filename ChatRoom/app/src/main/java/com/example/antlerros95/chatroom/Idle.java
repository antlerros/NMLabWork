package com.example.antlerros95.chatroom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by antlerros95 on 15/10/2016.
 */

public class Idle extends AppCompatActivity {
    private ArrayList<User> userList;
    private ListView userListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idle);
        userList = new ArrayList<User>();
        userListView = (ListView)findViewById(R.id.userListView);
    }

}
