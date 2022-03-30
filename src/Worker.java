import java.util.Random;

/**
 * Worker class - worker that is mining resources.
 * After a block is mined, loads resources onto a Lorry. Once full, send it to the Ferry.
 * @author Long
 * @version 1.0
 */
public class Worker implements Runnable{
    private final String NAME;
    private final Mine MINE;
    private final Random R;
    private final int SPEED;
    private Lorry lorry;
    private int resources;

    /**
     * Constructor for Worker
     * @param name name of Worker
     * @param speed max speed of mining one resource
     * @param mine Mine the Worker works at
     */
    public Worker(String name, int speed, Mine mine) {
        this.R = new Random();
        this.NAME = name;
        this.SPEED = speed;
        this.MINE = mine;
        this.resources = 0;
    }
    /**
     *
     */
    @Override
    public void run() {
        int work, workingTime;
        while(( work = MINE.getBoss().getWork(this.NAME) ) != 0) {

            /* --- Start mining --- */
            System.out.println(NAME + " - mining " + work + " resources");
            for (int i = 0; i < work; i++, resources++) {
                try {
                    workingTime = R.nextInt(SPEED);
                    Thread.sleep(workingTime);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            /* --- End mining --- */


            /* --- Start filling Lorry --- */
            while(work > 0) {
                try {
                    if(lorry.fillLorry(NAME)) {
                        Thread.sleep(10);     // Loading one resource on Lorry takes 1s
                        work--;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            /* --- End filling Lorry --- */

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
     * Setter for Lorry
     * @param lorry Lorry the Worker will bring resources to
     */
    public void setLorry(Lorry lorry) {
        this.lorry = lorry;
    }
}