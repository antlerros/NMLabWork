package com.example.antlerros95.chatroom;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by antlerros95 on 15/10/2016.
 */

public class Idle extends AppCompatActivity {
    private ArrayList<User> userList;
    private ListView userListView;
    private UserListAdapter adapter;
    private String mUserID;
    private String mFriendID = "";
    final Handler handler = new Handler();
    private boolean mExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        setContentView(R.layout.activity_idle);
        userList = new ArrayList<User>();
        userListView = (ListView)findViewById(R.id.userListView);
        adapter = new UserListAdapter(this, R.layout.item_user_list, userList);
        userListView.setAdapter(adapter);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User friend = (User) userListView.getItemAtPosition(position);
                mFriendID = friend.getId();
            }
        });

        mUserID = bundle.getString("userID");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                requestHttp(mExit, mFriendID);
                handler.postDelayed(this, 2000);
            }
        };
        handler.postDelayed(runnable, 2000);
    }



    protected JSONObject createInfoJSON(boolean exit, String callID) {
        JSONObject accountInfo = new JSONObject();
        try {
            accountInfo.put("ID", mUserID);
            accountInfo.put("Exit", exit);
            accountInfo.put("Call_ID", callID);
        } catch (JSONException e) {
            System.out.println("Null ID\n");
        }
        return accountInfo;
    }

    protected void requestHttp(boolean exit, String callID) {
        final Handler handler = new Handler();
        LoadContactToServer loader = new LoadContactToServer(getBaseContext());
        if (!exit) {
            if (callID.equals("")) {
                loader.execute(createInfoJSON(false, ""));
            } else {
                loader.execute(createInfoJSON(false, callID));
            }
        } else {
            loader.execute(createInfoJSON(true, ""));
        }
    }
    protected void jumpToChat(String friendID) {
        if (friendID == null) {
            return;
        }
        Intent _intent = new Intent();
        _intent.setClass(Idle.this, Chat.class);
        _intent.setClass(Idle.this, Chat.class);
        Bundle bundle = new Bundle();
        bundle.putString("friendID", friendID);
        _intent.putExtras(bundle);
        startActivity(_intent);
    }


    private class LoadContactToServer extends AsyncTask<JSONObject, Void, String> {
        private Context mContext;

        public LoadContactToServer(Context context) {
            mContext = context;
        }

        @Override
        protected String doInBackground(JSONObject... params) {
            String url = "http://140.112.18.193:4000/idle/";
            URL object;
            HttpURLConnection con;
            ConnectivityManager ConnMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = ConnMgr.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected()) {
                System.out.println("Device no Connection!\n");
            }
            try {
                object = new URL(url);
                con = (HttpURLConnection) object.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestMethod("POST");
                con.connect();
                for (JSONObject item : params) {
                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
                    wr.write(item.toString());
                    wr.flush();
                    wr.close();
                }
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    try {
                        BufferedReader input = new BufferedReader(
                                new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuilder result = new StringBuilder();
                        while ((inputLine = input.readLine()) != null) {
                            result.append(inputLine);
                        }
                        input.close();
                        return result.toString();
                    } catch (IOException e) {
                        System.out.println("no response!\n");
                    }
                } else {
                    System.out.println(con.getResponseMessage());
                    System.out.println("connection failed\n");
                }
            } catch (MalformedURLException e) {
                System.out.println("Invalid URL!");
                return null;
            } catch (IOException e) {
                System.out.println("Fail to connect!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result  == null) {
                System.out.println("no result!\n");
                return;
            }
            JSONObject returnInformation;
            boolean connection = true;
            boolean chat = false;
            JSONArray jArray;
            String friendID;
            try {
                returnInformation = new JSONObject(result);
                connection = (boolean) returnInformation.get("Connection");
                chat = (boolean) returnInformation.get("Chatting");
                jArray = (JSONArray) returnInformation.get("OnlineList");
                friendID = (String) returnInformation.get("Friend_ID");
                if (chat){
                    System.out.println("chatting mode...\n");
                    jumpToChat(friendID);
                } else if (connection) {
                    System.out.println("waiting mode...\n");
                    if (jArray == null) {
                        System.out.println("no Online List\n");
                        return;
                    }
                    System.out.print(jArray);
                    userList.clear();
                    for (int i = 0; i < jArray.length(); i++){
                        String name = jArray.get(i).toString();
                        System.out.println(name + "\n");
                        userList.add(new User(name,
                                "online"));
                    }
                    adapter.notifyDataSetChanged();

                }
            } catch (JSONException e) {
                System.out.println("unable to catch response\n");
            }

        }


    }
}
