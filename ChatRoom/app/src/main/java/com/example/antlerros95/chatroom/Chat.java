package com.example.antlerros95.chatroom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by antlerros95 on 23/10/2016.
 */

public class Chat extends AppCompatActivity {
    private String mfriendID;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        setContentView(R.layout.acitivity_chat);

        mfriendID = bundle.getString("friendID");

    }
}
