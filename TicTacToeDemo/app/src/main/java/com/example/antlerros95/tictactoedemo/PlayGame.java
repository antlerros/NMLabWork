package com.example.antlerros95.tictactoedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



/**
 * Created by antlerros95 on 10/5/16.
 */

public class PlayGame extends Activity {
    private int times;
    private Button b[][];
    private int c[][];
    private static boolean Turn = true;
    private int player1_score = 0;
    private int player2_score = 0;
    private TextView score1;
    private TextView score2;

    class MyClickListener implements View.OnClickListener {
        int x;
        int y;


        public MyClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }


        public void onClick(View view) {
            if (b[x][y].isEnabled()) {
                b[x][y].setEnabled(false);
                if (Turn) {
                    b[x][y].setText("O");
                    c[x][y] = 1;
                } else {
                    b[x][y].setText("X");
                    c[x][y] = 2;
                }
                if(checkboard()){
                    score1.setText(Integer.toString(player1_score));
                    score2.setText(Integer.toString(player2_score));

                    if(times==score1 || times==score2{
                        switch_to_start_menu();
                    }
                    else setBoard();
                }
                Turn ^= true;
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        Bundle bundle = this.getIntent().getExtras();
        times = bundle.getInt("times");


        setBoard();



    }

    private void setBoard() {
        b = new Button[3][3];
        c = new int[3][3];
        score1 = (TextView)findViewById(R.id.score1);
        score2 = (TextView)findViewById(R.id.score2);

        b[0][0] = (Button) findViewById(R.id.button1);
        b[0][1] = (Button) findViewById(R.id.button2);
        b[0][2] = (Button) findViewById(R.id.button3);
        b[1][0] = (Button) findViewById(R.id.button4);
        b[1][1] = (Button) findViewById(R.id.button5);
        b[1][2] = (Button) findViewById(R.id.button6);
        b[2][0] = (Button) findViewById(R.id.button7);
        b[2][1] = (Button) findViewById(R.id.button8);
        b[2][2] = (Button) findViewById(R.id.button9);



        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                b[i][j].setOnClickListener(new MyClickListener(i,j));
                if(!b[i][j].isEnabled()) {
                    b[i][j].setText(" ");
                    b[i][j].setEnabled(true);
                }
                c[i][j] = 0;

            }
        }


    }

    private boolean checkboard(){
        boolean gameOver = false;
        if ((c[0][0] == 1 && c[1][1] == 1 && c[2][2] == 1)
                || (c[0][2] == 1 && c[1][1] == 1 && c[2][0] == 1)
                || (c[0][1] == 1 && c[1][1] == 1 && c[2][1] == 1)
                || (c[0][2] == 1 && c[1][2] == 1 && c[2][2] == 1)
                || (c[0][0] == 1 && c[0][1] == 1 && c[0][2] == 1)
                || (c[1][0] == 1 && c[1][1] == 1 && c[1][2] == 1)
                || (c[2][0] == 1 && c[2][1] == 1 && c[2][2] == 1)
                || (c[0][0] == 1 && c[1][0] == 1 && c[2][0] == 1)) {

            player1_score++;
            gameOver = true;
        } else if ((c[0][0] == 2 && c[1][1] == 2 && c[2][2] == 2)
                || (c[0][2] == 2 && c[1][1] == 2 && c[2][0] == 2)
                || (c[0][1] == 2 && c[1][1] == 2 && c[2][1] == 2)
                || (c[0][2] == 2 && c[1][2] == 2 && c[2][2] == 2)
                || (c[0][0] == 2 && c[0][1] == 2 && c[0][2] == 2)
                || (c[1][0] == 2 && c[1][1] == 2 && c[1][2] == 2)
                || (c[2][0] == 2 && c[2][1] == 2 && c[2][2] == 2)
                || (c[0][0] == 2 && c[1][0] == 2 && c[2][0] == 2)) {

            player2_score++;
            gameOver = true;
        } else {
            boolean empty = false;
            for(int i=0; i<=2; i++) {
                for(int j=0; j<=2; j++) {
                    if(c[i][j]==0) {
                        empty = true;
                        break;
                    }
                }
            }
            if(!empty) {
                gameOver = true;
            }
        }
        return gameOver;

    }

    private void switch_to_start_menu() {
        Intent _intent = new Intent();
        _intent.setClass(PlayGame.this, StartGame.class);
        startActivity(_intent);

    }

}
