package gui;

import utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LoadBoard {
    //background image
    private Background loadBg;
    //main frame
    private JFrame loadFrame;
    //main panel
    private JPanel loadPanel;
    private JPanel gamePanel;
    private JPanel labelPanel;
    //button
    private JButton loadButton;
    private String username;

    private ArrayList<JRadioButton> buttonArrayList;
    private ArrayList<JLabel> jLabels;
    {
        try {
            loadBg = new Background(".\\Extras\\loadgame_bg.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LoadBoard(String username){
        this.username = username;
        buttonArrayList = new ArrayList<>();
        jLabels = new ArrayList<>();
        loadFrame = new JFrame("Load A Game");
        loadPanel = new JPanel();
        gamePanel = new JPanel();
        labelPanel = new JPanel();
        loadButton = new JButton("Load");
    }

    public void createLoadFrame(){
        //create start frame
        loadFrame.setSize(800,450);
        loadFrame.setLocationRelativeTo(null);
        loadFrame.setResizable(false);
//        loadFrame.setLayout(new GridLayout(2,1,5,5));
        loadFrame.setContentPane(loadBg);

        loadBg.setLayout(null);

        loadPanel.setBounds(70,100,300,300);
        loadPanel.setLayout(new BorderLayout(5,5));
        gamePanel.setBounds(70,70,300,300);
        gamePanel.setLayout(new GridLayout(10,1,5,5));

//        JRadioButton game1 = new JRadioButton("1");
//        JRadioButton game2 = new JRadioButton("2");
//        JRadioButton game3 = new JRadioButton("3");
//        JRadioButton game4 = new JRadioButton("4");

//        loadPanel.add(game1);
//        loadPanel.add(game2);
//        loadPanel.add(game3);
//        loadPanel.add(game4);
        updateBoard();
        loadPanel.add(loadButton, BorderLayout.SOUTH);
        loadPanel.add(gamePanel, BorderLayout.WEST);
        loadPanel.add(labelPanel, BorderLayout.CENTER);
        loadBg.add(loadPanel);
//        loadBg.add(gamePanel);
        loadFrame.setVisible(true);

    }

    public void updateBoard(){
        File[] allFiles = FileUtils.getFilesInDirectory(".\\users\\"+username+"\\");
        for(File allFile: allFiles)
        {
            String title = FileUtils.scanByLineNumber(allFile, 0);
            String str;
            if(title.equals("notFinished"))
            {
                String[] fName = allFile.getName().split("\\.");
                String name = fName[0];
                buttonArrayList.add(new JRadioButton(name));
                str = FileUtils.scanByLineNumber(allFile, 1);
                str = str + " - " +  FileUtils.scanByLineNumber(allFile, 2);
                jLabels.add(new JLabel(str));
            }
        }
        for (JRadioButton button: buttonArrayList)
            gamePanel.add(button);
        for (JLabel label: jLabels)
            labelPanel.add(label);
    }

    public String selectedItems(){
        String text = null;
        for (JRadioButton button:buttonArrayList) {
            if(button.isSelected())
                text = button.getText();
        }
        return text;
    }

    public JButton getLoadButton() {
        return loadButton;
    }
}
