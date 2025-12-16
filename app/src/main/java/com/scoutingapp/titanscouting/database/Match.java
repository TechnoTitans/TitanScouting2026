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
    @ColumnInfo(name = "highCount", defaultValue = "0")
    private int highCount;
    @ColumnInfo(name = "highMissedCount", defaultValue = "0")
    private int highMissedCount;
    @ColumnInfo(name = "lowCount", defaultValue = "0")
    private int lowCount;
    @ColumnInfo(name = "lowMissedCount", defaultValue = "0")
    private int lowMissedCount;
    @ColumnInfo(name = "moat", defaultValue = "false")
    private boolean moat;
    @ColumnInfo(name = "ramparts", defaultValue = "false")
    private boolean ramparts;
    @ColumnInfo(name = "drawbridge", defaultValue = "false")
    private boolean drawbridge;
    @ColumnInfo(name = "sallyPort", defaultValue = "false")
    private boolean sallyPort;
    @ColumnInfo(name = "rockwall", defaultValue = "false")
    private boolean rockwall;
    @ColumnInfo(name = "roughterrain", defaultValue = "false")
    private boolean roughterrain;
    @ColumnInfo(name = "portcullis", defaultValue = "false")
    private boolean portcullis;
    @ColumnInfo(name = "cheval", defaultValue = "false")
    private boolean cheval;
    @ColumnInfo(name = "endgamePos", defaultValue = "None")
    private String endgamePos;
    @ColumnInfo(name = "brokedown", defaultValue = "false")
    private boolean brokedown;
    @ColumnInfo(name = "notes", defaultValue = "0")
    private String notes;
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
    public int getHighCount() {
        return highCount;
    }
    public void setHighCount(int highCount) {
        this.highCount = highCount;
    }
    public int getHighMissedCount() {
        return highMissedCount;
    }
    public void setHighMissedCount(int highMissedCount) {
        this.highMissedCount = highMissedCount;
    }
    public int getLowCount() {
        return lowCount;
    }
    public void setLowCount(int lowCount) {
        this.lowCount = lowCount;
    }
    public int getLowMissedCount() {
        return lowMissedCount;
    }
    public void setLowMissedCount(int lowMissedCount) {
        this.lowMissedCount = lowMissedCount;
    }
    public boolean getBrokedown() {
        return brokedown;
    }
    public void setBrokedown(boolean brokedown) {
        this.brokedown = brokedown;
    }

    public boolean isMoat() {
        return moat;
    }
    public void setMoat(boolean moat) {
        this.moat = moat;
    }

    public boolean isRamparts() {
        return ramparts;
    }
    public void setRamparts(boolean ramparts) {
        this.ramparts = ramparts;
    }
    public boolean isDrawbridge() {
        return drawbridge;
    }
    public void setDrawbridge(boolean drawbridge) {
        this.drawbridge = drawbridge;
    }

    public boolean isSallyPort() {
        return sallyPort;
    }
    public void setSallyPort(boolean sallyPort) {
        this.sallyPort = sallyPort;
    }
    public boolean isRockwall() {
        return rockwall;
    }
    public void setRockwall(boolean rockwall) {
        this.rockwall = rockwall;
    }
    public boolean isRoughterrain() {
        return roughterrain;
    }
    public void setRoughterrain(boolean roughterrain) {
        this.roughterrain = roughterrain;
    }
    public boolean isPortcullis() {
        return portcullis;
    }
    public void setPortcullis(boolean portcullis) {
        this.portcullis = portcullis;
    }
    public boolean isCheval() {
        return cheval;
    }
    public void setCheval(boolean cheval) {
        this.cheval = cheval;
    }

    public String getEndgamePos() {
        return endgamePos;
    }
    public void setEndgamePos(String endgamePos) {
        this.endgamePos = endgamePos;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
}