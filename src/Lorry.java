/**
 *
 */
public class Lorry implements Runnable{
    private final int CAPACITY, TIME;
    private int currentCap;

    /**
     * Constructor for Lorry
     * @param capacity capacity of Lorry for resources
     * @param time max time it takes the Lorry to go from Mine to Ferry,
     *            and from Ferry to the destination
     */
    public Lorry(int capacity, int time) {
        this.CAPACITY = capacity;
        this.TIME = time;
        this.currentCap = 0;
    }

    @Override
    public void run() {

    }

    /**
     *
     * @param workerName
     * @return
     */
    public synchronized boolean fillLorry(String workerName) {
        System.out.println(workerName + " - filling lorry with resources");
        if(currentCap + 1 < CAPACITY){
            currentCap++;
            System.out.println(workerName + " - adding one resource to Lorry");
            return true;
        }

        System.out.println(workerName + " - Lorry is full. Sending it to the Ferry");
        return false;
    }
}