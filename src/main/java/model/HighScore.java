package model;

public class HighScore {

    public static String[] checkHighScore(int[] score) {
        String[] highScore = clsINI.readSettingsFileHScore();
        String[] new_HS = new String[]{"NEM", ""};
        if (score[0] == score[1]) {
            if (score[0] > Integer.parseInt(highScore[1])) {
                new_HS[0] = "01";
                new_HS[1] = Integer.toString(score[0]);
            }
        } else if (score[0] > score[1]) {
            if (score[0] > Integer.parseInt(highScore[1])) {
                new_HS[0] = "0";
                new_HS[1] = Integer.toString(score[0]);
            }
        } else if (score[1] > score[0]) {
            if (score[1] > Integer.parseInt(highScore[1])) {
                new_HS[0] = "1";
                new_HS[1] = Integer.toString(score[1]);
            }
        }
        return new_HS;
    }


}
