package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class User {
    private JButton createButton;
    private JButton renameButton;

    private Background userBg;
    private Background renameBg;

    private JFrame userFrame;
    private JFrame renameFrame;

    private Color bgColor;
    private Color fgColor;

    {
        try {
            userBg = new Background(".\\Extras\\new_user.jpeg");
            renameBg = new Background(".\\Extras\\rename_user.jpeg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User(){
        //create buttons
        createButton = new JButton("OK");
        renameButton = new JButton("Rename");

        bgColor = new Color(102,102,102);
        fgColor = new Color(0,102,0);
    }

    public void createUserFrame(){

    }

    public void renameUserFrame(){

    }

}
