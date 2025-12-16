package com.scoutingapp.titanscouting.views;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

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

public class Endgame2 extends AppCompatActivity {
    Match match;
    MatchViewModel matchViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame2); /* connects xml to the class file */
        EditText e = findViewById(R.id.comments); /*assigns variable e to what is typed in the comments (id)*/
        RadioGroup r = findViewById(R.id.parkPosition); /*assigns variable r to which park position is chosen (id)*/
        RadioButton parkRadio = findViewById(R.id.parkRadio);
        RadioButton climbRadio = findViewById(R.id.climbRadio);
        RadioButton noneRadio = findViewById(R.id.noneRadio);
        matchViewModel = new ViewModelProvider(this).get(MatchViewModel.class);
        matchViewModel.getMatch(getIntent().getIntExtra("matchNumber", 0)).observe(this, match -> {
            if(match == null) {
                finish();
                return;
            }
            this.match = match;
            if (match.getEndgamePos()!=null) {
                switch (match.getEndgamePos()) {
                    case "Park":
                        r.check(R.id.parkRadio);
                        break;
                    case "Climb":
                        r.check(R.id.climbRadio);
                        break;
                    default:
                        r.check(R.id.noneRadio);
                }
            }
            // r.setSelection(stagePositions.indexOf(match.getEndgamePos()));
            r.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton b = findViewById(checkedId);
                    match.setEndgamePos(b.getText().toString()); /* set match game to a string */
                }
            });

            final float[] previousDefenseRating = {0};
/*
            findViewById(R.id.defenseAbilityBar).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(isFinishing()) return false;
                    RatingBar ratingBar = (RatingBar) v;
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        previousDefenseRating[0] = ratingBar.getRating();
                        System.out.println(previousDefenseRating[0]);
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (previousDefenseRating[0] == ratingBar.getRating()) {
                            ratingBar.setRating(0);
                            match.setDefenseAbility(0);
                            System.out.println("Set to 0");
                            return true;
                        }
                        match.setDefenseAbility((int)ratingBar.getRating());
                    }
                    return false;
                }
            });*/

            e.setText(match.getNotes());
            ((EditText) (findViewById(R.id.comments))).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { /*set constructor for notes before changes*/
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { /*set constructor for notes when text changes*/
                    match.setNotes(s.toString()); /*set match notes to string*/
                }
                @Override
                public void afterTextChanged(Editable s) { /*allow text to be edited*/
                }
            });
            /* move to previous activity when pressing back/next button*/
        });
        View backButton = findViewById(R.id.back_to_teleop);
        View nextButton = findViewById(R.id.to_summary);
        backButton.setOnClickListener(v -> {
            Intent i = new Intent(Endgame2.this, Teleop.class);
            i.putExtra("matchNumber", match.getMatchNum());
            matchViewModel.addMatchInformation(match);
            startActivity(i);
            finish();
        });
        nextButton.setOnClickListener(v -> {
            Intent i = new Intent(Endgame2.this, Summary.class);
            i.putExtra("matchNumber", match.getMatchNum());
            matchViewModel.addMatchInformation(match);
            startActivity(i);
            finish();
        });
    }
}