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
    @ColumnInfo(name = "depot", defaultValue = "0")
    private int depot;
    @ColumnInfo(name = "climb", defaultValue = "0")
    private int climb;
    @ColumnInfo(name = "collectedFuel", defaultValue = "0")
    private int collectedFuel;
    @ColumnInfo(name = "scored", defaultValue = "0")
    private int scored;
    @ColumnInfo(name = "wentToNeutral", defaultValue = "0")
    private int wentToNeutral;

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
    public void setDepot(int depot) { this.depot = depot; }
    public int getDepot() { return depot; }
    public void setClimb(int climb) { this.climb = climb; }
    public int getClimb() { return climb; }
    public void setCollectedFuel(int collectedFuel) { this.collectedFuel = collectedFuel; }
    public int getCollectedFuel() { return collectedFuel; }
    public void setScored(int scored) { this.scored = scored; }
    public int getScored() { return scored; }
    public void setWentToNeutral(int wentToNeutral) { this.wentToNeutral = wentToNeutral; }
    public int getWentToNeutral() { return wentToNeutral; }

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