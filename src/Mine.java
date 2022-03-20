import java.util.ArrayList;
import java.util.List;

/**
 * Mine class
 */
public class Mine {
    private final Boss BOSS;
    private List<Thread> L_THREADS;
    private List<Lorry> LORRIES;

    /**
     *
     * @param args
     */
    public Mine(String[] args) {
        this.BOSS = new Boss(new Parser(args), this);
        this.L_THREADS = new ArrayList<>(BOSS.getLorryCount());
        this.LORRIES = new ArrayList<>(BOSS.getLorryCount());

        for(int i = 0; i < BOSS.getLorryCount(); i++) {
            LORRIES.add(new Lorry("Lorry" + i, BOSS.getLorryCap(), BOSS.getLorryTime()));
            L_THREADS.add(new Thread(LORRIES.get(i)));
        }
    }

    /**
     *
     */
    public void startMiningProject() {
        BOSS.planOutWork();
    }

    /**
     *
     * @param workerName
     * @return
     */
    public synchronized Lorry getLorry(String workerName) {
        for(Lorry lorry: LORRIES) {
            if (lorry.getLorryState() == LorryState.NOT_FULL) {
                return lorry;
            }
        }

        return null;
    }

    public synchronized void startLorry(String workerName) {

    }

}