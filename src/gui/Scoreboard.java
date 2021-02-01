package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Scoreboard {
    private Background rankingBg;
    private JFrame rankFrame;
//    private final JPanel normalPanel;
//    private final JPanel hardPanel;
//
//    private ArrayList<JLabel> normalTitle;
//    private ArrayList<JLabel> hardTitle;

    Color color = new Color(255,255,153);

//    JPanel mainNormalPanel = new JPanel(new GridLayout(15,5,5,5));
//    JPanel mainHardPanel = new JPanel(new GridLayout(15,5,5,5));
{

}

    private JTabbedPane modePane;


    public Scoreboard(){
//        rankFrame = new JFrame();
//        normalPanel = new JPanel();
//        hardPanel = new JPanel();
//        normalTitle = new ArrayList<>();
//        hardTitle = new ArrayList<>();
    }

    public void createBoard(ArrayList<String> allInfo){
        try {
            rankingBg = new Background(".\\Extras\\ranking_bg.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        rankFrame = new JFrame();
        rankFrame.setVisible(false);
        modePane = new JTabbedPane();
//        final JFrame rankFrame = new JFrame();;
//         final JPanel normalPanel = new JPanel();;
//         final JPanel hardPanel  = new JPanel();;
        JPanel mainNormalPanel = new JPanel(new GridLayout(15,5,5,5));
        JPanel mainHardPanel = new JPanel(new GridLayout(15,5,5,5));

         ArrayList<JLabel> normalTitle;
         ArrayList<JLabel> hardTitle;
        //create main frame for scoreboard
        rankFrame.setLayout(null);
        rankFrame.setLocation(300,180);
        rankFrame.setSize(650,500);
        rankFrame.setResizable(false);
        rankFrame.setContentPane(rankingBg);

        JPanel finalNormalPanel = new JPanel(new BorderLayout(5,5));
        JPanel finalHardPanel = new JPanel(new BorderLayout(5,5));

        //set main panel of normal mode
//        mainNormalPanel.add(normalPanel);
        mainNormalPanel.setSize(300,500);

        //set main panel for hard mode
//        mainHardPanel.add(hardPanel);
        mainHardPanel.setSize(300,500);

        rankingBg.setLayout(null);

        //check if the tabbed pane already has tabs or not, if not add them
        if(modePane.getTabCount() != 2) {
            modePane.addTab("normal", finalNormalPanel);
            modePane.addTab("hard", finalHardPanel);
        }

//        modePane.setLayout(null);

        modePane.setLocation(30,150);
        modePane.setSize(300,300);

        //create title
        normalTitle = createTitle("name","type","wins","looses"
        ,"score");

        hardTitle = createTitle("name","type","wins","looses"
                ,"score");



        //create panel for all users and set layouts
//        normalPanel.setLayout(new GridLayout(10,5,5,5));
//        hardPanel.setLayout(new GridLayout(10,5,5,5));
//        normalPanel.setLocation(30,150);
//        normalPanel.setSize(300,300);

        //add titles if not already added
        if(mainNormalPanel.getComponentCount() == 0) {
            for (JLabel label : normalTitle) {
                mainNormalPanel.add(label);
            }
        }
        if(mainHardPanel.getComponentCount() == 0) {
            for (JLabel label : hardTitle) {
                mainHardPanel.add(label);
            }
        }

        JScrollPane normalScroll = new JScrollPane(mainNormalPanel);
        normalScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JScrollPane hardScroll = new JScrollPane(mainHardPanel);
        hardScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        int normalNum = 0;
        int hardNum = 0;
        for (String userInfo:allInfo) {
//            System.out.println(allInfo.size());
            String[] info = userInfo.split("-");
            //split a line using -
            String u = info[0].trim(); //username
            String m = info[1].trim(); //mode
            String t = info[2].trim(); //type
            String w = info[3].trim(); //wins
            String l = info[4].trim(); //looses
            String s = info[5].trim(); //score
//            ArrayList<JLabel> row = new ArrayList<>();
//            row = createArray(u,t,w,l,s);

            if(m.equalsIgnoreCase("normal"))
            {
//                System.out.println(u);
                mainNormalPanel.add(new JLabel(u));
                mainNormalPanel.add(new JLabel(t));
                mainNormalPanel.add(new JLabel(w));
                mainNormalPanel.add(new JLabel(l));
                mainNormalPanel.add(new JLabel(s));
                normalNum += 5;
            }

            else if (m.equalsIgnoreCase("hard"))
            {
                mainHardPanel.add(new JLabel(u));
                mainHardPanel.add(new JLabel(t));
                mainHardPanel.add(new JLabel(w));
                mainHardPanel.add(new JLabel(l));
                mainHardPanel.add(new JLabel(s));
                hardNum += 5;
            }

//            if(m.equalsIgnoreCase("normal")){
//                for (JLabel label : createArray(u,t,w,l,s)) {
//                    mainNormalPanel.add(label);
//                    normalNum++;
//                }
//            }
//            else if (m.equalsIgnoreCase("hard")){
//                for (JLabel label : createArray(u,t,w,l,s)) {
//                    mainHardPanel.add(label);
////                    System.out.println("hard" + label.getText());
//                    hardNum++;
//                }
//            }
        }

//        System.out.println(normalNum);

        for(int i = 0; i < (70 - hardNum); i++){
            JLabel label = new JLabel();
            label.setBackground(new Color(255,255,153));
            label.setOpaque(true);
            mainNormalPanel.add(label);
        }
        for(int i = 0; i < (70 - hardNum); i++){
            JLabel label = new JLabel();
            label.setBackground(new Color(255,255,153));
            label.setOpaque(true);
            mainHardPanel.add(label);
        }

        finalNormalPanel.add(mainNormalPanel);
        finalNormalPanel.add(normalScroll,BorderLayout.EAST);
        finalHardPanel.add(mainHardPanel);
        finalHardPanel.add(hardScroll,BorderLayout.EAST);

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
        array.add(scores);

        //return the array
        return array;
    }

    public ArrayList<JLabel> createTitle(String username, String type
            , String numOfWins, String numOfLooses, String score) {
        ArrayList<JLabel> array = new ArrayList<>();


        //create labels
        JLabel user = new JLabel(username, SwingConstants.CENTER);
        user.setBackground(color);
        user.setOpaque(true);
        JLabel gType = new JLabel(type, SwingConstants.CENTER);
        gType.setBackground(color);
        gType.setOpaque(true);
        JLabel wins = new JLabel(numOfWins, SwingConstants.CENTER);
        wins.setBackground(color);
        wins.setOpaque(true);
        JLabel lost = new JLabel(numOfLooses, SwingConstants.CENTER);
        lost.setBackground(color);
        lost.setOpaque(true);
        JLabel scores = new JLabel(score, SwingConstants.CENTER);
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

//    public void updateScores(ArrayList<String> allInfo){
//        int normalNum = 0;
//        int hardNum = 0;
//        for (String userInfo:allInfo) {
//            String[] info = userInfo.split("-");
//            //split a line using -
//            String u = info[0].trim(); //username
//            String m = info[1].trim(); //mode
//            String t = info[2].trim(); //type
//            String w = info[3].trim(); //wins
//            String l = info[4].trim(); //looses
//            String s = info[5].trim(); //score
//
//            ArrayList<JLabel> row = createArray(u,t,w,l,s);
//
//            if(m.equalsIgnoreCase("normal")){
//                    for (JLabel label : row) {
//                        mainNormalPanel.add(label);
//                        normalNum++;
//                    }
//            }
//            else if (m.equalsIgnoreCase("hard")){
//                for (JLabel label : row) {
//                    mainHardPanel.add(label);
//                    hardNum++;
//                }
//            }
//        }
//
//        for(int i = 0; i < (70 - hardNum); i++){
//            JLabel label = new JLabel();
//            label.setBackground(new Color(255,255,153));
//            label.setOpaque(true);
//                mainNormalPanel.add(label);
//        }
//        for(int i = 0; i < (70 - hardNum); i++){
//            JLabel label = new JLabel();
//            label.setBackground(new Color(255,255,153));
//            label.setOpaque(true);
//                mainHardPanel.add(label);
//        }
//    }
    //TODO:

//    public JFrame getRankFrame() {
//        return rankFrame;
//    }
}