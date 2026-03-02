package com.scoutingapp.titanscouting.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.scoutingapp.titanscouting.Autofill;
import com.scoutingapp.titanscouting.Homepage;
import com.scoutingapp.titanscouting.R;
import com.scoutingapp.titanscouting.database.Match;
import com.scoutingapp.titanscouting.database.MatchViewModel;

import java.util.Objects;

public class Pregame extends AppCompatActivity {

    private MatchViewModel matchViewModel;
    private Match match;
    private EditText matchNumberInput, scouterNameInput, teamNumberInput;
    private CheckBox noShowCheckBox;
    private View red1, red2, red3, blue1, blue2, blue3, toAuto;

    private boolean isFromAuto;

    private final Autofill finder = new Autofill();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_game);

        matchViewModel = new ViewModelProvider(this).get(MatchViewModel.class);
        initViews();

        isFromAuto = Objects.equals(getIntent().getStringExtra("transition"), "true");
        int matchNumber = getIntent().getIntExtra("matchNumber", 0);

        if (isFromAuto) {
            matchViewModel.getMatch(matchNumber).observe(this, match -> {
                if (match == null) {
                    finish();
                    return;
                }
                setupUIWithMatch(match);
            });
        } else {
            match = new Match();
            match.setMatchNum(matchNumber);
            scouterNameInput.setText("");
            setupListeners();

            SharedPreferences sharedPref = getSharedPreferences("ScoutingPrefs", Context.MODE_PRIVATE);
            int savedMatch = sharedPref.getInt("matchNumber", 1);
            String savedPosition = sharedPref.getString("position", "");

            if(Objects.equals(finder.getScouterName(savedMatch, savedPosition), finder.getScouterName(savedMatch - 1, savedPosition))) {
                match.setPosition(savedPosition);
            }
            matchNumberInput.setText(String.valueOf(savedMatch));
            updatePositionColors();
        }
    }

    private void initViews() {
        matchNumberInput = findViewById(R.id.MatchNumberPregameResponse);
        scouterNameInput = findViewById(R.id.ScouterNamePregameResponse);
        teamNumberInput = findViewById(R.id.TeamNumberInput);
        noShowCheckBox = findViewById(R.id.noShowCheckBox);
        toAuto = findViewById(R.id.NextButtonPregame);

        red1 = findViewById(R.id.Red1);
        red2 = findViewById(R.id.Red2);
        red3 = findViewById(R.id.Red3);
        blue1 = findViewById(R.id.Blue1);
        blue2 = findViewById(R.id.Blue2);
        blue3 = findViewById(R.id.Blue3);

        Button backButton = findViewById(R.id.BackButtonPregame);
        backButton.setOnClickListener(v -> {
            startActivity(new Intent(this, Homepage.class));
            finish();
        });
    }

    private void setupUIWithMatch(Match m) {
        this.match = m;

        matchNumberInput.setText(String.valueOf(match.getMatchNum()));
        scouterNameInput.setText(match.getScouterName());
        teamNumberInput.setText(String.valueOf(match.getTeamNumber()));
        noShowCheckBox.setChecked(match.isNoShow());

        setupListeners();
        updatePositionColors();
    }

    private void setupListeners() {
        matchNumberInput.addTextChangedListener(createTextWatcher(() -> {
            if (!matchNumberInput.getText().toString().isEmpty()) {
                match.setMatchNum(Integer.parseInt(matchNumberInput.getText().toString().trim()));
                useAutofill();
            }
        }));

        scouterNameInput.addTextChangedListener(createTextWatcher(() -> {
            match.setScouterName(scouterNameInput.getText().toString());
        }));

        teamNumberInput.addTextChangedListener(createTextWatcher(() -> {
            if (!teamNumberInput.getText().toString().isEmpty()) {
                match.setTeamNumber(Integer.parseInt(teamNumberInput.getText().toString().trim()));
            }
        }));

        noShowCheckBox.setOnClickListener(v -> {
            match.setNoShow(!match.isNoShow());
        });

        setAllianceButton(red1, "R1", R.color.light_red, R.color.red);
        setAllianceButton(red2, "R2", R.color.light_red, R.color.red);
        setAllianceButton(red3, "R3", R.color.light_red, R.color.red);
        setAllianceButton(blue1, "B1", R.color.techno_titans, R.color.darkblue);
        setAllianceButton(blue2, "B2", R.color.techno_titans, R.color.darkblue);
        setAllianceButton(blue3, "B3", R.color.techno_titans, R.color.darkblue);

        toAuto.setOnClickListener(v -> {
            if (!matchNumberInput.getText().toString().isEmpty()
                    && !scouterNameInput.getText().toString().isEmpty()
                    && match.getPosition() != null
                    && match.getTeamNumber() != 0) {

                matchViewModel.addMatchInformation(match);

                Intent i = new Intent(this, match.isNoShow() ? Summary.class : Autonomous.class);
                i.putExtra("matchNumber", match.getMatchNum());
                i.putExtra("color", match.getPosition().substring(0, 1));
                startActivity(i);
                finish();
            }
        });
    }

    private TextWatcher createTextWatcher(Runnable afterChange) {
        return new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { afterChange.run(); }
            @Override public void afterTextChanged(Editable s) {}
        };
    }

    private void useAutofill() {
        if (isFinishing()) {
            return;
        }
        if (match.getPosition() != null && match.getMatchNum() != 0) {
            match.setTeamNumber(finder.getTeamNumberFromTable(match.getMatchNum(), match.getPosition()));
            teamNumberInput.setText(String.valueOf(match.getTeamNumber()));
            match.setScouterName(finder.getScouterName(match.getMatchNum(), match.getPosition()));
            scouterNameInput.setText(match.getScouterName());
        }
    }

    private void setAllianceButton(View button, String position, int selectedColor, int defaultColor) {
        button.setOnClickListener(v -> {
            match.setPosition(position);
            updatePositionColors();
            useAutofill();
        });
    }

    private void updatePositionColors() {
        setTint(red1, "R1", R.color.light_red, R.color.red);
        setTint(red2, "R2", R.color.light_red, R.color.red);
        setTint(red3, "R3", R.color.light_red, R.color.red);
        setTint(blue1, "B1", R.color.techno_titans, R.color.darkblue);
        setTint(blue2, "B2", R.color.techno_titans, R.color.darkblue);
        setTint(blue3, "B3", R.color.techno_titans, R.color.darkblue);
    }

    private void setTint(View view, String pos, int selectedColor, int defaultColor) {
        int color = pos.equals(match.getPosition()) ? selectedColor : defaultColor;
        view.setBackgroundTintList(ContextCompat.getColorStateList(this, color));
    }
}
