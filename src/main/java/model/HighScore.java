package model;

/*
 * #%L
 * prgkorny-jatek
 * %%
 * Copyright (C) 2016 Debreceni Egyetem, Informatikai Kar
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import dao.SettingsXML;

public class HighScore {

    public static String[] checkHighScore(int[] score) {
        String[] highScore = SettingsXML.getInstance().readSettingsFileHScore();
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
