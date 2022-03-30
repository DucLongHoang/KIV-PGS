package mineobjects;

import logging.Log;
import main.Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Boss class - delegates mining work to Worker class
 * @author Long
 * @version 1.0
 */
public class Boss {
    /** Variables from parsed from command line arguments */
    private final int WORKER_COUNT, WORKER_TIME,
            LORRY_CAP, LORRY_TIME, FERRY_CAP;
    /** Array of Threads. One for each Worker */
    private final Thread[] W_THREADS;
    /** Workers that are Mining */
    private final Worker[] WORKERS;
    /** Mine the Boss is in */
    private final Mine MINE;
    /** List of all mining blocks. Integer is amount of resources */
    private List<Integer> miningBlocks;
    /** Space for regex splitting */
    private final String SPACE = " ";

    /**
     * Constructor for Boss
     * @param parser main.Parser object to get data from
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

        this.MINE = mine;

        printMineAnalysis();
    }

    /**
     * Method for the Boss to read from the input file
     * @param br BufferedReader that reads from file
     * @return List of individual mining block sizes
     * @throws IOException exception if file not found
     */
    private List<Integer> readMap(BufferedReader br) throws IOException {
        List<Integer> result = new LinkedList<>();
        String[] tokens;
        String line;

        while( ( line = br.readLine() ) != null){
            tokens = line.split(SPACE);
            for(String token: tokens) result.add(token.length());
        }
        result.removeIf(e -> e == 0);

        try {
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

        MINE.getLogger().log(new Log(this.getClass().getSimpleName(),
                "Blocks: " + numOfBlocks + ", resources: " + numOfResources));

        System.out.println("Mine analysis:");
        System.out.println("\tTotal number of blocks to be mined: " + numOfBlocks);
        System.out.println("\tTotal number of resources to be mined: " + numOfResources);
    }

    /**
     * Method initializes Workers and makes them work. Also makes them stop working.
     */
    public void planOutWork() {
        System.out.println("Boss - making Workers");
        Lorry tmp = MINE.getLorryBoss().getLorry();

        for(int i = 0; i < WORKER_COUNT; i++) {
            WORKERS[i] = new Worker("Worker" + i, WORKER_TIME, MINE);
            WORKERS[i].setLorry(tmp);

            W_THREADS[i] = new Thread(WORKERS[i]);
            System.out.println("Boss - making " + WORKERS[i].getName());
            W_THREADS[i].start();
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
    }

    /**
     * Critical section of app!
     * Method delegates work to a Worker. Gives him number of resources to mine.
     * Returns 0 if nothing is left be mined.
     * @param workerName name of Worker that is asking for work
     * @return number of resources in a block to be mined.
     */
    public synchronized int getWork(String workerName) {
        System.out.println(workerName + " - asking for work");
        int work = 0;

        if(!miningBlocks.isEmpty()) {
            work = miningBlocks.get(0);
            miningBlocks.remove(0);
            return work;
        }

        System.out.println(workerName + " - there is no more work");
        return work;
    }

    /**
     * Getter for LORRY_CAP
     * @return max capacity of each and every Lorry
     */
    public int getLorryCap() {
        return LORRY_CAP;
    }

    /**
     * Getter for LORRY_TIME
     * @return max time it can take a Lorry to transport
     */
    public int getLorryTime() {
        return LORRY_TIME;
    }

    /**
     * Getter for FERRY_CAP
     * @return max Lorry capacity of a Ferry
     */
    public int getFerryCap() {
        return FERRY_CAP;
    }

    /**
     * Getter for WORKERS
     * @return array of all Workers
     */
    public Worker[] getWorkers() {
        return WORKERS;
    }
}