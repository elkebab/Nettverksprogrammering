/*
 * TellerKlient.java   E.L. 2004-10-11
 *
 * Dette er hovedprogrammet p� klientsiden.
 * Kontakt med tjeneren opprettes og tilbakekallsobjekt (klientobjekt) lages.
 */

import static javax.swing.JOptionPane.*;
import java.rmi.Naming;
class ChatKlient {
  public static void main(String[] args) throws Exception {
    String objektnavn = "åses_tellebyrå_AS";
    String maskinnavn = "localhost";
    String url = "rmi://" + maskinnavn + "/" + objektnavn;

    /* Henter et stubb-objekt som vi kan bruke til � kommunisere med tjeneren */
    Chat tellemaskin = (Chat) Naming.lookup(url);

    String klientnavn = showInputDialog(null, "Oppgi klientnavn:");
    System.out.println("Her er konsollvinduet til " + klientnavn);

    /* Lager objekt som kan motta meldinger fra tjenersiden */
    Klient denneKlienten = new KlientImpl(klientnavn);

    /* Vi f�r vinduet p� plass. */
    Vindu vindu = new Vindu(tellemaskin, denneKlienten);
    vindu.setVisible(true);

    /*
     * Statusmeldinger til klienten skal skrives ut i vinduet.
     * Klienten m� derfor vite om vinduet.
     */
    denneKlienten.settVindu(vindu);

    /* Vi registrerer oss hos tjeneren. */
    tellemaskin.registrerMeg(denneKlienten);
  }
}