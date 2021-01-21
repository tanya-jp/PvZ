package game.template.bufferstrategy;

public class CherryBomb implements Card{

    private final int neededSuns;
    private String type;
    private String timeType;
    private boolean card;
    private long flowerTime;

    /**
     * Constructs a new cherry bomb
     * @param type normal / hard
     * @param timeType night / day
     */
    public CherryBomb(String type, String timeType)
    {
        this.type = type;
        this.timeType = timeType;
        this.card = false;
        neededSuns = 150;
    }
    /**
     * makes state of cards based on proper time
     */
    @Override
    public void setCard() {
        if(card)
        {
            if(type.equals("normal") && (System.currentTimeMillis() - flowerTime) >= 30000)
                card = false;
            else if(type.equals("hard") && (System.currentTimeMillis() - flowerTime) >= 45000)
                card = false;
        }
    }
    /**
     * If card can be appeared, returns false.
     */
    @Override
    public boolean getCard()
    {
        return card;
    }
    /**
     * Checks if flower can be chosen, changes it state and sets the time that flower has been chosen
     * @param sunsNumber the number of available suns
     * @return the number of available suns
     */
    @Override
    public int chooseFlower(int sunsNumber)
    {
        if(sunsNumber >= neededSuns && !card)
        {
            sunsNumber -= neededSuns;
            flowerTime = System.currentTimeMillis();
            card = true;
        }
        return sunsNumber;
    }
}
