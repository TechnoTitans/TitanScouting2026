package com.scoutingapp.titanscouting.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.scoutingapp.titanscouting.Homepage;
import com.scoutingapp.titanscouting.R;
import com.scoutingapp.titanscouting.database.Match;
import com.scoutingapp.titanscouting.database.MatchViewModel;
import com.scoutingapp.titanscouting.views.logs.Logs;
import com.scoutingapp.titanscouting.Autofill;

public class Summary extends AppCompatActivity {

    LiveData<Match> liveDataMatch;

    Match match;

    MatchViewModel matchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Called when the activity is first created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary); // Sets the layout for the activity

// Finds views by their ID
        View submit = findViewById(R.id.submit);
        View back = findViewById(R.id.back);
        View delete = findViewById(R.id.delete);

// Initializes match object and ViewModel
        matchViewModel = new ViewModelProvider(this).get(MatchViewModel.class);

// Retrieves match data based on match number passed in the intent
        liveDataMatch = matchViewModel.getMatch(getIntent().getIntExtra("matchNumber", 0));

// Observes the match LiveData and updates the UI when data changes
        liveDataMatch.observe(this, match -> {
            if(match == null) {
                finish();
                return;
            }
            this.match = match;
            ((TextView) (findViewById(R.id.matchNumberSummary))).setText(String.valueOf(match.getMatchNum()));

            ((TextView) (findViewById(R.id.teamNumberSummary))).setText(String.valueOf(match.getTeamNumber()));

            ((TextView) (findViewById(R.id.teamPositionSummary))).setText(match.getPosition());

            ((TextView) (findViewById(R.id.scouterNameSummary))).setText(match.getScouterName());

            if (match.isNoShow()){
                ((TextView) (findViewById(R.id.noShowSummary))).setText("True");
            } else {
                ((TextView) (findViewById(R.id.noShowSummary))).setText("False");
            }

/*
            ((TextView) (findViewById(R.id.l1Summary))).setText(String.format("%d/%d", match.getL1Count(), match.getL1MissedCount()));

            ((TextView) (findViewById(R.id.l2Summary))).setText(String.format("%d/%d", match.getL2Count(), match.getL2MissedCount()));

            ((TextView) (findViewById(R.id.l3Summary))).setText(String.format("%d/%d", match.getL3Count(), match.getL3MissedCount()));

            ((TextView) (findViewById(R.id.l4Summary))).setText(String.format("%d/%d", match.getL4Count(), match.getL4MissedCount()));

            ((TextView) (findViewById(R.id.processorCountSummary))).setText(String.format("%d/%d", match.getProcessorCount(), match.getProcessorMissedCount()));

            ((TextView) (findViewById(R.id.netCountSummary))).setText(String.format("%d/%d", match.getNetCount(), match.getNetMissedCount()));

            ((TextView) (findViewById(R.id.endgamePosSummary))).setText(match.getEndgamePos());

            ((TextView) (findViewById(R.id.mechanicalReliabilitySummary))).setText(String.valueOf(match.getMechanicalReliability()));


            ((TextView) (findViewById(R.id.notesSummary))).setText(match.getNotes());*/

            // Sets click listener for submit button to navigate to QR
            submit.setOnClickListener(v -> {
                if(!Autofill.matchSubmitted[match.getMatchNum()]) {
                    SharedPreferences sharedPref = getSharedPreferences("ScoutingPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("matchNumber", match.getMatchNum() + 1);
                    editor.putString("position", match.getPosition());
                    System.out.println(match.getPosition());
                    editor.apply();
                }

                Intent i = new Intent(Summary.this, QRScreen.class);
                i.putExtra("matchNumber", match.getMatchNum());
                startActivity(i);
                finish();
            });

            delete.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(Summary.this);
                builder.setMessage("Are you sure you want to delete? ONLY CONTINUE IF YOU KNOW WHAT YOU'RE DOING!");
                builder.setTitle("Confirm Deletion");

                EditText passwordInput = new EditText(Summary.this);
                passwordInput.setHint("Enter password");
                passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(passwordInput);

                builder.setCancelable(false)
                        .setPositiveButton("Yes", (dialog, which) -> {
                            String enteredPassword = passwordInput.getText().toString();
                            String correctPassword = "l"; // .-.

                            if (enteredPassword.equals(correctPassword)) {
                                Toast.makeText(Summary.this, "Match deleted!", Toast.LENGTH_SHORT).show();
                                matchViewModel.deleteMatch(match.getMatchNum());

                                Intent intent = new Intent(Summary.this, Logs.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                new Handler(Looper.getMainLooper()).postDelayed(() -> finish(), 300);

                                dialog.dismiss();
                            } else {
                                Toast.makeText(Summary.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                        .show();
                System.out.println("ran");
            });

            // Sets click listener for back button to navigate to Endgame2 with match number
            back.setOnClickListener(v -> {
                Intent i;
                if(match.isNoShow()) {
                    i = new Intent(Summary.this, Pregame.class);
                    i.putExtra("transition", "true");
                }
                else{
                    i = new Intent(Summary.this, Endgame2.class);
                }
                i.putExtra("matchNumber", match.getMatchNum());
                startActivity(i);
                finish();
            });
        });

    }
}