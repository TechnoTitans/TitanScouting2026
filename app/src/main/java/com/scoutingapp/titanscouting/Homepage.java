package com.scoutingapp.titanscouting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.scoutingapp.titanscouting.views.Endgame;
import com.scoutingapp.titanscouting.views.Pregame;
import com.scoutingapp.titanscouting.views.Teleop;
import com.scoutingapp.titanscouting.views.logs.Logs;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the main layout
        setContentView(R.layout.activity_main);

        // Initialize buttons from main layout
        Button pregameButton = findViewById(R.id.button);
        Button logsButton = findViewById(R.id.logs_btn);
        Button buttonNext = findViewById(R.id.button2);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button8 = findViewById(R.id.button8);
        // pregameButton opens Pregame activity
        pregameButton.setOnClickListener(v -> {
            startActivity(new Intent(Homepage.this, Pregame.class));
        });

        // logsButton opens Logs activity
        logsButton.setOnClickListener(v -> {
            startActivity(new Intent(Homepage.this, Logs.class));
        });

        // buttonNext swaps layout to schedule.xml (just shows screenshot)
        buttonNext.setOnClickListener(v -> {
            setContentView(R.layout.schedule);

            // back button inside schedule.xml
            Button backButton = findViewById(R.id.backButton);
            backButton.setOnClickListener(view -> {
                // Return to the main layout

            });

        });

        button5.setOnClickListener(v -> {

            Intent i = new Intent(Homepage.this, Pregame.class);
            i.putExtra("matchNumber", 6);
            startActivity(i);
            finish();
        });
        button8.setOnClickListener(v -> {
            button8.setText("Text Changed!");
        });
    }
}





