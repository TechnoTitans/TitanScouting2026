package com.scoutingapp.titanscouting.views;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.scoutingapp.titanscouting.R;
import com.scoutingapp.titanscouting.database.Match;
import com.scoutingapp.titanscouting.database.MatchViewModel;

import java.util.function.Consumer;

public class Endgame extends AppCompatActivity {
    Match match;
    MatchViewModel matchViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame2); /* connects xml to the class file */
        EditText e = findViewById(R.id.comments); /*assigns variable e to what is typed in the comments (id)*/
        CheckBox penalties = findViewById(R.id.penalties);
        CheckBox brokeDown = findViewById(R.id.brokeDown);
        CheckBox usedTrench = findViewById(R.id.trench);
        CheckBox usedBump = findViewById(R.id.bump);
        matchViewModel = new ViewModelProvider(this).get(MatchViewModel.class);

        final boolean[] isRed = {true};
        matchViewModel.getMatch(getIntent().getIntExtra("matchNumber", 0)).observe(this, match -> {
            if(match == null) {
                finish();
                return;
            }
            this.match = match;
            penalties.setChecked(match.getPenalties());
            penalties.setOnClickListener(v -> {
                match.setPenalties(!match.getPenalties());
            });
            brokeDown.setChecked(match.getBrokeDown());
            brokeDown.setOnClickListener(v -> {
                match.setBrokeDown(!match.getBrokeDown());
            });
            usedTrench.setChecked(match.getTrench());
            usedTrench.setOnClickListener(v -> {
                match.setTrench(!match.getTrench());
            });
            usedBump.setChecked(match.getBump());
            usedBump.setOnClickListener(v -> {
                match.setBump(!match.getBump());
            });

            setupRatingBar(R.id.pinning, match.getPinRating(), match::setPinRating);
            setupRatingBar(R.id.stealing, match.getStealRating(), match::setStealRating);
            setupRatingBar(R.id.blocking, match.getBlockRating(), match::setBlockRating);
            setupRatingBar(R.id.ramming, match.getRamRating(), match::setRamRating);
            setupRatingBar(R.id.antiPinning, match.getAntiPinRating(), match::setAntiPinRating);
            setupRatingBar(R.id.antiStealing, match.getAntiStealRating(), match::setAntiStealRating);
            setupRatingBar(R.id.antiBlocking, match.getAntiBlockRating(), match::setAntiBlockRating);
            setupRatingBar(R.id.antiRamming, match.getAntiRamRating(), match::setAntiRamRating);
            setupRatingBar(R.id.climb, match.getEndgameClimb(), match::setEndgameClimb);

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
            Intent i = new Intent(Endgame.this, Teleop.class);
            i.putExtra("matchNumber", match.getMatchNum());
            matchViewModel.addMatchInformation(match);
            startActivity(i);
            finish();
        });
        nextButton.setOnClickListener(v -> {
            Intent i = new Intent(Endgame.this, QRScreen.class);
            i.putExtra("matchNumber", match.getMatchNum());
            matchViewModel.addMatchInformation(match);
            startActivity(i);
            finish();
        });
    }
    private void setupRatingBar(int ratingBarId, int initialValue, Consumer<Integer> setter) {
        RatingBar ratingBar = findViewById(ratingBarId);
        ratingBar.setRating(initialValue);

        final float[] previousRating = { initialValue };

        ratingBar.setOnRatingBarChangeListener((bar, rating, fromUser) -> {
            if (!fromUser) return;
            if (previousRating[0] == rating) {
                bar.setRating(0);
                setter.accept(0);
            } else {
                setter.accept((int) rating);
            }
            previousRating[0] = bar.getRating();
        });
    }
}