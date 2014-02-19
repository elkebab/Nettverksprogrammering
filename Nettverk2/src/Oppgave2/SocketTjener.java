package Oppgave2; /**
 * Programmet åpner en socket og venter på at en klient skal ta kontakt.
 * Programmet leser tekster som klienten sender over, og returnerer disse.
 */

import java.io.*;
import java.net.*;

class SocketTjener {
    public static void main(String[] args) throws IOException {
        final int PORTNR = 1250;

        ServerSocket tjener = new ServerSocket(PORTNR);
        System.out.println("Logg for tjenersiden. Nå venter vi...");

        while (true) {
            Socket forbindelse = tjener.accept();  // venter inntil noen tar kontakt
            Thread trad = new TraadKlientHaandterer(forbindelse);
            trad.start();
        }
    }
}