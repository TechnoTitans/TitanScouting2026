package com.scoutingapp.titanscouting.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;

import com.scoutingapp.titanscouting.R;
import com.scoutingapp.titanscouting.database.Match;
import com.scoutingapp.titanscouting.database.MatchViewModel;


public class Teleop extends AppCompatActivity {

    private Match match;
    private MatchViewModel matchViewModel;

    private final int[][] shotGrid = new int[8][8];

    private static final int GRID_SIZE = 8;


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
                    createGrid();
                    loadShotData(match.getShotCoordinates());
                    setupButtons();
                });
    }

    private void createGrid() {
        GridLayout grid = findViewById(R.id.shotGrid);
        grid.removeAllViews(); // safety

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {

                View cell = new View(this);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 0;
                params.rowSpec = GridLayout.spec(r, 1f);
                params.columnSpec = GridLayout.spec(c, 1f);
                cell.setLayoutParams(params);

                cell.setBackgroundColor(Color.TRANSPARENT);

                int finalR = r;
                int finalC = c;

                cell.setOnClickListener(v -> {
                    shotGrid[finalR][finalC] =
                            shotGrid[finalR][finalC] == 0 ? 1 : 0;

                    if (shotGrid[finalR][finalC] == 1) {
                        v.setBackgroundColor(Color.argb(128, 255, 152, 0));
                    } else {
                        v.setBackgroundColor(Color.TRANSPARENT);
                    }
                });

                grid.addView(cell);
            }
        }
    }

    private void setupButtons() {
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

    // Convert 2D grid into a string to save in database
    private String gridToString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (shotGrid[row][col] == 1) {
                    int storedRow;
                    int storedCol;
                    if (isBlueAlliance()) {
                        storedRow = GRID_SIZE - row;
                        storedCol = GRID_SIZE - col;
                    } else {
                        storedRow = row + 1;
                        storedCol = col + 1;
                    }
                    sb.append(storedRow).append(",").append(storedCol).append(";");
                }
            }
        }
        return sb.toString();
    }

    private void loadShotData(String data) {
        if (data == null || data.trim().isEmpty()) return;

        GridLayout grid = findViewById(R.id.shotGrid);
        String[] shots = data.split(";");
        for (String shot : shots) {
            if (shot == null) continue;
            shot = shot.trim();
            if (shot.isEmpty()) continue;

            String[] parts = shot.split(",");
            if (parts.length != 2) continue;

            try {
                int parsedRow = Integer.parseInt(parts[0].trim());
                int parsedCol = Integer.parseInt(parts[1].trim());

                int row;
                int col;

                if (isBlueAlliance()) {
                    if (parsedRow < 1 || parsedRow > GRID_SIZE || parsedCol < 1 || parsedCol > GRID_SIZE) {
                        continue;
                    }
                    row = GRID_SIZE - parsedRow;
                    col = GRID_SIZE - parsedCol;
                } else {
                    if (parsedRow < 1 || parsedRow > GRID_SIZE || parsedCol < 1 || parsedCol > GRID_SIZE) {
                        continue;
                    }
                    row = parsedRow - 1;
                    col = parsedCol - 1;
                }

                if (row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE) {
                    shotGrid[row][col] = 1;
                    int index = row * GRID_SIZE + col;
                    View cell = grid.getChildAt(index);
                    if (cell != null) {
                        cell.setBackgroundColor(Color.argb(128, 255, 152, 0));
                    }
                }
            } catch (NumberFormatException ignored) {
            }
        }
    }

    private boolean isBlueAlliance() {
        String position = match != null ? match.getPosition() : null;
        return position != null && !position.isEmpty() && position.charAt(0) != 'R';
    }

    // Save the shot data to the match object
    private void saveShotData() {
        if (match != null) {
            match.setShotCoordinates(gridToString());
            matchViewModel.addMatchInformation(match);
        }
    }
}