public class Main {

    private final static String INPUT_FILE = "ref_input.txt";

    public static void main(String[] args) {

        System.out.println("Main - running the Big Boss.");
        Parser parser = new Parser(INPUT_FILE);
        Boss boss = new Boss();

        boss.planOutWork();

        System.out.println("Main - Fin.");
    }

}
