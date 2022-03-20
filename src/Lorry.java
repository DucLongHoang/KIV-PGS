/**
 * Lorry class
 */
public class Lorry implements Runnable{
    private final String NAME;
    private final int CAPACITY, TIME;
    private LorryState lorryState;
    private int currentCap;

    /**
     * Constructor for Lorry
     * @param capacity capacity of Lorry for resources
     * @param time max time it takes the Lorry to go from Mine to Ferry,
     *            and from Ferry to the destination
     */
    public Lorry(String name, int capacity, int time) {
        this.NAME = name;
        this.CAPACITY = capacity;
        this.TIME = time;
        this.currentCap = 0;
        this.lorryState = LorryState.NOT_FULL;
    }

    /**
     *
     */
    @Override
    public void run() {
        System.out.println(NAME + " - going to the Ferry");
        this.lorryState = LorryState.TO_FERRY;
    }

    /**
     *
     * @param workerName
     * @return
     */
    public synchronized boolean fillLorry(String workerName) {
//        System.out.println(workerName + " - filling lorry with resources");
        if(currentCap + 1 <= CAPACITY){
            currentCap++;
            System.out.println(workerName + " - adding one resource to " + NAME);
            return true;
        }

        System.out.println(workerName + " - " + NAME + " is full. Sending it to the Ferry");
        this.lorryState = LorryState.FULL;
        return false;
    }

    /**
     * Getter for lorryState
     * @return the state the Lorry is currently in
     */
    public synchronized LorryState getLorryState() {
        return lorryState;
    }
}