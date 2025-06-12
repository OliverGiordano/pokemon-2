import java.awt.Image;
import javax.swing.ImageIcon;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SpriteObject {
    private int x;
    private int y;
    private Image objectImage;
    private int spriteHeight;
    private boolean isFlying;
    private int HALFSCREENWIDTH = GamePanel.SCREEN_WIDTH/2;
    private int HALFSCREENHEIGHT = GamePanel.SCREEN_HEIGHT/2;
    
    private double hmod = 0;
    private double changeHmod = -0.01;

    private boolean isNocternal;

    public SpriteObject(int x, int y, String image, int h, boolean isFlying, boolean isNocternal){
        this.spriteHeight = h;
        this.x = x;
        this.y = y;
        ImageIcon ii = new ImageIcon("../assets/"+image);   
        objectImage = toBufferedImage(ii.getImage());
        this.isFlying = isFlying;
        this.isNocternal = isNocternal;
        //objectImage = ii.getImage();
    }


    public static BufferedImage toBufferedImage(Image image){
        BufferedImage bImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bImage.createGraphics();
        bGr.drawImage(image, 0, 0, null);
        bGr.dispose();
        return bImage;
    }

    public Image getImage(){
        return objectImage;
    }

    public void displayOnScreen(Graphics2D g2D, Player player, double focalDistance){
        //if(distance(player.x, player.y) > 600) return;
        int dX = (x-player.getX());
        int dY = (y-player.getY());

        double angleToSprite = Math.atan2(-dY, dX);
        double relativeAngle = angleToSprite - player.getAngleRads();

        while(relativeAngle > Math.PI) relativeAngle -= Math.PI*2;
        while(relativeAngle < -Math.PI) relativeAngle += Math.PI*2;

        double distance = distance(player.getX(), player.getY());

        if(distance <=.000001) return;// if we abouta do some devious devsion by 0
        double height = ((spriteHeight*focalDistance)/distance);
        double width = height;
        int screenX = (int)(HALFSCREENWIDTH + Math.tan(relativeAngle) * focalDistance); 
        //int screenY = (int)(HALFSCREENHEIGHT - (spriteHeight-25)*focalDistance/distance);
        int screenY = (int)(HALFSCREENHEIGHT - (spriteHeight-25)*focalDistance/distance) + (int) (hmod*height);
        //int screenY = (int)(HALFSCREENHEIGHT - (spriteHeight-25)*focalDistance/distance);
        if (isFlying) {
            hmod += changeHmod;
            if (hmod >= -0.5) {
                changeHmod = -0.01;
            }
            if (hmod <= -1) {
                changeHmod = 0.01;
            }
        }
        //long startTime = System.currentTimeMillis();
        g2D.drawImage(objectImage, (screenX-(int)(width/2)), screenY, (int)height, (int)width, null); // takes about 1 millisecond
        //long endTime = System.currentTimeMillis();
        //System.out.println("time it took: " + (endTime - startTime));
    }

    public boolean isInFeildOfVeiw(int playerX, int playerY, double playerAngle, int fov){
        double angleToEnemy = (Math.atan2(-(y-playerY), x-playerX)+Math.PI*2)%(Math.PI*2);
        double checkAngleL = Math.toRadians((playerAngle-(fov/2+10)+360)%(360));
        double checkAngleR = Math.toRadians((playerAngle+(fov/2+10)+360)%(360));
        if(checkAngleL<checkAngleR){
            return angleToEnemy <= checkAngleR && angleToEnemy >= checkAngleL;
        } else {
            return angleToEnemy > checkAngleL || angleToEnemy < checkAngleR;
        }
    }
    public double distance(int playerX, int playerY){
        return Math.sqrt(Math.pow(playerX-x, 2)+Math.pow(playerY-y, 2));
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public void setX(int xpos){
        x = xpos;
    }
    public void setY(int ypos){
        y = ypos;
    }
    public boolean getNocternal(){
        return isNocternal;
    }

}
