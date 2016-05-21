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
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameLogicTest {

    ArrayList<Square> squares;
    ArrayList<SquareModel> squareModels;
    Controller cntrl = new Controller();
    GameLogic instance;

    @Before
    public void setUp() throws Exception {
        squares = new ArrayList<>();
        squareModels = new ArrayList<>();
        instance = new GameLogic(squares, squareModels, cntrl);

        for (int col = 0; col < 6; col++) {
            for (int row = 0; row < 6; row++) {
                final SquareModel sm = new SquareModel(row, col);
                squareModels.add(sm);

                if (col == 3 && row == 3) {
                    sm.setState(3);
                    sm.isNeutral = false;
                } else if ((col == 0 && row == 0) || (col == 5 && row == 5)) {
                    sm.setOwner(0);
                } else if ((col == 5 && row == 0) || (col == 0 && row == 5)) {
                    sm.setOwner(1);
                }

                squareModels.add(sm);
            }
        }
        instance.currentPlayer = 0;
    }

    @Test
    public void getSquare() throws Exception {
        assertTrue(true);

    }


    @Test
    public void getSquareModel() throws Exception {
        SquareModel gotSq = instance.getSquareModel(1, 1);
        SquareModel expectedSq = new SquareModel(1, 1);
        assertTrue(expectedSq.equals(gotSq));
    }

    @Test
    public void updateGameLogic() throws Exception {
        assertTrue(true);
    }

    @Test
    public void isThereAnyStep() throws Exception {
        // currentplayer = red
        // start at the game
        instance.currentPlayer = 0;
        boolean canMove = instance.isThereAnyStep(0, 0);
        assertEquals(true, canMove);
    }

    @Test
    public void isThereAnyStep2() throws Exception {
        // currentplayer = red
        // all square blue expect the on in the (0, 0) position
        for (int col = 0; col < 6; col++) {
            for (int row = 0; row < 6; row++) {
                instance.getSquareModel(col, row).setOwner(1);
            }
        }
        instance.getSquareModel(0, 0).setOwner(0);
        instance.currentPlayer = 0;

        boolean canMove = instance.isThereAnyStep(0, 0);
        assertEquals(false, canMove);
    }

    @Test
    public void isThereAnyStep3() throws Exception {
        // currentplayer = red
        // all square blue expect the on in the (0, 0) and (0, 1) position
        for (int col = 0; col < 6; col++) {
            for (int row = 0; row < 6; row++) {
                instance.getSquareModel(col, row).setOwner(1);
            }
        }
        instance.getSquareModel(0, 0).setOwner(0);
        instance.getSquareModel(0, 1).setOwner(2);
        boolean canMove = instance.isThereAnyStep(0, 0);
        assertEquals(true, canMove);
    }

    @Test
    public void isGameOver() throws Exception {
        for (int col = 0; col < 6; col++) {
            for (int row = 0; row < 6; row++) {
                instance.getSquareModel(col, row).setOwner(1);
            }
        }
        instance.getSquareModel(0, 0).setOwner(0);
        instance.currentPlayer = 0;

        boolean canMove = instance.isGameOver();
        assertEquals(true, canMove);
    }

    @Test
    public void isGameOver2() throws Exception {
        for (int col = 0; col < 6; col++) {
            for (int row = 0; row < 6; row++) {
                instance.getSquareModel(col, row).setOwner(1);
            }
        }
        instance.getSquareModel(0, 0).setOwner(0);
        instance.getSquareModel(0, 1).setOwner(2);
        instance.currentPlayer = 0;

        boolean canMove = instance.isGameOver();
        assertEquals(false, canMove);
    }
}