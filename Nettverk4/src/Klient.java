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
            
            Konto k = new Konto();
            k.setKontonr("123");
            k.setSaldo(200);
            k.setNavn("Arne");
            k.setLaasingsFelt(0);
            fasade.lagreNyKonto(k);//lagrer boka

            //lager en ny bok med konstruktor i stedet for setMetodene
            k = new Konto("321", 200, "Franz", 0);//tar alle parametre Id som lages automatisk
            fasade.lagreNyKonto(k);

            //Skriv ut bÃ¸kene som er lagret
            System.out.println("FÃ¸lgende kontoer er lagret i DB:");
            liste = fasade.getAlleKontoer();
            for (Konto b : liste) {
                System.out.println(b);
            }
            
            k = (Konto)liste.get(0);
            k.setNavn("Sivertz");
            fasade.endreNavn(k);
  
            System.out.println("\nFÃ¸lgende kontoer er lagret i DB:");
            liste = fasade.getAlleKontoer();
            for (Konto b : liste) {
                System.out.println(b);
            }
            
            fasade.overfør((Konto)liste.get(0),(Konto)liste.get(1),150);
           
            System.out.println("\nFÃ¸lgende kontoer er lagret i DB:");
            liste = fasade.getAlleKontoer();
            for (Konto b : liste) {
                System.out.println(b);
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
