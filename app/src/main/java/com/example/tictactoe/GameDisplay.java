package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameDisplay extends AppCompatActivity {

    private TicTacToeBoard ticTacToeBoard;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_display);

        Button playAgainBTN=findViewById(R.id.play_again);
        Button homeBTN= findViewById(R.id.home_button);
        TextView playerTurn=findViewById(R.id.player_display);

        playAgainBTN.setVisibility(View.GONE);
        homeBTN.setVisibility(View.GONE);

        String[] playerNames=getIntent().getStringArrayExtra("PLAYER NAMES");
        if(playerNames!=null)
        {
            playerTurn.setText(playerNames[0]+"'s Turn");
        }

        ticTacToeBoard=findViewById(R.id.ticTacToeBoard);
        ticTacToeBoard.setUpGame(playAgainBTN,homeBTN,playerTurn,playerNames);
    }

    public void playagainOnClick(View view)
    {
        ticTacToeBoard.resetGame();
        ticTacToeBoard.invalidate();
    }
    public void homebuttonOnClick(View view)
    {
        //Create intent to load home page again
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        //to start up the main activity
    }
}