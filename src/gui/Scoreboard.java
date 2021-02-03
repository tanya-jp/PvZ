package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class creates UI for scoreboard.
 * It updates the data whenever needed.
 * @author Elaheh Akbari and Tanya Djavaherpour
 * @version 1.0 2021
 */
public class Scoreboard {

    //scoreboard background
    private Background rankingBg;
    //scoreboard frame
    private JFrame rankFrame;

    //create new color
    Color color = new Color(255,255,153);

    //tabbed pane to separate different modes
    private JTabbedPane modePane;


    /**
     * This method creates the board and all objects.
     * Also updates information on the board each time its called.
     * @param allInfo an array list of strings including all usersInfoFile content
     */
    public void createBoard(ArrayList<String> allInfo){

        //create background image and handle possible exceptions
        try {
            rankingBg = new Background(".\\Extras\\ranking_bg.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //create ranking frame
        rankFrame = new JFrame();
        rankFrame.setVisible(false);

        //create tabbed pane
        modePane = new JTabbedPane();

        //create a panel to include normal game information
        JPanel mainNormalPanel = new JPanel(new GridLayout(15,5,5,5));
        //create a panel to include hard game information
        JPanel mainHardPanel = new JPanel(new GridLayout(15,5,5,5));

        //create array list of labels for normal games title
        ArrayList<JLabel> normalTitle;
        //create array list of labels for hard games title
        ArrayList<JLabel> hardTitle;


        //create main frame for scoreboard
        rankFrame.setLayout(null);
        rankFrame.setLocation(300,180);
        rankFrame.setSize(650,500);
        rankFrame.setResizable(false);
        //set rank frame content to background image
        rankFrame.setContentPane(rankingBg);

        //create new panel to add all normal mode components to
        JPanel finalNormalPanel = new JPanel(new BorderLayout(5,5));
        //create new panel to add all hard mode components to
        JPanel finalHardPanel = new JPanel(new BorderLayout(5,5));

        //set main panel of normal mode
        mainNormalPanel.setSize(300,500);

        //set main panel for hard mode
        mainHardPanel.setSize(300,500);

        //set ranking background layout to null
        rankingBg.setLayout(null);

        //check if the tabbed pane already has tabs or not, if not add them
        if(modePane.getTabCount() != 2) {
            modePane.addTab("normal", finalNormalPanel);
            modePane.addTab("hard", finalHardPanel);
        }

        //set tabbed pane location and size
        modePane.setLocation(30,150);
        modePane.setSize(300,300);

        //create normal panel title
        normalTitle = createTitle("name","type","wins","looses"
        ,"score");

        //create hard panel title
        hardTitle = createTitle("name","type","wins","looses"
                ,"score");




        //add titles if not already added
        //to both normal and hard panels
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

        //create a scroll pane for normal panel
        JScrollPane normalScroll = new JScrollPane(mainNormalPanel);
        //make it vertical
        normalScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //create a scroll pane for normal panel
        JScrollPane hardScroll = new JScrollPane(mainHardPanel);
        //make it vertical
        hardScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //number od normal labels added
        int normalNum = 0;
        //number od hard labels added
        int hardNum = 0;

        //foreach on all info
        //each line is for one player
        //each text is added to a label and labels are added to panels
        for (String userInfo:allInfo) {

            //split each line using -
            String[] info = userInfo.split("-");

            //separate info into proper strings
            String u = info[0].trim(); //username
            String m = info[1].trim(); //mode
            String t = info[2].trim(); //type
            String w = info[3].trim(); //wins
            String l = info[4].trim(); //looses
            String s = info[5].trim(); //score

            //add to normal panel if mode is normal
            if(m.equalsIgnoreCase("normal"))
            {
                //user label
                JLabel user = new JLabel(u, SwingConstants.CENTER);
                user.setBackground(color);
                user.setOpaque(true);
                //type label
                JLabel gType = new JLabel(t, SwingConstants.CENTER);
                gType.setBackground(color);
                gType.setOpaque(true);
                //wins label
                JLabel wins = new JLabel(w, SwingConstants.CENTER);
                wins.setBackground(color);
                wins.setOpaque(true);
                //lost label
                JLabel lost = new JLabel(l, SwingConstants.CENTER);
                lost.setBackground(color);
                lost.setOpaque(true);
                //score label
                JLabel scores = new JLabel(s, SwingConstants.CENTER);
                scores.setBackground(color);
                scores.setOpaque(true);
                //add
                mainNormalPanel.add(user);
                mainNormalPanel.add(gType);
                mainNormalPanel.add(wins);
                mainNormalPanel.add(lost);
                mainNormalPanel.add(scores);
            }

            //add to hard panel if mode is hard
            else if (m.equalsIgnoreCase("hard"))
            {
                //user label
                JLabel user = new JLabel(u, SwingConstants.CENTER);
                user.setBackground(color);
                user.setOpaque(true);

                //type label
                JLabel gType = new JLabel(t, SwingConstants.CENTER);
                gType.setBackground(color);
                gType.setOpaque(true);

                //wins label
                JLabel wins = new JLabel(w, SwingConstants.CENTER);
                wins.setBackground(color);
                wins.setOpaque(true);

                //lost label
                JLabel lost = new JLabel(l, SwingConstants.CENTER);
                lost.setBackground(color);
                lost.setOpaque(true);

                //score label
                JLabel scores = new JLabel(s, SwingConstants.CENTER);
                scores.setBackground(color);
                scores.setOpaque(true);

                //add
                mainHardPanel.add(user);
                mainHardPanel.add(gType);
                mainHardPanel.add(wins);
                mainHardPanel.add(lost);
                mainHardPanel.add(scores);
                hardNum += 5;
            }

        }


        //add empty labels to normal panel if needed
        for(int i = 0; i < (70 - hardNum); i++){
            JLabel label = new JLabel();
            label.setBackground(new Color(255,255,153));
            label.setOpaque(true);
            mainNormalPanel.add(label);
        }

        //add empty labels to hard panel if needed
        for(int i = 0; i < (70 - hardNum); i++){
            JLabel label = new JLabel();
            label.setBackground(new Color(255,255,153));
            label.setOpaque(true);
            mainHardPanel.add(label);
        }

        //add panels and scroll pane to normal panel
        finalNormalPanel.add(mainNormalPanel);
        finalNormalPanel.add(normalScroll,BorderLayout.EAST);
        //add panels and scroll pane to hard panel
        finalHardPanel.add(mainHardPanel);
        finalHardPanel.add(hardScroll,BorderLayout.EAST);

        //add tabbed pane to background
        rankingBg.add(modePane);
        //show the frame
        rankFrame.setVisible(true);
    }


    /**
     * This method gets string infos, add them to labels and adds color to them.
     * @param username player username
     * @param type game type (day or night)
     * @param numOfWins number of wins
     * @param numOfLooses number of looses
     * @param score given score
     * @return an array list of labels
     */
    public ArrayList<JLabel> createTitle(String username, String type
            , String numOfWins, String numOfLooses, String score) {
        ArrayList<JLabel> array = new ArrayList<>();


        //create labels
        //user label
        JLabel user = new JLabel(username, SwingConstants.CENTER);
        user.setBackground(color);
        user.setOpaque(true);
        //type label
        JLabel gType = new JLabel(type, SwingConstants.CENTER);
        gType.setBackground(color);
        gType.setOpaque(true);
        //wins label
        JLabel wins = new JLabel(numOfWins, SwingConstants.CENTER);
        wins.setBackground(color);
        wins.setOpaque(true);
        //lost label
        JLabel lost = new JLabel(numOfLooses, SwingConstants.CENTER);
        lost.setBackground(color);
        lost.setOpaque(true);
        //score label
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
}