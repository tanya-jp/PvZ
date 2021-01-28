package gui;


import javax.swing.*;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * This class sets GUI for gameOver state
 * Whenever the game is over, we create an object of this class
 * @author Elaheh Akbari
 */
public class GameOver {
    //gameOver background
    private Background gameOverBg;
    //end of game background
    private Background endOfGameBg;
    //button to go redirect player to main menu
    private JButton leaveButton;
    //frame
    private JFrame gameOverFrame;
    //gameOver of end of game
    private String type;

    //create new background for the frame and handle possible exception
    {
        try {
            gameOverBg = new Background(".\\PVS Design Kit\\images\\gameOver.jpg");
            endOfGameBg = new Background(".\\PVS Design Kit\\images\\endOfGame.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * constructor to create buttons and frames
     */
    public GameOver(){
        leaveButton = new JButton("Go Back To Menu");
    }

    /**
     * Sets type of frame -> gameOver / endOfGame
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * Get leave button (go back to main menu button)
     * @return leave button
     */
    public JButton getLeaveButton() {
        return leaveButton;
    }

    /**
     * This method starts the UI and shows it whenever it's called
     */
    public void start(){
        //set frames type
        if(type.equals("gameOver"))
            gameOverFrame = new JFrame("Game Over!");
        else
            gameOverFrame = new JFrame("End of game!");
        //set frame features
        gameOverFrame.setSize(1090,630);
        gameOverFrame.setLocationRelativeTo(null);
        gameOverFrame.setResizable(false);
        if(type.equals("gameOver"))
            gameOverFrame.setContentPane(gameOverBg);
        else
            gameOverFrame.setContentPane(endOfGameBg);

        gameOverBg.setLayout(null);

        //set leave button features
        leaveButton.setBounds(350,500,400,80);
        leaveButton.setFont(new Font("new",Font.PLAIN,32));
        leaveButton.setBackground(Color.BLACK);
        if(type.equals("gameOver"))
            leaveButton.setForeground(Color.GREEN);
        else
            leaveButton.setForeground(Color.BLUE);

        if(type.equals("gameOver"))
            gameOverBg.add(leaveButton);
        else
            endOfGameBg.add(leaveButton);

        gameOverFrame.setVisible(true);
    }

    /**
     * Closes gameOverFrame
     */
    public void closeFrame()
    {
        gameOverFrame.setVisible(false);
    }
}
