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


import javax.swing.JFrame;

/**
 * Kétszemélyes logikai játék.
 * 
 * JDK 1.8
 */
public class Game extends JFrame
{
	
	/**
	 * 
	 * @param args default paraméter
	 */
	public static void main(String[] args) 
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame wFrame = new JFrame("					 2.33. Feladat");
		wFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameGUI gameWindow = new gameGUI();
		wFrame.setContentPane(gameWindow.createGUI(wFrame));
		wFrame.addWindowFocusListener(gameWindow);
		wFrame.setSize(550,675);
		wFrame.setResizable(false);
		wFrame.setVisible(true);
		wFrame.pack();
	}
}