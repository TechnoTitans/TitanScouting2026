//package com.scoutingapp.titanscouting.views;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.ViewModelProvider;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
package com.scoutingapp.titanscouting.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.scoutingapp.titanscouting.R;
import com.scoutingapp.titanscouting.database.Match;
import com.scoutingapp.titanscouting.database.MatchViewModel;

public class Teleop extends AppCompatActivity {

    private Match match;
    private MatchViewModel matchViewModel;

    // 8x8 grid to store shot positions
    private int[][] shotGrid = new int[8][8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleop_blue);

        matchViewModel = new ViewModelProvider(this).get(MatchViewModel.class);
        matchViewModel.getMatch(getIntent().getIntExtra("matchNumber", 0))
                .observe(this, match -> {
                    if (match == null) {
                        finish();
                        return;
                    }
                    this.match = match;

                    setupGridButtons();
                    setupNavigationButtons();
                });
    }

    private void setupGridButtons() {
        Button[][] gridButtons = new Button[8][8];

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Buttons must be named button_0_0, button_0_1, ..., button_7_7 in XML
                int resID = getResources().getIdentifier(
                        "button_" + row + "_" + col, "id", getPackageName());
                gridButtons[row][col] = findViewById(resID);

                int finalRow = row;
                int finalCol = col;

                gridButtons[row][col].setOnClickListener(v -> {
                    // Toggle shot: 0 = no shot, 1 = shot
                    shotGrid[finalRow][finalCol] = shotGrid[finalRow][finalCol] == 0 ? 1 : 0;
                });
            }
        }
    }

    private void setupNavigationButtons() {
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
    }

    // Convert 2D grid into a string to save in database
    private String gridToString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (shotGrid[row][col] == 1) {
                    sb.append(row).append(",").append(col).append(";");
                }
            }
        }
        return sb.toString();
    }

    // Save the shot data to the match object
    private void saveShotData() {
        if (match != null) {
            match.setShotCoordinates(gridToString());
            matchViewModel.addMatchInformation(match);
        }
    }
}


//import android.widget.TextView;
//
//import com.scoutingapp.titanscouting.R;
//import com.scoutingapp.titanscouting.database.Match;
//import com.scoutingapp.titanscouting.database.MatchViewModel;
//
//public class Teleop extends AppCompatActivity {
//
//    Match match;
//    MatchViewModel matchViewModel;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_teleop_2);
//
//        Button l4inc = findViewById(R.id.inc_l4_scored);
//        Button l4dec = findViewById(R.id.dec_l4_scored);
//        TextView l4 = findViewById(R.id.l4_score);
//        Button l3inc = findViewById(R.id.inc_l3_scored);
//        Button l3dec = findViewById(R.id.dec_l3_scored);
//        TextView l3 = findViewById(R.id.l3_score);
//        Button l2inc = findViewById(R.id.inc_l2_scored);
//        Button l2dec = findViewById(R.id.dec_l2_scored);
//        TextView l2 = findViewById(R.id.l2_score);
//        Button l1inc = findViewById(R.id.inc_l1_scored);
//        Button l1dec = findViewById(R.id.dec_l1_scored);
//        TextView l1 = findViewById(R.id.l1_score);
//        Button netInc = findViewById(R.id.inc_net);
//        Button netDec = findViewById(R.id.dec_net);
//        TextView net = findViewById(R.id.net_score);
//        Button procInc = findViewById(R.id.inc_proc);
//        Button procDec = findViewById(R.id.dec_proc);
//        TextView proc = findViewById(R.id.proc_score);
//
//        Button l4incMissed = findViewById(R.id.inc_l4_missed);
//        Button l4decMissed = findViewById(R.id.dec_l4_missed);
//        TextView l4Missed = findViewById(R.id.l4_missed_score);
//        Button l3incMissed = findViewById(R.id.inc_l3_missed);
//        Button l3decMissed = findViewById(R.id.dec_l3_missed);
//        TextView l3Missed = findViewById(R.id.l3_missed_score);
//        Button l2incMissed = findViewById(R.id.inc_l2_missed);
//        Button l2decMissed = findViewById(R.id.dec_l2_missed);
//        TextView l2Missed = findViewById(R.id.l2_missed_score);
//        Button l1incMissed = findViewById(R.id.inc_l1_missed);
//        Button l1decMissed = findViewById(R.id.dec_l1_missed);
//        TextView l1Missed = findViewById(R.id.l1_missed_score);
//        Button netIncMissed = findViewById(R.id.inc_net_missed);
//        Button netDecMissed = findViewById(R.id.dec_net_missed);
//        TextView netMissed = findViewById(R.id.net_missed_score);
//        Button procIncMissed = findViewById(R.id.inc_proc_missed);
//        Button procDecMissed = findViewById(R.id.dec_proc_missed);
//        TextView procMissed = findViewById(R.id.proc_missed_score);
//
//        Button backToAuto = findViewById(R.id.back_to_auto);
//        Button toEndgame = findViewById(R.id.to_endgame);
//
//        matchViewModel = new ViewModelProvider(this).get(MatchViewModel.class);
//        matchViewModel.getMatch(getIntent().getIntExtra("matchNumber", 0)).observe(this, match -> {
//            if(match == null) {
//                finish();
//                return;
//            }
//            this.match = match;
//
//            //(TextView) ;
//
//            l4.setText(String.valueOf(match.getL4Count()));
//            l3.setText(String.valueOf(match.getL3Count()));
//            l2.setText(String.valueOf(match.getL2Count()));
//            l1.setText(String.valueOf(match.getL1Count()));
//            net.setText(String.valueOf(match.getNetCount()));
//            proc.setText(String.valueOf(match.getProcessorCount()));
//
//            l4Missed.setText(String.valueOf(match.getL4MissedCount()));
//            l3Missed.setText(String.valueOf(match.getL3MissedCount()));
//            l2Missed.setText(String.valueOf(match.getL2MissedCount()));
//            l1Missed.setText(String.valueOf(match.getL1MissedCount()));
//            netMissed.setText(String.valueOf(match.getNetMissedCount()));
//            procMissed.setText(String.valueOf(match.getProcessorMissedCount()));
//
//            l4inc.setOnClickListener(v -> {
//                match.setL4Count(match.getL4Count() + 1);
//                l4.setText(String.valueOf(match.getL4Count()));
//                matchViewModel.addMatchInformation(match);
//            });
//            l4dec.setOnClickListener(v -> {
//                match.setL4Count(match.getL4Count() == 0 ? 0 : match.getL4Count() - 1);
//                l4.setText(String.valueOf(match.getL4Count()));
//                matchViewModel.addMatchInformation(match);
//            });
//            l3inc.setOnClickListener(v -> {
//                match.setL3Count(match.getL3Count() + 1);
//                l3.setText(String.valueOf(match.getL3Count()));
//                matchViewModel.addMatchInformation(match);
//            });
//            l3dec.setOnClickListener(v -> {
//                match.setL3Count(match.getL3Count() == 0 ? 0 : match.getL3Count() - 1);
//                l3.setText(String.valueOf(match.getL3Count()));
//                matchViewModel.addMatchInformation(match);
//            });
//            l2inc.setOnClickListener(v -> {
//                match.setL2Count(match.getL2Count() + 1);
//                l2.setText(String.valueOf(match.getL2Count()));
//                matchViewModel.addMatchInformation(match);
//            });
//            l2dec.setOnClickListener(v -> {
//                match.setL2Count(match.getL2Count() == 0 ? 0 : match.getL2Count() - 1);
//                l2.setText(String.valueOf(match.getL2Count()));
//                matchViewModel.addMatchInformation(match);
//            });
//            l1inc.setOnClickListener(v -> {
//                match.setL1Count(match.getL1Count() + 1);
//                l1.setText(String.valueOf(match.getL1Count()));
//                matchViewModel.addMatchInformation(match);
//            });
//            l1dec.setOnClickListener(v -> {
//                match.setL1Count(match.getL1Count() == 0 ? 0 : match.getL1Count() - 1);
//                l1.setText(String.valueOf(match.getL1Count()));
//                matchViewModel.addMatchInformation(match);
//            });
//            procInc.setOnClickListener(v -> {
//                match.setProcessorCount(match.getProcessorCount() + 1);
//                proc.setText(String.valueOf(match.getProcessorCount()));
//                matchViewModel.addMatchInformation(match);
//            });
//            procDec.setOnClickListener(v -> {
//                match.setProcessorCount(match.getProcessorCount() == 0 ? 0 : match.getProcessorCount() - 1);
//                proc.setText(String.valueOf(match.getProcessorCount()));
//                matchViewModel.addMatchInformation(match);
//            });
//            netInc.setOnClickListener(v -> {
//                match.setNetCount(match.getNetCount() + 1);
//                net.setText(String.valueOf(match.getNetCount()));
//                matchViewModel.addMatchInformation(match);
//            });
//            netDec.setOnClickListener(v -> {
//                match.setNetCount(match.getNetCount() == 0 ? 0 : match.getNetCount() - 1);
//                net.setText(String.valueOf(match.getNetCount()));
//                matchViewModel.addMatchInformation(match);
//            });
//
//
//            l4incMissed.setOnClickListener(v -> {
//                match.setL4MissedCount(match.getL4MissedCount() + 1);
//                l4Missed.setText(String.valueOf(match.getL4MissedCount()));
//                matchViewModel.addMatchInformation(match);
//            });
//            l4decMissed.setOnClickListener(v -> {
//                match.setL4MissedCount(match.getL4MissedCount() == 0 ? 0 : match.getL4MissedCount() - 1);
//                l4Missed.setText(String.valueOf(match.getL4MissedCount()));
//                matchViewModel.addMatchInformation(match);
//            });
//            l3incMissed.setOnClickListener(v -> {
//                match.setL3MissedCount(match.getL3MissedCount() + 1);
//                l3Missed.setText(String.valueOf(match.getL3MissedCount()));
//                matchViewModel.addMatchInformation(match);
//            });
//            l3decMissed.setOnClickListener(v -> {
//                match.setL3MissedCount(match.getL3MissedCount() == 0 ? 0 : match.getL3MissedCount() - 1);
//                l3Missed.setText(String.valueOf(match.getL3MissedCount()));
//                matchViewModel.addMatchInformation(match);
//            });
//            l2incMissed.setOnClickListener(v -> {
//                match.setL2MissedCount(match.getL2MissedCount() + 1);
//                l2Missed.setText(String.valueOf(match.getL2MissedCount()));
//                matchViewModel.addMatchInformation(match);
//            });
//            l2decMissed.setOnClickListener(v -> {
//                match.setL2MissedCount(match.getL2MissedCount() == 0 ? 0 : match.getL2MissedCount() - 1);
//                l2Missed.setText(String.valueOf(match.getL2MissedCount()));
//                matchViewModel.addMatchInformation(match);
//            });
//            l1incMissed.setOnClickListener(v -> {
//                match.setL1MissedCount(match.getL1MissedCount() + 1);
//                l1Missed.setText(String.valueOf(match.getL1MissedCount()));
//                matchViewModel.addMatchInformation(match);
//            });
//            l1decMissed.setOnClickListener(v -> {
//                match.setL1MissedCount(match.getL1MissedCount() == 0 ? 0 : match.getL1MissedCount() - 1);
//                l1Missed.setText(String.valueOf(match.getL1MissedCount()));
//                matchViewModel.addMatchInformation(match);
//            });
//            procIncMissed.setOnClickListener(v -> {
//                match.setProcessorMissedCount(match.getProcessorMissedCount() + 1);
//                procMissed.setText(String.valueOf(match.getProcessorMissedCount()));
//                matchViewModel.addMatchInformation(match);
//            });
//            procDecMissed.setOnClickListener(v -> {
//                match.setProcessorMissedCount(match.getProcessorMissedCount() == 0 ? 0 : match.getProcessorMissedCount() - 1);
//                procMissed.setText(String.valueOf(match.getProcessorMissedCount()));
//                matchViewModel.addMatchInformation(match);
//            });
//            netIncMissed.setOnClickListener(v -> {
//                match.setNetMissedCount(match.getNetMissedCount() + 1);
//                netMissed.setText(String.valueOf(match.getNetMissedCount()));
//                matchViewModel.addMatchInformation(match);
//            });
//            netDecMissed.setOnClickListener(v -> {
//                match.setNetMissedCount(match.getNetMissedCount() == 0 ? 0 : match.getNetMissedCount() - 1);
//                netMissed.setText(String.valueOf(match.getNetMissedCount()));
//                matchViewModel.addMatchInformation(match);
//            });
//
//
//            backToAuto.setOnClickListener(v -> {
//                Intent i = new Intent(Teleop.this, Autonomous.class);
//                i.putExtra("matchNumber", match.getMatchNum());
//                i.putExtra("color", match.getPosition().substring(0, 1));
//                matchViewModel.addMatchInformation(match);
//                startActivity(i);
//                finish();
//            });
//
//            toEndgame.setOnClickListener(v -> {
//                Intent i = new Intent(Teleop.this, Endgame.class);
//                i.putExtra("matchNumber", match.getMatchNum());
//                matchViewModel.addMatchInformation(match);
//                startActivity(i);
//                finish();
//            });
//
//
//        });
//
//    }
//}