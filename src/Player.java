public class Player {
    int x;
    int y;
    double angle;
    private double speedX = 0;
    private double speedY = 0 ;
    private double maxSpeed = 4;
    private double WALL_WIDTH_MODIFIER;
    private int NUMBEROFRAYS;
    private Ray[] rays;//new Ray[number];
    Player(int x, int y, double angle, int numRays){
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.NUMBEROFRAYS = numRays;
        this.rays = new Ray[NUMBEROFRAYS];
        WALL_WIDTH_MODIFIER = GamePanel.SCREEN_WIDTH/NUMBEROFRAYS;
    }

    public void castRays(){
        double curAngle = Math.toRadians(angle-(GamePanel.FOV/2.0));
        for(int i = 0; i < NUMBEROFRAYS; i++){
            curAngle=angle+Math.toDegrees(Math.atan2(-(GamePanel.SCREEN_WIDTH/2)+(i*WALL_WIDTH_MODIFIER), GamePanel.FOCALDISTANCE));
            double tmpAngle = curAngle;
            if (tmpAngle < 0){
                tmpAngle+=360;
            }
            if(tmpAngle > 360){
                tmpAngle-=360;
            }
            rays[i] = new Ray(x, y, (double) x, (double) y, tmpAngle);
            rays[i].setDrawIndex(i);
            rays[i].rayMarchV3(); 
        }
    }
    public int getGridPositionX(){
        return(x%100);
    }
    public int getGridPositionY(){
        return(y%100);
    }
    
    public void updateSpeed(InputManager keyHandler){
        if(keyHandler.isUpPressed()) speedY += 1; //accelerate
        if(keyHandler.isDownPressed()) speedY -= 1; 
        if (!keyHandler.isUpPressed() && !keyHandler.isDownPressed() && Math.abs(speedY) > 0){
            speedY -= 1*HelperFunctions.sign(speedY);
        }
        if(keyHandler.isLeftPressed()) speedX -= 1;
        if(keyHandler.isRightPressed()) speedX += 1;
        if (!keyHandler.isLeftPressed() && !keyHandler.isRightPressed() && Math.abs(speedX) > 0){
            speedX -= 1*HelperFunctions.sign(speedX);
        }
        if((keyHandler.isLeftPressed() || keyHandler.isRightPressed()) && (keyHandler.isDownPressed() || keyHandler.isUpPressed())){
            speedX*=1/Math.sqrt(2);
            speedY*=1/Math.sqrt(2);
        }

        if (Math.abs(speedX) > maxSpeed) speedX = HelperFunctions.sign(speedX) * maxSpeed;
        if (Math.abs(speedY) > maxSpeed) speedY = HelperFunctions.sign(speedY) * maxSpeed;
        if (keyHandler.isLeftArrowPressed()) angle-=2.0;
        if (keyHandler.isRightArrowPressed()) angle+=2.0;
        if(angle > 360){
            angle-=360;
        } else if (angle < 0){
            angle+=360;
        }


        //Caching cos and sign vals
        double angleInRads = Math.toRadians(angle);
        double offsetAngleInRads = Math.toRadians(angle+90);
        double pCosAngle = Math.cos(angleInRads);
        double pSinAngle = Math.sin(angleInRads);
        double pCosOffsetAngle = Math.cos(offsetAngleInRads);
        double pSinOffsetAngle = Math.sin(offsetAngleInRads);
        if(!GameMap.detectCollision(x+1+(int)(pCosAngle*speedY), y)&&!GameMap.detectCollision(x-1+(int)(pCosAngle*speedY), y)){
            x += (int)(pCosAngle*speedY);
        }
        if(!GameMap.detectCollision(x, y-1-(int)(pSinAngle*speedY)) && !GameMap.detectCollision(x, y+1-(int)(pSinAngle*speedY))){
            y += (int)(-pSinAngle*speedY);
        }
        if(!GameMap.detectCollision(x+1+(int)(pCosOffsetAngle*speedX), y)&&!GameMap.detectCollision(x-1+(int)(pCosOffsetAngle*speedX), y)){
            x += (int)(pCosOffsetAngle*speedX);
        }
        if(!GameMap.detectCollision(x, y-1-(int)(pSinOffsetAngle*speedX)) && !GameMap.detectCollision(x, y+1-(int)(pSinOffsetAngle*speedX))){
            y += (int)(-pSinOffsetAngle*speedX);
        }
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public double getAngle(){
        return angle;
    }
    public double getAngleRads(){
        return Math.toRadians(angle);
    }
    public Ray[] getRays(){
        return rays;
    }
}
