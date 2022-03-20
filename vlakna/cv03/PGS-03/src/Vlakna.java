class Vlakno extends Thread {
	private final int cisloVlakna;
	private final int pocetClenu;
	private double soucet;
	private int aktualniClen = 0;	
	private final int pocetVlaken;
	private final Bariera bariera;
	private final int synchronizace;
	
	Vlakno(int cisloVlakna, int pocetClenu, int pocetVlaken, Bariera bariera, int synchronizace) {
		this.cisloVlakna = cisloVlakna;
		this.pocetClenu = pocetClenu;
		this.pocetVlaken = pocetVlaken;
		this.bariera = bariera;
		this.synchronizace = synchronizace;
	}

	public double getSoucet() {
		return this.soucet;
	}

	@Override
	public void run() {
		int index;
				
		soucet = Math.pow(-1, cisloVlakna) / (2 * cisloVlakna + 1);
		aktualniClen++;
		
		while (aktualniClen < pocetClenu) {
			
			index = cisloVlakna + pocetVlaken * aktualniClen;
			soucet += Math.pow(-1, index) / (2 * cisloVlakna + 1 + 2 * pocetVlaken * aktualniClen); //každej sudej člen je kladnej ... Každej lichej je zapornej
			bariera.synchronizuj2(this);
			aktualniClen++;

			if (aktualniClen%synchronizace == 0) {
				bariera.synchronizuj2(this);
			}

		}
		System.out.println("Vlakno " + cisloVlakna + " ma soucet: " + soucet);
	}

	public int getAktualniClen() {
		return this.aktualniClen;
	}
}

class Bariera {
	private int citac = 0;
	private double suma = 0;
	private int pocetVlaken = 0;

	private boolean uspat = true;

	Bariera(int pocetVlaken) {
		this.pocetVlaken = pocetVlaken;
	}

	public synchronized void synchronizuj(Vlakno v) {
		suma += v.getSoucet();
		citac++;

		if (citac == pocetVlaken) {
			System.out.println("Cislo po souctu: " + v.getAktualniClen() * pocetVlaken + ". clenu: " + 4 * suma);

			citac = 0;
			suma = 0;

			notifyAll();
		} else {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void synchronizuj2(Vlakno v) {
		suma += v.getSoucet();

		while (!uspat) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		citac++;

		if (citac == pocetVlaken) {
			System.out.println("Cislo po souctu: " + v.getAktualniClen() * pocetVlaken + ". clenu: " + 4 * suma);

			suma = 0;
			uspat = false;

			notifyAll();
		}
		while (uspat) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		citac--;

		if (citac == 0) {
			uspat = true;
			notifyAll();
			}
		}
}

public class Vlakna {
	
	final static int POCET_CLENU = 1000000;
	final static int POCET_VLAKEN = 5;
	final static int SYNCHRONIZACE = 10000;

	public static void main(String[] args) {
		int i;
		double suma = 0;
		Vlakno[] v = new Vlakno[POCET_VLAKEN];
		Bariera bariera = new Bariera(POCET_VLAKEN);
		
		for (i = 0; i < POCET_VLAKEN; i++) {
			v[i] = new Vlakno(i, POCET_CLENU, POCET_VLAKEN, bariera, SYNCHRONIZACE);
			v[i].start();
		}

		for (i = 0; i < POCET_VLAKEN; i++) {
			try {
				v[i].join();
			}	catch (InterruptedException e) {
				e.printStackTrace();
			}
			suma += v[i].getSoucet();
		}

		System.out.println("Vysledne cislo pi: " + 4 * suma);
	}
}
