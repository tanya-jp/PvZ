package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Scoreboard {
    private Background rankingBg;
    private JFrame rankFrame;
    private JPanel normalPanel;
    private JPanel hardPanel;

    private ArrayList<JLabel> normalTitle;
    private ArrayList<JLabel> hardTitle;
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
        normalPanel = new JPanel();
        hardPanel = new JPanel();
        normalTitle = new ArrayList<>();
        hardTitle = new ArrayList<>();
        modePane = new JTabbedPane();

    }

    public void createBoard(){
        //create main frame for scoreboard
        rankFrame.setLayout(null);
        rankFrame.setLocation(300,180);
        rankFrame.setSize(650,500);
        rankFrame.setResizable(false);
        rankFrame.setContentPane(rankingBg);

        rankingBg.setLayout(null);

        if(modePane.getTabCount() != 2) {
            modePane.addTab("normal", normalPanel);
            modePane.addTab("hard", hardPanel);
        }

//        modePane.setLayout(null);

        modePane.setLocation(30,150);
        modePane.setSize(300,50);


        createArray(normalTitle,"name","type","wins","looses"
        ,"score");

        createArray(hardTitle,"name","type","wins","looses"
                ,"score");


        //create panel for all users
        normalPanel.setLayout(new GridLayout(1,5,5,5));
//        normalPanel.setLocation(30,150);
//        normalPanel.setSize(300,300);

        if(normalPanel.getComponentCount() == 0) {
            for (JLabel label : normalTitle) {
                normalPanel.add(label);
            }
        }
        if(hardPanel.getComponentCount() == 0) {
            for (JLabel label : hardTitle) {
                hardPanel.add(label);
            }
        }

        hardPanel.setLayout(new GridLayout(1,5,5,5));

        rankingBg.add(modePane);
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
