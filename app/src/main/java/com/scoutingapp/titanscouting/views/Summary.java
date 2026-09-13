package com.scoutingapp.titanscouting.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.scoutingapp.titanscouting.R;
import com.scoutingapp.titanscouting.database.Match;
import com.scoutingapp.titanscouting.database.MatchViewModel;
import com.scoutingapp.titanscouting.views.logs.Logs;

public class Summary extends AppCompatActivity {

    private Match match;
    private MatchViewModel matchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        matchViewModel = new ViewModelProvider(this).get(MatchViewModel.class);
        int matchNum = getIntent().getIntExtra("matchNumber", 0);

        matchViewModel.getMatch(matchNum).observe(this, match -> {
            if (match == null) {
                finish();
                return;
            }
            this.match = match;
            populateSummary(match);
        });

        Button back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Intent i = new Intent(Summary.this, Endgame.class);
            i.putExtra("matchNumber", match.getMatchNum());
            matchViewModel.addMatchInformation(match);
            startActivity(i);
            finish();
        });

        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(v -> {
            Intent i = new Intent(Summary.this, QRScreen.class);
            i.putExtra("matchNumber", match.getMatchNum());
            matchViewModel.addMatchInformation(match);
            startActivity(i);
            finish();
        });

        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Summary.this);
            builder.setMessage("Are you sure you want to delete?");
            builder.setTitle("Confirm Deletion");

            EditText passwordInput = new EditText(Summary.this);
            passwordInput.setHint("Enter match number to confirm");
            passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(passwordInput);

            builder.setCancelable(false)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        String enteredPassword = passwordInput.getText().toString();
                        String correctPassword = "" + match.getMatchNum(); // .-.

                        if (enteredPassword.equals(correctPassword)) {
                            matchViewModel.deleteAllMatches();
                            SharedPreferences sharedPref = getSharedPreferences("ScoutingPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putInt("matchNumber", match.getMatchNum());
                            editor.putString("position", match.getPosition());
                            editor.apply();
                            Toast.makeText(Summary.this, "Match deleted!", Toast.LENGTH_SHORT).show();
                            matchViewModel.deleteMatch(match.getMatchNum());
                            dialog.dismiss();
                            finish();
                        } else {
                            Toast.makeText(Summary.this, "Incorrect match number!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                    .show();
        });
    }

    private void populateSummary(Match match) {
        // Pregame
        setText(R.id.matchNumberSummary,  String.valueOf(match.getMatchNum()));
        setText(R.id.teamNumberSummary,   String.valueOf(match.getTeamNumber()));
        setText(R.id.teamPositionSummary, match.getPosition() != null ? match.getPosition() : "—");
        setText(R.id.scouterNameSummary,  match.getScouterName() != null ? match.getScouterName() : "—");
        setText(R.id.noShowSummary,       match.isNoShow() ? "Yes" : "No");


        // Teleop
        setText(R.id.shotWhileMovingSummary, match.getShotWhileMoving() ? "Yes" : "No");

        // Endgame
        setText(R.id.penaltiesSummary,    match.getPenalties() ? "Yes" : "No");
        setText(R.id.brokeDownSummary,    match.getBrokeDown() ? "Yes" : "No");
        setText(R.id.trenchSummary,       match.getTrench() ? "Yes" : "No");
        setText(R.id.bumpSummary,         match.getBump() ? "Yes" : "No");
        setText(R.id.endgameClimbSummary, String.valueOf(match.getEndgameClimb()) + "/5");

        // Ratings
        setText(R.id.pinRatingSummary,       match.getPinRating() + "/5");
        setText(R.id.stealRatingSummary,     match.getStealRating() + "/5");
        setText(R.id.blockRatingSummary,     match.getBlockRating() + "/5");
        setText(R.id.ramRatingSummary,       match.getRamRating() + "/5");
        setText(R.id.antiPinRatingSummary,   match.getAntiPinRating() + "/5");
        setText(R.id.antiStealRatingSummary, match.getAntiStealRating() + "/5");
        setText(R.id.antiBlockRatingSummary, match.getAntiBlockRating() + "/5");
        setText(R.id.antiRamRatingSummary,   match.getAntiRamRating() + "/5");

        // Notes
        String notes = match.getNotes();
        setText(R.id.notesSummary, (notes != null && !notes.isEmpty()) ? notes : "—");
    }

    private void setText(int id, String text) {
        TextView tv = findViewById(id);
        if (tv != null) tv.setText(text);
    }

    // depot/climb/etc use 2=yes, 1=no, 0=not set
    private String yesNo(int value) {
        if (value == 2) return "Yes";
        if (value == 1) return "No";
        return "—";
    }
}