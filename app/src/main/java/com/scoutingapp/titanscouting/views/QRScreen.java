package com.scoutingapp.titanscouting.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.scoutingapp.titanscouting.Homepage;
import com.scoutingapp.titanscouting.R;
import com.scoutingapp.titanscouting.database.Match;
import com.scoutingapp.titanscouting.database.MatchViewModel;
import com.scoutingapp.titanscouting.views.logs.Logs;
import java.util.Locale;
public class QRScreen extends AppCompatActivity {
    LiveData<Match> liveDataMatch;
    MatchViewModel matchViewModel;
    MultiFormatWriter multiFormatWriter;
    BitMatrix bitMatrix;
    BarcodeEncoder barcodeEncoder;
    String qrString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscreen);
        ImageView qrView = findViewById(R.id.qr_code);
        View backButton = findViewById(R.id.back_to_summary);
        View exitScouting = findViewById(R.id.exit_scouting_button);
        TextView matchText = findViewById(R.id.match_num_text_view);
        int matchNum = getIntent().getIntExtra("matchNumber", 0);
        matchViewModel = new ViewModelProvider(this).get(MatchViewModel.class);
        liveDataMatch = matchViewModel.getMatch(matchNum);
        liveDataMatch.observe(this, match -> {
            matchText.setText(String.format(Locale.US, "Match %d  - %s (%s)",
                    match.getMatchNum(),
                    match.getPosition(),
                    match.getScouterName()));
            qrString = String.join("\n",
                    "GACMP\n",
                    String.valueOf(match.getTeamNumber()),
                    String.valueOf(match.getMatchNum()),
                    match.getScouterName(),
                    String.valueOf(match.getPosition()),
                    String.valueOf(match.getLeave()),
                    String.valueOf(match.getAutoCubeL1()),
                    String.valueOf(match.getAutoCubeL2()),
                    String.valueOf(match.getAutoCubeL3()),
                    String.valueOf(match.getAutoConeL1()),
                    String.valueOf(match.getAutoConeL2()),
                    String.valueOf(match.getAutoConeL3()),
                    String.valueOf(match.getTeleCubeL1()),
                    String.valueOf(match.getTeleCubeL2()),
                    String.valueOf(match.getTeleCubeL3()),
                    String.valueOf(match.getTeleConeL1()),
                    String.valueOf(match.getTeleConeL2()),
                    String.valueOf(match.getTeleConeL3()),
                    String.valueOf(match.getParked()),
                    String.valueOf(match.getDocked()),
                    String.valueOf(match.getEngaged()),
                    String.valueOf(match.getComments()),
                    String.valueOf(match.getDefenseAbility()),
                    match.getNotes()
            );
            Log.d("match_num", String.valueOf(match.getMatchNum()));
            multiFormatWriter = new MultiFormatWriter();
            try {
                bitMatrix = multiFormatWriter.encode(qrString, BarcodeFormat.QR_CODE, 500, 500);
                barcodeEncoder = new BarcodeEncoder();
                qrView.setImageBitmap(barcodeEncoder.createBitmap(bitMatrix));
            } catch (WriterException e) {
                throw new RuntimeException(e);
            }
        });
        backButton.setOnClickListener(v -> {
            Intent i = new Intent(QRScreen.this, Logs.class);
            i.putExtra("matchNumber", matchNum);
            startActivity(i);
            finish();
        });
        exitScouting.setOnClickListener(v -> {
            Intent i = new Intent(QRScreen.this, Homepage.class);
            startActivity(i);
            finish();
        });
    }
}