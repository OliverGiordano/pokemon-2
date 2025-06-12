import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;

public class GamePanel extends JPanel implements Runnable{
    private Font drawFont = new Font("TimesRoman", Font.BOLD, 40);
    private Thread gameThread;
    private InputManager keyHandler = new InputManager();
    //static GameMap MAP = new GameMap();
    private static int NUMBEROFRAYS = 1920;//500;//1920;
    private Player player = new Player(150, 150, 60, NUMBEROFRAYS);
    //SpriteObject enemy = new SpriteObject(150, 300, "./src/pickachuSprite.png");
    private SpriteObject[] enemys = new SpriteObject[11];//46
    private ObjectInterface[] allDrawnObjects = new ObjectInterface[NUMBEROFRAYS+enemys.length+1];
    static int GAMEFPS = 60;
    static int FOV = 80;
    static int SCREEN_WIDTH = 1920;
    static int SCREEN_HEIGHT = 1080;
    private static int HALFSCREENHEIGHT = 1080/2;
    private static double WALL_WIDTH = (double)SCREEN_WIDTH/(double)NUMBEROFRAYS;
    static double FOCALDISTANCE = SCREEN_WIDTH/2/Math.tan(Math.toRadians(FOV/2));
    static boolean textInit = true;
    private static Color cBlue = new Color(50, 80, 180);
    private static Color cRed = new Color(170, 50, 30);
    private static Color cGreen = new Color(20, 170, 90);
    private static Color cBlack = new Color(0,0,0);

    static Color cForground = new Color(255, 255, 113);

    private static boolean isDay = true;
    private double colorChange;

    private boolean is3D = true;
    private boolean toggleDetect = false;
    private boolean toggleDetect2 = false;

    private static int pokeballsThrown = 0;
    private static int caughtPokemon = 0;

    private static ArrayList<CatchUI> toasts = new ArrayList<CatchUI>();

    private Pokeball pb = new Pokeball(0, 0, 0);

    public GamePanel(){
        enemys[0] = new Pokemon(250, 250, "pikachuSprite.png",25, "pikachu" , 3, false, false);
        enemys[1] = new Pokemon(450, 200, "charzardSprite.png", 75, "charzard", 1, false, false);
        enemys[2] = new Pokemon(650, 550, "psyduckSprite.png", 25, "psyduck", 1.5, false, false);
        enemys[3] = new Pokemon(950, 140, "aerodactylSprite.png", 50, "aerodactyl", 1.5, true, false);
        enemys[4] = new Pokemon(650, 650, "eeveeSprite.png", 20, "eevee", 2, false, false);
        enemys[5] = new SpriteObject(600, 750, "treeSprite.png",110, false, false);
        enemys[6] = new SpriteObject(600, 150, "treeSprite.png",130, false, false);
        enemys[7] = new Pokemon(850, 750, "zapdosSprite.png", 100, "ditto", 8, true, false);
        enemys[8] = new Pokemon(550, 650, "umbreonSprite.png", 20, "umbreon", 2, false, true);        
        enemys[9] = new SpriteObject(800, 550, "treeSprite.png",105, false, false);
        enemys[10] = new SpriteObject(150, 500, "treeSprite.png",120, false, false);
        enemys[10] = new SpriteObject(370, 720, "treeSprite.png",75, false, false);



        this.setDoubleBuffered(true);
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new DimensionUIResource(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.setFont(drawFont);
        player.castRays();//initialize all rays (this not happening here was causing a non-fatel error)
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public Color colorSelector(int wType, boolean isVertical){
        Color tmpColor = null;
        tmpColor = switch (wType) {
            case 1 -> cBlue;
            case 2 -> cRed;
            case 3 -> cGreen;
            default -> cBlack;
        };
        if (isVertical){
            colorChange = 1;
        } else {
            colorChange = .7;
        } 
        return new Color((int)(tmpColor.getRed()*colorChange), (int)(tmpColor.getGreen()*colorChange), (int)(tmpColor.getBlue()*colorChange));
    }

    @Override
    public void run(){
        double frameTime = 1000000000/GAMEFPS;// we take system time in nano seconds, so this is nanoseconds in one game fps
        double nextFrame = System.nanoTime()+frameTime;
        while(gameThread != null){
            //long startTime = System.currentTimeMillis();
            update();
            repaint();//this repaints the screen, its like if i called "paintComponet + refreshed the screen"
            try {
                long remainingFrameTime = (long) nextFrame - System.nanoTime();
                remainingFrameTime = remainingFrameTime/1000000;//thread.sleep takes milliseconds, remaining time is in nano seconds so this converts it
                if (remainingFrameTime < 0){
                    remainingFrameTime = 0;
                }
                nextFrame += frameTime;
                Thread.sleep(remainingFrameTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(0);
            }
            //long endTime = System.currentTimeMillis();
            //System.out.println("time it took: " + (endTime - startTime));
        }
    }
    public void update(){
        for (SpriteObject en : enemys) {
            if(en.getClass().getSimpleName() == "Pokemon"){
                ((Pokemon) en).move();
            }
        }
        player.castRays();
        player.updateSpeed(keyHandler);
        if(keyHandler.isQPressed() || toggleDetect){
            if(!keyHandler.isQPressed()){
                is3D = !is3D;
                toggleDetect = false;
            } else {
                toggleDetect = true;
            }
        }
        if(keyHandler.isNPressed() || toggleDetect2){
            if(!keyHandler.isNPressed()){
                isDay = !isDay;
                toggleDetect2 = false;
            } else {
                toggleDetect2 = true;
            }
        }

        if(keyHandler.isSpacedPressed() && !pb.isThrown()){
            pb = new Pokeball(player.getAngle(), player.getX(), player.getY());
            addThrownPoke();
            pb.throwPoke();
        } else {
            pb.update(enemys);
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;//cast Graphics into Graphics2D for extra functions
        //refresh2D(g2D);
        if(textInit){ 
            g2D.drawString("1",1,1);
            textInit = false;
        }
        if(is3D){
            refresh3D(g2D);
            refreshUI(g2D);
        } else {
            refresh2D(g2D);
        }
        g2D.dispose();

    }
    public void drawFloor(Graphics2D g2D){
        g2D.setColor(new Color(116, 195, 101));
        g2D.fillRect(0, (int)(SCREEN_HEIGHT/2.0), SCREEN_WIDTH, (int)(SCREEN_HEIGHT/2.0));
    }
    public void drawSky(Graphics2D g2D){
        g2D.setColor(new Color(105, 233, 255));
        g2D.fillRect(0, 0, SCREEN_WIDTH, (int)(SCREEN_HEIGHT/2.0));
    }
    public void refresh3D(Graphics2D g2D){
        
        //long startTime = System.currentTimeMillis();
        drawFloor(g2D);
        if(isDay) drawSky(g2D);
        for(int i = 0; i < NUMBEROFRAYS; i++){
            if(allDrawnObjects[i] == null){
                allDrawnObjects[i] = new ObjectInterface(0, i, player.getRays()[i].getRealDistance());
            } else {
                allDrawnObjects[i].setFeilds(0, i, player.getRays()[i].getRealDistance());
            }
        }
        for(int i = 0; i < enemys.length; i++){
            if(allDrawnObjects[NUMBEROFRAYS+i] == null){
                allDrawnObjects[NUMBEROFRAYS+i] = new ObjectInterface(1, i, enemys[i].distance(player.getX(), player.getY()));
            } else {
                allDrawnObjects[NUMBEROFRAYS+i].setFeilds(1, i, enemys[i].distance(player.getX(), player.getY()));
            }
        }
        if(allDrawnObjects[NUMBEROFRAYS+enemys.length] == null){
            allDrawnObjects[NUMBEROFRAYS+enemys.length] = new ObjectInterface(2, -1, pb.distance(player.getX(), player.getY()));
        } else {
            allDrawnObjects[NUMBEROFRAYS+enemys.length].setFeilds(2, -1, pb.distance(player.getX(), player.getY()));
        }
        Arrays.parallelSort(allDrawnObjects, Comparator.comparingDouble((ObjectInterface obj) -> obj.getDistance()).reversed());//takes from 6 to 18 milliseconds-> this is our overrun     
        int rInd = 0;
        for (int i = 0; i < allDrawnObjects.length; i++){ //drawraycasts
            if(allDrawnObjects[i].getArrayNum() == 0){
                rInd = allDrawnObjects[i].getArrayInd();
                double rayLength = (int)(((player.getRays()[rInd].getLength()*Math.cos(Math.toRadians(player.angle -  player.getRays()[rInd].getDirection())))));
                g2D.setColor(colorSelector(player.getRays()[rInd].getWallCollision(), player.getRays()[rInd].getIsVertical()));
                g2D.fillRect((int)(player.getRays()[rInd].getDrawIndex()*WALL_WIDTH), (HALFSCREENHEIGHT), (int)WALL_WIDTH+1, (int)(FOCALDISTANCE/rayLength*20));
                g2D.fillRect((int)(player.getRays()[rInd].getDrawIndex()*WALL_WIDTH), (HALFSCREENHEIGHT)-(int)(FOCALDISTANCE/rayLength*10), (int)WALL_WIDTH+1, (int)(FOCALDISTANCE/rayLength*20));
            } else if(allDrawnObjects[i].getArrayNum() == 1){
                //if(GameMap.detectCollision(enemys[allDrawnObjects[i].getArrayInd()].getX(), enemys[allDrawnObjects[i].getArrayInd()].getY())) continue;
                //if(enemys[allDrawnObjects[i].getArrayInd()].distance(player.getX(), player.getY()) < 400)
                if(enemys[allDrawnObjects[i].getArrayInd()].getNocternal() && isDay) continue;
                if(enemys[allDrawnObjects[i].getArrayInd()].isInFeildOfVeiw(player.x, player.y, player.angle, FOV)){
                    enemys[allDrawnObjects[i].getArrayInd()].displayOnScreen(g2D, player, FOCALDISTANCE);
                }
            } else if(allDrawnObjects[i].getArrayNum() == 2){
                if(pb.isInFeildOfVeiw(player.getX(), player.getY(), player.getAngle(), FOV)) pb.draw(g2D, player);
            }
        }
        //long endTime = System.currentTimeMillis();
        //System.out.println("time it took: " + (endTime - startTime));
    }
    public void refreshUI(Graphics2D g2D){
        g2D.setColor(cBlack);
        g2D.fillRect((int)(SCREEN_WIDTH/2.0)-10, (int)(SCREEN_HEIGHT/2.0)-10, 20, 20);
        manageToasts(g2D, 1350);
        drawPokesThrown(g2D);
        drawPokesCaught(g2D);
    }

    public void manageToasts(Graphics2D g2D, int hOffset){
         for(int i = 0; i < toasts.size(); i++){
            toasts.get(i).drawToast(g2D, i, hOffset);
            if(toasts.get(i).getRemainingTime() < 0){
                toasts.remove(i);
                i--;
            } else {
                toasts.get(i).deIncrementCatchTime();
            }
        }
    }
    public void drawPokesThrown(Graphics2D g2D){
        g2D.setColor(Color.BLACK);
        g2D.fillRect(1350, (770), 550, 70);
        g2D.setColor(cForground);
        g2D.drawString("You've thrown: " + GamePanel.getThrownPoke() + " pokeballs", 1360, 820);
    }

    public void drawPokesCaught(Graphics2D g2D){
        g2D.setColor(Color.BLACK);
        g2D.fillRect(1350, (890), 550, 70);
        g2D.setColor(cForground);
        g2D.drawString("You've caught: " + GamePanel.getCaughtPokemon() + " pokemon", 1360, 940);
    }



    public static void addUIELem(String pokename, int timeFrame){
        toasts.add(new CatchUI(pokename, timeFrame));

    }

    public void refresh2D(Graphics2D g2D){
        g2D.setColor(new Color(255, 255,255));
        g2D.fillRect(player.x, player.y, 10, 10);//draw player
        g2D.drawLine(player.getX()+5, player.getY()+5, player.getX()+(int)(30*Math.cos(player.getAngleRads())), player.getY()-(int)(30*Math.sin(player.getAngleRads())));
        for (int y = 0; y < GameMap.map.length; y++){//draw map
            for (int x = 0; x < GameMap.map[1].length; x++){
                if (GameMap.map[y][x] != 0){
                    g2D.setColor(colorSelector(GameMap.getCollisionType(x*GameMap.GridSize+1, y*GameMap.GridSize+1), true));
                    g2D.fillRect(x*100, y*100, 100, 100);
                }
            }
        }
        g2D.setColor(Color.RED);
        for(Ray ray : player.getRays()){
            g2D.drawLine(player.getX(), player.getY(), (int) ray.getXEnd(), (int) ray.getYEnd());
        }
        for(SpriteObject e : enemys){
            g2D.setColor(Color.BLUE);
            if(e.getClass().getSimpleName() == "Pokemon"){
                if(((Pokemon) e).isCaught()) continue;
                g2D.setColor(Color.YELLOW);
            }
            if(e.isInFeildOfVeiw(player.getX(), player.getY(), player.getAngle(), FOV) && GameMap.getCollisionType(e.getX(), e.getY()) == 0 && e.distance(player.getX(), player.getY()) < 500) g2D.fillRect(e.getX(), e.getY(), 10, 10);
        }
        g2D.setColor(Color.ORANGE);
        if(pb.isThrown()){
            g2D.fillRect((int)pb.getX(), (int)pb.getY(), 10, 10);
        }
        g2D.setColor(Color.WHITE);
        g2D.drawLine(player.getX(), player.getY(), player.getX()+(int)(600*Math.cos(player.getAngleRads())), player.getY()-(int)(600*Math.sin(player.getAngleRads())));
        manageToasts(g2D, 1350);
        drawPokesThrown(g2D);
        drawPokesCaught(g2D);
    }

    public static void addThrownPoke(){
        pokeballsThrown++;
    }
    public static void wtfIsThisCode(){
        pokeballsThrown--;
    }
    public static int getThrownPoke(){
        return pokeballsThrown;
    }

    public static void addCaughtPoke(){
        caughtPokemon++;
    }
    /*public static void wtfIsThisCode(){
        pokeballsThrown--;
    }*/
    public static int getCaughtPokemon(){
        return caughtPokemon;
    }
    public static boolean getIsDay(){
        return isDay;
    }
}
