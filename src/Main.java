public class Main {

    public static void main(String[] args) {
        Mine mine = new Mine(args);
        System.out.println("\nMain - running the Big Boss.");
        mine.startMiningProject();
        System.out.println("Main - ending main thread");
    }
}
