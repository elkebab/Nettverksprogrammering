import java.util.ArrayList;


public class PrimTraa extends Thread {
    private ArrayList<Intervall> intervall;

    public PrimTraa(ArrayList<Intervall> ints) {
        intervall = ints;
    }

    public void run() {
        for (int i = 0; i < intervall.size(); i++) {
            Intervall detteIntervall = intervall.get(i);

            for (long j = detteIntervall.getFra(); j < detteIntervall.getTil(); j++) {
                boolean primtall = true;

                if (j % 2 == 0) {   //vet vi ikke har partall
                    primtall = false;
                }
                else {
                    for (long k = 3; k <= Math.sqrt(j); k+=2) {  //#noobway
                        Long rest = j % k;
                        if (rest == 0) {
                            primtall = false;
                            break;
                        }
                    }
                }
                if (j == 2) {
                    primtall = true;
                }
                if (j == 1) {
                    primtall = false;
                }

                if (primtall) {
                    synchronized (Test.liste) {
                        Test.leggTil(j);
                    }
                }
            }
        }
        System.out.println("TRÅD FERDIG!!");
    }
}
