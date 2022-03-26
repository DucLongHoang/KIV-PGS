import java.util.Random;

/**
 * Lorry class - Lorry is being filled with resources by Workers.
 * Once full they are sent to they are sent to the Ferry and do another journey to the final destination.
 * A Lorry is always in one LorryStates
 * @author Long
 * @version 1.0
 */
public class Lorry implements Runnable{
    private final Mine MINE;
    private final Ferry FERRY;
    private final Random R;
    private final String NAME;
    private final int CAPACITY, TIME;
    private LorryState lorryState;
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
     *
     */
    @Override
    public void run() {
        try {
            System.out.println(NAME + " - going to the Ferry");
            int drivingTime = R.nextInt(TIME);
            Thread.sleep(drivingTime);
            FERRY.synchronize(this);

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
     * Method fills the Lorry if possible
     * @param workerName Worker that tries to fill the Lorry
     * @return true if Lorry can be filled otherwise false
     */
    public synchronized boolean fillLorry(String workerName) {
        if(lorryState != LorryState.NOT_FULL) return false;
        if(currentCap++ < CAPACITY){
            System.out.println(workerName + " - adding one resource to " + NAME);
            if(currentCap == CAPACITY) {
                System.out.println(workerName + " - " + NAME + " is full. Sending it to the Ferry");
                this.lorryState = LorryState.FULL;
                this.MINE.getLorryBoss().changeLorryForWorkers();
                return false;
            }
            return true;
        }
        return false;
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
     * Setter for lorryState
     * @param lorryState LorryState to be set to
     */
    public synchronized void setLorryState(LorryState lorryState) {
        this.lorryState = lorryState;
    }

    /**
     * Getter for currentCap
     * @return total amount of resources on Lorry
     */
    public int getCurrentCap() {
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