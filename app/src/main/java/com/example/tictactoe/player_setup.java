package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class player_setup extends AppCompatActivity {
   //defining edittext type variables to extract the names entered by the user
    private EditText player1;
    private EditText player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setup);
        //we need to assign values to the player1 and player2 variables
        player1=findViewById(R.id.editTextTextPersonName);
        player2=findViewById(R.id.editTextTextPersonName2);
    }

    public void submitButtonOnClick(View view)
    {
        //Within this method we are going to extract the player names that were entered by the user
        //and then bundle them in our intent and send it to Game Display Activity
        String player1name=player1.getText().toString();//extract the name and convert it to String
        String player2name=player2.getText().toString();
        //Now we need to bundle up the player names and send to the next activity
        if(player1name.equalsIgnoreCase(""))
        {
            //player1.setHint("please enter username");//it gives user to hint
            player1.setError("Required");//it gives user to info message //use any one //
        }
        else if(player2name.equalsIgnoreCase(""))
        {
            //player2.setHint("please enter username");
            player2.setError("Required");
        }
        else {
            Intent intent = new Intent(this, GameDisplay.class);
            intent.putExtra("PLAYER NAMES", new String[]{player1name, player2name});
            //putExtra method is used to add extended data to the intent
            //It takes two parameters(String,String array[])
            startActivity(intent);
        }
        //pass object name of the intent to start the new Activity

    }
}