import java.util.Random;
import java.util.Arrays;
import java.util.Vector;

public class Point {
    private Vector<Double> a = new Vector<Double>();


    private double x = 0;
    private double y = 0;
    private double z = 0;

    public Point() {
        this.setA(a);
        this.setX(x);
        this.setY(y);
        this.setZ(z);
    }

    public void setA(Vector<Double> a) {
        this.a = a;
    }
    public Vector<Double> getA(){
        return this.a;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return this.x;
    }

    public void setZ(double z) {
        this.z = z;
    }
    public double getZ(){
        return this.z;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return this.y;
    }

}
