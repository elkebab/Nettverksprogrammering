package Oppgave3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Kjetil on 19.02.14.
 */
public class Klienttrad extends Thread {
    Socket forbindelse;

    public Klienttrad(Socket s) {
        forbindelse = s;
    }

    //all kommunikasjon med klient
    public void run() {
        // Åpner strømmer for kommunikasjon med klientprogrammet
        try {
            InputStreamReader leseforbindelse = new InputStreamReader(forbindelse.getInputStream());
            BufferedReader leseren = new BufferedReader(leseforbindelse);
            PrintWriter skriveren = new PrintWriter(forbindelse.getOutputStream(), false);

            // Sender innledning til klienten
            skriveren.println("HTTP/1.1 200 OK");
            skriveren.println("Content-Type: text/html");
            skriveren.println("");
            skriveren.println("<HTML><BODY>");
            skriveren.println("<H1>V&aelig;lkomn t gars!</h1>");
            skriveren.println("<p>Header fra klient:</p><ul>");

            String linje = leseren.readLine();
            while (linje != null && !linje.equals("")) {
                skriveren.println("<li>" + linje + "</li>");
                linje = leseren.readLine();
            }
            skriveren.println("</ul></BODY></HTML>");
            skriveren.flush();

            // Lukker forbindelsen
            leseren.close();
            skriveren.close();
            forbindelse.close();
        } catch (Exception e) {
            System.out.println("Problem oppsto:\n" + e);
        }

    }
}
