package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class PlayerName extends AppCompatActivity {
    public static boolean multiplayer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name);


        final EditText playerNameEt = findViewById(R.id.playerNameEt);
        final Button startGameBtn = findViewById(R.id.startGameBtn);
        final RadioButton r_play_with_computer = findViewById(R.id.r_play_with_computer);
        final RadioButton r_with_online_player = findViewById(R.id.r_with_online_player);

        r_with_online_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiplayer = true;
            }
        });

        r_play_with_computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiplayer = false;
            }
        });

        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting player name from Edit Text
                final String getPlayerName = playerNameEt.getText().toString();
                //checking wheather player has entered his name
                if (getPlayerName.isEmpty()) {
                    Toast.makeText(PlayerName.this, "Please enter player name", Toast.LENGTH_SHORT).show();
                } else {
                    //create intent to open MainActivity
                    Intent intent = null;
                    if (multiplayer) {
                        intent = new Intent(PlayerName.this, OnlineMode.class);
                    } else {
                        intent = new Intent(PlayerName.this, ComputerMode.class);

                    }
                    // adding player name along with intent
                    intent.putExtra("playerName", getPlayerName);
                    // opening MainActivity
                    startActivity(intent);
                    //destroy this activity
                    finish();
                }
            }
        });
    }
}