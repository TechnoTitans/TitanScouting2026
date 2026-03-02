//package com.scoutingapp.titanscouting.views;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.ViewModelProvider;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
package com.scoutingapp.titanscouting.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.scoutingapp.titanscouting.R;
import com.scoutingapp.titanscouting.database.Match;
import com.scoutingapp.titanscouting.database.MatchViewModel;

public class Teleop extends AppCompatActivity {

    private Match match;
    private MatchViewModel matchViewModel;

    // 8x8 grid to store shot positions
    private final int[][] shotGrid = new int[8][8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleop_blue);

        matchViewModel = new ViewModelProvider(this).get(MatchViewModel.class);
        matchViewModel.getMatch(getIntent().getIntExtra("matchNumber", 0))
                .observe(this, match -> {
                    if (match == null) {
                        finish();
                        System.out.println(":(");
                        return;
                    }
                    this.match = match;

                    setupGridButtons();
                    setupNavigationButtons();
                });

        ImageButton yesSWM = findViewById(R.id.yesSWM);
        ImageButton noSWM = findViewById(R.id.noSWM);

        yesSWM.setOnClickListener(v -> {
            match.setShotWhileMoving(true);
            yesSWM.setImageAlpha(255);
            noSWM.setImageAlpha(130);;
        });

        noSWM.setOnClickListener(v -> {
            match.setShotWhileMoving(false);
            yesSWM.setImageAlpha(130);
            noSWM.setImageAlpha(255);;
        });
    }

    private void setupGridButtons() {
        Button[][] gridButtons = new Button[8][8];

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Buttons must be named button_0_0, button_0_1, ..., button_7_7 in XML
                int resID = getResources().getIdentifier(
                        "grid_" + row + "_" + col, "id", getPackageName());
                gridButtons[row][col] = findViewById(resID);

                int finalRow = row;
                int finalCol = col;

                gridButtons[row][col].setOnClickListener(v -> {
                    // Toggle shot: 0 = no shot, 1 = shot
                    shotGrid[finalRow][finalCol] = shotGrid[finalRow][finalCol] == 0 ? 1 : 0;
                    gridButtons[finalRow][finalCol].setAlpha(shotGrid[finalRow][finalCol] == 1 ? 0.4f : 0.0f);
                });
            }
        }
    }


    private void setupNavigationButtons() {
        Button backToAuto = findViewById(R.id.to_auto);
        Button toEndgame = findViewById(R.id.to_endgame);

        backToAuto.setOnClickListener(v -> {
            saveShotData();
            Intent i = new Intent(Teleop.this, Autonomous.class);
            i.putExtra("matchNumber", match.getMatchNum());
            i.putExtra("color", match.getPosition().substring(0, 1));
            startActivity(i);
            finish();
        });

        toEndgame.setOnClickListener(v -> {
            saveShotData();
            Intent i = new Intent(Teleop.this, Endgame.class);
            i.putExtra("matchNumber", match.getMatchNum());
            startActivity(i);
            finish();
        });
    }

    // Convert 2D grid into a string to save in database
    private String gridToString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (shotGrid[row][col] == 1) {
                    sb.append(row).append(",").append(col).append(";");
                }
            }
        }
        return sb.toString();
    }

    // Save the shot data to the match object
    private void saveShotData() {
        if (match != null) {
            match.setShotCoordinates(gridToString());
            matchViewModel.addMatchInformation(match);
        }
    }
}