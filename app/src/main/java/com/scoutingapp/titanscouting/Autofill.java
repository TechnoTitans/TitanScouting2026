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
    private final String matchSchedule =
            "\tMatch\tRed Alliance\tBlue Alliance\tScores\n" +
                    "Qualifications\n" +
                    "Quals 1\n" +
                    "6340\t2974\t6705\t8736\t8080\t6023\tFri 11:00 AM*\n" +
                    "Quals 2\n" +
                    "3635\t4189\t5074\t6829\t8865\t1771\tFri 11:09 AM*\n" +
                    "Quals 3\n" +
                    "4112\t4188\t9480\t5109\t3344\t5608\tFri 11:18 AM*\n" +
                    "Quals 4\n" +
                    "1833\t8100\t1648\t4026\t7451\t5900\tFri 11:27 AM*\n" +
                    "Quals 5\n" +
                    "6905\t1683\t7538\t4509\t9522\t1002\tFri 11:36 AM*\n" +
                    "Quals 6\n" +
                    "2415\t832\t1261\t8866\t8577\t3318\tFri 11:45 AM*\n" +
                    "Quals 7\n" +
                    "3815\t8080\t1414\t5293\t5608\t4189\tFri 11:55 AM*\n" +
                    "Quals 8\n" +
                    "3344\t5074\t1771\t5109\t6340\t4026\tFri 12:04 PM*\n" +
                    "Quals 9\n" +
                    "4112\t6023\t6905\t7538\t7451\t8736\tFri 12:13 PM*\n" +
                    "Quals 10\n" +
                    "3318\t1002\t832\t4188\t6829\t5900\tFri 12:22 PM*\n" +
                    "Quals 11\n" +
                    "1648\t2974\t2415\t1833\t1683\t5293\tFri 12:31 PM*\n" +
                    "Quals 12\n" +
                    "9480\t4509\t8866\t8577\t1414\t3635\tFri 2:00 PM*\n" +
                    "Quals 13\n" +
                    "8100\t9522\t8865\t6705\t1261\t3815\tFri 2:09 PM*\n" +
                    "Quals 14\n" +
                    "7451\t6023\t832\t5074\t1002\t5900\tFri 2:18 PM*\n" +
                    "Quals 15\n" +
                    "1771\t1683\t4112\t8080\t5293\t4026\tFri 2:27 PM*\n" +
                    "Quals 16\n" +
                    "4509\t6340\t1833\t1648\t8866\t5608\tFri 2:36 PM*\n" +
                    "Quals 17\n" +
                    "9522\t6705\t4188\t1414\t2415\t7538\tFri 2:45 PM*\n" +
                    "Quals 18\n" +
                    "8736\t8865\t4189\t6905\t3318\t9480\tFri 2:55 PM*\n" +
                    "Quals 19\n" +
                    "3635\t6829\t3344\t2974\t3815\t8100\tFri 3:04 PM*\n" +
                    "Quals 20\n" +
                    "5900\t5109\t8577\t1261\t8080\t1683\tFri 3:13 PM*\n" +
                    "Quals 21\n" +
                    "5293\t6340\t7451\t832\t4188\t4509\tFri 3:22 PM*\n" +
                    "Quals 22\n" +
                    "1002\t6905\t8865\t1771\t2415\t4026\tFri 3:31 PM*\n" +
                    "Quals 23\n" +
                    "1414\t8100\t8866\t6023\t9480\t2974\tFri 3:40 PM*\n" +
                    "Quals 24\n" +
                    "9522\t7538\t3635\t5074\t1648\t8736\tFri 3:50 PM*\n" +
                    "Quals 25\n" +
                    "6829\t3815\t1833\t1261\t4112\t5109\tFri 3:59 PM*\n" +
                    "Quals 26\n" +
                    "5608\t6705\t8577\t4189\t3318\t3344\tFri 4:08 PM*\n" +
                    "Quals 27\n" +
                    "7451\t1002\t1683\t8080\t9480\t6340\tFri 4:17 PM*\n" +
                    "Quals 28\n" +
                    "832\t4026\t9522\t6023\t4509\t3635\tFri 4:26 PM*\n" +
                    "Quals 29\n" +
                    "5293\t8866\t7538\t6829\t8100\t5109\tFri 4:35 PM*\n" +
                    "Quals 30\n" +
                    "5608\t8736\t2415\t5900\t1771\t3815\tFri 4:45 PM*\n" +
                    "Quals 31\n" +
                    "8577\t3344\t4188\t8865\t1833\t5074\tFri 4:54 PM*\n" +
                    "Quals 32\n" +
                    "1261\t1648\t3318\t4189\t4112\t1414\tFri 5:03 PM*\n" +
                    "Quals 33\n" +
                    "832\t5109\t2974\t6905\t7451\t6705\tFri 5:12 PM*\n" +
                    "Quals 34\n" +
                    "9480\t4026\t6829\t9522\t5293\t2415\tFri 5:21 PM*\n" +
                    "Quals 35\n" +
                    "7538\t1771\t8080\t8866\t3344\t1833\tFri 5:30 PM*\n" +
                    "Quals 36\n" +
                    "1683\t1414\t5608\t3318\t8736\t8100\tFri 5:40 PM*\n" +
                    "Quals 37\n" +
                    "3815\t8577\t6905\t6023\t1648\t4188\tFri 5:49 PM*\n" +
                    "Quals 38\n" +
                    "5900\t1261\t6340\t8865\t3635\t4112\tFri 5:58 PM*\n" +
                    "Quals 39\n" +
                    "5074\t2974\t4509\t4189\t6705\t1002\tFri 6:07 PM*\n" +
                    "Quals 40\n" +
                    "5608\t1833\t3318\t7451\t9480\t9522\tFri 6:16 PM*\n" +
                    "Quals 41\n" +
                    "1683\t3815\t6023\t1648\t832\t1771\tFri 6:25 PM*\n" +
                    "Quals 42\n" +
                    "1414\t1261\t6829\t3344\t6905\t6340\tSat 9:00 AM*\n" +
                    "Quals 43\n" +
                    "5109\t3635\t8736\t2415\t1002\t8577\tSat 9:09 AM*\n" +
                    "Quals 44\n" +
                    "8080\t4509\t4189\t4188\t5074\t8100\tSat 9:18 AM*\n" +
                    "Quals 45\n" +
                    "4112\t5900\t5293\t6705\t8865\t8866\tSat 9:27 AM*\n" +
                    "Quals 46\n" +
                    "2974\t4026\t1261\t7538\t5608\t832\tSat 9:36 AM*\n" +
                    "Quals 47\n" +
                    "1771\t8577\t7451\t6340\t9522\t6829\tSat 9:45 AM*\n" +
                    "Quals 48\n" +
                    "8100\t4189\t6023\t8080\t2415\t5109\tSat 9:55 AM*\n" +
                    "Quals 49\n" +
                    "4188\t1833\t6905\t1683\t3635\t6705\tSat 10:04 AM*\n" +
                    "Quals 50\n" +
                    "3344\t5293\t1648\t8866\t8736\t1002\tSat 10:13 AM*\n" +
                    "Quals 51\n" +
                    "9480\t5900\t8865\t4509\t7538\t3815\tSat 10:22 AM*\n" +
                    "Quals 52\n" +
                    "4026\t4112\t5074\t3318\t1414\t2974\tSat 10:31 AM*\n" +
                    "Quals 53\n" +
                    "8100\t5608\t8080\t3635\t6905\t832\tSat 10:40 AM*\n" +
                    "Quals 54\n" +
                    "1002\t4188\t5293\t1833\t6023\t1261\tSat 10:50 AM*\n" +
                    "Quals 55\n" +
                    "8736\t1771\t9480\t6705\t6829\t1648\tSat 10:59 AM*\n" +
                    "Quals 56\n" +
                    "8577\t4189\t9522\t2974\t5900\t3344\tSat 11:08 AM*\n" +
                    "Quals 57\n" +
                    "2415\t3318\t4509\t6340\t7538\t4112\tSat 11:17 AM*\n" +
                    "Quals 58\n" +
                    "4026\t8866\t1683\t3815\t5074\t7451\tSat 11:26 AM*\n" +
                    "Quals 59\n" +
                    "1002\t5109\t1414\t8865\t832\t8080\tSat 11:35 AM*";

    // copy chart from google sheets. if in quotes, it'll automatically add the \t and stuff
    private final String scouterSchedule = "1 - 5\tRichard Peng\tAn Nguyen\tYajjat Sinha\tRoman Krutau\tAayush Prasad\tLily Stauffer\n" +
            "6 - 10\tVivian Tran\tWilliam Lu\tVedha Tamilinian\tPatrick Peng\tRoman Krutau\tSaathvik Sundaram\n" +
            "11 - 15\tEli Pak\tRishi Pai\tSelina Lin\tAayush Prasad\tMichael Reyes\tJiayu Wang\n" +
            "16 - 20\tAshley Zhang\tAn Nguyen\tYajjat Sinha\tPatrick Peng\tKaitlyn Mak\tLily Stauffer\n" +
            "21 - 25\tSelina Lin\tWilliam Lu\tEli Pak\tVedha Tamilinian\tAayush Prasad\tSaathvik Sundaram\n" +
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