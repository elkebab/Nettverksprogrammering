package Oppgave1;

import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Klient {
    public static void main(String[] args) throws IOException {

        // get a datagram socket
        DatagramSocket socket = new DatagramSocket();

        // regnestykke
        String stykke  = "-10 - -3";

        // send request
        byte[] buf = stykke.getBytes();
        InetAddress address = InetAddress.getByName("Richard");
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);
        System.out.println("Forespørsel sendt:");
        System.out.println(stykke+"\n");

        buf = new byte[256];
        // get response
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        // display response
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Svar mottatt:");
        System.out.println(received);
        socket.close();
    }
}