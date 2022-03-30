package main;

import logging.Logger;
import mineobjects.Lorry;
import mineobjects.LorryState;
import mineobjects.Mine;

/**
 * Main class
 * @author Long
 * @version 1.0
 */
public class Main {
    /**
     * Method runs the program
     * @param args arguments from command line
     */
    public static void main(String[] args) {
        System.out.println();
        System.out.println("\nMain - starting main thread.");

        Parser parser = new Parser(args);
        Logger logger = new Logger(parser.getOutputFile());
        Mine mine = new Mine(parser, logger);
        mine.executeSpecialMiningOperation();

        // fancy lambda + stream
        int total = mine.getLorryBoss().getLorries().
                stream().
                filter(lorry -> lorry.getLorryState() == LorryState.IN_DESTINATION).
                mapToInt(Lorry::getCurrentCap).
                sum();

        for(Lorry l : mine.getLorryBoss().getLorries()) {
            System.out.println(l.getName() + " has " + l.getCurrentCap());
        }

        System.out.println("Total resources transported is: " + total);
        logger.writeLogsToFile();
        System.out.println("Main - ending main thread");
    }
}
