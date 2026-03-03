package com.scoutingapp.titanscouting.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.scoutingapp.titanscouting.R;
import com.scoutingapp.titanscouting.database.Match;
import com.scoutingapp.titanscouting.database.MatchViewModel;

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

        Button backButton = findViewById(R.id.summary_back_button);
        backButton.setOnClickListener(v -> {
            Intent i = new Intent(Summary.this, Pregame.class);
            i.putExtra("matchNumber", match.getMatchNum());
            i.putExtra("transition", "true");
            startActivity(i);
            finish();
        });
    }

    private void populateSummary(Match match) {
        boolean isRed = match.getPosition() != null && match.getPosition().startsWith("R");
        int accentColor = isRed ? Color.parseColor("#FF3838") : Color.parseColor("#3890FF");

        // Header
        setText(R.id.summary_match_number, "MATCH #" + match.getMatchNum());
        setText(R.id.summary_team_number, "Team: " + match.getTeamNumber());
        setText(R.id.summary_scouter_name, "Scout: " + match.getScouterName());
        setText(R.id.summary_position, positionLabel(match.getPosition()));
        ((TextView) findViewById(R.id.summary_position)).setTextColor(accentColor);
        findViewById(R.id.header_divider).setBackgroundColor(accentColor);

        // Pregame
        setYesNo(R.id.val_no_show, match.isNoShow());

        // Autonomous
        setYesNo(R.id.val_depot,          match.getDepot() == 2);
        setYesNo(R.id.val_climb_auto,     match.getClimb() == 2);
        setYesNo(R.id.val_collected_fuel, match.getCollectedFuel() == 2);
        setYesNo(R.id.val_scored,         match.getScored() == 2);
        setYesNo(R.id.val_went_neutral,   match.getWentToNeutral() == 2);

        // Teleop
        setYesNo(R.id.val_shot_while_moving, match.getShotWhileMoving());
        buildShotGrid(match.getShotCoordinates());

        // Endgame status
        setYesNo(R.id.val_penalties,  match.getPenalties());
        setYesNo(R.id.val_broke_down, match.getBrokeDown());
        setYesNo(R.id.val_trench,     match.getTrench());
        setYesNo(R.id.val_bump,       match.getBump());

        // Ratings
        setRating(R.id.val_climb_end,  match.getEndgameClimb());
        setRating(R.id.val_pinning,    match.getPinRating());
        setRating(R.id.val_stealing,   match.getStealRating());
        setRating(R.id.val_blocking,   match.getBlockRating());
        setRating(R.id.val_ramming,    match.getRamRating());
        setRating(R.id.val_anti_pin,   match.getAntiPinRating());
        setRating(R.id.val_anti_steal, match.getAntiStealRating());
        setRating(R.id.val_anti_block, match.getAntiBlockRating());
        setRating(R.id.val_anti_ram,   match.getAntiRamRating());

        // Notes
        String notes = match.getNotes();
        TextView notesView = findViewById(R.id.val_notes);
        if (notes != null && !notes.isEmpty()) {
            notesView.setText(notes);
            notesView.setTextColor(Color.parseColor("#BBBBBB"));
        } else {
            notesView.setText("—");
            notesView.setTextColor(Color.parseColor("#555555"));
        }
    }

    private void setText(int id, String text) {
        TextView tv = findViewById(id);
        if (tv != null) tv.setText(text);
    }

    private void setYesNo(int id, boolean value) {
        TextView tv = findViewById(id);
        if (tv == null) return;
        tv.setText(value ? "YES" : "NO");
        tv.setTextColor(Color.parseColor(value ? "#00FF90" : "#FF4060"));
        tv.setBackgroundColor(Color.parseColor(value ? "#0D2B1E" : "#2B0D12"));
    }

    private void setRating(int id, int value) {
        TextView tv = findViewById(id);
        if (tv == null) return;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) sb.append(i < value ? "●" : "○");
        sb.append("  ").append(value).append("/5");
        tv.setText(sb.toString());
        tv.setTextColor(value > 0 ? Color.parseColor("#FF6B00") : Color.parseColor("#555555"));
    }

    private void buildShotGrid(String coordString) {
        GridLayout grid = findViewById(R.id.shot_grid);
        if (grid == null) return;
        grid.removeAllViews();
        grid.setRowCount(8);
        grid.setColumnCount(8);

        boolean[][] shots = new boolean[8][8];
        if (coordString != null && !coordString.isEmpty()) {
            for (String shot : coordString.split(";")) {
                String[] parts = shot.split(",");
                if (parts.length == 2) {
                    try {
                        int r = Integer.parseInt(parts[0].trim());
                        int c = Integer.parseInt(parts[1].trim());
                        if (r >= 0 && r < 8 && c >= 0 && c < 8) shots[r][c] = true;
                    } catch (NumberFormatException ignored) {}
                }
            }
        }

        int cellSize = (int) (getResources().getDisplayMetrics().density * 22);
        int gap      = (int) (getResources().getDisplayMetrics().density * 2);

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                View cell = new View(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                        GridLayout.spec(r), GridLayout.spec(c));
                params.width  = cellSize;
                params.height = cellSize;
                params.setMargins(gap, gap, gap, gap);
                cell.setLayoutParams(params);
                cell.setBackgroundColor(shots[r][c]
                        ? Color.argb(200, 255, 107, 0)
                        : Color.parseColor("#1A1A1A"));
                grid.addView(cell);
            }
        }

        int total = 0;
        for (boolean[] row : shots) for (boolean b : row) if (b) total++;
        setText(R.id.val_shot_count, total + " shot" + (total != 1 ? "s" : "") + " logged");
    }

    private String positionLabel(String pos) {
        if (pos == null) return "—";
        switch (pos) {
            case "R1": return "Red 1";
            case "R2": return "Red 2";
            case "R3": return "Red 3";
            case "B1": return "Blue 1";
            case "B2": return "Blue 2";
            case "B3": return "Blue 3";
            default:   return pos;
        }
    }
}