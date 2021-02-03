package gui;

import utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class creates the UI for game loading ability.
 * It shows a list of unfinished games of the player.
 * Player can choose one game at a time and continue it.
 * @author Elaheh Akbari and Tanya Djavaherpour
 * @version 1.0 2021
 */
public class LoadBoard {
    //background image
    private Background loadBg;
    //main frame
    private final JFrame loadFrame;
    //load panel which includes load button
    private final JPanel loadPanel;
    //game panel which includes game options
    private final JPanel gamePanel;
    //label panel including labels that keep game info
    private final JPanel labelPanel;
    //load button which is pressed when choice is made
    private final JButton loadButton;
    //player username
    private final String username;

    //array list of buttons to choose from
    private final ArrayList<JRadioButton> buttonArrayList;
    //array list of labels including game info
    private final ArrayList<JLabel> jLabels;

    //create a Background object to draw
    {
        try {
            loadBg = new Background(".\\Extras\\loadgame_bg.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * constructor to create fields.
     * @param username gets player username
     */
    public LoadBoard(String username){
        //sets username
        this.username = username;

        //create buttons array list
        buttonArrayList = new ArrayList<>();

        //create labels array list
        jLabels = new ArrayList<>();

        //create frame
        loadFrame = new JFrame("Load A Game");

        //create button panel
        loadPanel = new JPanel();

        //create options panel
        gamePanel = new JPanel();

        //create label panel
        labelPanel = new JPanel();

        //create load button
        loadButton = new JButton("Load");
    }

    /**
     * This method creates game loading frame whenever called.
     * It calles updateBoard method.
     */
    public void createLoadFrame(){
        
        //create start frame and set features
        loadFrame.setSize(800,450);
        loadFrame.setLocationRelativeTo(null);
        loadFrame.setResizable(false);
        //add backGround image
        loadFrame.setContentPane(loadBg);

        //set image layout to null
        loadBg.setLayout(null);

        //manually set bounds for each panel
        loadPanel.setBounds(70,100,300,300);
        loadPanel.setLayout(new BorderLayout(5,5));
        //set game panel bounds
        gamePanel.setBounds(70,70,300,300);
        gamePanel.setLayout(new GridLayout(10,1,5,5));

        //call updateBoard method here
        updateBoard();

        //add button
        loadPanel.add(loadButton, BorderLayout.SOUTH);
        //add game panel
        loadPanel.add(gamePanel, BorderLayout.WEST);
        //add label panel
        loadPanel.add(labelPanel, BorderLayout.CENTER);
        //add load panel to load backGround
        loadBg.add(loadPanel);
        loadFrame.setVisible(true);
    }

    /**
     * This method updates the game board each time its called.
     * It iterates through all files and adds the name of unfinished games to labels array.
     */
    public void updateBoard(){
        File[] allFiles = FileUtils.getFilesInDirectory(".\\users\\"+username+"\\");
        //for each on all player files
        for(File allFile: allFiles)
        {
            String title = FileUtils.scanByLineNumber(allFile, 0);
            String str;

            //check if the game is finished or not
            if(title.equals("notFinished"))
            {
                //extract the name
                String[] fName = allFile.getName().split("\\.");
                String name = fName[0];

                //add to buttons
                buttonArrayList.add(new JRadioButton(name));

                //find the game mode
                str = FileUtils.scanByLineNumber(allFile, 1);

                //find the game type
                str = str + " - " +  FileUtils.scanByLineNumber(allFile, 2);

                //add features to a label and add to the lables list
                jLabels.add(new JLabel(str));
            }
        }
        //for each on buttons array list and add them to game panel
        for (JRadioButton button: buttonArrayList)
            gamePanel.add(button);
        for (JLabel label: jLabels)
            labelPanel.add(label);
    }

    /**
     * This method finds the selected item and returns it.
     * @return the name of selected item
     */
    public String selectedItems(){
        String text = null;
        for (JRadioButton button:buttonArrayList) {
            if(button.isSelected())
                text = button.getText();
        }
        return text;
    }

    /**
     * This method gets load button
     * @return load button
     */
    public JButton getLoadButton() {
        return loadButton;
    }

    /**
     * This method sets load frame visibility to false (closes the frame)
     */
    public void disable()
    {
        loadFrame.setVisible(false);
    }
}
