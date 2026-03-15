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
    private final String matchSchedule = "\n" +
            "Match\tRed Alliance\tBlue Alliance\tScores\n" +
            "Qualifications\n" +
            "Quals 1\n" +
            "8100\t1833\t8815\t6340\t5074\t1261\tSat 11:00 AM*\n" +
            "Quals 2\n" +
            "6705\t4189\t10482\t2415\t6910\t7538\tSat 11:10 AM*\n" +
            "Quals 3\n" +
            "9522\t3318\t1648\t1795\t4701\t9561\tSat 11:20 AM*\n" +
            "Quals 4\n" +
            "9057\t5293\t5109\t8865\t1771\t4509\tSat 11:30 AM*\n" +
            "Quals 5\n" +
            "3091\t1683\t5651\t11174\t4468\t8577\tSat 11:40 AM*\n" +
            "Quals 6\n" +
            "1648\t9561\t6705\t1833\t1795\t4189\tSat 11:50 AM*\n" +
            "Quals 7\n" +
            "6910\t4701\t8815\t3318\t2415\t4509\tSat 12:00 PM*\n" +
            "Quals 8\n" +
            "10482\t4468\t8100\t6340\t5293\t8577\tSat 12:08 PM*\n" +
            "Quals 9\n" +
            "5109\t7538\t1683\t5651\t8865\t5074\tSat 12:16 PM*\n" +
            "Quals 10\n" +
            "1261\t9057\t3091\t9522\t1771\t11174\tSat 12:24 PM*\n" +
            "Quals 11\n" +
            "4189\t1648\t2415\t4468\t4509\t1795\tSat 12:32 PM*\n" +
            "Quals 12\n" +
            "5293\t5651\t3318\t9561\t1683\t1833\tSat 12:40 PM*\n" +
            "Quals 13\n" +
            "9057\t8577\t8865\t3091\t8100\t6910\tSat 2:00 PM*\n" +
            "Quals 14\n" +
            "1771\t8815\t7538\t1261\t9522\t4701\tSat 2:08 PM*\n" +
            "Quals 15\n" +
            "6705\t11174\t6340\t10482\t5074\t5109\tSat 2:16 PM*\n" +
            "Quals 16\n" +
            "4189\t8100\t9561\t3318\t8865\t4468\tSat 2:24 PM*\n" +
            "Quals 17\n" +
            "1795\t2415\t1261\t6910\t4509\t1683\tSat 2:32 PM*\n" +
            "Quals 18\n" +
            "1648\t5074\t4701\t5293\t8815\t11174\tSat 2:40 PM*\n" +
            "Quals 19\n" +
            "10482\t8577\t7538\t9057\t1833\t1771\tSat 2:48 PM*\n" +
            "Quals 20\n" +
            "6705\t5109\t5651\t3091\t9522\t6340\tSat 2:56 PM*\n" +
            "Quals 21\n" +
            "8865\t6910\t1261\t4701\t4189\t5293\tSat 3:04 PM*\n" +
            "Quals 22\n" +
            "4468\t9057\t1683\t7538\t3318\t9561\tSat 3:12 PM*\n" +
            "Quals 23\n" +
            "9522\t1833\t5074\t1648\t3091\t10482\tSat 3:20 PM*\n" +
            "Quals 24\n" +
            "11174\t2415\t8100\t8815\t5109\t1795\tSat 3:28 PM*\n" +
            "Quals 25\n" +
            "4509\t6340\t6705\t8577\t5651\t1771\tSat 3:36 PM*\n" +
            "Quals 26\n" +
            "5293\t8865\t7538\t3318\t1833\t3091\tSat 3:44 PM*\n" +
            "Quals 27\n" +
            "5074\t4189\t6910\t5109\t1648\t8100\tSat 3:52 PM*\n" +
            "Quals 28\n" +
            "1683\t6705\t11174\t4701\t9057\t10482\tSat 4:00 PM*\n" +
            "Quals 29\n" +
            "8577\t9522\t1795\t2415\t9561\t8815\tSat 4:08 PM*\n" +
            "Quals 30\n" +
            "4509\t1261\t1771\t4468\t5651\t6340\tSat 4:16 PM*\n" +
            "Quals 31\n" +
            "11174\t3091\t5109\t6910\t10482\t3318\tSat 4:24 PM*\n" +
            "Quals 32\n" +
            "8865\t1683\t4189\t6705\t5293\t9522\tSat 4:32 PM*\n" +
            "Quals 33\n" +
            "6340\t1795\t9057\t8815\t1261\t5651\tSat 4:40 PM*\n" +
            "Quals 34\n" +
            "8100\t5074\t4509\t1771\t9561\t2415\tSat 4:48 PM*\n" +
            "Quals 35\n" +
            "7538\t4468\t4701\t1833\t1648\t8577\tSat 4:56 PM*\n" +
            "Quals 36\n" +
            "8815\t10482\t6340\t3091\t1795\t8865\tSat 5:04 PM*\n" +
            "Quals 37\n" +
            "4509\t1261\t9561\t5651\t6910\t6705\tSat 5:12 PM*\n" +
            "Quals 38\n" +
            "1683\t1771\t3318\t5109\t8577\t1648\tSat 5:20 PM*\n" +
            "Quals 39\n" +
            "5074\t7538\t9057\t5293\t2415\t4468\tSat 5:28 PM*\n" +
            "Quals 40\n" +
            "11174\t4701\t1833\t9522\t4189\t8100\tSat 5:36 PM*\n" +
            "Quals 41\n" +
            "1261\t8577\t6705\t3318\t8865\t5109\tSat 5:44 PM*\n" +
            "Quals 42\n" +
            "1795\t3091\t6910\t1683\t8815\t5074\tSat 5:52 PM*\n" +
            "Quals 43\n" +
            "1771\t4468\t4189\t2415\t4701\t5651\tSun 9:30 AM*\n" +
            "Quals 44\n" +
            "1833\t4509\t5293\t9057\t8100\t9522\tSun 9:40 AM*\n" +
            "Quals 45\n" +
            "9561\t11174\t10482\t7538\t6340\t1648\tSun 9:50 AM*\n" +
            "Quals 46\n" +
            "4189\t8815\t3091\t5074\t6705\t1771\tSun 9:58 AM*\n" +
            "Quals 47\n" +
            "4701\t9522\t4468\t5109\t9057\t4509\tSun 10:06 AM*\n" +
            "Quals 48\n" +
            "9561\t6340\t8865\t2415\t1683\t8577\tSun 10:14 AM*\n" +
            "Quals 49\n" +
            "8100\t1795\t3318\t1261\t10482\t5293\tSun 10:22 AM*\n" +
            "Quals 50\n" +
            "5651\t7538\t1833\t6910\t11174\t1648\tSun 10:30 AM*\n" +
            "Quals 51\n" +
            "4701\t3091\t2415\t1683\t6340\t4189\tSun 10:38 AM*\n" +
            "Quals 52\n" +
            "8577\t3318\t5074\t4509\t10482\t9522\tSun 10:46 AM*\n" +
            "Quals 53\n" +
            "4468\t5109\t1833\t9561\t6705\t9057\tSun 10:54 AM*\n" +
            "Quals 54\n" +
            "8865\t1648\t8815\t8100\t7538\t1261\tSun 11:02 AM*\n" +
            "Quals 55\n" +
            "1771\t5293\t6910\t1795\t5651\t11174\tSun 11:10 AM*\n" +
            "Quals 56\n" +
            "6340\t5109\t2415\t8577\t4509\t3091\tSun 11:18 AM*\n" +
            "Quals 57\n" +
            "1648\t1261\t1683\t4189\t3318\t9057\tSun 11:26 AM*\n" +
            "Quals 58\n" +
            "5651\t8100\t9522\t8815\t6705\t4468\tSun 11:34 AM*\n" +
            "Quals 59\n" +
            "10482\t1771\t1795\t1833\t6910\t8865\tSun 11:42 AM*\n" +
            "Quals 60\n" +
            "5074\t9561\t5293\t7538\t11174\t4701\tSun 11:50 AM*";

    // copy chart from google sheets. if in quotes, it'll automatically add the \t and stuff
    private final String scouterSchedule = "1 - 5\tRichard Peng\tAn Nguyen\tSelina Lin\tRoman Krutau\tAayush Prasad\tLily Stauffer\n" +
            "6 - 10\tVivian Tran\tWilliam Lu\tVedha Tamilinian\tPatrick Peng\tRoman Krutau\tSaathvik Sundaram\n" +
            "11 - 15\tEli Pak\tRishi Pai\tYajjat Sinha\tAayush Prasad\tMichael Reyes\tJiayu Wang\n" +
            "16 - 20\tAshley Zhang\tAn Nguyen\tEli Pak\tPatrick Peng\tKaitlyn Mak\tLily Stauffer\n" +
            "21 - 25\tSelina Lin\tWilliam Lu\tYajjat Sinha\tVedha Tamilinian\tAayush Prasad\tSaathvik Sundaram\n" +
            "26 - 30\tRichard Peng\tAnirudh Vijay\tWilliam Lu\tKaitlyn Mak\tJiayu Wang\tVincent Ng\n" +
            "31 - 35\tEli Pak\tRishi Pai\tSelina Lin\tLily Stauffer\tMichael Reyes\tRoman Krutau\n" +
            "36 - 40\tAshley Zhang\tAnirudh Vijay\tYajjat Sinha\tPatrick Peng\tKaitlyn Mak\tVincent Ng\n" +
            "41 - 44\tRichard Peng\tAn Nguyen\tVedha Tamilinian\tJiayu Wang\tAayush Prasad\tRoman Krutau\n" +
            "45 - 49\tAbdul Gargodhi\tAnirudh Vijay\tYajjat Sinha\tTushar Gudeti\tGrace Li\tRayna Johncaptain\n" +
            "50 - 54\tEli Pak\tRishi Pai\tAswath Manojkumar\tVincent Ng\tMichael Reyes\tEvan Saran\n" +
            "55 - 59\tAshley Zhang\tKaitlyn Mak\tRayna Johncaptain\tPatrick Peng\tTushar Gudeti\tGrace Li\n" +
            "60 - 64\tRichard Peng\tEvan Saran\tAswath Manojkumar\tLily Stauffer\tAbdul Gargodhi\tWilliam Lu";
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

            String b1 = columns[1];
            String b2 = columns[2];
            String b3 = columns[3];
            String r1 = columns[4];
            String r2 = columns[5];
            String r3 = columns[6];

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