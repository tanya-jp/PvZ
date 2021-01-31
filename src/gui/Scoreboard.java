package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Scoreboard {
    private Background rankingBg;
    private final JFrame rankFrame;
    private final JPanel normalPanel;
    private final JPanel hardPanel;

    private ArrayList<JLabel> normalTitle;
    private ArrayList<JLabel> hardTitle;

    Color color = new Color(255,255,153);

    JPanel mainNormalPanel = new JPanel(new GridLayout(1,5,5,5));
    JPanel mainHardPanel = new JPanel(new GridLayout(1,5,5,5));


    private final JTabbedPane modePane;

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

        //set main panel of normal mode
        mainNormalPanel.add(normalPanel);
        mainNormalPanel.setSize(300,60);

        //set main panel for hard mode
        mainHardPanel.add(hardPanel);
        mainHardPanel.setSize(300,60);

        rankingBg.setLayout(null);

        //check if the tabbed pane already has tabs or not, if not add them
        if(modePane.getTabCount() != 2) {
            modePane.addTab("normal", mainNormalPanel);
            modePane.addTab("hard", mainHardPanel);
        }

//        modePane.setLayout(null);

        modePane.setLocation(30,150);
        modePane.setSize(300,60);

        //create title
        normalTitle = createTitle("name","type","wins","looses"
        ,"score");

        hardTitle = createTitle("name","type","wins","looses"
                ,"score");



        //create panel for all users and set layouts
        normalPanel.setLayout(new GridLayout(1,5,5,5));
        hardPanel.setLayout(new GridLayout(1,5,5,5));
//        normalPanel.setLocation(30,150);
//        normalPanel.setSize(300,300);

        //add titles if not already added
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

//        updateBoard("normal","mmd","day",
//                2,3,100);
//        updateBoard("normal","mmd","day",
//                2,3,100);
//
//
//        //test
//
//        updateBoard("normal","mmd","day",
//                2,3,100);
//
//        updateBoard("hard","mmd","day",
//                2,4,100);
//        updateBoard("hard","mmd","day",
//                2,4,100);
//        updateBoard("hard","mmd","day",
//                2,4,100);




        rankingBg.add(modePane);
        rankFrame.setVisible(true);
    }

    public ArrayList<JLabel> createArray(String username, String type
    ,String numOfWins,String numOfLooses, String score){
        //create the main array
        ArrayList<JLabel> array = new ArrayList<>();

        //create labels
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

        //add labels to main array
        array.add(user);
        array.add(gType);
        array.add(wins);
        array.add(lost);
        array.add(lost);
        array.add(scores);

        //return the array
        return array;
    }
//TODO:
    public void updateNormalBoard(ArrayList<String> info){
        //split a line using -
        String username = info.get(0); //username
        String mode = info.get(1); //mode
        String type = info.get(2); //type
        String numOfWins = info.get(3); //wins
        String numOfLooses = info.get(4); //looses
        String score = info.get(5); //score


        //create a new row for the new user
        ArrayList<JLabel> row = createArray(username,type
                ,numOfWins,numOfLooses,score);

        int normalNumOfRows = mainNormalPanel.getComponentCount() % 5;
        int hardNumOfRows = mainHardPanel.getComponentCount() % 5;
//        System.out.println(normalNumOfRows);
//        System.out.println(hardNumOfRows);
        JPanel newRow = new JPanel(new GridLayout(1,5,5,5));
        int height = 60;
//        if((mode.equals("normal") && normalNumOfRows < hardNumOfRows)
//        || (mode.equals("hard") && hardNumOfRows < normalNumOfRows))
//            height = (modePane.getHeight());
//        else
            height = (modePane.getHeight()) + 40;

        mainNormalPanel.setLayout(new GridLayout(normalNumOfRows + 1,5,5,5));
        mainHardPanel.setLayout(new GridLayout(hardNumOfRows + 1,5,5,5));

        modePane.setSize(300,height);

        modePane.revalidate();
        modePane.repaint();
        mainNormalPanel.add(newRow);
        for (JLabel label:row) {
            newRow.add(label);
        }

        if(mode.equals("normal")){
//            mainNormalPanel.setSize(300,10);
//            mainNormalPanel.revalidate();
//            mainNormalPanel.repaint();
            mainNormalPanel.add(newRow);
            for (JLabel label:row) {
                newRow.add(label);
            }
        }
        else if (mode.equals("hard")){
//            mainHardPanel.setSize(300,10);
//            mainHardPanel.revalidate();
//            mainHardPanel.repaint();
            mainHardPanel.add(newRow);
            for (JLabel label:row) {
                newRow.add(label);
            }
        }

    }

    public void updateHardBoard(ArrayList<String> info){
        //split a line using -
        String username = info.get(0); //username
        String mode = info.get(1); //mode
        String type = info.get(2); //type
        String numOfWins = info.get(3); //wins
        String numOfLooses = info.get(4); //looses
        String score = info.get(5); //score


        //create a new row for the new user
        ArrayList<JLabel> row = createArray(username,type
                ,numOfWins,numOfLooses,score);

        int normalNumOfRows = mainNormalPanel.getComponentCount() % 5;
        int hardNumOfRows = mainHardPanel.getComponentCount() % 5;
//        System.out.println(normalNumOfRows);
//        System.out.println(hardNumOfRows);
        JPanel newRow = new JPanel(new GridLayout(1,5,5,5));
        int height = 60;
//        if((mode.equals("normal") && normalNumOfRows < hardNumOfRows)
//        || (mode.equals("hard") && hardNumOfRows < normalNumOfRows))
//            height = (modePane.getHeight());
//        else
        height = (modePane.getHeight()) + 40;

        mainNormalPanel.setLayout(new GridLayout(normalNumOfRows + 1,5,5,5));
        mainHardPanel.setLayout(new GridLayout(hardNumOfRows + 1,5,5,5));

        modePane.setSize(300,height);

        modePane.revalidate();
        modePane.repaint();

        mainHardPanel.add(newRow);
        for (JLabel label:row) {
            newRow.add(label);
        }

//        if(mode.equals("normal")){
//
//            mainNormalPanel.add(newRow);
//            for (JLabel label:row) {
//                newRow.add(label);
//            }
//        }
//        else if (mode.equals("hard")){
//
//            mainHardPanel.add(newRow);
//            for (JLabel label:row) {
//                newRow.add(label);
//            }
//        }

    }

    public ArrayList<JLabel> createTitle(String username, String type
            , String numOfWins, String numOfLooses, String score){
        ArrayList<JLabel> array = new ArrayList<>();


        //create labels
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

        //add to the array
        array.add(user);
        array.add(gType);
        array.add(wins);
        array.add(lost);
        array.add(lost);
        array.add(scores);

        //return array
        return array;
    }

    //TODO:

    public JFrame getRankFrame() {
        return rankFrame;
    }
}