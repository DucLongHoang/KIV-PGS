package mineobjects;

import logging.Log;

import java.util.Random;

/**
 * Worker class - worker that is mining resources.
 * After a block is mined, loads resources onto a Lorry. Once full, send it to the Ferry.
 * @author Long
 * @version 1.0
 */
public class Worker implements Runnable{
    /** Name of Worker */
    private final String NAME;
    /** Mine the Worker is in */
    private final Mine MINE;
    /** Reference to a Random generator */
    private final Random R;
    /** MAx speed of mining one resource */
    private final int SPEED;
    /** Reference to Lorry */
    private Lorry lorry;
    /** Total number of resources mined */
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
     * Method makes the Worker ask for work and once the resources are mined,
     * he fills the Lorry with them. Worker works as long as he has work from his Boss
     */
    @Override
    public void run() {
        int work, workingTime, blockTime;
        while(( work = MINE.getBoss().getWork(this.NAME) ) != 0) {

            /* --- Start mining --- */
            blockTime = 0;
//            System.out.println(NAME + " - mining " + work + " resources");

            for (int i = 0; i < work; i++, resources++) {
                try {
                    workingTime = R.nextInt(SPEED);
                    blockTime += workingTime;
                    Thread.sleep(workingTime);

                    MINE.getLogger().log(new Log(this.getClass().getSimpleName(),
                            "Resource," + workingTime));
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            MINE.getLogger().log(new Log(this.getClass().getSimpleName(),
                    "Block," + blockTime));
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
//        System.out.println(NAME + " - finishing. Total resources mined: " + resources);
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

    /**
     * Getter for resources
     * @return total resources mined
     */
    public int getResources() {
        return resources;
    }
}