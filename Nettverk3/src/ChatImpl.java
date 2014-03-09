/*

 */

import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;

class ChatImpl extends UnicastRemoteObject implements Chat {

    private ArrayList<String> innlegg = new ArrayList<String>();
    private ArrayList<String> innloggede = new ArrayList<String>();
    private ArrayList<Klient> klientene = new ArrayList<Klient>();

    public ChatImpl() throws RemoteException {
    }

    /* Registrerer en ny klient */
    public synchronized void registrerMeg(Klient klienten) throws RemoteException {
        try {
            klientene.add(klienten);
            System.out.println("N� er " + klienten.finnNavn() + " registrert.");
            innloggede.add(klienten.finnNavn());
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
                for (int i = 0; i < innloggede.size(); i++) {
                    String k2 = innloggede.get(i);
                    if (denne.finnNavn().equals(k2)) {
                        innloggede.remove(i);
                        break;
                    }
                }
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

    public synchronized void nyPrivatMelding(String inn, String n) throws RemoteException {
        System.out.println("Ny melding publisert");

        for (int j = 0; j < klientene.size(); j++) {
            Klient denne = klientene.get(j);
            if (denne.finnNavn().equals(n)) {
                ArrayList<String> privMld = new ArrayList<String>();
                for (int i = 0; i < innlegg.size(); i++) {
                    privMld.add(innlegg.get(i));
                }
                privMld.add(inn);
                denne.skrivStatus(privMld,innloggede);
                break;
            }
        }
    }

    private synchronized void varsleAlle() throws RemoteException {
        System.out.println("Skal varsle alle om endringen.");

        int klientIndeks = 0;
        while (klientIndeks < klientene.size()){
            Klient denne = klientene.get(klientIndeks);
            try {
                denne.skrivStatus(innlegg, innloggede);
                klientIndeks++; // oppdaterer indeks bare dersom vi har f�tt kontakt
            } catch (ConnectException e) {  // klienten er koblet ned
                System.out.println("F�r ikke kontakt med klient med indeks " + klientIndeks + ": " + e);
                klientene.remove(klientIndeks);
                System.out.println("N� er vedkommende fjernet fra listen. Vi fortsetter ...");
            }
        }
    }
}