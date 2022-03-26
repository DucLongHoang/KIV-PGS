/**
 * Ferry class - once Lorry count equals CAPACITY it will leave to drop them off
 * @author Long
 * @version 1.0
 */
public class Ferry {
    private final int CAPACITY;
    private int lorryCounter, voyageCounter;
    private boolean wait;

    /**
     * Constructor for Ferry
     * @param capacity how many Lorries the Ferry can take
     */
    public Ferry(int capacity) {
        this.CAPACITY = capacity;
        this.lorryCounter = 0;
        this.voyageCounter = 0;
        this.wait = true;
    }

    public synchronized void synchronize(Lorry lorry) {
        lorry.setLorryState(LorryState.ON_FERRY);
        System.out.println(lorry.getName() + " - on Ferry");

        while(!wait) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lorryCounter++;

        if(lorryCounter == CAPACITY) {
            System.out.println("Ferry - starting voyage " + voyageCounter++);
            wait = false;
            notifyAll();
        }

        while(wait) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lorryCounter--;

        if(lorryCounter == 0) {
            wait = true;
            notifyAll();
        }
    }
}