// monitor
class Bariera {
    private int citac = 0;
    private double suma = 0;
    private int pocetVlaken;
    private boolean uspat = true;

    public Bariera(int pocetVlaken) {
        this.pocetVlaken = pocetVlaken;
    }

    synchronized public void synchronizujBezpecne(Vlakno v) {
        suma += v.getSoucet();

        while (uspat == false) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        citac++;

        if (citac == pocetVlaken) {
            System.out.println("Cislo pi po souctu " + v.getAktualniClen() * pocetVlaken + ". clenu: " + 4 * suma);

            suma = 0;
            uspat = false;

            notifyAll();
        }

        while (uspat == true) {
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

    synchronized public void synchronizuj(Vlakno v) {
        suma += v.getSoucet();
        citac++;

        if (citac == pocetVlaken) {
            System.out.println("Cislo pi po souctu " + v.getAktualniClen() * pocetVlaken + ". clenu: " + 4 * suma);

            suma = 0;
            citac = 0;

            notifyAll();
        }
        else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Vlakno extends Thread {
    private int cisloVlakna;
    private int pocetClenu;
    private double soucet;
    private int aktualniClen = 0;
    private int pocetVlaken;
    private Bariera bariera;
    private int synchronizace;

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

    public int getAktualniClen() {
        return this.aktualniClen;
    }

    public void run() {
        int index;

        soucet = Math.pow(-1, cisloVlakna) / (2 * cisloVlakna + 1);
        aktualniClen++;

        if (aktualniClen % synchronizace == 0) {
            bariera.synchronizujBezpecne(this);
        }

        while (aktualniClen < pocetClenu) {

            index = cisloVlakna + pocetVlaken * aktualniClen;
            soucet += Math.pow(-1, index) / (2 * cisloVlakna + 1 + 2 * pocetVlaken * aktualniClen);

            aktualniClen++;

            if (aktualniClen % synchronizace == 0) {
                bariera.synchronizujBezpecne(this);
            }
        }
        System.out.println("Vlakno " + cisloVlakna + " ma soucet: " + soucet);
    }
}

public class Vlakna {

    final static int POCET_CLENU = 1000000;
    final static int POCET_VLAKEN = 5;
    final static int SYNCHRONIZACE = 10;

    public static void main(String[] args) {

        int i;
        double suma = 0;

        Bariera bariera = new Bariera(POCET_VLAKEN);

        Vlakno[] v = new Vlakno[POCET_VLAKEN];

        for (i = 0; i < POCET_VLAKEN; i++) {
            v[i] = new Vlakno(i, POCET_CLENU, POCET_VLAKEN, bariera, SYNCHRONIZACE);
            v[i].start();
        }

        for (i = 0; i < POCET_VLAKEN; i++) {
            try {
                v[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            suma += v[i].getSoucet();
        }

        System.out.println("Vysledne cislo pi: " + 4 * suma);
    }
}