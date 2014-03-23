package Oppgave1; /**
 * Programmet åpner en socket og venter på at en klient skal ta kontakt.
 * Programmet leser tekster som klienten sender over, og returnerer disse.
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class Tjener {
    public static void main(String[] args) throws IOException {
        System.out.println("Logg for tjenersiden. Nå venter vi...");

        DatagramSocket socket = new DatagramSocket(4445);

        byte[] buf = new byte[256];

        // receive request
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        String stykke = new String(packet.getData(), 0, packet.getLength());

        Kalkulator k = new Kalkulator(stykke);
        String retur = k.toString();
        System.out.println("Setter returverdi: "+retur);

        buf = retur.getBytes();

        // send the response to the client at "address" and "port"
        InetAddress address = packet.getAddress();
        //System.out.println(address.toString());
        int port = packet.getPort();
        packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);

        //System.out.println(new String(packet.getData(), 0, packet.getLength()));

        socket.close();
    }
}