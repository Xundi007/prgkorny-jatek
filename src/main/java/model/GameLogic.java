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


import controller.Controller;
import controller.Square;

import java.util.ArrayList;

public class GameLogic {
    private final int[] score = {0, 0};

    ArrayList<Square> squares;
    ArrayList<SquareModel> squareModels;

    Square selected = null;
    SquareModel selectedModel = null;
    public boolean isSelected = false, isStarted = false, isGameOver = false;
    int currentPlayer = 0;
    Controller controller;

    public GameLogic(ArrayList<Square> squares,
                     ArrayList<SquareModel> squareModels,
                     Controller controller) {
        this.squares = squares;
        this.squareModels = squareModels;
        this.controller = controller;
    }

    public Square getSquare(int col, int row) {
        for (Square s : squares) {
            if (s.getCol() == col && s.getRow() == row) {
                return s;
            }
        }
        return null;
    }

    public SquareModel getSquareModel(int col, int row) {
        for (SquareModel s : squareModels) {
            if (s.getCol() == col && s.getRow() == row) {
                return s;
            }
        }
        return null;
    }

    public void updateGameLogic(int col, int row) {
        if (!isStarted) return;

        SquareModel s = getSquareModel(col, row);
        if (!isSelected) {
            if (currentPlayer == s.owner) {
                selectedModel = s;
                selected = getSquare(s.getCol(), s.getRow());
                selected.select(true);
                isSelected = true;
            }
        } else {
            // Deselect the currently selected
            if (s == selectedModel) {
                selected.select(false);
                isSelected = false;
            }
            // Check if the move is valid
            else if (s.isNeutral) {
                int deltaRow = Math.abs(s.getRow() - selectedModel.getRow());
                int deltaCol = Math.abs(s.getCol() - selectedModel.getCol());

                // Make a copy
                if (deltaRow < 2 && deltaCol < 2 && s.state != 3) {
                    turnOver(row, col, 1);
                    isSelected = false;
                    selected.select(false);
                    s.setOwner(currentPlayer);
                    getSquare(s.getCol(), s.getRow()).select(false);
                    getSquare(s.getCol(), s.getRow()).setOwner(currentPlayer);
                    nextTurn();
                }
                // Jump
                else if ((deltaRow == 2) && (deltaCol == 2) || (deltaRow == 0) && (deltaCol == 2) || (deltaRow == 2) && (deltaCol == 0)) {
                    if (s.state != 3) {
                        turnOver(row, col, 0);
                        isSelected = false;
                        selectedModel.setOwner(2);
                        selected.select(false);
                        selected.setOwner(2);
                        s.setOwner(currentPlayer);
                        getSquare(s.getCol(), s.getRow()).select(false);
                        getSquare(s.getCol(), s.getRow()).setOwner(currentPlayer);
                        nextTurn();
                    }
                }
                // Invalid move
                else {
                    controller.msg("Érvénytelen lépés");
                }
            }
            // Other player's square of offline square
            else {
                controller.msg("Érvénytelen lépés");
            }
        }
    }

    boolean isThereAnyStep(int sRow, int sCol) {
        int rFrom, rTo, cFrom, cTo;

        rFrom = sRow - 1;
        rTo = sRow + 1;
        cFrom = sCol - 1;
        cTo = sCol + 1;

        if (sRow == 0) rFrom = 0;
        if (sCol == 0) cFrom = 0;
        if (sRow == 5) rTo = 5;
        if (sCol == 5) cTo = 5;

        // 1 distance check
        for (int row = rFrom; row <= rTo; row++) {
            for (int col = cFrom; col <= cTo; col++) {
                if (row == sRow && col == sCol) continue;
                if (getSquareModel(col, row).isNeutral) return true;
            }
        }

        // 2 distance check
        int up = sRow - 2, down = sRow + 2, left = sCol - 2, right = sCol + 2;

        boolean isUp = up >= 0;
        boolean isDown = down <= 5;
        boolean isLeft = left >= 0;
        boolean isRight = right <= 5;

        if (isUp)
            if (getSquareModel(sCol, up).isNeutral) return true;

        if (isDown)
            if (getSquareModel(sCol, down).isNeutral) return true;

        if (isLeft)
            if (getSquareModel(left, sRow).isNeutral) return true;

        if (isRight)
            if (getSquareModel(right, sRow).isNeutral) return true;

        if (isUp && isLeft)
            if (getSquareModel(left, up).isNeutral) return true;

        if (isUp && isRight)
            if (getSquareModel(right, up).isNeutral) return true;

        if (isDown && isLeft)
            if (getSquareModel(left, down).isNeutral) return true;

        if (isDown && isRight)
            if (getSquareModel(right, down).isNeutral) return true;

        return false;
    }

    public boolean isGameOver() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (getSquareModel(col, row).owner == currentPlayer) {
                    if (isThereAnyStep(row, col)) return false;
                }
            }
        }
        return true;
    }

    private void turnOver(int sRow, int sCol, int mode) {
        int rFrom, rTo, cFrom, cTo;

        /*
        * Pontozási szabály:
        *   Lerakott saját korongonként 1 pont
        */
        if (mode == 1) score[currentPlayer]++;

        rFrom = sRow - 1;
        rTo = sRow + 1;
        cFrom = sCol - 1;
        cTo = sCol + 1;

        if (sRow == 0) rFrom = 0;
        if (sCol == 0) cFrom = 0;
        if (sRow == 5) rTo = 5;
        if (sCol == 5) cTo = 5;

        for (int row = rFrom; row <= rTo; row++) {
            for (int col = cFrom; col <= cTo; col++) {
                SquareModel s = getSquareModel(col, row);
                if (s.owner == otherPlayer()) {
                    s.setOwner(currentPlayer);
                    getSquare(s.getCol(), s.getRow()).setOwner(currentPlayer);
                    /*
                    * Pontozási szabály:
                    *   Lerakott saját korongonként 1 pont
                    */
                    score[currentPlayer]++;
                }
            }
        }
    }

    private int otherPlayer() {
        return currentPlayer == 0 ? 1 : 0;
    }

    private void nextTurn() {
        currentPlayer = currentPlayer == 0 ? 1 : 0;
        if (!isGameOver()) {
            controller.updateScores(score);
            controller.updateInfo(currentPlayer);
        } else {
            if (score[0] == score[1]) {
                controller.updateInfo("Döntetlen");
            } else if (score[0] > score[1]) {
                controller.updateInfo(controller.strP1 + " győzött (piros)");
            } else {
                controller.updateInfo(controller.strP2 + " győzött (kék)");
            }

            isGameOver = true;
            controller.gameOver();
            controller.updateScores(score);
            controller.updateHighScores(HighScore.checkHighScore(score));
            clsXML.saveData(controller.strP1, score[0]);
            clsXML.saveData(controller.strP2, score[1]);
        }
    }
}
