import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Kjetil on 27.01.14.
 */
public class Test {
    public static ArrayList<Long> liste = new ArrayList<Long>();

    public static void leggTil(Long l) {
        liste.add(l);
    }

    public static void sorter() {
        Collections.sort(Test.liste);
    }

    public static void main(String[] args) {
        long antTraader = 2;
        long fra = 0;
        long til = 10000000;

        ArrayList<PrimTraa> traader = new ArrayList<PrimTraa>();

        for (int i = 1; i <= antTraader; i++) {
          ArrayList<Intervall> intervaller = new ArrayList<Intervall>();
            System.out.println("Tråd: "+i);

            long steg = ((til-fra)/antTraader)/2;
            System.out.println("\tfra "+fra+", til "+til+": steg = "+steg);

            for (long j = fra; j < til; j += antTraader*steg) {
                long dFra = j + (steg * (i-1));
                long dTil = dFra + steg;

                if (dFra > til) {
                    break;
                }

                if (dTil > til) {
                    dTil = til;
                    j = til;
                }

                System.out.println("\t\tFra: "+dFra+", til: "+dTil);

                intervaller.add(new Intervall(dFra, dTil));
            }

            PrimTraa t = new PrimTraa(intervaller);
            t.start();
            traader.add(t);
        }
        try {
            for (int i = 0; i < traader.size(); i++) {
                PrimTraa t = traader.get(i);
                t.join();
            }
        } catch (InterruptedException e) {
            System.out.println("ERROR");
        }
        Test.sorter();

        System.out.println(liste);
    }
}