public class Ray {
    private int xStart;
    private int yStart;
    private boolean isVertical = false;
    private double xEnd;
    private double yEnd;
    private double direction;
    private int wallCollision;
    private int drawIndex;
    private double distance;
    Ray(int xStart, int yStart, double xEnd, double yEnd, double direction){
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.direction = direction;
    }
    Ray(){//idk overloading is weird java is weird 
        this.xStart = 0;
        this.yStart = 0;
        this.direction = 0.0;
    }
    /*public void marchRay(){
        //System.out.println(direction);
        for (int i = 0; i < maxDistance; i++){
            //System.out.println(Math.toRadians(direction));
            xEnd+=(double)Math.cos(Math.toRadians(direction));
            yEnd+=(double)Math.sin(Math.toRadians(direction));
            if (GameMap.detectCollision(xEnd, yEnd)){
                i = maxDistance+1; // end the loop lmao
            }
        }
    }*/
    //public void
    /*public void marchRayV2(){
        double yBigStep;
        double xBigStep;
        //double verticalRayPosX = xStart;
        //double verticalRayPosY = yStart;
        //these are for vertical walls
        //wtf
        double verticalRayPosX = ((int)(xStart/100)+1)*100;//xStart + (100-xStart%GameMap.GridSize)*HelperFunctions.sign(Math.cos(Math.toRadians(direction)));
        double verticalRayPosY = yStart + (100-xStart%GameMap.GridSize)*Math.tan(Math.toRadians(direction))*HelperFunctions.sign(Math.cos(Math.toRadians(direction)));
        if(Math.cos(Math.toRadians(direction)) <= 0){
            verticalRayPosX = xStart + ((xStart%GameMap.GridSize))*HelperFunctions.sign(Math.cos(Math.toRadians(direction)));
        }
        if(Math.sin(Math.toRadians(direction)) >= 0){
            verticalRayPosY = yStart + ((xStart%GameMap.GridSize))*Math.tan(Math.toRadians(direction))*HelperFunctions.sign(Math.cos(Math.toRadians(direction)));
        } 
        /*if(!GameMap.detectCollision(verticalRayPosX, verticalRayPosY)){
            yBigStep = (Math.tan(Math.toRadians(direction))*100*HelperFunctions.sign(Math.cos(Math.toRadians(direction))));
            xBigStep = 100*HelperFunctions.sign(Math.cos(Math.toRadians(direction)));
            for(int i = 0; i < 20; i++){ //20 is an arbatrarerly number bigger than like 6 idk
                verticalRayPosX+=xBigStep;
                verticalRayPosY+=yBigStep;
                if(GameMap.detectCollision(verticalRayPosX, verticalRayPosY)){
                    i = 21;// a number bigger than 20
                }
            }
        }*/
        
       
        /*double horizantalRayPosX = xStart - (100-yStart%GameMap.GridSize)*Math.tan(Math.toRadians(direction-90))*HelperFunctions.sign(Math.cos(Math.toRadians(direction-90)));
        double horizantalRayPosY = yStart - (100-yStart%GameMap.GridSize)*HelperFunctions.sign(Math.cos(Math.toRadians(direction-90)));
        if(Math.cos(Math.toRadians(direction-90)) >= 0){
            horizantalRayPosY = yStart - ((yStart%GameMap.GridSize)+1)*HelperFunctions.sign(Math.cos(Math.toRadians(direction-90)));
        }
        if(Math.sin(Math.toRadians(direction-90)) <= 0){
            horizantalRayPosX = xStart - (yStart%GameMap.GridSize)*Math.tan(Math.toRadians(direction-90))*HelperFunctions.sign(Math.cos(Math.toRadians(direction-90)));
        }
        /*if(!GameMap.detectCollision(horizantalRayPosX, horizantalRayPosY)){
            yBigStep = 100*HelperFunctions.sign(Math.sin(Math.toRadians(direction)));//just 100 * the direction 
            xBigStep = (100/Math.tan(Math.toRadians(direction)))*HelperFunctions.sign(Math.sin(Math.toRadians(direction)));
            for(int i = 0; i < 20; i++){ //20 is an arbatrarerly number bigger than like 6 idk 
                horizantalRayPosX+=xBigStep;
                horizantalRayPosY+=yBigStep;
                if(GameMap.detectCollision(horizantalRayPosX, horizantalRayPosY)){
                    i = 21;// a number bigger than 20
                }
            }
        }*/
        /*xEnd = horizantalRayPosX;
        yEnd = horizantalRayPosY;*/
        //xEnd = verticalRayPosX;
        //yEnd = verticalRayPosY;
        /*if(HelperFunctions.pythagoreanTheorem(verticalRayPosX-xStart, verticalRayPosY-yStart) < HelperFunctions.pythagoreanTheorem(horizantalRayPosX-xStart, horizantalRayPosY-yStart)){
            xEnd = verticalRayPosX;
            yEnd = verticalRayPosY;
        } else {
            xEnd = horizantalRayPosX;
            yEnd = horizantalRayPosY;
        }

        
    }*/
    public void rayMarchV3(){
        //System.out.println(direction);
        double directionRads = Math.toRadians(direction);
        int xInd = (int)(xStart/100);
        int xPosGrid = xStart%100;
        int yInd = (int)(yStart/100);
        int yPosGrid = yStart%100;
        double largeVerticalStepX;
        double largeVerticalStepY;
        //----------------------------------------------------// vertical grid space START
        double verticalPointPositionX = xStart;
        double verticalPointPositionY = yStart;
        int verticalDirectionX = 1;
        int verticalDirectionY = -1;
        if(direction <= 90 && direction >= 0){
            verticalPointPositionX=(xInd+1)*100;
            verticalPointPositionY=yStart-((100-xPosGrid)*Math.tan(directionRads));
            verticalDirectionX=1;
            verticalDirectionY=-1;
        } else if (direction >= 90 && direction <= 180){
            verticalPointPositionX=(xInd)*100;
            verticalPointPositionY=yStart+(xPosGrid*Math.tan(directionRads));
            verticalDirectionX=-1;
            verticalDirectionY=1;
        } else if (direction >= 180 && direction <= 270){
            verticalPointPositionX=(xInd)*100;
            verticalPointPositionY=yStart+(xPosGrid*Math.tan(directionRads));
            verticalDirectionX=-1;
            verticalDirectionY=1;
        } else {
            verticalPointPositionX=(xInd+1)*100;
            verticalPointPositionY=yStart-((100-xPosGrid)*Math.tan(directionRads));
            verticalDirectionX=1;
            verticalDirectionY=-1;
        }
        if(!GameMap.detectCollision(verticalPointPositionX-1, verticalPointPositionY) && !GameMap.detectCollision(verticalPointPositionX+2, verticalPointPositionY)){
            largeVerticalStepY = (Math.tan(directionRads)*100)*verticalDirectionY;
            largeVerticalStepX = 100*verticalDirectionX+verticalDirectionX;
            for(int i = 0; i < 20; i++){ //20 is an arbatrarerly number bigger than like 6 idk
                verticalPointPositionX+=largeVerticalStepX;
                verticalPointPositionY+=largeVerticalStepY;
                if(GameMap.detectCollision(verticalPointPositionX, verticalPointPositionY)){
                    i = 21;// a number bigger than 20
                }
            }
        } else if (verticalDirectionX == -1){
            verticalPointPositionX -= 1;
        }

        //----------------------------------------------------// END
        //----------------------------------------------------// Horizantal intial grid space START
        double horizantalPointPositionX = xStart;
        double horizantalPointPositionY = yStart;
        double horizantalOffsetAngle = 0;
        int horizantalDirectionX = 1;
        int horizantalDirectionY = 1;
        if(direction <= 90 && direction >= 0){
            horizantalPointPositionY=(yInd)*100;
            horizantalOffsetAngle = (Math.PI/2)-directionRads;
            horizantalPointPositionX=xStart+((yPosGrid)*Math.tan(horizantalOffsetAngle));
            horizantalDirectionX = -1;
            horizantalDirectionY = 1;
        } else if (direction <= 180 && direction >= 90){
            horizantalPointPositionY=(yInd)*100;
            horizantalOffsetAngle = directionRads-(Math.PI/2);
            horizantalPointPositionX=xStart-((yPosGrid)*Math.tan(horizantalOffsetAngle));
            horizantalDirectionX = -1;
            horizantalDirectionY = -1;
        } else if (direction <= 270 && direction >= 180){
            horizantalPointPositionY=(yInd+1)*100;
            horizantalOffsetAngle = (3*(Math.PI)/2)-directionRads;
            horizantalPointPositionX = xStart-((100-yPosGrid)*Math.tan(horizantalOffsetAngle));
            horizantalDirectionX = 1;
            horizantalDirectionY = -1;
        } else {
            horizantalPointPositionY=(yInd+1)*100;
            horizantalOffsetAngle = directionRads-(3*Math.PI/2);
            horizantalPointPositionX=xStart+((100-yPosGrid)*Math.tan(horizantalOffsetAngle));
            horizantalDirectionX = 1;
            horizantalDirectionY = 1;
        }
        if(!GameMap.detectCollision(horizantalPointPositionX, horizantalPointPositionY+1) && !GameMap.detectCollision(horizantalPointPositionX, horizantalPointPositionY-1)){
            largeVerticalStepY = 100*horizantalDirectionX+horizantalDirectionX;////////////////////////////////////////////////////////////////////////////////////////////////////////////
            largeVerticalStepX = (Math.tan(horizantalOffsetAngle)*100)*horizantalDirectionY;
            for(int i = 0; i < 20; i++){ //20 is an arbatrarerly number bigger than like 6 idk
                horizantalPointPositionX+=largeVerticalStepX;
                horizantalPointPositionY+=largeVerticalStepY;
                if(GameMap.detectCollision(horizantalPointPositionX, horizantalPointPositionY)){
                    i = 21;// a number bigger than 20
                }
            }
        } else{
            if (horizantalDirectionX == -1){
                horizantalPointPositionY -= 1;   
            }   
        } 

        //----------------------------------------------------// END
        double tmpDistance = HelperFunctions.pythagoreanTheorem(verticalPointPositionX-xStart, verticalPointPositionY-yStart);
        double tmp2Distance = HelperFunctions.pythagoreanTheorem(horizantalPointPositionX-xStart, horizantalPointPositionY-yStart);
        if(tmpDistance < tmp2Distance){
            xEnd = verticalPointPositionX;
            yEnd = verticalPointPositionY;
            distance = tmpDistance;
            isVertical = true;
        } else {
            xEnd = horizantalPointPositionX;
            yEnd = horizantalPointPositionY;
            distance = tmp2Distance;
            isVertical = false;
        }
        wallCollision = GameMap.getCollisionType(xEnd, yEnd);

    }
    public double getLength(){
        double distanceX = xEnd-xStart;
        double distanceY = yEnd-yStart;
        return Math.sqrt(distanceX*distanceX + distanceY*distanceY);
    }
    public double getAdjLength(Player p){
        return Math.sqrt((Math.pow(getLength(), 2)*Math.pow(Math.cos(Math.toRadians(direction-p.angle)),2)));
    }

    public int getWallCollision(){
        return wallCollision;
    }
    public void setWallCollision(int wallCollision){
        this.wallCollision = wallCollision;
    }
    public double getDistance(){
        return distance;
    }
    public int getDrawIndex(){
        return drawIndex;
    }
    public void setDrawIndex(int drawIndex){
        this.drawIndex = drawIndex;
    }
    public double getRealDistance(){
        return Math.sqrt(Math.pow(xStart - xEnd, 2) + Math.pow(yStart - yEnd, 2));
    }
    public double getXEnd(){
        return xEnd;
    }
    public double getYEnd(){
        return yEnd;
    }
    public double getDirection(){
        return direction;
    }
    public boolean getIsVertical(){
        return isVertical;
    }
}
