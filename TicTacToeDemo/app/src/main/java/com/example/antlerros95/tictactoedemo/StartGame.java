package com.example.antlerros95.tictactoedemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartGame extends Activity {

    private static final int DEFAULT_TIMES = 3;

    private EditText times_edt;
    private Button start_btn, leave_btn;
    private int times = DEFAULT_TIMES;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        times_edt = (EditText)findViewById(R.id.timesEditText);
        start_btn = (Button) findViewById(R.id.startButton);
        leave_btn = (Button) findViewById(R.id.leaveButton);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getTimes()) {
                    switchToPlayGame();
                }
            }
        });

        leave_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitWithAlert();
            }
        });
    }

    private boolean getTimes() {

        try {
            times = Integer.valueOf(times_edt.getText().toString());
            return true;
        } catch (NumberFormatException e) {
            times = DEFAULT_TIMES;
            Toast.makeText(getBaseContext(),
                    "Invalid Times!",
                    Toast.LENGTH_SHORT).show();
            times_edt.setText(String.valueOf(DEFAULT_TIMES));
            return false;
        }
    }

    private void switchToPlayGame() {
        Intent _intent = new Intent();
//        _intent.setClass(StartGame.this, PlayGame.class);

//        Bundle _bundle = new Bundle();
//        _bundle.putInt("times", times);
//
//        _intent.putExtras(_bundle);

//        startActivity(_intent);

    }

    private void exitWithAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(StartGame.this);

        builder.setTitle("警告");
        builder.setMessage("確定要離開嗎？");

        builder.setPositiveButton("是的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                System.exit(0);
            }
        });

        builder.setNegativeButton("ㄛ不是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
