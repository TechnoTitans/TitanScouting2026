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
    public int getProcessorCount() {
        return processorCount;
    }
    public void setProcessorCount(int processorCount) {
        this.processorCount = processorCount;
    }
    public int getNetCount() {
        return netCount;
    }
    public void setNetCount(int netCount) {
        this.netCount = netCount;
    }
    public int getL1MissedCount() {
        return l1MissedCount;
    }
    public void setL1MissedCount(int l1MissedCount) {
        this.l1MissedCount = l1MissedCount;
    }
    public int getL2MissedCount() {
        return l2MissedCount;
    }
    public void setL2MissedCount(int l2MissedCount) {
        this.l2MissedCount = l2MissedCount;
    }
    public int getL3MissedCount() {
        return l3MissedCount;
    }
    public void setL3MissedCount(int l3MissedCount) {
        this.l3MissedCount = l3MissedCount;
    }
    public int getL4MissedCount() {
        return l4MissedCount;
    }
    public void setL4MissedCount(int l4MissedCount) {
        this.l4MissedCount = l4MissedCount;
    }
    public int getProcessorMissedCount() {
        return processorMissedCount;
    }
    public void setProcessorMissedCount(int processorMissedCount) {
        this.processorMissedCount = processorMissedCount;
    }
    public int getNetMissedCount() {
        return netMissedCount;
    }
    public void setNetMissedCount(int netMissedCount) {
        this.netMissedCount = netMissedCount;
    }
    public String getEndgamePos() {
        return endgamePos;
    }
    public void setEndgamePos(String endgamePos) {
        this.endgamePos = endgamePos;
    }
    public boolean isGroundCoral() {
        return groundCoral;
    }
    public void setGroundCoral(boolean groundCoral) {
        this.groundCoral = groundCoral;
    }
    public boolean isGroundAlgae() {
        return groundAlgae;
    }
    public void setGroundAlgae(boolean groundAlgae) {
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