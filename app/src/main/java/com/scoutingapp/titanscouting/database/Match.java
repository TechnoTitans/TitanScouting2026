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
    public int getL1Count() {
        return l1Count;
    }
    public void setL1Count(int l1Count) {
        this.l1Count = l1Count;
    }
    public int getL2Count() {
        return l2Count;
    }
    public void setL2Count(int l2Count) {
        this.l2Count = l2Count;
    }
    public int getL3Count() {
        return l3Count;
    }
    public void setL3Count(int l3Count) {
        this.l3Count = l3Count;
    }
    public int getL4Count() {
        return l4Count;
    }
    public void setL4Count(int l4Count) {
        this.l4Count = l4Count;
    }

    public boolean getLeave() {return leave; }
    public void setLeave(boolean leave) {this.leave = leave;}

    public int get() {return leave; }
    public void setLeave(boolean leave) {this.leave = leave;}

    public String getEndgamePos() {
        return endgamePos;
    }
    public void setEndgamePos(String endgamePos) {
        this.endgamePos = endgamePos;
    }public void setGroundAlgae(boolean groundAlgae) {
        this.groundAlgae = groundAlgae;
    }
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