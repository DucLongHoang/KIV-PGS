package mineobjects;

import logging.Logger;
import main.Parser;

/**
 * Mine class - holds references for Boss and LorryBoss objects
 * @author Long
 * @version 1.0
 */
public class Mine {
    /** Boss of the Mine*/
    private final Boss BOSS;
    /** LorryBoss of the Mine */
    private final LorryBoss LORRYBOSS;
    /** Parser reference */
    private final Parser PARSER;
    /** Logger of the whole app */
    private final Logger LOGGER;

    /**
     * Constructor for Mine
     * @param parser Parser reference to give to the Boss
     * @param logger Logger reference so all Objects can log
     */
    public Mine(Parser parser, Logger logger) {
        this.PARSER = parser;
        this.LOGGER = logger;
        this.BOSS = new Boss(PARSER, this);
        this.LORRYBOSS = new LorryBoss(this);
    }

    /**
     * Method makes Boss plan out and execute the mining project
     */
    public void executeSpecialMiningOperation() {
        BOSS.planOutWork();
        LORRYBOSS.wrapUpWork();
    }

    /**
     * Getter for BOSS
     * @return Boss of the Mine
     */
    public Boss getBoss() {
        return BOSS;
    }

    /**
     * Getter for LORRYBOSS
     * @return LorryBoss of the Mine
     */
    public LorryBoss getLorryBoss() {
        return LORRYBOSS;
    }

    /**
     * Getter for LOGGER
     * @return Logger reference
     */
    public Logger getLogger() {
        return LOGGER;
    }
}