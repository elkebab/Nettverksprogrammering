import java.rmi.Naming;
import java.util.List;

/**
 * Created by Kjetil on 10.03.14.
 */
public class Klient {
    public static void main(String[] args) {
        try {
            String url = "rmi://localhost/kontoFasade";
            
            KontoFasade fasade = (KontoFasade) Naming.lookup(url);
            List<Konto> liste;
            //lager en bok med setMetodene i bok
            
            Konto k = new Konto("123",200,"Arne",0);
            fasade.lagreNyKonto(k);//lagrer boka

            //lager en ny bok med konstruktor i stedet for setMetodene
            k = new Konto("321", 200, "Franz", 0);//tar alle parametre Id som lages automatisk
            fasade.lagreNyKonto(k);

            k = new Konto("456", 10, "Lars", 0);//tar alle parametre Id som lages automatisk
            fasade.lagreNyKonto(k);
            
            double saldoGrense = 15;
            //Skriv ut bÃ¸kene som er lagret
            System.out.println("FÃ¸lgende kontoer er lagret med mer enn kr "+saldoGrense);
            liste = fasade.getAlleKontoer(saldoGrense);
            for (Konto b : liste) {
                System.out.println(b);
            }
            
            k = (Konto)liste.get(0);
            k.setNavn("Sivert");
            fasade.endreNavn(k);
  
            System.out.println("\nFÃ¸lgende kontoer er lagret i DB:");
            liste = fasade.getAlleKontoer(0);
            for (Konto b : liste) {
                System.out.println(b);
            }
            
            fasade.overfør((Konto)liste.get(0),(Konto)liste.get(1),300);
            
            fasade.overfør((Konto)liste.get(0),(Konto)liste.get(1),150);
           
            System.out.println("\nFÃ¸lgende kontoer er lagret i DB:");
            liste = fasade.getAlleKontoer(0);
            for (Konto b : liste) {
                System.out.println(b);
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
