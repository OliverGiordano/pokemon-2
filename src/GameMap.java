//import java.awt.Color;
public class GameMap {
    static int GridSize = 100;
    static int[][] map = {{1,1,1,1,1,1,1,1,1,1,1},
                          {1,0,0,1,0,0,0,0,0,0,1},
                          {1,0,0,0,0,3,0,1,0,0,1},
                          {1,0,2,2,0,3,0,1,1,1,1},
                          {1,0,1,0,0,0,0,0,0,1,1},
                          {1,0,0,0,3,0,0,0,0,0,1},
                          {1,0,0,3,1,0,0,3,3,0,1},
                          {1,0,0,0,2,0,0,3,0,0,1},
                          {1,1,1,1,1,1,1,1,1,1,1},
                        };
    

    public static boolean detectCollision(double x, double y){
        boolean collided = false;
        if ((int)(y/GridSize) > map.length-1 || (int)(y/GridSize) < 0 || (int)(x/GridSize) > map[1].length-1 || (int)(x/GridSize) < 0){
            collided = true;
        }else if (map[(int)(y/GridSize)][(int)(x/GridSize)] != 0){
            collided = true;
        }
        return collided;
    }
    public static int getCollisionType(double x, double y){
        
        int collisionType = 0;
        if ((int)(y/GridSize) > map.length-1 || (int)(y/GridSize) < 0 || (int)(x/GridSize) > map[1].length-1 || (int)(x/GridSize) < 0){
            collisionType = 1;
        }else if (map[(int)(y/GridSize)][(int)(x/GridSize)] != 0){
            collisionType = map[(int)(y/GridSize)][(int)(x/GridSize)];
        }
        return collisionType;
    }

}
