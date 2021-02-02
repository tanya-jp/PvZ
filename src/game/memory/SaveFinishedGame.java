package game.memory;
import game.template.bufferstrategy.GameState;
import utils.FileUtils;

import java.io.File;

/**
 * After finishing a game, this class saves information such as its type and time type,
 * and defines if the player has won or lost.
 * This class also updates the score of the player and all files that should be changed
 * @version 1.0 2021
 * @authors Tanya Djavaherpour, Elaheh Akbari
 */
public class SaveFinishedGame {
    private GameState state;
    private String userName;
    //gameOver / endOfGame
    private String finishState;
    //normal / hard
    private String type;
    //day / night
    private String timeType;
    //the text that should be written
    private String text;
    //number of saved game or null if this game has not been saved before
    private String gameNum;
    //updated score based on result of this game
    private int currentScore;
    // path of the folder of all user's info
    private static final String PATH = ".\\users\\";
    //the path that this player's score is saved
    private  String scorePath;

    /**
     * Saves information
     * @param state as gameState
     * @param userName as name of the player
     * @param finishState -> gameOver / endOfGame
     * @param gameNum as number of saved game
     */
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

    /**
     * Updates this player's score based on type and the result of this finished game
     * @param number as decrement or increment number of score
     */
    public void updateScore(int number)
    {
        currentScore = Integer.parseInt(FileUtils.scanByLineNumber(new File(scorePath), 1));
        currentScore += number;
        String content = "score\n" + currentScore;
        FileUtils.fileWriter(content, PATH+userName+"\\");
    }
    /**
     * Updates the file that is used in network.
     * overwrites the result and score
     */
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
