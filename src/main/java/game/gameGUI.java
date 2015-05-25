package game;

import static game.clsINI.setKeyValue;
import static game.clsINI.writeINIFileXML;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

/**
 * 
 */
public class gameGUI implements ActionListener, KeyListener, WindowFocusListener
{
	
    private gameWindow myWindow;
    private JButton cmdNewGame, cmdSetNames, cmdExit;
    private JTextField txtPlayerOne, txtPlayerTwo;
    private JLabel lblPlayerOne, lblPlayerTwo;
    private Color clrBackground = new Color(0,0,0);
	
    public void gameGUI ()
    {
    }

    /**
     * 
     * @param mainApp
     * @return 
     */
    public Container createGUI (JFrame mainApp)
    {
        CheckOrCreateFolder();
        clsINI.cretaeINIFile();
        clsLogger.initLogger();
        clsLogger.addLog("F", "createGUI" , null);
        JPanel panRoot = new JPanel(new BorderLayout());
        panRoot.setOpaque(true);
        panRoot.setPreferredSize(new Dimension(550,650));
        myWindow = new gameWindow();
        myWindow.setSize(new Dimension(500, 500));
        cmdExit = new JButton("Kilépés a játékból");
        cmdExit.addActionListener(this);
        cmdNewGame = new JButton("Játék indul");
        cmdNewGame.addActionListener(this);
        cmdSetNames = new JButton("Nevek beállítása");
        cmdSetNames.addActionListener(this);
        txtPlayerOne = new JTextField("Player1", 10);
        txtPlayerOne.addKeyListener(this);
        txtPlayerTwo = new JTextField("Player2", 10);
        txtPlayerTwo.addKeyListener(this);
        lblPlayerOne = new JLabel("    ", JLabel.RIGHT);
        lblPlayerTwo = new JLabel("    ", JLabel.RIGHT);
        try
        {
            clsLogger.addLog("I", "Program inditás", null);
            clsXML.getData();
            String playerName1, playerName2;
            playerName1 = clsINI.readINIFileXML("Player1");
            playerName2 = clsINI.readINIFileXML("Player2");
            if (playerName1.equals(""))
                playerName1 = "Player1";
            if (playerName2.equals(""))
                playerName2 = "Player2";
            txtPlayerOne.setText(playerName1);
            txtPlayerTwo.setText(playerName2);
            myWindow.setNames(playerName1, playerName2);
            myWindow.setupGame();
        }
        catch (NullPointerException e)
        {
            clsLogger.addLog("I", "Nem sikerült betölteni a képet.", null);
            cmdNewGame.setEnabled(false);
            cmdSetNames.setEnabled(false);
        }
        JPanel panBottomHalf = new JPanel(new BorderLayout());
        JPanel panNameArea = new JPanel(new GridLayout(3,1,2,2));
        JPanel panPlayerOne = new JPanel();
        JPanel panPlayerTwo = new JPanel();
        JPanel panNameButton = new JPanel();
        JPanel panNewGame = new JPanel();
        JPanel panExitButton = new JPanel();
        panRoot.add(myWindow, BorderLayout.NORTH);
        panRoot.add(panBottomHalf, BorderLayout.SOUTH);
        panBottomHalf.add(panNameArea, BorderLayout.WEST);
        panNameArea.add(panPlayerOne);
        panPlayerOne.add(lblPlayerOne);
        panPlayerOne.add(txtPlayerOne);
        panNameArea.add(panPlayerTwo);
        panPlayerTwo.add(lblPlayerTwo);
        panPlayerTwo.add(txtPlayerTwo);
        panNameArea.add(panNameButton);
        panNameButton.add(cmdSetNames);
        panExitButton.add(cmdExit);
        panBottomHalf.add(panNewGame, BorderLayout.SOUTH);
        panBottomHalf.add(panExitButton, BorderLayout.CENTER);
        panNewGame.add(cmdNewGame);
        clrBackground = myWindow.clrBackground;
        panRoot.setBackground(clrBackground);
        panBottomHalf.setBackground(clrBackground);
        panNameArea.setBackground(clrBackground);
        panPlayerOne.setBackground(clrBackground);
        panPlayerTwo.setBackground(clrBackground);
        panNameButton.setBackground(clrBackground);
        panExitButton.setBackground(clrBackground);
        panNewGame.setBackground(clrBackground);
        lblPlayerOne.setBackground(new Color(236,17,17)); //red
        lblPlayerTwo.setBackground(new Color(17,27,237)); //blue
        cmdNewGame.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        cmdExit.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        return panRoot;
    }
	
    /**
     * 
     * @param e 
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == cmdSetNames)
        {
            if (txtPlayerOne.getText().equals(""))
            {
                txtPlayerOne.setText("Player1");
            }
            if (txtPlayerTwo.getText().equals(""))
            {
                txtPlayerTwo.setText("Player2");
            }
            myWindow.setNames(txtPlayerOne.getText(), txtPlayerTwo.getText());
            clsLogger.addLog("I", "Player1:" + txtPlayerOne.getText(), null);
            clsLogger.addLog("I", "Player2:" + txtPlayerTwo.getText(), null);
            clsINI.setKeyValue("Player1", txtPlayerOne.getText());
            clsINI.setKeyValue("Player2", txtPlayerTwo.getText());
            clsINI.writeINIFileXML();
        }
        else if (e.getSource() == cmdNewGame)
        {
            if (cmdNewGame.getText() == "Játék indul")
            {
                cmdNewGame.setText("Új játék");
                cmdSetNames.setEnabled(false);
                txtPlayerOne.setEnabled(false);
                txtPlayerTwo.setEnabled(false);
                myWindow.firstTime = false;
                myWindow.newGame();
            }
            else
            {
                cmdSetNames.setEnabled(true);
                txtPlayerOne.setEnabled(true);
                txtPlayerTwo.setEnabled(true);
                myWindow.resetBoard();
                myWindow.firstTime = true;
                cmdNewGame.setText("Játék indul");
            }
        }
        else if (e.getSource() == cmdExit)
        {
            clsLogger.addLog("I", "Program befejezés", null);
            clsLogger.closeLogger();
            System.exit(0);
        }
    }
	
    /**
     * 
     * @param e 
     */
    @Override
    public void keyTyped(KeyEvent e)
    {
        String strBuffer;
        char c = e.getKeyChar();
        if (e.getSource() == txtPlayerOne)
        {
            strBuffer = txtPlayerOne.getText();
        }
        else
        {
            strBuffer = txtPlayerTwo.getText();
        }
        if (strBuffer.length() > 10 && !((c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))
        {
            e.consume();
        }
    }

    /**
     * 
     * @param e 
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
    }

    /**
     * 
     * @param e 
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
    }

    /**
     * 
     * @param e 
     */
    @Override
    public void windowGainedFocus (WindowEvent e)
    {
        myWindow.gotFocus();
    }

    /**
     * 
     * @param e 
     */
    @Override
    public void windowLostFocus (WindowEvent e)
    {
    }
    
    /**
     * 
     */
    public void enableButtons()
    {
        cmdSetNames.setEnabled(true);
        txtPlayerOne.setEnabled(true);
        txtPlayerTwo.setEnabled(true);
    }
    
    /**
     * A játék könyvtárának meglétét ellenőrzi,
     * ha még nem létezik, akkor létrehozza.
     */
    public void CheckOrCreateFolder()
    {	
        System.out.println(System.getProperty("user.dir"));
	File file = new File(System.getProperty("user.home") + "\\game");
	if (!file.exists()) 
        {
            try
            {
                if (file.mkdir()) 
                {
                    System.out.println("A könyvtár létre lett hozva!");
                } else {
                    System.out.println("Nem sikerült létrehozni a könyvtárat!");
                }
            }
            catch(SecurityException se)
            {
                System.out.println("Exception thrown: " + se);
            }
	}
        else
        {
            System.out.println("A könyvtár már létezik!" );
        }
    }
}