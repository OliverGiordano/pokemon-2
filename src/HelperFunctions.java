public class HelperFunctions{
    public static int sign(double num){
        return (int) (num/Math.abs(num));
    }
    public static int sign(int num){
        return (int) (num/Math.abs(num));
    }
    public static double pythagoreanTheorem(double a, double b){
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }
}