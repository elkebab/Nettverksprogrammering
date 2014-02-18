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
        final int PORTNR = 80;

        ServerSocket tjener = new ServerSocket(PORTNR);
        System.out.println("Logg for tjenersiden. Nå venter vi...");
        Socket forbindelse = tjener.accept();  // venter inntil noen tar kontakt

        /* Åpner strømmer for kommunikasjon med klientprogrammet */
        InputStreamReader leseforbindelse = new InputStreamReader(forbindelse.getInputStream());
        BufferedReader leseren = new BufferedReader(leseforbindelse);
        PrintWriter skriveren = new PrintWriter(forbindelse.getOutputStream(), false);

        /* Sender innledning til klienten */
        skriveren.println("HTTP/1.1 200 OK");
        skriveren.println("Content-Type: text/html");
        skriveren.println("");
        skriveren.println("<HTML><BODY>");
        skriveren.println("<H1>V&aelig;lkomn t gars!</h1>");
        skriveren.println("<p>Header fra klient:</p><ul>");

        String linje = leseren.readLine();
        while (linje != null && !linje.equals("")) {
            System.out.println(linje);
            skriveren.println("<li>"+linje+"</li>");
            linje = leseren.readLine();
        }
        skriveren.println("</ul></BODY></HTML>");
        skriveren.flush();

        /* Lukker forbindelsen */
        leseren.close();
        skriveren.close();
        forbindelse.close();
    }
}
