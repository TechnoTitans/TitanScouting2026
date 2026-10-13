package com.scoutingapp.titanscouting;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;

import com.scoutingapp.titanscouting.database.MatchRepository;
import com.scoutingapp.titanscouting.database.MatchViewModel;

public class Autofill {
    public static boolean[] matchSubmitted = new boolean[200];
    // set to number of matches. no problems with having extra
    private final int numMatches = 126;

    // copy entire table from TBA for qualification matches
    private final String matchSchedule = "\tMatch\tRed Alliance\tBlue Alliance\t-Scores\n" +
            "Qualifications\n" +
            "Quals 1\n" +
            "1002\t1833\t8736\t1102\t6919\t7538\tSat 9:30 AM*\n" +
            "Quals 2\n" +
            "9971\t1683\t9477\t9086\t1771\t6829\tSat 9:45 AM*\n" +
            "Quals 3\n" +
            "9977\t4189\t5293\t1648\t1261\t9999\tSat 10:00 AM*\n" +
            "Quals 4\n" +
            "8736\t9086\t4026\t6829\t1102\t1683\tSat 10:15 AM*\n" +
            "Quals 5\n" +
            "1002\t1261\t1833\t9971\t1771\t1648\tSat 10:30 AM*\n" +
            "Quals 6\n" +
            "9977\t9999\t9477\t4189\t7538\t4026\tSat 10:45 AM*\n" +
            "Quals 7\n" +
            "6919\t6829\t1833\t5293\t1648\t1102\tSat 11:00 AM*\n" +
            "Quals 8\n" +
            "1261\t9086\t1002\t9977\t4026\t8736\tSat 11:15 AM*\n" +
            "Quals 9\n" +
            "9477\t5293\t7538\t9971\t4189\t6919\tSat 11:30 AM*\n" +
            "Quals 10\n" +
            "1771\t9999\t1002\t1683\t9977\t1648\tSat 11:45 AM*\n" +
            "Quals 11\n" +
            "5293\t4026\t1261\t9086\t7538\t6919\tSat 12:00 PM*\n" +
            "Quals 12\n" +
            "6829\t9999\t8736\t1102\t1771\t9971\tSat 12:15 PM*\n" +
            "Quals 13\n" +
            "1102\t9477\t1833\t1683\t4189\t1002\tSat 1:30 PM*\n" +
            "Quals 14\n" +
            "6829\t8736\t9971\t7538\t1648\t9086\tSat 1:45 PM*\n" +
            "Quals 15\n" +
            "6919\t1261\t4189\t1771\t4026\t1683\tSat 2:00 PM*\n" +
            "Quals 16\n" +
            "9999\t1833\t5293\t9477\t9977\t9086\tSat 2:15 PM*\n" +
            "Quals 17\n" +
            "1648\t8736\t6919\t1261\t1683\t9971\tSat 2:30 PM*\n" +
            "Quals 18\n" +
            "9999\t5293\t1102\t9477\t4026\t1771\tSat 2:45 PM*\n" +
            "Quals 19\n" +
            "6829\t1833\t4189\t9977\t1002\t7538\tSat 3:00 PM*\n" +
            "Quals 20\n" +
            "8736\t1683\t5293\t4026\t9971\t9999\tSat 3:15 PM*\n" +
            "Quals 21\n" +
            "1833\t1648\t1261\t1002\t6829\t9477\tSat 4:00 PM*\n" +
            "Quals 22\n" +
            "9086\t4189\t1102\t1771\t6919\t9977\tSat 4:15 PM*\n" +
            "Quals 23\n" +
            "7538\t9477\t1683\t1648\t1002\t4026\tSat 4:30 PM*\n" +
            "Quals 24\n" +
            "5293\t9971\t9086\t9977\t1102\t1261\tSat 4:45 PM*\n" +
            "Quals 25\n" +
            "4189\t8736\t1771\t9999\t7538\t6829\tSat 5:00 PM*\n" +
            "Quals 26\n" +
            "1833\t9977\t9971\t4026\t6919\t1102\tSun 8:30 AM*\n" +
            "Quals 27\n" +
            "9999\t9086\t4189\t1648\t1002\t6829\tSun 8:45 AM*\n" +
            "Quals 28\n" +
            "1833\t1771\t5293\t8736\t9477\t1261\tSun 9:00 AM*\n" +
            "Quals 29\n" +
            "1683\t6919\t9999\t7538\t9971\t1002\tSun 9:15 AM*\n" +
            "Quals 30\n" +
            "4189\t9477\t1648\t8736\t1102\t9977\tSun 9:30 AM*\n" +
            "Quals 31\n" +
            "9086\t1833\t1683\t1261\t7538\t1771\tSun 9:45 AM*\n" +
            "Quals 32\n" +
            "6919\t5293\t1002\t4026\t6829\t9977\tSun 10:00 AM*";

    // copy chart from google sheets. if in quotes, it'll automatically add the \t and stuff
    private final String scouterSchedule = "1 - 5\tAlex Zheng\tAshirvad Manickandan\tRuichen Yang\tRoman Krutau\tRohan Hariharan\tSymon Sediki\tAditya Ahuja\tJiayu Wang\n" +
            "6 - 10\tShivesh Ramesh\tShrihaan Chowdhury\tPreston Lee\tDedeepya Vatti\tPeggy Liu\tKaitlyn Mak\tAswath Manojkumar\tFarzan Kudchikar\n" +
            "11- 15\tJianing He\tEli Pak\tShivam Kataria \tRoman Krutau\tRuichen Yang\tSymon Sediki\tEvan Saran\tRichard Peng\n" +
            "16 - 20\tShrihaan Chowdhury\tRohan Hariharan\tDedeepya Vatti\tAshirvad Manickandan\tAlex Zheng\tPreston Lee\tAayush Prasad\tAshley Zhang\n" +
            "21 - 25\tPeggy Liu\tRoman Krutau\tRuichen Yang\tJianing He\tEli Pak\tShivam Kataria\tJiayu Wang\tAswath Manojkumar\n" +
            "26 - 30\tPreston Lee\tAlex Zheng\tSymon Sediki\tShrihaan Chowdhury\tAshirvad Manickandan\tKaitlyn Mak\tFarzan Kudchikar\tAditya Ahuja\n" +
            "31 - 32\tShivesh Ramesh\tShlok Gohil\tRohan Hariharan\tIshayu Dasgupta\tShivam Kataria\tRishitha Nalukurthy\tAshley Zhang\tEvan Saran";
    private final int[] red1 = new int[numMatches];
    private final int[] red2 = new int[numMatches];
    private final int[] red3 = new int[numMatches];
    private final int[] blue1 = new int[numMatches];
    private final int[] blue2 = new int[numMatches];
    private final int[] blue3 = new int[numMatches];

    String[] r1Scouter = new String[numMatches];
    String[] r2Scouter = new String[numMatches];
    String[] r3Scouter = new String[numMatches];
    String[] b1Scouter = new String[numMatches];
    String[] b2Scouter = new String[numMatches];
    String[] b3Scouter = new String[numMatches];

    public Autofill() {
        String[] lines = matchSchedule.split("\n");

        int matchIndex = 1;
        //implementation code uses index starting at 1

        // iterate through the lines and process each match
        for (int i = 0; i < lines.length; i++) {
            // look for match lines like "Quals 1", "Quals 2", etc.
            if (lines[i].contains("Quals")) {
                String[] columns = lines[i + 1].split("\t");

                red1[matchIndex] = Integer.parseInt(columns[0]);
                red2[matchIndex] = Integer.parseInt(columns[1]);
                red3[matchIndex] = Integer.parseInt(columns[2]);
                blue1[matchIndex] = Integer.parseInt(columns[3]);
                blue2[matchIndex] = Integer.parseInt(columns[4]);
                blue3[matchIndex] = Integer.parseInt(columns[5]);

                matchIndex++;  // increment match index for the next match
            }
        }

        String[] schedule = scouterSchedule.split("\n");

        for (int i = 0; i < schedule.length; i++) {
            String[] columns = schedule[i].split("\t");

            String[] matchRange = columns[0].replace(" ", "").split("-");
            int start = Integer.parseInt(matchRange[0]);
            int end = Integer.parseInt(matchRange[1]);

            String r1 = columns[1];
            String r2 = columns[2];
            String r3 = columns[3];
            String b1 = columns[4];
            String b2 = columns[5];
            String b3  = columns[6];

            for (int j = start; j <= end; j++) {
                b1Scouter[j] = b1;
                b2Scouter[j] = b2;
                b3Scouter[j] = b3;
                r1Scouter[j] = r1;
                r2Scouter[j] = r2;
                r3Scouter[j] = r3;
            }
        }
    }

    // Method to get a specific team's number from the parsed schedule
    public int getTeamNumberFromTable(int matchNumber, String position) {
        // Ensure matchNumber is within bounds
        if (matchNumber < 0 || matchNumber > numMatches) {
            return 0;
        }

        // Check position and return the corresponding team number
        if (position != null) {
            switch (position) {
                case "R1":
                    return red1[matchNumber];
                case "R2":
                    return red2[matchNumber];
                case "R3":
                    return red3[matchNumber];
                case "B1":
                    return blue1[matchNumber];
                case "B2":
                    return blue2[matchNumber];
                case "B3":
                    return blue3[matchNumber];
                default:
                    return -1;  // Return -1 for invalid position, shouldn't ever happen
            }
        } else {
            System.out.println("Position is null!");
            return 0;  // Return 0 for null position
        }
    }

    public String getScouterName(int matchNumber, String position) {
        if (matchNumber < 0 || matchNumber > numMatches) {
            System.out.println("error getting scouter name");
            return "Error";
        }

        if (position != null) {
            switch (position) {
                case "R1":
                    System.out.println("its " + r1Scouter[matchNumber]);
                    return r1Scouter[matchNumber];
                case "R2":
                    System.out.println("its " + r2Scouter[matchNumber]);
                    return r2Scouter[matchNumber];
                case "R3":
                    System.out.println("its " + r3Scouter[matchNumber]);
                    return r3Scouter[matchNumber];
                case "B1":
                    System.out.println("its " + b1Scouter[matchNumber]);
                    return b1Scouter[matchNumber];
                case "B2":
                    System.out.println("its " + b2Scouter[matchNumber]);
                    return b2Scouter[matchNumber];
                case "B3":
                    System.out.println("its " + b3Scouter[matchNumber]);
                    return b3Scouter[matchNumber];
                default:
                    System.out.println("erro1");
                    return "Error1";
            }
        } else {
            System.out.println("Position is null!");
            return "hi";  // Return 0 for null position
        }
    }

    public int getNextMatch() {
        // not implemented yet
        return 0;
    }
}