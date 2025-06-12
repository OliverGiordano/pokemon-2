public class ObjectInterface {
    private int arrayNum;
    private int arrayInd;
    private double distance;

    public ObjectInterface(int arrayNum, int arrayInd, double distance){
        this.arrayNum = arrayNum;
        this.arrayInd = arrayInd;
        this.distance = distance;
    }
    public void setFeilds(int arrayNum, int arrayInd, double distance){
        this.arrayNum = arrayNum;
        this.arrayInd = arrayInd;
        this.distance = distance;
    }

    public int getArrayNum(){
        return arrayNum;
    }

    public int getArrayInd(){
        return arrayInd;
    }

    public double getDistance(){
        return distance;
    }

    public void setArrayNum(int arrayNum){
        this.arrayNum = arrayNum;
    }
    
    public void setArrayInd(int arrayInd){
        this.arrayInd = arrayInd;
    }

    public void setDistance(double distance){
        this.distance = distance;
    }
 
}

