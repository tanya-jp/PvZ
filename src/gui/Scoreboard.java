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
    JPanel mainNormalPanel = new JPanel(new GridLayout(1,5,5,5));
    public void createBoard(){
        //create main frame for scoreboard
        rankFrame.setLayout(null);
        rankFrame.setLocation(300,180);
        rankFrame.setSize(650,500);
        rankFrame.setResizable(false);
        rankFrame.setContentPane(rankingBg);


        mainNormalPanel.add(normalPanel);
        mainNormalPanel.setSize(300,50);

        rankingBg.setLayout(null);

        if(modePane.getTabCount() != 2) {
            modePane.addTab("normal", mainNormalPanel);
            modePane.addTab("hard", hardPanel);
        }

//        modePane.setLayout(null);

        modePane.setLocation(30,150);
        modePane.setSize(300,50);


        normalTitle = createArray("name","type","wins","looses"
        ,"score");

        hardTitle = createArray("name","type","wins","looses"
                ,"score");



        //create panel for all users
        normalPanel.setLayout(new GridLayout(1,5,5,5));
//        normalPanel.setLocation(30,150);
//        normalPanel.setSize(300,300);

        //add titles
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

        //test
        updateBoard("normal","mmd","day",
                "2","3","100");

        rankingBg.add(modePane);
        rankFrame.setVisible(true);
    }

    public ArrayList<JLabel> createArray(String username, String type
    ,String numOfWins, String numOfLooses, String score){
        ArrayList<JLabel> array = new ArrayList<>();
        Color color = new Color(255,255,153);

        JLabel user = new JLabel(username,SwingConstants.CENTER);
        user.setBackground(color);
        user.setOpaque(true);
        JLabel gType = new JLabel(type,SwingConstants.CENTER);
        gType.setBackground(color);
        gType.setOpaque(true);
        JLabel wins = new JLabel(numOfWins,SwingConstants.CENTER);
        wins.setBackground(color);
        wins.setOpaque(true);
        JLabel lost = new JLabel(numOfLooses,SwingConstants.CENTER);
        lost.setBackground(color);
        lost.setOpaque(true);
        JLabel scores = new JLabel(score,SwingConstants.CENTER);
        scores.setBackground(color);
        scores.setOpaque(true);

        array.add(user);
        array.add(gType);
        array.add(wins);
        array.add(lost);
        array.add(lost);
        array.add(scores);

        return array;
    }

    public void updateBoard(String mode,String username, String type
            ,String numOfWins, String numOfLooses, String score){
        ArrayList<JLabel> row = createArray(username,type
                ,numOfWins,numOfLooses,score);
        int normalNumOfRows = mainNormalPanel.getComponentCount() % 5;
//        int hardNumOfRows = hardPanel.getComponentCount() % 5;
        JPanel newRow = new JPanel(new GridLayout(1,5,5,5));
        int height = (modePane.getHeight()) * 2;

        mainNormalPanel.setLayout(new GridLayout(normalNumOfRows + 1,5,5,5));


        mainNormalPanel.setSize(300,height);
        modePane.setSize(300,height);
        mainNormalPanel.revalidate();
        mainNormalPanel.repaint();
        modePane.revalidate();
        modePane.repaint();

        if(mode.equals("normal")){
            modePane.getComponentAt(0).setSize(300, 450);
//            normalPanel.setLayout(new GridLayout(normalNumOfRows + 1,
//                    5,5,5));
            mainNormalPanel.add(newRow);
            for (JLabel label:row) {
                newRow.add(label);
            }
        }
        else if (mode.equals("hard")){
            for (JLabel label:row) {
                modePane.getComponentAt(1).setSize(300,
                        modePane.getHeight() * 2);
                hardPanel.add(label);
            }
        }

    }

}
