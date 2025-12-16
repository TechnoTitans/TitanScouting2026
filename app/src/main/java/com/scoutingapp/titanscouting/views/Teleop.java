package com.scoutingapp.titanscouting.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.scoutingapp.titanscouting.R;
import com.scoutingapp.titanscouting.database.Match;
import com.scoutingapp.titanscouting.database.MatchViewModel;

public class Teleop extends AppCompatActivity {

    Match match;
    MatchViewModel matchViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleop_2);

        Button highScoredInc = findViewById(R.id.inc_high_scored);
        Button highScoredDec = findViewById(R.id.dec_high_scored);
        TextView highScored = findViewById(R.id.high_scored);
        Button lowScoredInc = findViewById(R.id.inc_low_scored);
        Button lowScoredDec = findViewById(R.id.dec_low_scored);
        TextView lowScored = findViewById(R.id.low_scored);

        Button highMissedInc = findViewById(R.id.inc_high_missed);
        Button highMissedDec = findViewById(R.id.dec_high_missed);
        TextView highMissed = findViewById(R.id.high_missed);
        TextView lowMissed = findViewById(R.id.low_missed);
        Button lowMissedInc = findViewById(R.id.inc_low_missed);
        Button lowMissedDec = findViewById(R.id.dec_low_missed);

        CheckBox moat = findViewById(R.id.moat);
        CheckBox ramparts = findViewById(R.id.rampart);
        CheckBox drawbridge = findViewById(R.id.drawbridge);
        CheckBox sallyPort = findViewById(R.id.sallyport);
        CheckBox rockWall = findViewById(R.id.rock_wall);
        CheckBox roughTerrain = findViewById(R.id.rough_terrain);
        CheckBox portcullis = findViewById(R.id.portcullis);
        CheckBox cheval = findViewById(R.id.cheval);

        Button backToAuto = findViewById(R.id.back_to_auto);
        Button toEndgame = findViewById(R.id.to_endgame);

        matchViewModel = new ViewModelProvider(this).get(MatchViewModel.class);
        matchViewModel.getMatch(getIntent().getIntExtra("matchNumber", 0)).observe(this, match -> {
            if(match == null) {
                finish();
                return;
            }
            this.match = match;

            //(TextView) ;

            highScored.setText(String.valueOf(match.getHighCount()));
            lowScored.setText(String.valueOf(match.getLowCount()));
            highMissed.setText(String.valueOf(match.getHighMissedCount()));
            lowMissed.setText(String.valueOf(match.getLowMissedCount()));



            highScoredInc.setOnClickListener(v -> {
                match.setHighCount(match.getHighCount() + 1);
                highScored.setText(String.valueOf(match.getHighCount()));
                matchViewModel.addMatchInformation(match);
            });
            highScoredDec.setOnClickListener(v -> {
                match.setHighCount(match.getHighCount() == 0 ? 0 : match.getHighCount() - 1);
                highScored.setText(String.valueOf(match.getHighCount()));
                matchViewModel.addMatchInformation(match);
            });
            lowScoredInc.setOnClickListener(v -> {
                match.setLowCount(match.getLowCount() + 1);
                lowScored.setText(String.valueOf(match.getLowCount()));
                matchViewModel.addMatchInformation(match);
            });
            lowScoredDec.setOnClickListener(v -> {
                match.setLowCount(match.getLowCount() == 0 ? 0 : match.getLowCount() - 1);
                lowScored.setText(String.valueOf(match.getLowCount()));
                matchViewModel.addMatchInformation(match);
            });
            highMissedInc.setOnClickListener(v -> {
                match.setHighMissedCount(match.getHighMissedCount() + 1);
                highMissed.setText(String.valueOf(match.getHighMissedCount()));
                matchViewModel.addMatchInformation(match);
            });
            highMissedDec.setOnClickListener(v -> {
                match.setHighMissedCount(match.getHighMissedCount() == 0 ? 0 : match.getHighMissedCount() - 1);
                highMissed.setText(String.valueOf(match.getHighMissedCount()));
                matchViewModel.addMatchInformation(match);
            });
            lowMissedInc.setOnClickListener(v -> {
                match.setLowMissedCount(match.getLowMissedCount() + 1);
                lowMissed.setText(String.valueOf(match.getLowMissedCount()));
                matchViewModel.addMatchInformation(match);
            });
            lowMissedDec.setOnClickListener(v -> {
                match.setLowMissedCount(match.getLowMissedCount() == 0 ? 0 : match.getLowMissedCount() - 1);
                lowMissed.setText(String.valueOf(match.getLowMissedCount()));
                matchViewModel.addMatchInformation(match);
            });

            moat.setChecked(match.isMoat());
            ramparts.setChecked(match.isRamparts());
            drawbridge.setChecked(match.isDrawbridge());
            sallyPort.setChecked(match.isSallyPort());
            rockWall.setChecked(match.isRockwall());
            roughTerrain.setChecked(match.isRoughterrain());
            portcullis.setChecked(match.isPortcullis());
            cheval.setChecked(match.isCheval());

            moat.setOnCheckedChangeListener((buttonView, isChecked) -> {
                match.setMoat(isChecked);
                matchViewModel.addMatchInformation(match);
            });
            ramparts.setOnCheckedChangeListener((buttonView, isChecked) -> {
                match.setRamparts(isChecked);
                matchViewModel.addMatchInformation(match);
            });
            drawbridge.setOnCheckedChangeListener((buttonView, isChecked) -> {
                match.setDrawbridge(isChecked);
                matchViewModel.addMatchInformation(match);
            });
            sallyPort.setOnCheckedChangeListener((buttonView, isChecked) -> {
                match.setSallyPort(isChecked);
                matchViewModel.addMatchInformation(match);
            });
            rockWall.setOnCheckedChangeListener((buttonView, isChecked) -> {
                match.setRockwall(isChecked);
                matchViewModel.addMatchInformation(match);
            });
            roughTerrain.setOnCheckedChangeListener((buttonView, isChecked) -> {
                match.setRoughterrain(isChecked);
                matchViewModel.addMatchInformation(match);
            });
            portcullis.setOnCheckedChangeListener((buttonView, isChecked) -> {
                match.setPortcullis(isChecked);
                matchViewModel.addMatchInformation(match);
            });
            cheval.setOnCheckedChangeListener((buttonView, isChecked) -> {
                match.setCheval(isChecked);
                matchViewModel.addMatchInformation(match);
            });


            backToAuto.setOnClickListener(v -> {
                Intent i = new Intent(Teleop.this, Autonomous.class);
                i.putExtra("matchNumber", match.getMatchNum());
                i.putExtra("color", match.getPosition().substring(0, 1));
                matchViewModel.addMatchInformation(match);
                startActivity(i);
                finish();
            });

            toEndgame.setOnClickListener(v -> {
                Intent i = new Intent(Teleop.this, Endgame2.class);
                i.putExtra("matchNumber", match.getMatchNum());
                matchViewModel.addMatchInformation(match);
                startActivity(i);
                finish();
            });


        });

    }
}