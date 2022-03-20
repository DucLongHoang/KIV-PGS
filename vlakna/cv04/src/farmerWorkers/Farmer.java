package farmerWorkers;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

public class Farmer extends Thread {
	private TreeSet<WordRecord> wordRecords;
	private BufferedReader in;
	private int jokeID = 1;
	private final static int WORKERS = 3;
	private Worker[] worker = new Worker[WORKERS];

	Farmer (String file) {

		System.out.println("Farmar - zacinam.");

		// nacteni vstupniho souboru
		System.out.println("Farmar - oteviram soubor...");

		try {
			FileReader fr = new FileReader(file);
			in = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// vytvoreni globalniho seznamu vysledku
		wordRecords = new TreeSet<WordRecord>(new WRComparer());
	}

	public void run() {

		int i;

		System.out.println("Farmar - Vytvarim delniky");
		for (i = 0; i < WORKERS; i++) {
			worker[i] = new Worker("Delnik" + i, this, (i + 1) * 100);
			worker[i].start();
			System.out.println("Farmar - " + worker[i].getJmeno());
		}

		System.out.println("Farmar - Cekam na ukonceni delniku");
		for (i = 0; i < WORKERS; i++) {
			try {
				worker[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Farmar - Zaviram soubor.");
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Farmar - Tisknu vysledek.");
		printResult();
	}

	// prideleni prace workerovi
	public synchronized String getJoke(String jmeno) {

		String jokeText = "";
		String line;

		System.out.println(jmeno + " - Zadam vtip.");

		try {
			while ((line = in.readLine()) != null) {
				if (line.trim().equals("%")) {
					System.out.println(jmeno + " - Vtip nacten (" + jokeID + ").");
					jokeID++;
					return jokeText.trim();
				}
				jokeText += line + "\n";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(jmeno +" - Neni co cist.");
		return "$$skonci$$";
	}

	// ukladani vysledku z jednoho vtipu do wordRecords
	public synchronized void reportResult(TreeSet<WordRecord> results, String jmeno) {
		System.out.println(jmeno + " - Pridavam vysledky.");

		Iterator<WordRecord> it = results.iterator();

		while (it.hasNext()) {
			WordRecord listItem = it.next();

			WordRecord wr = getWordRecord(listItem.word, wordRecords);

			if (wr == null)
				wordRecords.add(listItem);
			else {
				wordRecords.remove(wr);
				wr.frequency += listItem.frequency;
				wordRecords.add(wr);
			}
		}
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

	// tisk vysledneho seznamu
	public void printResult() {
		Iterator<WordRecord> it = wordRecords.iterator();

		System.out.println("Vysledek:");
		System.out.println("=========");

		while (it.hasNext()) {
			WordRecord listItem = it.next();
			System.out.println(listItem.word + " - " + listItem.frequency);
		}
	}
}