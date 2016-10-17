package com.example.antlerros95.chatroom;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    String response;


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
                    SendInfoTask sendRequest = new SendInfoTask();
                    sendRequest.execute(info);
                } else {
                    return;
                }
                if(response.equals("Right")) {
                    Intent _intent = new Intent();
                    _intent.setClass(Login.this, Idle.class);
                    startActivity(_intent);
                } else if (response.equals("New")) {
                    Toast.makeText(getBaseContext(),
                            "Create New ID!",
                            Toast.LENGTH_SHORT).show();
                } else if (response.equals("Wrong")) {
                    Toast.makeText(getBaseContext(),
                            "ID Not Found!",
                            Toast.LENGTH_SHORT).show();
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
        @Override
        protected String doInBackground(JSONObject... params) {
            String url = "http://140.112.18.193/login";
            URL object;
            HttpURLConnection con;
            int HttpResult;
            try {
                object = new URL(url);
            } catch (MalformedURLException e) {
                Toast.makeText(getBaseContext(),
                        "Invalid URL!",
                        Toast.LENGTH_SHORT).show();
                return null;
            }
            try {
                con = (HttpURLConnection) object.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestMethod("POST");
                for (JSONObject item : params) {
                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                    wr.write(item.toString());
                    wr.flush();
                }
                HttpResult = con.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_OK) {
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
                }
            } catch (IOException e) {
                Toast.makeText(getBaseContext(),
                        "Fail to connect!",
                        Toast.LENGTH_SHORT).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            response = result;
        }
    }
}
