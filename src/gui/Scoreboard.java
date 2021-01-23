package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Scoreboard {
    private Background rankingBg;
    private JFrame rankFrame;
    private JPanel usersPanel;

    private ArrayList<JLabel> title;
    private ArrayList<JLabel> user1;
    private ArrayList<JLabel> user2;
    private ArrayList<JLabel> user3;
    private ArrayList<JLabel> user4;
    private ArrayList<JLabel> user5;


    private JTabbedPane modePane;

    {
        try {
            rankingBg = new Background(".\\Extras\\ranking_bg.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Scoreboard(){
        rankFrame = new JFrame();
        usersPanel = new JPanel();
        title = new ArrayList<>();
        modePane = new JTabbedPane();
    }

    public void createBoard(){
        //create main frame for scoreboard
        rankFrame.setLayout(null);
        rankFrame.setLocation(300,180);
        rankFrame.setSize(650,500);
        rankFrame.setResizable(false);
        rankFrame.setContentPane(rankingBg);

        modePane.addTab("normal",usersPanel);
//        modePane.addTab("hard",usersPanel);

        createArray(title,"eli","day","1","2"
        ,"1000");


        //create panel for all users
        usersPanel.setLayout(new GridLayout(1,5,5,5));
        usersPanel.setLocation(100,50);
        usersPanel.setSize(300,300);
        for (JLabel label:title) {
            usersPanel.add(label);
        }

        rankingBg.add(usersPanel);
        rankFrame.setVisible(true);
    }

    public void createArray(ArrayList<JLabel> array,String username, String type
    ,String numOfWins, String numOfLooses, String score){
        JLabel user = new JLabel();
        JLabel gType = new JLabel();
        JLabel wins = new JLabel();
        JLabel lost = new JLabel();
        JLabel scores = new JLabel();

        user.setText(username);
        gType.setText(type);
        wins.setText(numOfWins);
        lost.setText(numOfLooses);
        scores.setText(score);

        array.add(user);
        array.add(gType);
        array.add(wins);
        array.add(lost);
        array.add(lost);
        array.add(scores);

    }

}
