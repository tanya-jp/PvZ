//package game.template.bufferstrategy;
//
//import java.awt.*;
//import java.util.HashMap;
//import java.util.Random;
//
//public class Day extends GameState{
//    public static final int GAME_HEIGHT = 772;
//    public static final int GAME_WIDTH = 1010;
//    //    private KeyHandler keyHandler;
//    private final MouseHandler mouseHandler;
//    private boolean sunState;
//    private HashMap<Integer, Boolean> sunFlowerState;
//    private final HashMap<Integer, Long> sunFlowerSunTime;
//    public int sunX, sunY,sunNumber, cardW, cardH;
//    private final Image sun;
//    private boolean peaShooter, sunFlower, cherryBomb, wallNut, freezePeaShooter, lock, shovel;
//    private HashMap<Integer, String> info ;
//    Random rand = new Random();
//    private long peaShooterTime, sunFlowerTime, cherryBombTime, wallNutTime, freezePeaShooterTime, sunTime, cherryBombState;
//    private String type;
//
//    public Day(String type) {
//        super(type);
//    }
//
//    @Override
//    public void update()
//    {
//        super();
//    }
//    /**
//     * The method which updates the sun state.
//     */
//    @Override
//    public void setSun()
//    {
//        if(sunState && ((type.equals("normal") && (System.currentTimeMillis() - sunTime) >= 25000) ||
//                (type.equals("hard") && (System.currentTimeMillis() - sunTime) >= 30000)))
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
//        if(sunFlower && (System.currentTimeMillis() - sunFlowerTime) >= 7500)
//            sunFlower = false;
//        if(peaShooter && (System.currentTimeMillis() - peaShooterTime) >= 7500)
//            peaShooter = false;
//        if(wallNut && (System.currentTimeMillis() - wallNutTime) >= 30000)
//            wallNut = false;
//        if(cherryBomb)
//        {
//            if(type.equals("normal") && (System.currentTimeMillis() - cherryBombTime) >= 30000)
//                cherryBomb = false;
//            else if(type.equals("hard") && (System.currentTimeMillis() - cherryBombTime) >= 45000)
//                cherryBomb = false;
//            if((System.currentTimeMillis() - cherryBombState) >= 2000)
//            {
//                for (HashMap.Entry<Integer, String> set : info.entrySet()) {
//                    if (set.getValue() != null)
//                        if(set.getValue().equals("cherryBomb"))
//                        {
//                            int loc = set.getKey();
//                            info.replace(loc, null);
//                        }
//                }
//            }
//
//        }
//        if(freezePeaShooter)
//        {
//            if(type.equals("normal") && (System.currentTimeMillis() - freezePeaShooterTime) >= 7500)
//                freezePeaShooter = false;
//            else if(type.equals("hard") && (System.currentTimeMillis() - freezePeaShooterTime) >= 30000)
//                freezePeaShooter = false;
//        }
//        //Unlock --> new flower can be added to the playground
//        if(!sunFlower && !peaShooter && !wallNut && !cherryBomb && !freezePeaShooter && !mushroom)
//            lock = false;
//    }
//
//
//}
