package Oppgave2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by Kjetil on 23.03.14.
 */
public class Klient {
    public static void main(String[] args) throws IOException {

        MulticastSocket socket = new MulticastSocket(4446);
        InetAddress address = InetAddress.getByName("230.0.0.1");
        socket.joinGroup(address);

        DatagramPacket packet;
        boolean mer = true;
        while (mer) {
            byte[] buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            System.out.println("venter...");
            socket.receive(packet);

            String received = new String(packet.getData(), 0, packet.getLength());

            if (received.equals("")) {
                mer = false;
            }
            System.out.println("Mottatt fra server: " + received);
        }

        socket.leaveGroup(address);
        socket.close();
    }
}
