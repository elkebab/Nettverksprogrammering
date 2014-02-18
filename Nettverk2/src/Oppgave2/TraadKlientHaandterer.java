package Oppgave2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Kjetil on 18.02.14.
 */
public class TraadKlientHaandterer extends Thread {
    Socket forbindelse;

    public TraadKlientHaandterer(Socket s) {
        forbindelse = s;
    }

    //all kommunikasjon med klient
    public void run() {
        try {
            /* Åpner strømmer for kommunikasjon med klientprogrammet */
            InputStreamReader leseforbindelse = new InputStreamReader(forbindelse.getInputStream());
            BufferedReader leseren = new BufferedReader(leseforbindelse);
            PrintWriter skriveren = new PrintWriter(forbindelse.getOutputStream(), true);

        /* Sender innledning til klienten */
            skriveren.println("Hei, du har kontakt med tjenersiden!");
            skriveren.println("Skriv hva du vil, så skal jeg gjenta det, avslutt med linjeskift.");

        /* Mottar data fra klienten */
            String enLinje = leseren.readLine();  // mottar en linje med tekst
            while (enLinje != null) {  // forbindelsen på klientsiden er lukket
                System.out.println("En klient skrev: " + enLinje);
                skriveren.println("Du skrev: " + enLinje);  // sender svar til klienten
                enLinje = leseren.readLine();
            }

        /* Lukker forbindelsen */
            leseren.close();
            skriveren.close();
            forbindelse.close();
        }
        catch (Exception e) {
            System.out.println("Her har vi et godt gammeldags \"problem\" å hanskes med...");
        }
    }
}
