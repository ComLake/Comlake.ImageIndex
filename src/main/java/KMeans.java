
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.Random;
import java.io.FileWriter;


public class KMeans {

    static Vector<Point> Points = new Vector<Point>();
    static List<Vector<Point>> Clusters = new ArrayList<Vector<Point>>();
    private static int K = 4;

    public static final void Readdata() throws IOException {
        String filePath = "C:\\Users\\Thong\\Downloads\\ImageIndex\\MyFile.csv";
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath)));
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            if (line.length() == 0) continue;
            String[] cols = line.split(",");
            Vector<String> s = new Vector<String>();
            Vector<Double> snew = new Vector<Double>();
            for (int j =0; j< cols.length;j++){
                snew.add(Double.valueOf(cols[j]));
            }
            Point points = new Point();
            points.setA(snew);
            Points.add(points);

        }
        br.close();
    }

    public static Double Euclidean(Point p1, Point p2) {
        Vector<Double> a1 = p1.getA();
        Vector<Double> a2 = p2.getA();
        double distant = 0;
        for (int i = 0; i < a1.size(); i++) {
            distant += (Math.pow(a2.get(i) - a1.get(i), 2));
        }
        return Math.sqrt(distant);
    }

    public static Double CalculateCentroid() {
        Double distant = Double.MAX_VALUE;
        FileWriter output = null;
        try {
            output = new FileWriter("C:\\Users\\Thong\\Downloads\\ImageIndex\\Clusters.csv", Charset.forName("UTF8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < Clusters.size(); i++) {
            Vector<Point> newClusters = Clusters.get(i);
            Point points = new Point();
            Vector<Double> sumA = new Vector<Double>();
            Double NumberofPoints = Double.valueOf(newClusters.size());
            for (int j = 0; j < NumberofPoints; j++) {
                Point p = newClusters.get(j);
                Vector<Double> p1 = p.getA();
                for (int k = 0; k < p1.size(); k++) {
                    sumA.add(((p1.get(k))/NumberofPoints));
                }
            }
            points.setA(sumA);
            Double dist = Euclidean(newClusters.get(0), points);
            if (dist < distant) {
                distant = dist;
            }
            System.out.println(newClusters.size());
            Clusters.get(i).clear();
            Clusters.get(i).add(points);
            try {
                output.write("Centroid of cluster : " + i + "," + points.getA()+ "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return distant;
    }

    public static void Clustering () {
        int Numberofiteration = 13;
        FileWriter output = null;
        try {
            output = new FileWriter("C:\\Users\\Thong\\Downloads\\ImageIndex\\Points and Clusters.csv", Charset.forName("UTF8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int iteration = 0; iteration < Numberofiteration; iteration++) {
            for (int i = 0; i < Points.size(); i++) {
                Point p = new Point();
                p = Points.get(i);
                int index = -1;
                double dist = Double.MAX_VALUE;
                for (int k = 0; k < K; k++) {
                    Point centre = Clusters.get(k).get(0);
                    double distant = Euclidean(p, centre);
                    if (distant < dist) {
                        dist = distant;
                        index = k;
                    }
                }
                if (iteration == Numberofiteration-1) {
                    try {
                        output.write(p.getA() + ",Cluster:" + index + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Clusters.get(index).add(p);
            }
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CalculateCentroid();
    }

    public static void FirstIteration () {
        for (int k = 0; k < K; k++) {
            Vector<Point> newClusters = new Vector<Point>();
            Point p = new Point();
            Random rand = new Random();
            p = Points.get(rand.nextInt(743));
            newClusters.add(p);
            Clusters.add(newClusters);

        }
        for (int i = K; i < Points.size(); i++) {
            Point p = new Point();
            p = Points.get(i);
            int index = -1;

            double dist = Double.MAX_VALUE;
            for (int k = 0; k < K; k++) {
                Point centre = Clusters.get(k).get(0);
                double distant = Euclidean(p, centre);
                if (distant < dist) {
                    dist = distant;
                    index = k;
                }
            }
            Clusters.get(index).add(p);
        }
    }

    public void main () throws IOException {
        Readdata();

        FirstIteration();

        Clustering();

    }
}
