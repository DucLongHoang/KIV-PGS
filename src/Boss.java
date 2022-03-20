import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Boss class - delegates mining work to Worker class
 */
public class Boss {
    private final int WORKER_COUNT, WORKER_TIME, LORRY_COUNT,
            LORRY_CAP, LORRY_TIME, FERRY_CAP;
    private final Thread[] W_THREADS;
    private final Worker[] WORKERS;
    private final Mine MINE;

    private List<Integer> miningBlocks;
    private final String SPACE = " ";

    /**
     * Constructor for Boss
     * @param parser Parser object to get data from
     */
    public Boss(Parser parser, Mine mine) {
        try {
            miningBlocks = readMap(new BufferedReader(
                    new FileReader(parser.getInputFile())));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        this.WORKER_COUNT = parser.getWorkerCount();
        this.WORKER_TIME = parser.getWorkerTime();
        this.LORRY_CAP = parser.getLorryCap();
        this.LORRY_TIME = parser.getLorryTime();
        this.FERRY_CAP = parser.getFerryCap();

        this.W_THREADS = new Thread[WORKER_COUNT];
        this.WORKERS = new Worker[WORKER_COUNT];

        this.LORRY_COUNT = (miningBlocks.stream().mapToInt(e -> e).sum() / LORRY_CAP) + 1;
        this.MINE = mine;

        printMineAnalysis();
    }

    /**
     * Method for the Boss to read from the input file
     * @param br BufferedReader that reads from file
     * @return List of individual mining block sizes
     * @throws IOException exception to be thrown
     */
    public List<Integer> readMap(BufferedReader br) throws IOException {
        List<Integer> result = new LinkedList<>();
        String[] tokens;
        String line;

        System.out.println("Boss - reading file.");
        while(( line = br.readLine() ) != null){
            tokens = line.split(SPACE);
            for(String token: tokens) result.add(token.length());
        }
        result.removeIf(e -> e == 0);

        try {
            System.out.println("Boss - closing file");
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Method prints total number of blocks and resources to be mined
     */
    private void printMineAnalysis() {
        int numOfBlocks = miningBlocks.size();
        int numOfResources = miningBlocks.stream().mapToInt(e -> e).sum();
        System.out.println("Total number of blocks to be mined: " + numOfBlocks);
        System.out.println("Total number of resources to be mined: " + numOfResources);
    }

    /**
     * Method initializes Workers and makes them work. Also makes them stop working.
     */
    public void planOutWork() {
        System.out.println("Boss - making Workers");

        for(int i = 0; i < WORKER_COUNT; i++) {
            WORKERS[i] = new Worker("Worker" + i,
                    this, WORKER_TIME);
            W_THREADS[i] = new Thread(WORKERS[i]);
            W_THREADS[i].start();
            System.out.println("Boss - making " + WORKERS[i].getName());
        }

        System.out.println("Boss - waiting for Workers to end");
        for (int i = 0; i < WORKER_COUNT; i++) {
            try {
                W_THREADS[i].join();
                System.out.println("\t" + WORKERS[i].getName() + " - ending job");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Boss - printing result");
    }

    /**
     * Critical section of app!
     * Method delegates work to a Worker. Gives him number of resources to mine.
     * Returns 0 if nothing is left be mined.
     * @param workerName name of Worker that is asking for work
     * @return number of resources in a block to be mined.
     */
    public synchronized int getWork(String workerName) {
        int work = 0;

        System.out.println(workerName + " - asking for work");
        if(!miningBlocks.isEmpty()) {
            work = miningBlocks.get(0);
            miningBlocks.remove(0);
            return work;
        }

        System.out.println(workerName + " - there is no more work");
        return work;
    }

    /**
     * Getter for LORRY_COUNT
     * @return number of lorries needed to transport all resources
     */
    public int getLorryCount() {
        return LORRY_COUNT;
    }
}