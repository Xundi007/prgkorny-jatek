package game;

import javax.swing.JFrame;

/**
 *
 */
public class Game extends JFrame
{
	
    /**
     * 
     * @param args 
     */
    public static void main(String[] args) 
    {
	JFrame.setDefaultLookAndFeelDecorated(true); 
        JFrame wFrame = new JFrame("                     2.33. Feladat"); 
        wFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameGUI gameWindow = new gameGUI();
        wFrame.setContentPane(gameWindow.createGUI(wFrame));
        wFrame.addWindowFocusListener(gameWindow);
        wFrame.setSize(550,650);
        wFrame.setResizable(false);
        wFrame.setVisible(true);  
        wFrame.pack();       
    }	
}