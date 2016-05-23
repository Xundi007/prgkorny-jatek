package controller;

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


import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.GameLogic;
import model.SquareModel;
import model.clsINI;
import model.clsLogger;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    Button cmdSetNames;

    @FXML
    Button cmdNewGame;

    @FXML
    Button cmdExit;

    @FXML
    TextField txtPlayerOne;

    @FXML
    TextField txtPlayerTwo;

    @FXML
    GridPane board;

    @FXML
    public Text p1_score, p2_score, high_score, messages, info;
    public String strP1 = "p1", strP2 = "p2";
    public int hScore;
    FadeTransition ft = new FadeTransition(Duration.millis(3000), messages);

    Timeline timeline;
    public ArrayList<Square> squares;
    public ArrayList<SquareModel> squareModels;
    GameLogic logic;
    int column, row;

    @FXML
    public void cmdSetNamesButtonAction(ActionEvent e) {
        if (txtPlayerOne.getText().equals(""))
            txtPlayerOne.setText("Player1");
        if (txtPlayerTwo.getText().equals(""))
            txtPlayerTwo.setText("Player2");

        strP1 = txtPlayerOne.getText();
        strP2 = txtPlayerTwo.getText();

        clsINI.setPlayers(strP1, strP2);
        updateScores(new int[]{0, 0});
    }

    void startGame() {
        logic.isStarted = true;
    }

    public void gameOver() {
        cmdNewGame.setText("Játék indítása");
        cmdSetNames.setDisable(false);
        txtPlayerOne.setDisable(false);
        txtPlayerTwo.setDisable(false);
    }

    @FXML
    public void cmdNewGameButtonAction(ActionEvent e) {
        if (cmdNewGame.getText().equals("Játék indítása")) {
            cmdNewGame.setText("Új játék");
            cmdSetNames.setDisable(true);
            txtPlayerOne.setDisable(true);
            txtPlayerTwo.setDisable(true);
            if (logic.isGameOver) {
                setUpSquares();
            }
            startGame();
            return;
        } else {
            cmdSetNames.setDisable(false);
            txtPlayerOne.setDisable(false);
            txtPlayerTwo.setDisable(false);
            cmdNewGame.setText("Új játék");
        }

        if (cmdNewGame.getText().equals("Új játék")) {
            setUpSquares();
            startGame();
        }
    }

    @FXML
    public void cmdExitButtonAction(ActionEvent e) {
        clsLogger.closeLogger();
        System.exit(0);
    }

    public void setUpSquares() {
        // Arguably shouldn't really do this in the controller, but avoiding it gets messy.
        squares = new ArrayList<>();
        squareModels = new ArrayList<>();
        logic = new GameLogic(squares, squareModels, this);

        for (column = 0; column < 6; column++) {
            for (row = 0; row < 6; row++) {
                try {
                    final Square square = new Square(column, row);
                    final SquareModel sm = new SquareModel(row, column);

                    board.getChildren().add(square);
                    squares.add(square);
                    squareModels.add(sm);

                    if (column == 3 && row == 3) {
                        square.disable();
                        sm.setState(3);
                        sm.isNeutral = false;
                    } else if ((column == 0 && row == 0) || (column == 5 && row == 5)) {
                        square.setOwner(0);
                        sm.setOwner(0);
                    } else if ((column == 5 && row == 0) || (column == 0 && row == 5)) {
                        square.setOwner(1);
                        sm.setOwner(1);
                    }

                    square.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            logic.updateGameLogic(square.getCol(), square.getRow());
                        }
                    });
                } catch (IOException e) {
                    clsLogger.addLog("S", "A játéktábla felépítése sikertelen.", e);
                }
            }
        }
    }

    public void updateScores(int[] scores) {
        p1_score.setText(strP1 + " pontszáma: " + scores[0]);
        p2_score.setText(strP2 + " pontszáma: " + scores[1]);
    }

    public void setHighScoreText(String name, int score) {
        high_score.setText(name + " : " + score);
    }

    public void updateHighScores(String[] tmpHS) {
        switch (tmpHS[0]) {
            case "01":
                setHighScoreText(strP1 + ", " + strP2, Integer.parseInt(tmpHS[1]));
                clsINI.writeSettingsFileHScore(strP1, tmpHS[1]);
                break;
            case "0":
                setHighScoreText(strP1, Integer.parseInt(tmpHS[1]));
                clsINI.writeSettingsFileHScore(strP1, tmpHS[1]);
                break;
            case "1":
                setHighScoreText(strP2, Integer.parseInt(tmpHS[1]));
                clsINI.writeSettingsFileHScore(strP2, tmpHS[1]);
                break;
            case "NEM":
                break;
        }
    }

    public void updateInfo(int currentplayer) {
        if (currentplayer == 0) {
            info.setText(strP1 + " következik");
        } else {
            info.setText(strP2 + " következik");
        }
    }

    public void updateInfo(String str) {
        info.setText(str);
    }

    public void msg(String msg) {
        timeline.stop();
        ft.stop();
        messages.setText(msg);
        timeline.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clsINI.createGameFolder();
        clsINI.createConfigFile();
        clsLogger.initLogger();
        clsLogger.addLog("FT", "A logger elindult.", null);

        setUpSquares();
        clsLogger.addLog("F", "A tábla felépítése megtörtént.", null);

        messages.setText("");
        info.setText("A játék elindításához nyomja meg a 'Játék indítása' gombot!");

        timeline = new Timeline(new KeyFrame(
                Duration.millis(1500),
                ar -> ft.play()
        ));
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setAutoReverse(true);
        ft.setNode(messages);
        ft.setDuration(Duration.millis(900));

        clsLogger.addLog("F", "A játékos adatok betöltése elkezdődött...", null);
        String playerName1, playerName2;
        String[] highScore = clsINI.readSettingsFileHScore();
        playerName1 = clsINI.readSettingsFileXML("Player1");
        playerName2 = clsINI.readSettingsFileXML("Player2");
        if (playerName1.equals(""))
            playerName1 = "Player1";
        if (playerName2.equals(""))
            playerName2 = "Player2";
        txtPlayerOne.setText(playerName1);
        txtPlayerTwo.setText(playerName2);
        strP1 = playerName1;
        strP2 = playerName2;
        updateScores(new int[]{0, 0});
        setHighScoreText(highScore[0], Integer.parseInt(highScore[1]));
        clsLogger.addLog("F", "A játékos adatok betöltése megtörtént.", null);
    }
}
