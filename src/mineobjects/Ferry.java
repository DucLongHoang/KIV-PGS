package mineobjects;

import logging.Log;
import logging.Logger;

/**
 * Ferry class - once Lorry count equals CAPACITY it will leave to drop them off
 * @author Long
 * @version 1.0
 */
public class Ferry {
    /** Reference to Logger to log all voyages */
    private final Logger LOGGER;
    /** Capacity for Lorries */
    private final int CAPACITY;
    /** A counter for Lorry and number of voyages */
    private int lorryCounter, voyageCounter;
    /** Boolean so that synchronization works as intended */
    private boolean wait;

    /**
     * Constructor for Ferry
     * @param capacity how many Lorries the Ferry can take
     */
    public Ferry(int capacity, Logger logger) {
        this.CAPACITY = capacity;
        this.LOGGER = logger;
        this.lorryCounter = 0;
        this.voyageCounter = 0;
        this.wait = true;
    }

    /**
     * Method makes Lorries wait until the Ferry is full
     * Code taken from a class
     */
    public synchronized void synchronize() {
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
            LOGGER.log(new Log(this.getClass().getSimpleName(), "Voyage start"));
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