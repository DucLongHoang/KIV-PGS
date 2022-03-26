/**
 * Mine class - holds references for Boss and LorryBoss objects
 * @author Long
 * @version 1.0
 */
public class Mine {
    private final Boss BOSS;
    private final LorryBoss LORRYBOSS;

    /**
     * Constructor for Mine
     * @param args arguments from command line
     */
    public Mine(String[] args) {
        this.BOSS = new Boss(new Parser(args), this);
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
}