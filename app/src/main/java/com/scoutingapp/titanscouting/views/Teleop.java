package com.scoutingapp.titanscouting.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.CheckBox;

import com.scoutingapp.titanscouting.R;
import com.scoutingapp.titanscouting.database.Match;
import com.scoutingapp.titanscouting.database.MatchViewModel;


public class Teleop extends AppCompatActivity {

    private Match match;
    private MatchViewModel matchViewModel;

    private int cycleCount = 0;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        matchViewModel = new ViewModelProvider(this).get(MatchViewModel.class);
        matchViewModel.getMatch(getIntent().getIntExtra("matchNumber", 0))
                .observe(this, m -> {
                    if (m == null) {
                        finish();
                        return;
                    }
                    this.match = m;
                    if (match.getPosition().charAt(0) == 'R') {
                        setContentView(R.layout.activity_teleop_red);
                    } else {
                        setContentView(R.layout.activity_teleop_blue);
                    }
                    setupButtons();
                });
    }



    private void setupButtons() {
        Button backToAuto = findViewById(R.id.to_auto);
        Button toEndgame = findViewById(R.id.to_endgame);

        Button addbutton = findViewById(R.id.addbutton);
        Button minusbutton = findViewById(R.id.minusbutton);

        CheckBox tier1 = findViewById(R.id.TIER1);
        CheckBox tier2 = findViewById(R.id.TIER2);
        CheckBox tier3 = findViewById(R.id.TIER3);

        TextView cycleText = findViewById(R.id.cycleText);

        cycleCount = match.getCycleCount();
        cycleText.setText(String.valueOf(cycleCount));

        int tier = match.getTier();
        tier1.setChecked(tier == 1);
        tier2.setChecked(tier == 2);
        tier3.setChecked(tier == 3);

        backToAuto.setOnClickListener(v -> {
            match.setCycleCount(cycleCount);
            matchViewModel.addMatchInformation(match);
            Intent i = new Intent(Teleop.this, Autonomous.class);
            i.putExtra("matchNumber", match.getMatchNum());
            startActivity(i);
            finish();
        });

        toEndgame.setOnClickListener(v -> {
            match.setCycleCount(cycleCount);
            matchViewModel.addMatchInformation(match);
            Intent i = new Intent(Teleop.this, Endgame.class);
            i.putExtra("matchNumber", match.getMatchNum());
            startActivity(i);
            finish();
        });




        addbutton.setOnClickListener(v -> {
            cycleCount++;
            cycleText.setText(String.valueOf(cycleCount));

        });

        minusbutton.setOnClickListener(v -> {
            if (cycleCount > 0) {
                cycleCount--;
            }
            cycleText.setText(String.valueOf(cycleCount));

        });

        tier1.setOnClickListener(v -> {
            tier2.setChecked(false);
            tier3.setChecked(false);
            match.setTier(1);
        });

        tier2.setOnClickListener(v -> {
            tier1.setChecked(false);
            tier3.setChecked(false);
            match.setTier(2);
        });

        tier3.setOnClickListener(v -> {
            tier1.setChecked(false);
            tier2.setChecked(false);
            match.setTier(3);
        });
    }



}