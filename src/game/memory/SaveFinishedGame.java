package game.memory;

import game.template.bufferstrategy.GameFrame;
import game.template.bufferstrategy.GameState;
import utils.FileUtils;

import java.io.File;

public class SaveFinishedGame {
    private GameState state;
    private String userName;
    private String finishState;
    private String type;
    private String timeType;
    private String text;
    private static final String PATH = ".\\users\\";
    private  String scorePath;
    public SaveFinishedGame(GameState state, String userName, String finishState)
    {
        this.state = state;
        this.userName = userName;
        this.finishState = finishState;
        this.type = state.getType();
        this.timeType = state.getTimeType();
        scorePath = PATH+ userName +"\\score.txt";
        text = toString();
        saveInformation();
    }
    /**
     * Saves information as a text file in defined path.
     */
    public void saveInformation()
    {
        FileUtils.makeFolder(PATH);
        FileUtils.gamesWriter(text, PATH+userName+"\\");
    }
    public void updateScore(int number)
    {
        int currentScore = Integer.parseInt(FileUtils.scanByLineNumber(new File(scorePath), 1));
        currentScore += number;
        String content = "score\n" + currentScore;
        FileUtils.fileWriter(content, PATH+userName+"\\");
    }
    /**
     * Appends all information and make a string that should be saved in file.
     * @return text that should be saved.
     */
    @Override
    public String toString()
    {
        String res = finishState + "\ntype " + type + "\ntimeType " + timeType;
        return res;
    }
}
