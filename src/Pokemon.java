import java.awt.Graphics2D;


public class Pokemon extends SpriteObject {
    private boolean isCaught = false;
    private String name = "";

    private double speed;
    private double velX;
    private double velY;
    private double calcX;
    private double calcY;
    private boolean isFlying;

    
    private int framesToTarget = 0;

    public Pokemon(){
        super(0, 0, "../src/pikachuSprite.png", 50, false, false);
        name = "pikachu";
        speed = 2;
    }

    public Pokemon(int x, int y, String imagePath, int h, String name, double speed, boolean isFlying, boolean isNocternal){
        super(x, y, imagePath, h, isFlying, isNocternal);
        this.name = name;
        this.speed = speed;
        calcX = getX();
        calcY = getY();
        this.isFlying = isFlying;
    }

    public void catchPokemon(){
        isCaught = true;
        //catchTime = 3*GamePanel.GAMEFPS;
        GamePanel.addUIELem(name, 3*GamePanel.GAMEFPS);

    }

    public String getName(){
        return name;
    }

    public boolean isCaught(){
        return isCaught;
    }

    public void displayOnScreen(Graphics2D g2D, Player player, double focalDistance){
        if(isCaught){
            return;
        }
        super.displayOnScreen(g2D, player, focalDistance);
    }

    public void setTarget() {
        double tempX = -1000 + Math.random()*2001;
        double tempY = -1000 + Math.random()*2001;
        double magTarget = Math.sqrt(Math.pow(tempX,2)+Math.pow(tempY,2));
        framesToTarget = (int) (magTarget/speed+0.5);
        velX = speed*(tempX)/magTarget;
        velY = speed*(tempY)/magTarget;
    }

    public void move() {
        if (framesToTarget == 0) {
            setTarget();
        }
        framesToTarget--;
        calcX += velX;
        calcY += velY;
        if (GameMap.detectCollision(calcX, calcY) && !isFlying) {
            velX *= -1;
            velY *= -1;
            framesToTarget = 1;
        } else if (0 > calcX || 0 > calcY || GameMap.map[1].length*100 < calcX || GameMap.map.length*100 < calcY) {
            velX *= -1;
            velY *= -1;
            framesToTarget = 1;
        } else {
            setX((int) (calcX));
            setY((int) (calcY));
        }
    }
    /*public void move() {
        if (framesToTarget == 0) {
            setTarget();
        }
        framesToTarget--;
        calcX += velX;
        calcY += velY;
        setX((int) (calcX + 0.5));
        setY((int) (calcY + 0.5));
        if (GameMap.detectCollision(calcX, calcY)) {
            velX *= -1;
            velY *= -1;
            framesToTarget = 1;
        }
    }*/
}
