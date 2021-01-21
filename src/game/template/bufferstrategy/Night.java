//package game.template.bufferstrategy;
//
//import java.awt.*;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
//import java.util.HashMap;
//import java.util.Random;
//
//public class Night extends GameState{
//    public static final int GAME_HEIGHT = 772;
//    public static final int GAME_WIDTH = 1010;
//    //    private KeyHandler keyHandler;
//    private MouseHandler mouseHandler;
//    private boolean sunState;
//    private HashMap<Integer, Boolean> sunFlowerState;
//    private HashMap<Integer, Long> sunFlowerSunTime;
//    protected int sunX, sunY,sunNumber, cardW, cardH;
//    private Image sun;
//    private boolean peaShooter, sunFlower, cherryBomb, wallNut, freezePeaShooter, mushroom, lock, shovel;
//    private HashMap<Integer, String> info ;
//    Random rand = new Random();
//    private long peaShooterTime, sunFlowerTime, cherryBombTime, wallNutTime, freezePeaShooterTime, sunTime, cherryBombState,
//    mushroomTime;
//    private String type;
//
//    public Night(String type) {
//        super(type);
//        this.type = type;
//        mushroom = false;
//    }
//    @Override
//    public void update()
//    {
//        super.update();
//    }
//    /**
//     * The method which updates the sun state.
//     */
//    @Override
//    public void setSun()
//    {
//        if(sunState && ((type.equals("normal") && (System.currentTimeMillis() - sunTime) >= 50000) ||
//                (type.equals("hard") && (System.currentTimeMillis() - sunTime) >= 60000)))
//        {
//            sunState = false;
//            changeSunState();
//        }
//        else if(!sunState)
//            changeSunState();
//    }
//    /**
//     * makes state of cards based on proper time
//     */
//    @Override
//    public void setCardsState()
//    {
//        super.setCardsState();
//        if(mushroom && (System.currentTimeMillis() - mushroomTime) >= 500)
//            mushroom = false;
//        //Unlock --> new flower can be added to the playground
//        if(!sunFlower && !peaShooter && !wallNut && !cherryBomb && !freezePeaShooter && !mushroom)
//            lock = false;
//    }
//    /**
//     * This class controls sun when it is dropping down.
//     */
//    @Override
//    public void changeSunState()
//    {
//        super.changeSunState();
//    }
//    /**
//     * sets the state of the flower that should be shown near sunFlower
//     */
//    @Override
//    public void setSunFlowerState()
//    {
//        long time = System.currentTimeMillis();
//        for (HashMap.Entry<Integer, Boolean> set : sunFlowerState.entrySet()) {
//            if (set.getValue() != null)
//                if(!set.getValue())
//                {
//                    int loc = set.getKey();
//                    if(type.equals("normal") && time - sunFlowerSunTime.get(loc) >= 25000)
//                        sunFlowerState.replace(loc, true);
//                    else if(type.equals("hard") && time - sunFlowerSunTime.get(loc) >= 30000)
//                        sunFlowerState.replace(loc, true);
//                }
//        }
//    }
//    @Override
//    public MouseListener getMouseListener() {
//        return mouseHandler;
//    }
//    @Override
//    public MouseMotionListener getMouseMotionListener() {
//        return mouseHandler;
//    }
//    /**
//     *If sun has been chosen returns true.
//     * When returns false, means sun should continue dropping.
//     */
//    @Override
//    public boolean getSun() { return super.getSun(); }
//
//    /**
//     * Returns the number of chosen suns*25.
//     */
//    @Override
//    public int getSunNumber(){
//        return super.getSunNumber();
//    }
//    /**
//     * If peaShooter's card can be appeared, returns false.
//     */
//    @Override
//    public boolean getPea()
//    {
//        return super.getPea();
//    }
//    /**
//     * If sunFlower's card can be appeared, returns false.
//     */
//    @Override
//    public boolean getSunFlower()
//    {
//        return super.getSunFlower();
//    }
//    /**
//     * If cherryBomb's card can be appeared, returns false.
//     */
//    @Override
//    public boolean getCherry()
//    {
//        return super.getCherry();
//    }
//    /**
//     * If wallNut's card can be appeared, returns false.
//     */
//    @Override
//    public boolean getWallNut()
//    {
//        return super.getWallNut();
//    }
//    /**
//     * If freezePeaShooter's card can be appeared, returns false.
//     */
//    @Override
//    public boolean getFreezePea()
//    {
//        return super.getFreezePea();
//    }
//    /**
//     * If mushroom's card can be appeared, returns false.
//     */
//    @Override
//    public boolean getMushroom()
//    {
//        return super.getMushroom();
//    }
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
//    /**
//     * Returns hashMap of information of every cell
//     */
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
//    @Override
//    public void chooseItem(MouseEvent e)
//    {
//        if(e.getX() >= 110 - cardW + cardW*5 &&
//                e.getX() <= 110 + cardW + cardW*5 &&
//                e.getY() >= 38 - cardH &&
//                e.getY() <= 38 + cardH)
//        {
//            if(sunNumber >= 25 && !mushroom)
//            {
//                mushroom = true;
//                sunNumber -= 25;
//                mushroomTime = System.currentTimeMillis();
//                lock = false;
//            }
//        }
//        else
//            super.chooseItem(e);
//        System.out.println(e.getX());
//        System.out.println(cardW);
//    }
//    /**
//     * The mouse handler.
//     */
//    class MouseHandler implements MouseListener, MouseMotionListener{
//
//        @Override
//        public void mouseClicked(MouseEvent e) {
//        chooseItem(e);
//        //mushroom has been chosen
//            System.out.println(cardH);
//            System.out.println(cardW);
//        }
//
//        @Override
//        public void mousePressed(MouseEvent e) {
//            int x = e.getX();
//            int y = e.getY();
//            //saves sun
//            if((e.getX() >= sunX - sun.getWidth(null) &&
//                    e.getX() <= sunX + sun.getWidth(null)) &&
//                    (e.getY() >= sunY - sun.getHeight(null) &&
//                            e.getY() <= sunY + sun.getHeight(null)))
//            {
//                sunState = true;
//                sunNumber += 25;
//                sunY = GAME_HEIGHT;
//                sunTime = System.currentTimeMillis();
//            }
//
//            int loc = findLoc(x,y);
//            if(sunFlowerState.get(loc) != null)
//                if (sunFlowerState.get(loc))
//                {
//                    sunFlowerState.replace(loc, false);
//                    sunFlowerSunTime.replace(loc, System.currentTimeMillis());
//                    sunNumber += 25;
//                }
//        }
//
//        @Override
//        public void mouseReleased(MouseEvent e) {
//            int x = e.getX();
//            int y = e.getY();
//            //find the selected location for putting flowers
//            if((peaShooter || sunFlower || cherryBomb || wallNut || freezePeaShooter || mushroom) && !lock)
//            {
//                int loc = findLoc(x, y);
//                if(info.get(loc) == null)
//                {
//                    if(peaShooter)
//                        info.replace(loc, "peaShooter");
//                    else if(sunFlower)
//                    {
//                        info.replace(loc, "sunFlower");
//                        if(sunFlowerState.get(loc) == null)
//                            sunFlowerState.replace(loc, true);
//                        long time = System.currentTimeMillis();
//                        if(sunFlowerSunTime.get(loc) == null)
//                            sunFlowerSunTime.replace(loc, time);
//                    }
//                    else if(cherryBomb)
//                    {
//                        info.replace(loc, "cherryBomb");
//                        cherryBombState = System.currentTimeMillis();
//                    }
//                    else if(wallNut)
//                        info.replace(loc, "wallNut");
//                    else if(freezePeaShooter)
//                        info.replace(loc, "freezePeaShooter");
//                    else if(mushroom)
//                        info.replace(loc, "mushroom");
//                    lock = true;
//                }
//            }
//            //removes a flower by shovel
//            if(shovel)
//            {
//                int loc = findLoc(x, y);
//                if(info.get(loc) != null)
//                {
//                    if(info.get(loc).equals("sunFlower"))
//                    {
//                        sunFlowerState.replace(loc, null);
//                        sunFlowerSunTime.replace(loc, null);
//                    }
//                    info.replace(loc, null);
//                    shovel = false;
//                }
//            }
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
