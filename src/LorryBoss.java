import java.util.ArrayList;
import java.util.List;

/**
 * LorryBoss class - coordinates all Lorries and gives them to Workers ad-hoc
 * @author Long
 * @version 1.0
 */
public class LorryBoss {
    private final Mine MINE;
    private final Ferry FERRY;
    private final List<Thread> L_THREADS;
    private final List<Lorry> LORRIES;
    private final int LORRY_CAP, LORRY_TIME;
    private int lorryCounter;

    /**
     * Constructor for LorryBoss
     * @param mine Mine where LorryBoss works in
     */
    public LorryBoss(Mine mine) {
        Boss boss = mine.getBoss();
        this.MINE = mine;
        this.FERRY = new Ferry(boss.getFerryCap());
        this.L_THREADS = new ArrayList<>();
        this.LORRIES = new ArrayList<>();

        this.LORRY_CAP = boss.getLorryCap();
        this.LORRY_TIME = boss.getLorryTime();
        this.lorryCounter = 0;

        this.prepareNewLorry();
    }

    /**
     * Critical section of app!
     * Method returns a Lorry to the worker that is currently in LorryState.NOT_FULL
     * @return Lorry to be filled with resources
     */
    public synchronized Lorry getLorry() {
        return LORRIES.get(lorryCounter);
    }

    /**
     * Critical section of app!
     * Method starts the Lorry and sends it to the Ferry
     */
    private synchronized void startLorry(boolean prepareNewLorry) {
        L_THREADS.get(lorryCounter).start();
        lorryCounter++;

        if(prepareNewLorry) prepareNewLorry();
    }

    /**
     * Critical section of app!
     * Method changes Lorry references of all Workers
     */
    public synchronized void changeLorryForWorkers() {
        this.startLorry(true);
        System.out.println("LorryBoss - getting new Lorry" + lorryCounter + " for all Workers");
        for(Worker w: MINE.getBoss().getWorkers()) {
            w.setLorry(this.getLorry());
        }
    }

    /**
     * Method tells the Lorry Threads to stop
     */
    public void wrapUpWork() {
        System.out.println("LorryBoss - waiting for Lorries to end");
        this.startLorry(false);
        for (int i = 0; i < lorryCounter; i++) {
            try {
                L_THREADS.get(i).join();
                System.out.println("\t" + LORRIES.get(i).getName() + " - ending job");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method creates a new Lorry object to be available
     */
    private synchronized void prepareNewLorry() {
        Lorry tmp = new Lorry("Lorry" + lorryCounter, LORRY_CAP, LORRY_TIME, FERRY, MINE);
        LORRIES.add(tmp);
        L_THREADS.add(new Thread(tmp));
    }

    /**
     * Getter for LORRIES
     * @return array of Lorries
     */
    public List<Lorry> getLorries() {
        return LORRIES;
    }
}