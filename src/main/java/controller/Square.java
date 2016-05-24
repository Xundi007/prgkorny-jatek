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


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class Square extends StackPane {
    private int row, col;

    @FXML
    Label label;
    Circle c;

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void disable() {
        label.setStyle("-fx-background-color: black");
    }

    public void select(boolean isSelected) {
        if (isSelected)
            c.setStroke(Color.GRAY);
        else
            c.setStroke(Color.BLACK);
    }

    public Square(int col, int row) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Square.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
        GridPane.setColumnIndex(this, col);
        GridPane.setRowIndex(this, row);

        this.row = row;
        this.col = col;

        c = new Circle(26);
        c.setStroke(Color.TRANSPARENT);
        c.setStrokeWidth(8);
        c.setCenterX(getTranslateX());
        c.setCenterY(getTranslateY());
        c.setFill(Color.TRANSPARENT);
        getChildren().add(c);

        label.setStyle("-fx-background-color: white");
        c.setStroke(Color.TRANSPARENT);
        c.setFill(Color.TRANSPARENT);
    }

    public void setOwner(int owner) {
        if (owner == 0) {
            c.setFill(Color.RED);
            c.setStroke(Color.BLACK);
        } else if (owner == 1) {
            c.setFill(Color.BLUE);
            c.setStroke(Color.BLACK);
        } else {
            c.setFill(Color.TRANSPARENT);
            c.setStroke(Color.TRANSPARENT);
        }
    }
}
