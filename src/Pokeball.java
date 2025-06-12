import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;


public class Pokeball {
    private double direction;
    private double curX;
    private double curY;
    private Image objectImage;
    private int spriteHeight = 10;
    private boolean isThrown = false;
    private final int SPEED = 8;
    private double walkDistX;
    private double walkDistY;
    
    Pokeball(double direction, int startX, int startY){
        this.direction = direction;
        walkDistX = Math.cos(Math.toRadians(direction)) * SPEED;
        walkDistY = -Math.sin(Math.toRadians(direction)) * SPEED;
        curX = startX;
        curY = startY;
        ImageIcon ii = new ImageIcon("../assets/pokeballSprite.png");    
        objectImage = ii.getImage();
    }

    public void update(SpriteObject[] sprites){
        if(!isThrown){
            return;
        }
        curX += walkDistX;
        curY += walkDistY;
        checkForPokemon(sprites);
        wallCollision();
    }
    public void wallCollision(){
        if(GameMap.detectCollision(curX, curY)){
            returnPokeball();
        } else {
            isThrown = true;
        }
    }

 
    public void draw(Graphics2D g2D, Player player){
        if(!isThrown) return;
        //if(distance(player.x, player.y) > 600) return;
        int dX = (int)(curX-player.getX());
        int dY = (int)(curY-player.getY());

        double angleToSprite = Math.atan2(-dY, dX);
        double relativeAngle = angleToSprite - player.getAngleRads();

        while(relativeAngle > Math.PI) relativeAngle -= Math.PI*2;
        while(relativeAngle < -Math.PI) relativeAngle += Math.PI*2;

        double distance = distance(player.getX(), player.getY());

        if(distance <=.000001) return;// if we abouta do some devious devsion by 0
        double height = ((spriteHeight*GamePanel.FOCALDISTANCE)/distance);
        double width = height;
        int screenX = (int)(960 + Math.tan(relativeAngle) * GamePanel.FOCALDISTANCE); 
        int screenY = 540;//(int)(540 - (spriteHeight-25)*GamePanel.FOCALDISTANCE/distance);
        g2D.drawImage(objectImage, (screenX-(int)(width/2)), screenY-(int)(height/2), (int)height, (int)width, null); // takes about 1 millisecond
    }

    public void checkForPokemon(SpriteObject[] sprites){
        for(SpriteObject sprite : sprites){
            if(sprite.getClass().getSimpleName() == "Pokemon"){
                if(!((Pokemon) sprite).isCaught()){// return false;
                    if((sprite.distance((int)curX, (int)curY) < 20)){
                        if(sprite.getNocternal() && GamePanel.getIsDay()) continue;
                        ((Pokemon) sprite).catchPokemon();
                        returnPokeball();
                        GamePanel.addCaughtPoke();
                    }
                }
            } 
        }
    }

    public boolean isInFeildOfVeiw(int playerX, int playerY, double playerAngle, int fov){
        double angleToEnemy = Math.atan2(-(curY-playerY), curX-playerX);
        if(angleToEnemy<0) angleToEnemy+=Math.PI*2;
        double checkAngleL = Math.toRadians((playerAngle-(fov/2)-(fov/4)+Math.PI*2))%(Math.PI*2);
        double checkAngleR = Math.toRadians(playerAngle+(fov/2)+(fov/4))%(Math.PI*2);
        if(checkAngleL<checkAngleR){
            return angleToEnemy <= checkAngleR && angleToEnemy >= checkAngleL;
        } else {
            return angleToEnemy > checkAngleL || angleToEnemy < checkAngleR;
        }
    }

    public double distance(int playerX, int playerY){
        return Math.sqrt(Math.pow(playerX-curX, 2)+Math.pow(playerY-curY, 2));
    }
    public double adjDistance(int playerX, int playerY, double angle){
        return (distance(playerX, playerY)*Math.cos(Math.toRadians(direction-angle)));
    }

    public void returnPokeball(){
        //GamePanel.addThrownPoke();
        isThrown = false;
        curX = 0;
        curY = 0;
    }

    public double getX(){
        return curX;
    }
    public double getY(){
        return curY;
    }
    public void throwPoke(){
        if(isThrown) return;
        isThrown = true;
    }

    public boolean isThrown(){
        return isThrown;
    }
}
