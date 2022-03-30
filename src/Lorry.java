import java.util.Random;

/**
 * Lorry class - Lorry is being filled with resources by Workers.
 * Once full they are sent to they are sent to the Ferry and do another journey to the final destination.
 * A Lorry is always in one LorryStates
 * @author Long
 * @version 1.0
 */
public class Lorry implements Runnable{
    /** Mine the Lorry is in */
    private final Mine MINE;
    /** Ferry where the Lorry will go to */
    private final Ferry FERRY;
    /** Reference to a Random generator */
    private final Random R;
    /** Name of the Lorry */
    private final String NAME;
    /** Capacity of the Lorry and max time of one journey */
    private final int CAPACITY, TIME;
    /** State of the Lorry */
    private LorryState lorryState;
    /** Current capacity of Lorry. Never higher than CAPACITY */
    private int currentCap;

    /**
     * Constructor for Lorry
     * @param capacity capacity of Lorry for resources
     * @param time max time it takes the Lorry to go from Mine to Ferry,
     * @param ferry Ferry where the Lorry will go once loaded or if last
     */
    public Lorry(String name, int capacity, int time, Ferry ferry, Mine mine) {
        this.R = new Random();
        this.NAME = name;
        this.CAPACITY = capacity;
        this.TIME = time;
        this.FERRY = ferry;
        this.MINE = mine;
        this.currentCap = 0;
        this.lorryState = LorryState.NOT_FULL;
    }

    /**
     * Method is called once the Lorry is fully loaded with resources.
     * Once woken up, the Lorry goes to the Ferry where it waits for other Lorries.
     * When the capacity of the Ferry is full, it will transport all Lorries.
     * Finally the Lorry makes another journey to the final destination.
     */
    @Override
    public void run() {
        int drivingTime;
        try {
            lorryState = LorryState.TO_FERRY;
            System.out.println(NAME + " - going to the Ferry");
            drivingTime = R.nextInt(TIME);
            Thread.sleep(drivingTime);

            lorryState = LorryState.ON_FERRY;
            System.out.println(NAME + " - on Ferry, waiting for other Lorries");
            FERRY.synchronize();

            lorryState = LorryState.TO_DESTINATION;
            System.out.println(NAME + " - going to the destination");
            drivingTime = R.nextInt(TIME);
            Thread.sleep(drivingTime);

            lorryState = LorryState.IN_DESTINATION;
            System.out.println(NAME + " - arrived in destination");
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Critical section of the app!
     * Method tries to fill the Lorry.
     * @param workerName Worker that fills the Lorry
     * @return true if filling successful otherwise false
     */
    public synchronized boolean fillLorry(String workerName) {
        // preventing a weird case when the Worker tries to fill a Lorry that is already gone!
        if(lorryState != LorryState.NOT_FULL) return false;

        System.out.println(workerName + " - adding one resource to " + NAME);
        currentCap++;

        if(currentCap == CAPACITY) {
            this.lorryState = LorryState.FULL;
            System.out.println(workerName + " - " + NAME + " is full. Sending it to the Ferry");
            this.MINE.getLorryBoss().changeLorryForWorkers();
        }
        return true;
    }

    /**
     * Critical section of the app!
     * Getter for lorryState
     * @return the state the Lorry is currently in
     */
    public synchronized LorryState getLorryState() {
        return lorryState;
    }

    /**
     * Critical section of the app!
     * Setter for lorryState
     * @param lorryState LorryState to be set to
     */
    public synchronized void setLorryState(LorryState lorryState) {
        this.lorryState = lorryState;
    }

    /**
     * Critical section of the app!
     * Getter for currentCap
     * @return total amount of resources on Lorry
     */
    public synchronized int getCurrentCap() {
        return currentCap;
    }

    /**
     * Geter for NAME
     * @return name of Lorry
     */
    public String getName() {
        return NAME;
    }
}