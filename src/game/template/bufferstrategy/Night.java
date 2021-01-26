//package game.template.bufferstrategy;
//
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
//import java.util.HashMap;
//import java.util.Random;
//
//public class Night extends GameState{
//    public static final int GAME_HEIGHT = 772;
//    public static final int GAME_WIDTH = 1010;
//    protected MouseHandler mouseHandler;
//    //    private boolean sunState;
//    protected HashMap<Integer, Boolean> sunFlowerState;
//    protected HashMap<Integer, Long> sunFlowerSunTime;
//    protected int sunNumber, cardW, cardH;
//    protected boolean lock, shovel;
//    protected HashMap<Integer, String> info ;
//    protected long cherryBombState;
//    protected String type;
//    protected String timeType;
//    protected PeaShooter peashooter;
//    protected SunFlower sunFlower;
//    protected CherryBomb cherryBomb;
//    protected WallNut wallNut;
//    protected FreezePeaShooter freezePeaShooter;
//    private Mushroom mushroom;
//    /**
//     * Constructs game state and sets first state of game lements
//     *
//     * @param type     normal/hard
//     * @param timeType day/night
//     */
//    public Night(String type, String timeType) {
//        super(type, timeType);
//        mushroom = new Mushroom(type, timeType);
//    }
//    /**
//     * The method which updates the game state.
//     */
//    @Override
//    public void update()
//    {
//        super.update();
//    }
//    /**
//     * makes state of cards based on proper time
//     */
//    @Override
//    public void setCardsState()
//    {
//        super.setCardsState();
//        mushroom.setCard();
//        if(!sunFlower.getCard() && !peashooter.getCard() && !wallNut.getCard() &&
//                !cherryBomb.getCard() && !freezePeaShooter.getCard() && !mushroom.getCard())
//            lock = false;
//    }
//    /**
//     * Checks if sun flower can make based on game type.
//     * If it is possible, sets it location.
//     */
//    @Override
//    public void setSunFlowerState()
//    {
//        super.setSunFlowerState();
//    }
//    public MouseListener getMouseListener() {
//        return mouseHandler;
//    }
//    public MouseMotionListener getMouseMotionListener() {
//        return mouseHandler;
//    }
//    /**
//     *Returns mushroom
//     */
//    public Mushroom getMushroom()
//    {
//        return mushroom;
//    }
//    /**
//     * Returns the number of chosen suns*25.
//     */
//    @Override
//    public int getSunNumber(){
//        return super.getSunNumber();
//    }
//    /**
//     * Returns peashooter
//     */
//    @Override
//    public PeaShooter getPea()
//    {
//        return super.getPea();
//    }
//    /**
//     * Returns sunFlower
//     */
//    @Override
//    public SunFlower getSunFlower()
//    {
//        return super.getSunFlower();
//    }
//    /**
//     * Returns getCherry
//     */
//    @Override
//    public CherryBomb getCherry()
//    {
//        return super.getCherry();
//    }
//    /**
//     * Returns wall nut
//     */
//    @Override
//    public WallNut getWallNut()
//    {
//        return super.getWallNut();
//    }
//    /**
//     * Returns freeze pea shooter
//     */
//    @Override
//    public FreezePeaShooter getFreezePea()
//    {
//        return super.getFreezePea();
//    }
//
//    /**
//     * If shovel has been chosen, returns true.
//     */
//    @Override
//    public boolean getShovel()
//    {
//        return super.getShovel();
//    }
//    /**
//     * If sunflower's sun can be appeared, returns true.
//     */
//    @Override
//    public HashMap<Integer, Boolean> getSunFlowerState()
//    {
//        return super.getSunFlowerState();
//    }
//
//
//    @Override
//    public HashMap<Integer, String> getInfo()
//    {
//        return super.getInfo();
//    }
//
//    /**
//     * Finds the row and the column of the chosen cell.
//     * @param x of clicked location
//     * @param y of clicked location
//     * @return chosen location in the form of yx -> row*10 + column
//     */
//    @Override
//    public int findLoc(int x, int y)
//    {
//        return super.findLoc(x, y);
//    }
//    /**
//     * Finds which item has been chosen. Flower or shovel.
//     * If a flower had been chosen will find which flower
//     * @param e clicked location
//     */
//    @Override
//    public void chooseItem(MouseEvent e)
//    {
//        //mushroom has been chosen
//        if(e.getX() >= 110 - cardW + cardW*5 &&
//            e.getX() <= 110 + cardW + cardW*5 &&
//            e.getY() >= 38 - cardH &&
//            e.getY() <= 38 + cardH && timeType.equals("night"))
//        {
//            sunNumber = mushroom.chooseFlower(sunNumber);
//            lock = false;
//        }
//        else
//            super.chooseItem(e);
//    }
//
//    /**
//     * Saves dropping sun after clicking
//     * @param e clicked location
//     */
//    @Override
//    public void saveSun(MouseEvent e)
//    {
//        super.saveSun(e);
//    }
//
//    /**
//     * Puts flower in selected cell after choosing its card
//     * @param e clicked location
//     */
//    private void putFlower(MouseEvent e)
//    {
//        int x = e.getX();
//        int y = e.getY();
//        //find the selected location for putting flowers
//        if((peashooter.getCard() || sunFlower.getCard() || cherryBomb.getCard()
//                || wallNut.getCard() || freezePeaShooter.getCard() ||
//                 mushroom.getCard()) && !lock)
//        {
//            int loc = findLoc(x, y);
//            if(info.get(loc) == null)
//            {
//                if(peashooter.getCard())
//                    info.replace(loc, "peaShooter");
//                else if(sunFlower.getCard())
//                {
//                    info.replace(loc, "sunFlower");
//                    if(sunFlowerState.get(loc) == null)
//                        sunFlowerState.replace(loc, true);
//                    long time = System.currentTimeMillis();
//                    if(sunFlowerSunTime.get(loc) == null)
//                        sunFlowerSunTime.replace(loc, time);
//                }
//                else if(cherryBomb.getCard())
//                {
//                    info.replace(loc, "cherryBomb");
//                    cherryBombState = System.currentTimeMillis();
//                }
//                else if(wallNut.getCard())
//                    info.replace(loc, "wallNut");
//                else if(freezePeaShooter.getCard())
//                    info.replace(loc, "freezePeaShooter");
//                else if(mushroom.getCard())
//                    info.replace(loc, "mushroom");
//                lock = true;
//            }
//        }
//    }
//
//    /**
//     * After choosing shovel, removes selected flower.
//     * @param e clicked location
//     */
//    @Override
//    public void removeFlower(MouseEvent e)
//    {
//        super.removeFlower(e);
//    }
//    /**
//     * The mouse handler.
//     */
//    class MouseHandler implements MouseListener, MouseMotionListener {
//
//        @Override
//        public void mouseClicked(MouseEvent e) {
//            chooseItem(e);
//        }
//
//        @Override
//        public void mousePressed(MouseEvent e) {
//            saveSun(e);
//        }
//
//        @Override
//        public void mouseReleased(MouseEvent e) {
//            putFlower(e);
//            removeFlower(e);
//        }
//
//        @Override
//        public void mouseEntered(MouseEvent e) {
//        }
//
//        @Override
//        public void mouseExited(MouseEvent e) {
//        }
//
//        @Override
//        public void mouseDragged(MouseEvent e) {
//        }
//
//        @Override
//        public void mouseMoved(MouseEvent e) {
//        }
//    }
//}
//
//
