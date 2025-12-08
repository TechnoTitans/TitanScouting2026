package com.scoutingapp.titanscouting.database;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.scoutingapp.titanscouting.Autofill;

//The schema for our database; each @ColumnInfo is a column in the db
@Entity(tableName = "scouting_database")
public class Match {
    @PrimaryKey
    @ColumnInfo(name = "matchNum")
    private int matchNum;
    @ColumnInfo(name = "teamNumber", defaultValue = "00000")
    private int teamNumber;
    @ColumnInfo(name = "position", defaultValue = "R1")
    private String position;
    @ColumnInfo(name = "scouterName", defaultValue = "0")
    private String scouterName;
    @ColumnInfo(name = "noShow", defaultValue = "0")
    private boolean noShow;
    @ColumnInfo(name="autoPath", defaultValue = "0")
    private String autoPath;

    @ColumnInfo(name = "autoCubeL1", defaultValue = "0")
    private int autoCubeL1;
    @ColumnInfo(name = "autoCubeL2", defaultValue = "0")
    private int autoCubeL2;
    @ColumnInfo(name = "autoCubeL3", defaultValue = "0")
    private int autoCubeL3;
    @ColumnInfo(name = "autoConeL1", defaultValue = "0")
    private int autoConeL1;
    @ColumnInfo(name = "autoConeL2", defaultValue = "0")
    private int autoConeL2;
    @ColumnInfo(name = "autoConeL3", defaultValue = "0")
    private int autoConeL3;

    @ColumnInfo(name = "teleCubeL1", defaultValue = "0")
    private int teleCubeL1;
    @ColumnInfo(name = "teleCubeL2", defaultValue = "0")
    private int teleCubeL2;
    @ColumnInfo(name = "teleCubeL3", defaultValue = "0")
    private int teleCubeL3;

    @ColumnInfo(name = "teleConeL1", defaultValue = "0")
    private int teleConeL1;
    @ColumnInfo(name = "teleConeL2", defaultValue = "0")
    private int teleConeL2;
    @ColumnInfo(name = "teleConeL3", defaultValue = "0")
    private int teleConeL3;

    @ColumnInfo(name = "parked", defaultValue = "0")
    private boolean parked;

    @ColumnInfo(name = "docked", defaultValue = "0")
    private boolean docked;

    @ColumnInfo(name = "engaged", defaultValue = "0")
    private boolean engaged;

    @ColumnInfo(name = "comments", defaultValue = "None")
    private String comments;

    @ColumnInfo(name = "endgamePos", defaultValue = "None")
    private String endgamePos;
    @ColumnInfo(name = "mechanicalReliability", defaultValue = "1")
    private boolean mechanicalReliability;
    @ColumnInfo(name = "defenseAbility", defaultValue = "0")
    private int defenseAbility;
    @ColumnInfo(name = "notes", defaultValue = "0")
    private String notes;
    @ColumnInfo(name = "leave", defaultValue = "0")
    private boolean leave;

    public int getMatchNum() {
        return matchNum;
    }
    public void setMatchNum(int matchNum) {
        this.matchNum = matchNum;
        //updateTeamNumber();
    }
    public int getTeamNumber() {
        return teamNumber;
    }
    public void updateTeamNumber(){
        final Autofill teamNumberFinder = new Autofill();
        setTeamNumber(teamNumberFinder.getTeamNumberFromTable(getMatchNum(), getPosition()));
    }
    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getScouterName() {
        return scouterName;
    }
    public void setScouterName(String scouterName) {
        this.scouterName = scouterName;
    }
    public boolean isNoShow() {
        return noShow;
    }
    public void setNoShow(boolean noShow) {
        this.noShow = noShow;
    }
    public String getAutoPath() { return autoPath; }
    public void setAutoPath(String autoPath) { this.autoPath = autoPath; }

    public boolean getLeave() {return leave; }
    public void setLeave(boolean leave) {this.leave = leave;}

    public int getAutoCubeL1() {return autoCubeL1; }
    public void setAutoCubeL1(int autoCubeL1) {this.autoCubeL1 = autoCubeL1;}

    public int getAutoCubeL2() {return autoCubeL2; }
    public void setAutoCubeL2(int autoCubeL2) {this.autoCubeL2 = autoCubeL2;}

    public int getAutoCubeL3() {return autoCubeL3; }
    public void setAutoCubeL3(int autoCubeL3) {this.autoCubeL3 = autoCubeL3;}

    public int getAutoConeL1() {return autoConeL1; }
    public void setAutoConeL1(int autoConeL1) {this.autoConeL1 = autoConeL1;}

    public int getAutoConeL2() {return autoConeL2; }
    public void setAutoConeL2(int autoConeL2) {this.autoConeL2 = autoConeL2;}

    public int getAutoConeL3() {return autoConeL3; }
    public void setAutoConeL3(int autoConeL3) {this.autoConeL3 = autoConeL3;}

    public int getTeleCubeL1() {return teleCubeL1; }
    public void setTeleCubeL1(int teleCubeL1) {this.teleCubeL1 = teleCubeL1;}

    public int getTeleCubeL2() {return teleCubeL2; }
    public void setTeleCubeL2(int teleCubeL2) {this.teleCubeL2 = teleCubeL2;}

    public int getTeleCubeL3() {return teleCubeL3; }
    public void setTeleCubeL3(int teleCubeL3) {this.teleCubeL3 = teleCubeL3;}

    public int getTeleConeL1() {return teleConeL1; }
    public void setTeleConeL1(int teleConeL1) {this.teleConeL1 = teleConeL1;}

    public int getTeleConeL2() {return teleConeL2; }
    public void setTeleConeL2(int teleConeL2) {this.teleConeL2 = teleConeL2;}

    public int getTeleConeL3() {return teleConeL3; }
    public void setTeleConeL3(int teleConeL3) {this.teleConeL3 = teleConeL3;}

    public boolean getParked() {return parked; }
    public void setParked(boolean parked) {this.parked = parked;}

    public boolean getDocked() {return docked; }
    public void setDocked(boolean docked) {this.docked = docked;}

    public boolean getEngaged() {return engaged; }
    public void setEngaged(boolean engaged) {this.engaged = engaged;}

    public String getComments() {return comments; }
    public void setComments(String comments) {this.comments = comments;}




    public String getEndgamePos() {
        return endgamePos;
    }
    public void setEndgamePos(String endgamePos) {
        this.endgamePos = endgamePos;

    public boolean getMechanicalReliability() {
        return mechanicalReliability;
    }
    public void setMechanicalReliability(boolean mechanicalReliability) {
        this.mechanicalReliability = mechanicalReliability;
    }
    public int getDefenseAbility() {
        return defenseAbility;
    }
    public void setDefenseAbility(int defenseAbility) {
        this.defenseAbility = defenseAbility;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
}