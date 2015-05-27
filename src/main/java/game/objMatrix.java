package game;

/*
 * #%L
 * prgkorny-jatek
 * %%
 * Copyright (C) 2015 Debreceni Egyetem, Informatikai Kar
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


/**
 * A játéktábla mátrix reprezentációja.
 * A játékosok lépései a mátrixban rögzítődnek.
*/
public class objMatrix
{
	/**
	* A játék mátrixa.
	*/
	private final int[][] gameMatrix = new int[6][6]; 
	
	/**
	* Az osztály konstruktora.
	*/
	public void objMatrix ()
	{
	}

	/**
	* A játék alapfelállása.
	* 1 és 2 a játékosok sorszáma
	* 3 a sötét mezőt prezentálja (ahova nem szabad lépni)
	* Az üres celláknak megfelelő mátrix elemeket 0-val tölti fel.
	*/
	public void resetMatrix ()
	{
		for (int row = 0; row < 6; row++)
		{
			for (int col = 0; col < 6; col++)
			{
				gameMatrix[row][col] = 0;
			}
		}
		gameMatrix[3][3] = 3;
		gameMatrix[0][0] = 1;
		gameMatrix[0][5] = 2;
		gameMatrix[5][0] = 2;
		gameMatrix[5][5] = 1;
	}

	/**
	* Egy cella birtokosának lekérdezése.
	* @param row a cella y koordinátája
	* @param col a cella x koordinátája
	* @return a cella tartalma (a játékos sorszáma)
	*/
	public int getPlayerCell (int row, int col)
	{
		return gameMatrix[row][col];
	}

	/**
	* Egy cella birtokosának beállítása.
	* @param row a cella y koordinátája
	* @param col a cella x koordinátája
	* @param player a cella birtokosa (a játékos sorszáma)
	*/
	public void setPlayerCell (int row, int col, int player)
	{
		gameMatrix[row][col] = player;
	}

	/**
	* A játéktér pillanatnyi állapotának vizsgálatához
	*   lekérjük a mátrixot.
	* @return a teljes mátrix
	*/
	public int[][] getgameMatrix ()
	{
		return gameMatrix;
	}

}