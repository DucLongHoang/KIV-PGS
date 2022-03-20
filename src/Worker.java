import java.util.Random;

/**
 * Worker class - worker that is mining resources.
 * After a block is mined, loads resources onto a Lorry. Once full, send it to the Ferry.
 */
public class Worker implements Runnable{
    private final Random R;
    private final String NAME;
    private final Boss BOSS;
    private final int SPEED;

    /**
     * Constructor for Worker
     * @param name name of Worker
     * @param boss Boss of Worker
     * @param speed max speed of mining one resource
     */
    public Worker(String name, Boss boss, int speed) {
        this.R = new Random();
        this.NAME = name;
        this.BOSS = boss;
        this.SPEED = speed;
    }

    /**
     *
     */
    @Override
    public void run() {
        int work, resources = 0;

        System.out.println(NAME + " - starting to work");
        while(( work = BOSS.getWork(this.NAME) ) != 0) {
            for (int i = 0; i < work; i++) {
                try {
                    int workingTime = R.nextInt(SPEED);
                    Thread.sleep(workingTime);
//                    System.out.println("\t" + NAME + " - mining time: " + workingTime);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                resources++;
            }
        }
        System.out.println(NAME + " - finishing. Total resources mined: " + resources);
    }

    /**
     * Getter for NAME
     * @return name of Worker
     */
    public String getName() {
        return NAME;
    }

    /**
     * Getter for BOSS
     * @return Boss of this Worker
     */
    public Boss getBoss() {
        return BOSS;
    }
}