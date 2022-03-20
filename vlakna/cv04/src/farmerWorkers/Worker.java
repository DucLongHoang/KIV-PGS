package farmerWorkers;

import java.util.Iterator;
import java.util.TreeSet;

public class Worker extends Thread {
    private String jmeno;
    private Farmer farmer;
    private int speed;

    public Worker(String name, Farmer farmer, int speed) {
        this.jmeno = name;
        this.farmer = farmer;
        this.speed = speed;
    }

    public String getJmeno() {
        return jmeno;
    }

    public Farmer getFarmer() {
        return farmer;
    }

    public void run() {

        int i;

        int jobCount = 0;
        String joke;

        TreeSet<WordRecord> results;

        System.out.println(jmeno + " - Zacinam makat.");

        while (!(joke = farmer.getJoke(jmeno)).equals("$$skonci$$")) {
            System.out.println("Dostal jsem praci.");

            results = new TreeSet<>(new WRComparer());

            String[] words = joke.split("[^A-Za-z0-9]+");

            long prevTime = System.currentTimeMillis();

            for (i = 0; i < words.length; i++) {

                long currTime = System.currentTimeMillis();
                while(currTime-prevTime < speed){
                    currTime = System.currentTimeMillis();
                }

                // prevedeni slova na mala pismena
                words[i] = words[i].toLowerCase();

                // zpracovani slova
                WordRecord wr = getWordRecord(words[i], results);

                if (wr == null) { // nove slovo ve vtipu
                    wr = new WordRecord();
                    wr.word = words[i];
                    wr.frequency = 1;
                }
                else
                { // slovo se jiz ve vtipu vyskytlo
                    results.remove(wr);
                    wr.frequency++;
                }
                results.add(wr);

                System.out.println(jmeno + " - Zpracovano slovo: " + " / " + wr.word + " /");

                prevTime = currTime;
            }
            // ulozeni vysledku do globalniho seznamu u farmera
            farmer.reportResult(results, jmeno);
            jobCount++;
        }
        System.out.println(jmeno + " - Koncim, zpracoval jsem " + jobCount + " vtipu.");
    }

    // ziskani zaznamu slova z results
    public WordRecord getWordRecord (String item, TreeSet<WordRecord> results) {
        Iterator<WordRecord> it = results.iterator();

        while (it.hasNext()) {
            WordRecord listItem = it.next();

            if (listItem.word.equals(item))
                return listItem;
        }
        return null;
    }
}
