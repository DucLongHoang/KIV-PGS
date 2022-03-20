import java.util.LinkedList;
import java.util.List;

public class Mine {
    private final Boss BOSS;
    private List<Thread> L_THREADS;
    private List<Lorry> LORRIES;

    public Mine(String[] args) {
        BOSS = new Boss(new Parser(args), this);
        this.L_THREADS = new LinkedList<>();
        this.LORRIES = new LinkedList<>();
    }

    public void startMiningProject() {
        BOSS.planOutWork();
    }

    public synchronized Lorry getLorry(String workerName) {
        
        return null;
    }

}
