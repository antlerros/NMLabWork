package com.example.antlerros95.chatroom;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login extends AppCompatActivity {

    private EditText _userIdLogin;
    private EditText _userPassword;
    Button loginBtn;
    String userIDText;
    String userPasswordText;
    JSONObject info = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _userIdLogin = (EditText) findViewById(R.id.userIDLogin);
        _userPassword = (EditText) findViewById(R.id.passwordLogin);
        loginBtn = (Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getInfo()) {
                    SendInfoTask sendRequest = new SendInfoTask(getBaseContext());
                    sendRequest.execute(info);
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        });

    }

    private boolean getInfo() {
        try {
            userIDText = _userIdLogin.getText().toString();
            userPasswordText = _userPassword.getText().toString();
        } catch (NumberFormatException e) {
            Toast.makeText(getBaseContext(),
                    "Wrong ID/Password!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            info.put("userID", userIDText);
            info.put("password", userPasswordText);
            return true;
        } catch (JSONException e) {
            Toast.makeText(getBaseContext(),
                    "Wrong ID/Password!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private class SendInfoTask extends AsyncTask<JSONObject, Void, String> {
        private Context mContext;

        public SendInfoTask(Context context) {
            mContext = context;
        }

        @Override
        protected String doInBackground(JSONObject... params) {
            String url = "http://140.112.18.193:4000/login/";
            URL object;
            HttpURLConnection con;
            ConnectivityManager ConnMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = ConnMgr.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected()) {
                System.out.println("Device no Connection!");
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
                    System.out.println("\nconnection failed\n");
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
            if (result == null) {
                System.out.println("response = null");
                return;
            }
            if (result.equals("Wrong")) {
                Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                v.vibrate(500);
                Toast.makeText(mContext,
                        "ID Not Found!",
                        Toast.LENGTH_SHORT).show();
            } else {
                if (result.equals("New")) {
                    Toast.makeText(mContext,
                            "Create New ID!",
                            Toast.LENGTH_SHORT).show();
                }
                Intent _intent = new Intent();
                _intent.setClass(Login.this, Idle.class);
                startActivity(_intent);
            }
        }
    }

}
