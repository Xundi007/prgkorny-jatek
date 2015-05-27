package game;

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