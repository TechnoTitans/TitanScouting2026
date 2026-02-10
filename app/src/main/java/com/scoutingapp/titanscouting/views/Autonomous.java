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

        // Setup UI Elements
        EditText autoText = findViewById(R.id.autoDisplay);
        Button leave = findViewById(R.id.leaveCheckbox);
        Button toPregame = findViewById(R.id.back_to_pregame);
        Button toTeleop = findViewById(R.id.to_teleop);
        Button yesDepot = findViewById(R.id.yes_depot);
        Button noDepot = findViewById(R.id.no_depot);
        Button yesClimb = findViewById(R.id.yes_climb);
        Button noClimb = findViewById(R.id.no_climb);
        Button yesCollectedFuel = findViewById(R.id.yes_collected_fuel);
        Button noCollectedFuel = findViewById(R.id.no_collected_fuel);
        Button yesScored = findViewById(R.id.yes_scored);
        Button noScored = findViewById(R.id.no_scored);
        Button yesWentToNeutral = findViewById(R.id.yes_went_to_neutral);
        Button noWentToNeutral = findViewById(R.id.no_went_to_neutral);


        // 0 for not pressed, 1 for no depot, 2 for yes depot
        yesDepot.setOnClickListener(v->{
            match.setDepot(2);
            yesDepot.setAlpha(1);
            noDepot.setAlpha(0.5f);
        });
        noDepot.setOnClickListener(
                v->{
                    match.setDepot(1);
                    noDepot.setAlpha(1);
                    yesDepot.setAlpha(0.5f);
                }
        );
        yesClimb.setOnClickListener(
                v->{
                    match.setClimb(2);
                    yesClimb.setAlpha(1);
                    noClimb.setAlpha(0.5f);
                }
        );
        noClimb.setOnClickListener(
                v->{
                    match.setClimb(1);
                    noClimb.setAlpha(1);
                    yesClimb.setAlpha(0.5f);
                }
        );
        yesCollectedFuel.setOnClickListener(
                v->{
                    match.setCollectedFuel(2);
                    yesCollectedFuel.setAlpha(1);
                    noCollectedFuel.setAlpha(0.5f);
                }
        );
        noCollectedFuel.setOnClickListener(
                v->{
                    match.setCollectedFuel(1);
                    noCollectedFuel.setAlpha(1);
                    yesCollectedFuel.setAlpha(0.5f);
                }
        );
        yesScored.setOnClickListener(
                v->{
                    match.setScored(2);
                    yesScored.setAlpha(1);
                    noScored.setAlpha(0.5f);
                }
        );
        noScored.setOnClickListener(
                v->{
                    match.setScored(1);
                    noScored.setAlpha(1);
                    yesScored.setAlpha(0.5f);

                }
        );
        yesWentToNeutral.setOnClickListener(
                v->{
                    match.setWentToNeutral(2);
                    yesWentToNeutral.setAlpha(1);
                    noWentToNeutral.setAlpha(0.5f);
                }
        );
        noWentToNeutral.setOnClickListener(
                v->{
                    match.setWentToNeutral(1);
                    noWentToNeutral.setAlpha(1);
                    yesWentToNeutral.setAlpha(0.5f);
                }
        );


        // Branch buttons A–L
        int[] branchIds = {
                R.id.branch_a, R.id.branch_b, R.id.branch_c, R.id.branch_d,
                R.id.branch_e, R.id.branch_f, R.id.branch_g, R.id.branch_h,
                R.id.branch_i, R.id.branch_j, R.id.branch_k, R.id.branch_l
        };
        String[] branches = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};

        // Other action buttons
        Button net = findViewById(R.id.net_button);
        Button processor = findViewById(R.id.processor_button);
        Button cs1 = findViewById(R.id.cs1_button);
        Button cs2 = findViewById(R.id.cs2_button);
        Button lollipopNet = findViewById(R.id.lollipop1);
        Button lollipopMiddle = findViewById(R.id.lollipop2);
        Button lollipopProcessor = findViewById(R.id.lollipop3);

        matchViewModel = new ViewModelProvider(this).get(MatchViewModel.class);
        int matchNum = getIntent().getIntExtra("matchNumber", 0);

        matchViewModel.getMatch(matchNum).observe(this, match -> {
            if(match == null) {
                finish();
                return;
            }
            this.match = match;

            autoText.setText(match.getAutoPath());

            if (match.getAutoPath() != null && match.getAutoPath().startsWith("LEAVE")) {
                leave.setChecked(true);
            } else {
                leave.setChecked(false);
            }

            autoText.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                    match.setAutoPath(s.toString().trim());
                }
                @Override public void afterTextChanged(Editable s) {}
            });

            resetButton.setOnClickListener(v -> {
                match.setAutoPath("");
                leave.setChecked(false);
                autoText.setText("");
            });

            leave.setOnClickListener(v -> {
                String current = match.getAutoPath() == null ? "" : match.getAutoPath();
                if (leave.isChecked()) {
                    if (!current.startsWith("LEAVE ")) {
                        match.setAutoPath("LEAVE " + current);
                        autoText.setText(match.getAutoPath());
                    }
                } else {
                    if (current.startsWith("LEAVE")) {
                        match.setAutoPath(current.substring(5));
                        autoText.setText(match.getAutoPath());
                    }
                    if (current.startsWith("LEAVE ")) {
                        match.setAutoPath(current.substring(6));
                        autoText.setText(match.getAutoPath());
                    }
                }
            });

            for (int i = 0; i < branchIds.length; i++) {
                String branch = branches[i];
                Button btn = findViewById(branchIds[i]);
                btn.setOnClickListener(v -> branchPopup(btn, branch, result -> {
                    safeAppendToAutoPath(result, autoText);
                }));
            }

            net.setOnClickListener(v -> safeAppendToAutoPath("N", autoText));
            processor.setOnClickListener(v -> safeAppendToAutoPath("P", autoText));
            cs1.setOnClickListener(v -> safeAppendToAutoPath("CS1", autoText));
            cs2.setOnClickListener(v -> safeAppendToAutoPath("CS2", autoText));
            lollipopNet.setOnClickListener(v -> safeAppendToAutoPath("Q1", autoText));
            lollipopMiddle.setOnClickListener(v -> safeAppendToAutoPath("Q2", autoText));
            lollipopProcessor.setOnClickListener(v -> safeAppendToAutoPath("Q3", autoText));

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
        });
    }

    private void safeAppendToAutoPath(String addition, EditText autoText) {
        if (match != null) {
            String base = match.getAutoPath() == null ? "" : match.getAutoPath();
            String newPath = base + addition;
            match.setAutoPath(newPath);
            autoText.setText(newPath);
        }
    }

    public void branchPopup(Button a, String branch, ResultCallback callback) {
        String[] levels = {"L4", "L3", "L2", "L1"};
        final int[] selectedPosition = {-1};
        final boolean[] isChecked = {false};

        View customLayout = LayoutInflater.from(this).inflate(R.layout.branch_popup_layout, null);
        RadioGroup radioGroup = customLayout.findViewById(R.id.radio_group);
        CheckBox checkBox = customLayout.findViewById(R.id.checkbox);

        for (String level : levels) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(level);
            radioButton.setId(View.generateViewId());
            radioGroup.addView(radioButton);
            radioButton.setOnClickListener(v -> selectedPosition[0] = radioGroup.indexOfChild(v));
        }

        checkBox.setOnCheckedChangeListener((buttonView, isCheckedValue) -> isChecked[0] = isCheckedValue);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Select Level")
                .setView(customLayout)
                .setPositiveButton("Confirm", (dialog, id) -> {
                    if (selectedPosition[0] != -1) {
                        String selectedLevel = levels[selectedPosition[0]];
                        String suffix = isChecked[0] ? "M" : "S";
                        callback.onResult(branch + (4 - selectedPosition[0]) + suffix);
                    } else {
                        Toast.makeText(this, "No level selected", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());

        builder.create().show();
    }

    public interface ResultCallback {
        void onResult(String result);
    }
}