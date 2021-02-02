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
    private String gameNum;
    private int currentScore;
    private static final String PATH = ".\\users\\";
    private  String scorePath;
    public SaveFinishedGame(GameState state, String userName, String finishState, String gameNum)
    {
        this.state = state;
        this.userName = userName;
        this.finishState = finishState;
        this.type = state.getType();
        this.timeType = state.getTimeType();
        this.gameNum = gameNum;
        scorePath = PATH+ userName +"\\score.txt";
        text = toString();
        saveInformation();
//        updateNetworkFile();
    }
    /**
     * Saves information as a text file in defined path.
     */
    public void saveInformation()
    {
        FileUtils.makeFolder(PATH);
        if(gameNum == null)
            FileUtils.gamesWriter(text, PATH+userName+"\\");
        else
            FileUtils.fileWriterByFileName(text, gameNum, PATH+userName+"\\");
    }
    public void updateScore(int number)
    {
        currentScore = Integer.parseInt(FileUtils.scanByLineNumber(new File(scorePath), 1));
        currentScore += number;
        String content = "score\n" + currentScore;
        FileUtils.fileWriter(content, PATH+userName+"\\");
    }
    public void updateNetworkFile()
    {
        String info = FileUtils.scanByName(new File(".\\src\\network\\usersInfoFile.txt"),
                userName, type);
        String[] res = info.split("-");
        int wins = Integer.parseInt(res[3]);
        int loses = Integer.parseInt(res[4]);
        if(finishState.equals("gameOver"))
            loses ++;
        else
            wins++;
        String newInfo = userName + "-" + type + "-d/n-" + wins + "-" + loses + "-" + currentScore;
        String other = FileUtils.scanOtherInfo(new File(".\\src\\network\\usersInfoFile.txt"),
                userName, type, currentScore);
        newInfo = other + newInfo;
        FileUtils.networkFileWriter(newInfo,".\\src\\network\\");
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
