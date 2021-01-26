package gui;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * This class sets GUI for gameOver state
 * Whenever the game is over, we create an object of this class
 * @author Elaheh Akbari
 */
public class GameOver {
    //gameOver background
    private Background gameOverBg;
    //button to go redirect player to main menu
    private final JButton leaveButton;
    //frame
    private final JFrame gameOverFrame;

    //create new background for the frame and handle possible exception
    {
        try {
            gameOverBg = new Background(".\\PVS Design Kit\\images\\gameOver.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * constructor to create buttons and frames
     */
    public GameOver(){
        leaveButton = new JButton("Go Back To Menu");
        gameOverFrame = new JFrame("Game Over!");
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
        //set frame features
        gameOverFrame.setSize(1090,630);
        gameOverFrame.setLocationRelativeTo(null);
        gameOverFrame.setResizable(false);
        gameOverFrame.setContentPane(gameOverBg);

        gameOverBg.setLayout(null);

        //set leave button features
        leaveButton.setBounds(350,500,400,80);
        leaveButton.setFont(new Font("new",Font.PLAIN,32));
        leaveButton.setBackground(Color.BLACK);
        leaveButton.setForeground(Color.GREEN);

        gameOverBg.add(leaveButton);

        gameOverFrame.setVisible(true);
    }
}
