/**
 * LorryBoss class - coordinates all Lorries and gives them to Workers ad-hoc
 * @author Long
 * @version 1.0
 */
public class LorryBoss {
    private final Mine MINE;
    private final Ferry FERRY;
    private final int LORRY_COUNT;
    private final Thread[] L_THREADS;
    private final Lorry[] LORRIES;

    /**
     * Constructor for LorryBoss
     * @param mine Mine where LorryBoss works in
     */
    public LorryBoss(Mine mine) {
        Boss boss = mine.getBoss();
        this.MINE = mine;
        this.FERRY = new Ferry(boss.getFerryCap());
        this.LORRY_COUNT = boss.getLorryCount() + 1;
        this.L_THREADS = new Thread[LORRY_COUNT];
        this.LORRIES = new Lorry[LORRY_COUNT];

        for(int i = 0; i < LORRY_COUNT; i++) {
            LORRIES[i] = (new Lorry("Lorry" + i, boss.getLorryCap(),
                    boss.getLorryTime(), FERRY, MINE));
            L_THREADS[i] = (new Thread(LORRIES[i]));
        }
    }

    /**
     * Critical section of app!
     * Method returns a Lorry to the worker that is currently in LorryState.NOT_FULL
     * @return Lorry to be filled with resources
     */
    public synchronized Lorry getLorry() {
        for(Lorry l : LORRIES) {
            if(l.getLorryState() == LorryState.NOT_FULL) return l;
        }
        System.out.println("LorryBoss - no more Lorries to give out");
        return null;
    }

    /**
     * Critical section of app!
     * Method starts the Lorry and sends it to the Ferry
     */
    public synchronized void startLorry() {
        for(int i = 0; i < LORRY_COUNT; i++) {
            if(LORRIES[i].getLorryState() == LorryState.FULL) {
                LORRIES[i].setLorryState(LorryState.TO_FERRY);
                L_THREADS[i].start();
                return;
            }
        }
    }

    /**
     * Critical section of app!
     * Method changes Lorry references of all Workers
     */
    public synchronized void changeLorryForWorkers() {
        this.startLorry();
        System.out.println("LorryBoss - changing Lorry for all Workers");
        for(Worker w: MINE.getBoss().getWorkers()) {
            w.setLorry(this.getLorry());
        }
    }

    /**
     * Method tells the Lorry Threads to stop
     */
    public void wrapUpWork() {
        System.out.println("LorryBoss - waiting for Lorries to end");
        for (int i = 0; i < LORRY_COUNT; i++) {
            try {
                L_THREADS[i].join();
                System.out.println("\t" + LORRIES[i].getName() + " - ending job");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Getter for LORRIES
     * @return array of Lorries
     */
    public Lorry[] getLorries() {
        return LORRIES;
    }
}
