package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void playButtonClick(View view) {
        Intent intent = new Intent(this, player_setup.class);
        //player_setup.class denotes the activity that we want to launch
        startActivity(intent);
        //when this method will be executed then it will load the player_setup activity
        //we also need to link this method logic with the play button in the xml file
    }
    }