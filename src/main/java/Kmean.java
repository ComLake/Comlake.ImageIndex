import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Kmean {
    Integer clusterNo;

    class Record{
        HashMap<String, Double> record;
        Integer clusterNo;

        public Record(HashMap<String, Double> record){
            this.record = record;
        }

        public HashMap<String, Double> getRecord() {
            return record;
        }
    }
    private final Random random = new Random();
    private final LinkedList<Record> records = new LinkedList<>();
    private final LinkedList<String> Attribute = new LinkedList<>();

    public void setClusterNo(Integer clusterNo) {
        this.clusterNo = clusterNo;
    }
    LinkedList<HashMap<String,Double>> centroids = new LinkedList<>();

    public HashMap<String,Double> RandomCentroid() {
        int cent = random.nextInt(records.size());
        return records.get(cent).getRecord();
    }

    public Kmean (String csvFilename) throws IOException{
        String row;
        BufferedReader CSV = new BufferedReader(new FileReader(csvFilename));
        while ((row = CSV.readLine()) != null) {
            String[] data = row.split(",");
            centroids.add(RandomCentroid());
            Collections.addAll(Attribute, data);
        }
    }
    public Double euclideanDistance(HashMap<String, Double> a, HashMap<String, Double> b){
        if(!a.keySet().equals(b.keySet())){
            return Double.POSITIVE_INFINITY;
        }
        double sum = 0.0;
        for(String Attribute : a.keySet()){
            sum += Math.sqrt(Math.pow(a.get(Attribute) - b.get(Attribute), 2));
        }
        return sum;
    }
}
