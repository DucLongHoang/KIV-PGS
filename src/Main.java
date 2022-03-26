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
        Mine mine = new Mine(args);
        System.out.println("\nMain - starting main thread.");
        mine.executeSpecialMiningOperation();

        int i = 0;
        for(Lorry l : mine.getLorryBoss().getLorries()) {
            if(l.getLorryState() == LorryState.IN_DESTINATION)
                i += l.getCurrentCap();
        }
        System.out.println("Total resources transported is: " + i);
        System.out.println("Main - ending main thread");
    }
}
