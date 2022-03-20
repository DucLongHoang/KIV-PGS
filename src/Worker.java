public class Worker implements Runnable{

    private final String NAME;
    private final Boss BOSS;
    private final int SPEED;

    public Worker(String name, Boss boss, int speed) {
        this.NAME = name;
        this.BOSS = boss;
        this.SPEED = speed;
    }

    @Override
    public void run() {

    }

    public String getName() {
        return NAME;
    }

    public Boss getBoss() {
        return BOSS;
    }
}
