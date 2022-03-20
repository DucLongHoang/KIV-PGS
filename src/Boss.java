import java.util.List;
import java.util.Random;

public class Boss {
    private static int WORKERS = 3;
    private Thread[] workers = new Thread[WORKERS];
    private List<Integer> blocks;

    public Boss() {

    }


    public void planOutWork() {
        System.out.println("Making Workers");
        Worker tmpWorker;

        for(int i = 0; i < WORKERS; i++) {
            tmpWorker = new Worker("Worker" + i,
                    this, new Random().nextInt(5));
            workers[i] = new Thread(tmpWorker);
            workers[i].start();
            System.out.println("Boss - " + workers[i].getName());
        }

        System.out.println("Waiting for Workers to end");
        for (int i = 0; i < WORKERS; i++) {
            try {
                workers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Farmar - Tisknu vysledek.");

    }


    public synchronized int getWork(String workerName) {
        int work;
        if(!blocks.isEmpty()) {
            work = blocks.get(0);
            blocks.remove(0);
            return work;
        }
        System.out.println(workerName + " - Nothing to do, Dobby is free!!!");

        return 0;
    }



}
