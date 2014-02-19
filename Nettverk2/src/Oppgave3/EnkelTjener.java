package Oppgave3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Kjetil on 18.02.14.
 * ip: http://192.168.0.6/
 */
public class EnkelTjener {
    public static void main(String[] args) throws IOException {
        int klienter = 0;
        final int PORTNR = 80;

        ServerSocket tjener = new ServerSocket(PORTNR);
        System.out.println("Logg for tjenersiden. Nå venter vi...");

        while (true) {
            Socket forbindelse = tjener.accept();  // venter inntil noen tar kontakt
            klienter++; // +=2 ved pc??
            System.out.println("Antall klienter: "+klienter);
            Thread trad = new Klienttrad(forbindelse);
            trad.start();
        }
    }
}
