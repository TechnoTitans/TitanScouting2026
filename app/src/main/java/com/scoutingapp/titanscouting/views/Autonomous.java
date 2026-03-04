package com.scoutingapp.titanscouting.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.api.ResultCallback;
import com.scoutingapp.titanscouting.R;
import com.scoutingapp.titanscouting.database.Match;
import com.scoutingapp.titanscouting.database.MatchViewModel;
import android.widget.ImageButton;

import java.util.Objects;

public class Autonomous extends AppCompatActivity {

    Match match;
    MatchViewModel matchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(
                Objects.equals(getIntent().getStringExtra("color"), "B") ?
                        R.layout.activity_autonomous_blue : R.layout.activity_autonomous_red
        );

        Button toPregame = findViewById(R.id.to_pregame);
        Button toTeleop = findViewById(R.id.to_teleop);
        ImageButton yesDepot = findViewById(R.id.yes_depot);
        ImageButton noDepot = findViewById(R.id.no_depot);
        ImageButton yesClimb = findViewById(R.id.yes_climb);
        ImageButton noClimb = findViewById(R.id.no_climb);
        ImageButton yesCollectedFuel = findViewById(R.id.yes_collected_fuel);
        ImageButton noCollectedFuel = findViewById(R.id.no_collected_fuel);
        ImageButton yesScored = findViewById(R.id.yes_scored);
        ImageButton noScored = findViewById(R.id.no_scored);
        ImageButton yesWentToNeutral = findViewById(R.id.yes_went_to_neutral);
        ImageButton noWentToNeutral = findViewById(R.id.no_went_to_neutral);


        // 0 for not pressed, 1 for no depot, 2 for yes depot
        yesDepot.setOnClickListener(v->{
            match.setDepot(2);
            yesDepot.setImageAlpha(255);
            noDepot.setImageAlpha(130);
        });
        noDepot.setOnClickListener(
                v->{
                    match.setDepot(1);
                    noDepot.setImageAlpha(255);
                    yesDepot.setImageAlpha(130);
                }
        );
        yesClimb.setOnClickListener(
                v->{
                    match.setClimb(2);
                    yesClimb.setImageAlpha(255);
                    noClimb.setImageAlpha(130);
                }
        );
        noClimb.setOnClickListener(
                v->{
                    match.setClimb(1);
                    noClimb.setImageAlpha(255);
                    yesClimb.setImageAlpha(130);
                }
        );
        yesCollectedFuel.setOnClickListener(
                v->{
                    match.setCollectedFuel(2);
                    yesCollectedFuel.setImageAlpha(255);
                    noCollectedFuel.setImageAlpha(130);
                }
        );
        noCollectedFuel.setOnClickListener(
                v->{
                    match.setCollectedFuel(1);
                    noCollectedFuel.setImageAlpha(255);
                    yesCollectedFuel.setImageAlpha(130);
                }
        );
        yesScored.setOnClickListener(
                v->{
                    match.setScored(2);
                    yesScored.setImageAlpha(255);
                    noScored.setImageAlpha(130);
                }
        );
        noScored.setOnClickListener(
                v->{
                    match.setScored(1);
                    noScored.setImageAlpha(255);
                    yesScored.setImageAlpha(130);

                }
        );
        yesWentToNeutral.setOnClickListener(
                v->{
                    match.setWentToNeutral(2);
                    yesWentToNeutral.setImageAlpha(255);
                    noWentToNeutral.setImageAlpha(130);
                }
        );
        noWentToNeutral.setOnClickListener(
                v->{
                    match.setWentToNeutral(1);
                    noWentToNeutral.setImageAlpha(255);
                    yesWentToNeutral.setImageAlpha(130);
                }
        );



        matchViewModel = new ViewModelProvider(this).get(MatchViewModel.class);
        int matchNum = getIntent().getIntExtra("matchNumber", 0);

        matchViewModel.getMatch(matchNum).observe(this, match -> {
            if (match == null) {
                finish();
                return;
            }
            this.match = match;
        });

        toPregame.setOnClickListener(v -> {
                matchViewModel.addMatchInformation(match);
                Intent intent = new Intent(Autonomous.this, Pregame.class);
                intent.putExtra("transition", "true");
                intent.putExtra("matchNumber", match.getMatchNum());
                startActivity(intent);
                finish();
        });

        toTeleop.setOnClickListener(v -> {
            matchViewModel.addMatchInformation(match);
            Intent intent = new Intent(Autonomous.this, Teleop.class);
            intent.putExtra("matchNumber", match.getMatchNum());
            startActivity(intent);
            finish();
        });
    }
}