/*
 * TellemaskinFrontImpl.java   E.L. 2004-10-11
 *
 * Dette er implementasjonen av "fronten" mot tellemaskinen p� tjenersiden.
 * Klientene kommuniserer med et objekt av denne klassen. Dette objektet
 * sender meldinger videre til et objekt av klassen JaNeiTeller fra delkapittel 4.5
 * Klassen JaNeiTeller er ikke kjent for klientene.
 */

import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;

class ChatImpl extends UnicastRemoteObject implements Chat {
  /*
   * Et objekt av klassen best�r av et objekt av klassen JaNeiTeller og et objekt av
   * klassen ArrayList. Disse objektene brukes bare internt i denne klassen.
   * Det er derfor ikke n�dvendig � beskytte dem vha. synchronized. De er
   * beskyttet p� grunn av at alle metodene i klassen TellemaskinFront er
   * synkronisert.(ArrayList er ikke synkronisert. Se online API-dok. dersom du
   * trenger � synkronisere en ArrayList.)
   */
  private ArrayList<String> innlegg = new ArrayList<String>();
    private String innloggede;
  private ArrayList<Klient> klientene = new ArrayList<Klient>();

  public ChatImpl() throws RemoteException {
  }

  /* Registrerer en ny klient */
  public synchronized void registrerMeg(Klient klienten) throws RemoteException {
    try {
      klientene.add(klienten);
      System.out.println("N� er " + klienten.finnNavn() + " registrert.");
      varsleAlle();
    } catch (Exception e) {
      System.out.println("Feil oppst�tt i registrerMeg(): " + e);
      e.printStackTrace();
    }
  }

  /* Melder ut en klient. Ingenting skjer dersom klienten ikke eksisterer. */
  public synchronized void meldMegUt(Klient klienten) throws RemoteException {
    boolean funnet = false;
    int klientIndeks = 0;
    while (klientIndeks < klientene.size() && !funnet) {
      Klient denne = klientene.get(klientIndeks);
      if (denne.equals(klienten)) {  // bruker equals() for � sammenlikne stubbobjektene
        funnet = true;
        klientene.remove(klientIndeks);
        System.out.println("N� er klienten " + klienten.finnNavn() + " fjernet.");
        varsleAlle();
      } else klientIndeks++;
    }
  }

    public synchronized void nyMelding(String i) throws RemoteException {
        System.out.println("Ny melding publisert");
        innlegg.add(i);
        varsleAlle();
    }

  private synchronized void varsleAlle() throws RemoteException {
    System.out.println("Skal varsle alle om endringen.");

    int klientIndeks = 0;
    while (klientIndeks < klientene.size()){
      Klient denne = klientene.get(klientIndeks);
      try {
        denne.skrivStatus(innlegg);
        klientIndeks++; // oppdaterer indeks bare dersom vi har f�tt kontakt
      } catch (ConnectException e) {  // klienten er koblet ned
        System.out.println("F�r ikke kontakt med klient med indeks " + klientIndeks + ": " + e);
        klientene.remove(klientIndeks);
        System.out.println("N� er vedkommende fjernet fra listen. Vi fortsetter ...");
      }
    }
  }
}