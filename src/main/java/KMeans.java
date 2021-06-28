
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.io.FileWriter;

public class KMeans {

    static Vector<Point> Points = new Vector<Point>();
    static List<Vector<Point>> Clusters = new ArrayList<Vector<Point>>();
    private static int K =4;

    public static final void Readdata() throws IOException {
        String filePath = "C:\\Users\\Thong\\Downloads\\ImageIndex\\MyFile.csv";
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath)));
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            if (line.length() == 0 ) continue;
            String[] cols = line.split(",");
            Point points = new Point();
            points.setX(Double.valueOf(cols[0]));
            points.setY(Double.valueOf(cols[20]));
            Points.add(points);
        }
        br.close();
    }

    public static Double Euclidean(Point p1, Point p2) {
        Double tmp = Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2);
        return Math.sqrt(tmp);
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
            Double sumX = 0.0;
            Double sumY = 0.0;
            Double NumberofPoints = Double.valueOf(newClusters.size());
            for (int j = 0; j < NumberofPoints; j++) {
                Point nextp = newClusters.get(j);
                sumX = sumX + nextp.getX();
                sumY = sumY + nextp.getY();
            }
            System.out.println(newClusters.size());

            points.setX(sumX / NumberofPoints);
            points.setY(sumY / NumberofPoints);
            Double dist = Euclidean(newClusters.get(0), points);
            if (dist < distant){
                distant = dist;
            }
            Clusters.get(i).clear();
            Clusters.get(i).add(points);
            try {
                output.write("Cluster: "+i + ","+ points.getX() + "," + points.getY() + "\n");
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

    public static void Clustering() {
        FileWriter output = null;
        try {
            output = new FileWriter("C:\\Users\\Thong\\Downloads\\ImageIndex\\Points and Clusters.csv", Charset.forName("UTF8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int iteration = 0; iteration < 5; iteration++) {
            for (int i = 0; i < Points.size(); i++) {
                Point p = new Point();
                p = Points.get(i);
                int index = -1;
                double neardist = Double.MAX_VALUE;
                for (int k = 0; k < K; k++) {
                    Point centre = Clusters.get(k).get(0);
                    double currentdistant = Euclidean(p, centre);
                    if (currentdistant < neardist) {
                        neardist = currentdistant;
                        index = k;
                    }
                }
                if (iteration == 4) {
                    try {
                        output.write(p.getX() + "," + p.getY() + ",Cluster:" + index + "\n");
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

    public static void FirstIteration() {
        for (int k = 0; k < K; k++) {
            Vector<Point> newClusters = new Vector<Point>();
            Point p = new Point();
            p = Points.get(k);
            newClusters.add(p);
            Clusters.add(newClusters);
        }
        for (int i = K; i < Points.size(); i++) {
            Point p = new Point();
            p = Points.get(i);
            int index = -1;

            double neardist = Double.MAX_VALUE;
            for (int k = 0; k < K; k++) {
                Point centre = Clusters.get(k).get(0);
                double currentdistant = Euclidean(p, centre);
                if (currentdistant < neardist) {
                    neardist = currentdistant;
                    index = k;
                }
            }
            Clusters.get(index).add(p);



        }

    }

    public static void main(String[] args) throws Exception {

        Readdata();

        FirstIteration();

        Clustering();

    }
}
