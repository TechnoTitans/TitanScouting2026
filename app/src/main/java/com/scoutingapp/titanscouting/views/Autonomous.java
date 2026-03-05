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
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Objects;

public class Autonomous extends AppCompatActivity {

    Match match;
    MatchViewModel matchViewModel;

    private ArrayList<String> pathSteps = new ArrayList<>();
    private TextView pathListText;

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

        pathListText = findViewById(R.id.path_list_text);
        refreshPathDisplay();


        // 0 for not pressed, 1 for no depot, 2 for yes depot
        yesDepot.setOnClickListener(v->{
            match.setDepot(2);
            yesDepot.setImageAlpha(255);
            noDepot.setImageAlpha(130);

            addPathStep("Depot");
        });
        noDepot.setOnClickListener(
                v->{
                    match.setDepot(1);
                    noDepot.setImageAlpha(255);
                    yesDepot.setImageAlpha(130);

                    removeLastPathStep("Depot");
                }
        );
        yesClimb.setOnClickListener(
                v->{
                    match.setClimb(2);
                    yesClimb.setImageAlpha(255);
                    noClimb.setImageAlpha(130);
                    addPathStep("Climb");
                }
        );
        noClimb.setOnClickListener(
                v->{
                    match.setClimb(1);
                    noClimb.setImageAlpha(255);
                    yesClimb.setImageAlpha(130);
                    removeLastPathStep("Climb");
                }
        );
        yesCollectedFuel.setOnClickListener(
                v->{
                    match.setCollectedFuel(2);
                    yesCollectedFuel.setImageAlpha(255);
                    noCollectedFuel.setImageAlpha(130);
                    addPathStep("Collected fuel");
                }
        );
        noCollectedFuel.setOnClickListener(
                v->{
                    match.setCollectedFuel(1);
                    noCollectedFuel.setImageAlpha(255);
                    yesCollectedFuel.setImageAlpha(130);
                    removeLastPathStep("Collected fuel");
                }
        );
        yesScored.setOnClickListener(
                v->{
                    match.setScored(2);
                    yesScored.setImageAlpha(255);
                    noScored.setImageAlpha(130);
                    addPathStep("Scored");
                }
        );
        noScored.setOnClickListener(
                v->{
                    match.setScored(1);
                    noScored.setImageAlpha(255);
                    yesScored.setImageAlpha(130);
                    removeLastPathStep("Scored");

                }
        );
        yesWentToNeutral.setOnClickListener(
                v->{
                    match.setWentToNeutral(2);
                    yesWentToNeutral.setImageAlpha(255);
                    noWentToNeutral.setImageAlpha(130);
                    addPathStep("Went to neutral");
                }
        );
        noWentToNeutral.setOnClickListener(
                v->{
                    match.setWentToNeutral(1);
                    noWentToNeutral.setImageAlpha(255);
                    yesWentToNeutral.setImageAlpha(130);
                    removeLastPathStep("Went to neutral");
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
            loadPathFromCode(match.getAutoPath());
        });

//

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

    private void addPathStep(String step) {
        pathSteps.add(step);
        savePathCodeToMatch();
        refreshPathDisplay();
    }

    private void removeLastPathStep(String step) {
        for (int i = pathSteps.size() - 1; i >= 0; i--) {
            if (pathSteps.get(i).equals(step)) {
                pathSteps.remove(i);
                break;
            }
        }
        savePathCodeToMatch();
        refreshPathDisplay();
    }

    private void refreshPathDisplay() {
        if (pathListText == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append("Path:\n");

        for (int i = 0; i < pathSteps.size(); i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(pathSteps.get(i))
                    .append("\n");
        }

        pathListText.setText(sb.toString());
    }

    private String buildAutonPathCode() {
        StringBuilder sb = new StringBuilder();

        for (String step : pathSteps) {
            if ("Depot".equals(step)) {
                sb.append("D");
            } else if ("Climb".equals(step)) {
                sb.append("C");
            } else if ("Collected fuel".equals(step)) {
                sb.append("F");
            } else if ("Scored".equals(step)) {
                sb.append("S");
            } else if ("Went to neutral".equals(step)) {
                sb.append("N");
            }
        }
        return sb.toString();
    }

    private void loadPathFromCode(String code) {
        pathSteps.clear();

        if (code == null || code.isEmpty()) {
            refreshPathDisplay();
            return;
        }

        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);

            if (c == 'D') {
                pathSteps.add("Depot");
            } else if (c == 'C') {
                pathSteps.add("Climb");
            }else if (c == 'F') {
                pathSteps.add("Collected fuel");
            }else if (c == 'S') {
                pathSteps.add("Scored");
            }else if (c == 'N') {
                pathSteps.add("Went to neutral");
            }
        }

        refreshPathDisplay();
    }

    private void savePathCodeToMatch() {
        if (match == null) return;
        match.setAutoPath(buildAutonPathCode());
    }
}