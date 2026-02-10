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
    @ColumnInfo(name = "scoringLocations", defaultValue = "")
    private String scoringLocations;
    @ColumnInfo(name = "shotWhileMoving", defaultValue = "false")
    private boolean shotWhileMoving;
    @ColumnInfo(name = "pinRating", defaultValue = "0")
    private int pinRating;
    @ColumnInfo(name = "stealRating", defaultValue = "0")
    private int stealRating;
    @ColumnInfo(name = "blockRating", defaultValue = "0")
    private int blockRating;
    @ColumnInfo(name = "ramRating", defaultValue = "0")
    private int ramRating;
    @ColumnInfo(name = "antiPinRating", defaultValue = "0")
    private int antiPinRating;
    @ColumnInfo(name = "antiStealRating", defaultValue = "0")
    private int antiStealRating;
    @ColumnInfo(name = "antiBlockRating", defaultValue = "0")
    private int antiBlockRating;
    @ColumnInfo(name = "antiRamRating", defaultValue = "0")
    private int antiRamRating;
    @ColumnInfo(name = "endgameClimb", defaultValue = "")
    private String endgameClimb;
    @ColumnInfo(name = "notes", defaultValue = "0")
    private String notes;
    @ColumnInfo(name = "trench", defaultValue = "false")
    private boolean trench;
    @ColumnInfo(name = "bump", defaultValue = "false")
    private boolean bump;


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
    public void setScoringLocations(String scoringLocations) {
        this.scoringLocations = scoringLocations;
    }
    public String getScoringLocations() {
        return scoringLocations;
    }
    public void setShotWhileMoving(boolean shotWhileMoving) {
        this.shotWhileMoving = shotWhileMoving;
    }
    public boolean isShotWhileMoving() {
        return shotWhileMoving;
    }
    public void setPinRating(int pinRating) {
        this.pinRating = pinRating;
    }
    public int getPinRating() {
        return pinRating;
    }
    public void setStealRating(int stealRating) {
        this.stealRating = stealRating;
    }
    public int getStealRating() {
        return stealRating;
    }
    public void setBlockRating(int blockRating) {
        this.blockRating = blockRating;
    }
    public int getBlockRating() {
        return blockRating;
    }
    public void setRamRating(int ramRating) {
        this.ramRating = ramRating;
    }
    public int getRamRating() {
        return ramRating;
    }
    public void setAntiPinRating(int antiPinRating) {
        this.antiPinRating = antiPinRating;
    }
    public int getAntiPinRating() {
        return antiPinRating;
    }
    public void setAntiStealRating(int antiStealRating) {
        this.antiStealRating = antiStealRating;
    }
    public int getAntiStealRating() {
        return antiStealRating;
    }
    public void setAntiBlockRating(int antiBlockRating) {
        this.antiBlockRating = antiBlockRating;
    }
    public int getAntiBlockRating() {
        return antiBlockRating;
    }
    public void setAntiRamRating(int antiRamRating) {
        this.antiRamRating = antiRamRating;
    }
    public int getAntiRamRating() {
        return antiRamRating;
    }
    public void setEndgameClimb(String endgameClimb) {
        this.endgameClimb = endgameClimb;
    }
    public String getEndgameClimb() {
        return endgameClimb;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public void setTrench(boolean trench) {
        this.trench = trench;
    }
    public boolean getTrench() {
        return this.trench;
    }
    public void setBump(boolean bump) {
        this.bump = bump;
    }
    public boolean setBump() {
        return this.bump;
    }
}