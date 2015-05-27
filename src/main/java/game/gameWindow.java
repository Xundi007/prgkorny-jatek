package game;

import java.awt.*;
import java.awt.event.*;

/**
 * Az üzleti logika (azaz a játék) implementálása.
 */
public class gameWindow extends Canvas implements MouseListener, MouseMotionListener
{
	/**
	* Az ablak háttérszíne.
	*/
	public Color clrBackground = new Color(75,141,221);
	
	/**
	* Azt mutatja, hogy a játékos bábut választott.
	*/
	private boolean startSelection = false;
	
	/**
	* A játékosok pontszáma.
	*/
	private final int[] score = {0, 0};
	
	/**
	* A játékosok neve.
	*/
	private final String[] strPlayerName = {"Player1", "Player2"};
	
	
	/**
	* Üzenet a játékosok számára.
	*/
	private String strStatusMsg = "";
	
	/**
	* A játéktábla mátrix reprezentációja.
	*/
	private final objMatrix cellMatrix = new objMatrix();
	
	/**
	* Privát változók.
	* currentPlayer - melyik játékos léphet
	* startRow - a kiválasztott korong melyik sorban van (honnan indulunk)
	* startCol - a kiválasztott korong melyik oszlopban van (honnan indulunk)
	*/
	private int currentPlayer = 1, startRow = 0, startCol = 0;
	
	/**
	* A játék most kezdődik.
	*/
	public boolean firstTime = true;
	
	/**
	* Győzelem indikátor.
	*/
	private boolean victory = false;
	
	/**
	* Játékszabályok rövid ismertetése - 1. sor.
	*/
	private final String Msg1 = "2 mezővel elmozdíthatunk egy saját korongot vizszintesen, függőlegesen vagy átlósan,";

	/**
	* Játékszabályok rövid ismertetése - 2. sor.
	*/
	private final String Msg2 = "ha így az üres mezőre kerül. Át is lehet ugrani közben bármelyik korongot";

	/**
	* Játékszabályok rövid ismertetése - 3. sor.
	*/
	private final String Msg3 = "8 szomszédos üres mezőre helyezhetünk egy újabb saját korongot.";
	
	/**
	* Az osztály konstruktora.
	*/
	public gameWindow ()
	{
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	/**
	* Üzenetkezelő, a felhasználóknak ad információkat a játék során.
	* @return a játékosoknak szóló üzenet
	*/
	private String getGameMsg ()
	{
		if (victory)
		{
			/*
			* játék vége
			*/
			if (currentPlayer == 1)
			{						
				currentPlayer = 2;						
			}
			else
			{						
				currentPlayer = 1;						
			}
			
			setFinalScore();
			
			clsXML.saveData(strPlayerName[0], score[0]);
			clsXML.saveData(strPlayerName[1], score[1]);
			String msg;
			msg = "Gratulálok " + strPlayerName[getWinner()] + ", te nyertél!	Pontszámok: " + strPlayerName[0] + "=" + score[0] + " , " + strPlayerName[1] + "=" + score[1];
			clsLogger.addLog("I", msg, null);

			return msg;
		}
		else if (firstTime)
		{
			/*
			* játék kezdete
			*/
			return "" + strPlayerName[0] + " a piros, " + strPlayerName[1] + " a kék. A kezdéshez kattints a Játék indul gombra!";
		}
		else
		{
			/*
			* játékos váltás
			*/
			clsLogger.addLog("FT", strPlayerName[currentPlayer - 1] + " lép. ", null);
			return "" + strPlayerName[currentPlayer - 1] + " lép. " ;
		}
	}		 

	/**
	* Összehasonlítja a két játékos pontjainak a számát,
	* és a nagyobb értékkel rendelkező játékost kihirdeti győztesnek.
	* Játékszabály
	*   Az nyer, akinek több saját korongja van a táblán.
	* @return <code>int</code> típusú érték, a győztes játékos sorszáma
	*/
	private int getWinner()
	{
		if (score[0] > score[1])
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}
	
	
	/**
	* Új játék kezdése, a játék alaphelyzetbe hozása.
	*   Pontszámok nullázása
	*   a játék mátrixának alapbeállítása
	*   a játéktábla kezdő állapotának megrajzolása
	*/
	public void resetBoard ()
	{
		victory = false;
		currentPlayer = 1;
		strStatusMsg = getGameMsg();
		cellMatrix.resetMatrix();
		score[0] = 0;
		score[1] = 0;
		repaint();
	}

	/**
	* A játék meghívása ezzel történik.
	*/
	public void setupGame ()
	{
		resetBoard();
	}

	/**
	* Játékosok nevének beállítása.
	* A játék indításakor a konfigurációs fájlban letárolt játékosok neveit tölti be.
	* @param strPlayer1Name az első játékos neve
	* @param strPlayer2Name a második játékos neve
	*/
	public void setNames (String strPlayer1Name, String strPlayer2Name)
	{
		strPlayerName[0] = strPlayer1Name;
		strPlayerName[1] = strPlayer2Name;
		strStatusMsg = getGameMsg();
		repaint();
	}
	
	/**
	* @param playerIndex a játékosra mutató index
	* @return a bemenő paraméterhez tartozó játékos nevét adja vissza
	*/
	public String getPlayerName (int playerIndex)
	{
		return strPlayerName[playerIndex];
	}
	
	/**
	* A mátrix elemének megváltoztatása.
	* @param row a mátrix sora
	* @param col a mátrix oszlopa
	* @param value a mátrixelem új értéke
	*/
	public void setMatrixElement(int row, int col, int value)
	{
		cellMatrix.setPlayerCell(row, col, value);
	}
	
	/**
	* A mátrix elemének kiolvasása.
	* @param row a mátrix sora
	* @param col a mátrix oszlopa
	* @return <code>int</code> melyik játékos birtokolja az adott cellát
	*/
	public int getMatrixElement(int row, int col)
	{
		return cellMatrix.getPlayerCell(row, col);
	}
	
	/**
	* A játéktábla egy cellájának megrajzolása.
	* @param row a játéktábla sora
	* @param col a játéktábla oszlopa
	* @param g grafikus objektum
	*/
	private void drawCell (int row, int col, Graphics g)
	{
		if ((row == 3) && (col == 3))
		{
			/*
			* Sötét mező szinének beállítása.
			*/
			g.setColor(new Color(0,0,0)); 
		}
		else
		{
			/*
			* Szabad mező szinének beállítása.
			*/
			g.setColor(new Color(255,255,255));
		}
		
		/*
		* A cella megrajzolása (színezése).
		*/
		g.fillRect((50 + (col * 50)), (50 + (row * 50)), 50, 50);
		
		/*
		* A cella keretének megrajzolása (négyzetrács).
		*/
		g.setColor(new Color(0,0,0));
		g.drawRect((50 + (col * 50)), (50 + (row * 50)), 50, 50);
	}	
	
	/**
	* A teljes játéktábla megrajzolása.
	* @param g grafikus objektum
	*/
	protected void paintBoard(Graphics g)
	{
		g.setColor(clrBackground);
		
		/*
		* Az üzenő területet töröljük.
		*/
		g.fillRect(0,450,500,50); 
		
		for (int row = 0; row < 6; row++)
		{
			for (int col = 0; col < 6; col++)
			{
				drawCell(row, col, g);
			}
		}
	}

	/**
	* Korongok felrakása a táblára.
	* A játék mátrixában rögzített állásnak megfelelően felrajzolja 
	* a játékosok korongjait a táblára.
	* Megjeleníti az aktuális üzenetet.
	* @param g grafikus objektum
	*/
	protected void drawPlayStatus (Graphics g)
	{
		for (int row = 0; row < 6; row++)
		{
			for (int col = 0; col < 6; col++)
			{
				int playerCell = cellMatrix.getPlayerCell(row, col);
				if ((playerCell != 0) && (playerCell < 3))
				{
					try
					{
						drawCircle(g,((col + 1) * 50), ((row + 1) * 50),45, playerCell);
					}
					catch (ArrayIndexOutOfBoundsException e)
					{
						clsLogger.addLog("W", "Probléma adódott", e);
					}
				}
			}
		}
		g.setColor(new Color(0,0,0));
		g.drawString(Msg1, 50, 400);
		g.drawString(Msg2, 50, 415);
		g.drawString(Msg3, 50, 430);
		g.setColor(new Color(255,255,255));
		g.drawString(strStatusMsg, 50, 475);
	}
	
	/**
	* Egy korong lapjának a megrajzolása.
	* @param g grafikus objektum
	* @param x a korong körlapját magába foglaló négyzet bal-felső sarkának x koordinátája
	* @param y a korong körlapját magába foglaló négyzet bal-felső sarkának y koordinátája
	* @param r a kör sugara
	* @param player annak a játékosnak sorszáma, aki a korongot birtokolja
	*/
	public void drawCircle(Graphics g, int x, int y, int r, int player) 
	{
		if (player == 1)
			g.setColor(new Color(255,0,0));
		else
			g.setColor(new Color(0,0,255));
		x += 2;
		y += 2;
		g.fillOval(x,y,r,r);
	}
	
	/**
	* A játékos megjelöli azt a korongot, amivel lépni szeretne.
	* A kiválasztott korong megjelölése (sötétebb árnyalattal lesz újra kirajzolva).
	* @param g grafikus objektum
	* @param x a korong körlapját magába foglaló négyzet bal-felső sarkának x koordinátája
	* @param y a korong körlapját magába foglaló négyzet bal-felső sarkának y koordinátája
	* @param r a kör sugara
	* @param player annak a játékosnak sorszáma, aki a korongot birtokolja 
	*/
	public void drawSelectedDisk(Graphics g, int x, int y, int r, int player) 
	{
		if (player == 1)
			g.setColor(new Color(155,0,0));
		else
			g.setColor(new Color(0,0,155));
		x += 2;
		y += 2;
		g.fillOval(x,y,r,r);
	}
	
	/**
	* Új játékot kezdünk, a játék effektíve kezdhető.
	*/
	public void newGame ()
	{
		firstTime = false;
		resetBoard();
	}

	
	/**
	* Játékszabály.
	*   Miután a játékos lehelyezett vagy áthelyezett egy korongot,
	*   annak nyolcszomszédságában az ellenfél összes korongját 
	*   sajátra kell cserélni.
	* 
	* @param newDesRow a korong a tábla melyik sorába lett lehelyezve
	* @param newDesCol a korong a tábla melyik oszlopába lett lehelyezve
	* @param mode a lépés módja
	*  Ha <code>mode</code> értéke 0, akkor a lépés érvénytelen volt (nem üres cellára lépett,
	*	  vagy a lépés nem a szabályoknak megfelelő volt),
	*  ha 1, akkor egy közvetlenül szomszédos cellára lépett,
	*  ha 2, akkor két cellával távolabbi vízszintes, függőleges vagy átlós lépés volt.
	*/
	private void turnOver(int newDesRow, int newDesCol, int mode)
	{
		cellMatrix.setPlayerCell(newDesRow, newDesCol, currentPlayer);
		if (mode == 1)
		{
			/*
			* Pontozási szabály:
			*   Lerakott saját korongonként 1 pont
			*/
			score[currentPlayer - 1]++;
		}
		int fromRow, toRow, fromCol, toCol;
		if (newDesRow == 0)
		{
			fromRow = newDesRow;
		}
		else
		{
			fromRow = newDesRow - 1;
		}
		if (newDesCol == 0)
		{
			fromCol = newDesCol;
		}
		else
		{
			fromCol = newDesCol - 1;
		}
		if (newDesRow == 5)
		{
			toRow = newDesRow;
		}
		else
		{
			toRow = newDesRow + 1;
		}
		if (newDesCol == 5)
		{
			toCol = newDesCol;
		}
		else
		{
			toCol = newDesCol + 1;
		}
		for (int row = fromRow; row <= toRow; row++)
		{
			for (int col = fromCol; col <= toCol; col++)
			{
				if ((cellMatrix.getPlayerCell(row, col) > 0)
				 && (cellMatrix.getPlayerCell(row, col) != currentPlayer) 
				 && (cellMatrix.getPlayerCell(row, col) != 3))
				{
					cellMatrix.setPlayerCell(row, col, currentPlayer);
					/*
					* Pontozási szabály
					*   Átfordításonként is 1 pont
					*/
					score[currentPlayer - 1]++;
				}
			}
		}
		startSelection = false;
		if (currentPlayer == 1)
		{						
			currentPlayer = 2;						
		}
		else
		{						
			currentPlayer = 1;						
		}
		if (isGameOver(cellMatrix.getgameMatrix(), currentPlayer))
		{
			victory = true;
			firstTime = true;
		}
		strStatusMsg = getGameMsg();
	}
	
	
	/**
	* Játékszabály.
	*   Az üres mezők számát hozzá kell adni az utolsó lépni tudó
	*   játékos korongjainak számához.
	* A játék végén összeszámoljuk az üres cellákat, 
	* és hozzáadjuk az utoljára lépő játékos pontszámaihoz.
	*/
	private void setFinalScore()
	{
		for (int row = 0; row < 6; row++)
		{
			for (int col = 0; col < 6; col++)
			{
				if (cellMatrix.getPlayerCell(row, col) == 0)
				{
					score[currentPlayer-1]++;
				}
			}
		}
	}
	
	/**
	* Játékszabály.
	*   Egy saját koronggal nyolcszomszédos üres mezőre
	*   helyezhetnek egy saját korongot.
	*   Két mezővel elmozdíthatnak egy saját korongot, ha így az üres mezőre kerül.
	*   A korong függőlegesen, vízszintesen és átlósan is mozoghat, át lehet
	*   ugrani közben bármelyik korongot.
	* 
	* A lépés szabályosságának vizsgálata.
	* 
	* @param startRow melyik sorból indult a lépés
	* @param startCol melyik oszlopból indult a lépés
	* @param desRow melyik sorba léptünk (új helyzet)
	* @param desCol melyik oszlopba léptünk (új helyzet)
	* @return <code>int</code> típusú érték
	*	  0 - ha a lépés szabálytalan volt,
	*	  1 - ha egy saját koronggal nyolcszomszédos üres mezőre léptünk,
	*	  2 - ha két mezőt léptünk vízszintesen, függőlegesen vagy átlósan.
	*/
	int legalMove (int startRow, int startCol, int desRow, int desCol)
	{
		/*
		* Elmozdulás nagysága y irányban.
		*/
		int deltaRow;
		deltaRow = Math.abs(startRow - desRow);
		
		/*
		* Elmozdulás nagysága x irányban.
		*/
		int deltaCol;
		deltaCol = Math.abs(startCol - desCol);
		
		if ((deltaRow < 2) && (deltaCol < 2) )
			return 1;
		if ((deltaRow == 2) && (deltaCol == 2) || (deltaRow == 0) && (deltaCol == 2) || (deltaRow == 2) && (deltaCol == 0))
			return 2;
		
		return 0;
	}
	
	/**
	* Megvizsgálja, hogy a játékosnak van-e még lépés lehetősége.
	*
	* @param startRow a korong helyzete a táblán (y koordináta)
	* @param startCol a korong helyzete a táblán (x koordináta)
	* @param gameMatrix a játéktábla kitöltöttsége korongokkal
	*		   (a játéktábla mátrix reprezentációja)
	* @return a visszaadott érték típusa <code>boolean</code>, értéke <code>true</code> 
	*	   ha a paraméterekkel adott pozícióban levő koronggal  
	*	   még végezhetünk szabályos lépést, egyébként <code>false</code>
	*/
	boolean IsThereAnyStep (int startRow, int startCol, int[][] gameMatrix)
	{
		int fromRow, toRow, fromCol, toCol, steps;
		/*
		* Megszámoljuk az 1 hosszúságú lépés lehetőségeket.
		* Az első sorban van korong, így felfelé nem szabad vizsgálni a lépés lehetőségeket.
		*/
		if (startRow == 0)
		{
			fromRow = startRow;
		}
		else
		{
			fromRow = startRow - 1;
		}
		/*
		* Az első oszlopban van korong, így attól balra nem nézelődünk,
		* mert megygyűlik a bajunk a tömbindexel.
		*/
		if (startCol == 0)
		{
			fromCol = startCol;
		}
		else
		{
			fromCol = startCol - 1;
		}
		/*
		* Az utolsó sorban van korong, így attól lefele nem nézelődünk,
		* mert megygyűlik a bajunk a tömbindexel.
		*/
		if (startRow == 5)
		{
			toRow = startRow;
		}
		else
		{
			toRow = startRow + 1;
		}
		/*
		* Az utolsó oszlopban van korong, így attól jobbra nem nézelődünk,
		* mert megygyűlik a bajunk a tömbindexel.
		*/
		if (startCol == 5)
		{
			toCol = startCol;
		}
		else
		{
			toCol = startCol + 1;
		}
		steps = 0;
		/*
		* Átnézzük a szomszédos cellákat, van-e közöttük üres.
		*/
		for (int row = fromRow; row <= toRow; row++)
		{
			for (int col = fromCol; col <= toCol; col++)
			{
				if (gameMatrix[row][col] == 0)
				{
					steps++;
				}
			}
		}
		/*
		* Az eddigi lépésszámhoz hozzáadjuk
		* a 2 hosszúságú átlós lépések lehetőségét.
		*
		* Felfelé is van lehetőségünk kettőt lépni.
		*/
		if (startRow - 2 >= 0)
		{
			/*
			* Balra is van lehetőségünk kettőt lépni.
			*/
			if (startCol - 2 >= 0)
			{
				if (gameMatrix[startRow - 2][startCol - 2] == 0)
				{
					steps++;
				}
			}
			if (gameMatrix[startRow - 2][startCol] == 0)
			{
				steps++;
			}
			/*
			* Jobbra is van lehetőségünk kettőt lépni.
			*/
			if (startCol + 2 < 6)
			{
				if (gameMatrix[startRow - 2][startCol + 2] == 0)
				{
					steps++;
				}
			}
		}
		/*
		* Lefelé is van lehetőségünk kettőt lépni.
		*/
		if (startRow + 2 < 6) 
		{
			/*
			* Balra is van lehetőségünk kettőt lépni.
			*/
			if (startCol - 2 >= 0)
			{
				if (gameMatrix[startRow + 2][startCol - 2] == 0)
				{
					steps++;
				}
			}
			if (gameMatrix[startRow + 2][startCol] == 0)
			{
				steps++;
			}
			/*
			* Jobbra is van lehetőségünk kettőt lépni.
			*/
			if (startCol + 2 < 6)
			{
				if (gameMatrix[startRow + 2][startCol + 2] == 0)
				{
					steps++;
				}
			}
		}
		/*
		* Az eddigi lépésszámhoz hozzáadjuk
		* a 2 hosszúságú vízszintes lépések lehetőségét.
		*/
		if (startCol - 2 >= 0)
		{
			if (gameMatrix[startRow][startCol - 2] == 0)
			{
				steps++;
			}
		}
		
		/*
		* Az eddigi lépésszámhoz hozzáadjuk
		* a 2 hosszúságú függőleges lépések lehetőségét.
		*/
		if (startCol + 2 < 6)
		{
			if (gameMatrix[startRow][startCol + 2] == 0)
			{
				steps++;
			}
		}
		if (steps > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	* A mozgatással kapcsolatos ellenőrzések elvégzése,
	* ezek eredményétől függően a korong áthelyezése, vagy az áthelyezési
	* kísérlet visszavonása (nem szabályos lépés esetén).
	* 
	* @param desRow melyik sorba került a korong
	* @param desCol melyik oszlopba került a korong
	*/
	private void executeDiskMove (int desRow, int desCol)
	{
		/*
		* Megvizsgálja, hogy a korongot üres cellára akarjuk-e lehelyezni.
		*/
		if (cellMatrix.getPlayerCell(desRow, desCol) == 0)
		{
			int legalMove = 0;
			legalMove = legalMove(startRow, startCol, desRow, desCol);
			if (legalMove > 0)
			{
				if (legalMove == 2)
					cellMatrix.setPlayerCell(startRow, startCol, 0);
				turnOver(desRow,desCol,legalMove);
			}
			else
			{
				unsucessfullDrag();
				strStatusMsg = getGameMsg();
			}
		}
	}

	/**
	* Játékszabály.
	*   Akkor van vége a játknak, ha a lépni következő játékos nem tud lépni.
	*
	* Megvizsgálja folytatható-e a játék, van-e még lehetősége lépni
	* a soron következő játékosnak.
	* 
	* @param gameMatrix a játék mátrixa
	* @param playerIndex a játékosra mutató index
	* @return a visszaadott érték <code>true</code>, ha már nincs több lépés lehetőség,
	*		   egyébként <code>false</code>
	*/
	public boolean isGameOver(int[][] gameMatrix, int playerIndex)
	{
		for (int row = 0; row < 6; row++)
		{
			for (int col = 0; col < 6; col++)
			{
				if (gameMatrix[row][col] == playerIndex)
				{
					if (IsThereAnyStep(row, col, gameMatrix))
					{
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	* A mozgatás szabálytalan volt, visszalépünk a kiinduló cellára.
	*/
	private void unsucessfullDrag ()
	{
		cellMatrix.setPlayerCell(startRow, startCol, currentPlayer);
	}

	/**
	* 
	* @param e eseménykezeleő objektum  
	*/
	@Override
	public void mouseClicked (MouseEvent e)
	{
	}

	/**
	* 
	* @param e eseménykezeleő objektum  
	*/
	@Override
	public void mouseEntered (MouseEvent e)
	{
	}

	/**
	* 
	* @param e eseménykezeleő objektum  
	*/
	@Override
	public void mouseExited (MouseEvent e)
	{
		mouseReleased(e);
	}

	/**
	* Lenyomtuk az egér gombját.
	* Csak akkor foglalkozunk ezzel az eseménnyel,
	* ha az egérmutató a játéktáblán van.
	* Ha az adott cellában korong van, és még nincs kijelölve korong, 
	* és az a korong történetesen az éppen lépést végző játékosé, akkor a korong megjelölésre kerül.
	* Ha már volt kijelölve korong, és a kattintás egy üres cellán történt, akkor úgy tekintjük,
	* hogy a játékos befejezte a lépést, és elindítjuk a vizsgálatokat.
	* Ha a lépés érvénytelen volt, akkor a kattogás lehetősége marad a játékosnál.
	* @param e eseménykezeleő objektum 
	*/
	@Override
	public void mousePressed (MouseEvent e)
	{
		if (!victory && !firstTime)
		{			
			int x = e.getX();
			int y = e.getY();
			/*
			* Csak a játéktáblán történő kattogásra vagy húzásra reagálunk.
			*/
			if ((x > 50 && x < 350) && (y > 50 && y < 350))
			{
				if (!startSelection)
				{
					startRow = findSelected(y);
					startCol = findSelected(x);
					/*
					*
					*/
					if (cellMatrix.getPlayerCell(startRow, startCol) == currentPlayer)
					{
						startSelection = true;
					}
				}
				else
				{
					if (startRow == findSelected(y) && startCol == findSelected(x))
					{
						startSelection = false;
					}
					else
					{
						/*
						* Ide raktuk le a korongot.
						*/
						int desRow = findSelected(y);
						int desCol = findSelected(x);
						/*
						* Leellenőrizzük, hogy a korong mozgatása szabályos volt-e.
						*/
						executeDiskMove(desRow, desCol);
					 }
				}
				repaint();
			}
		}
	}

	/**
	* Felengedtük az egér gombját.
	* @param e eseménykezeleő objektum 
	*/
	@Override
	public void mouseReleased (MouseEvent e)
	{
	}

	/**
	* Az egér gombját lenyomva (és nyomva tartva) mozgatjuk az egeret.
	* @param e eseménykezeleő objektum 
	*/
	@Override
	public void mouseDragged (MouseEvent e)
	{
	}

	/**
	* Egér mozgatva lett.
	* @param e eseménykezeleő objektum 
	*/
	@Override
	public void mouseMoved (MouseEvent e)
	{
	}

	/**
	* A játék ablakán van a fókusz.
	*/
	public void gotFocus ()
	{
		repaint();		
	}
	
	
	/**
	* Az egérmutató pozíciójából meghatározzuk, 
	* hogy a játéktábla melyik sorában és oszlopában vagyunk.
	* @param coor a játéktábla x vagy y koordinátája (annak a pontnak a
	*	   koordinátája, ahova az egérrel kattintottunk)
	* @return annak a cellának az oszlop vagy sor indexe,
	*		amelybe az x vagy y koordináta beleesik.
	*/
	private int findSelected (int coor) 
	{
		for (int i = 0; i < 6; i++)
		{
			if ((coor >= (50 + (i * 50))) && (coor <= (100 + (i * 50))))
			{
				return i;
			}
		}
		return -1;
	}
	
	/**
	* Ablak frissítés metódusa.
	* @param g - grafikus objektum
	*/
	@Override
	public void update (Graphics g)
	{
		paint(g);
	}
	
	/**
	* Az ablak rajzoló metódusa.
	* @param g - grafikus objektum
	*/
	@Override
	public void paint (Graphics g)
	{
		paintBoard(g);
		drawPlayStatus(g);
		if (startSelection)
			drawSelectedDisk(g, ((startCol + 1) * 50), ((startRow + 1) * 50), 45, currentPlayer);
	}
}