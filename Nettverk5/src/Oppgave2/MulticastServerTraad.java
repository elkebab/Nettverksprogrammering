package Oppgave2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * Created by Kjetil on 23.03.14.
 */
public class MulticastServerTraad extends Thread {
    protected DatagramSocket socket = null;

    public MulticastServerTraad() throws IOException {
        this("ServerThread");
    }

    public MulticastServerTraad(String name) throws IOException {
        super(name);
        socket = new DatagramSocket(4445);
    }

    public void run() {
        boolean mer = true;
        while (mer) {
            try {
                //Bruker en scanner til å lese fra kommandovinduet
                Scanner leserFraKommandovindu = new Scanner(System.in);
                System.out.print("Skriv melding: ");
                String melding = leserFraKommandovindu.nextLine();

                if (melding.equals("")) {
                    mer = false;
                }
                byte[] buf = melding.getBytes();

                InetAddress group = InetAddress.getByName("230.0.0.1");
                DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
                socket.send(packet);
                System.out.println("    Sender: "+new String(packet.getData(), 0, packet.getLength()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }
}
