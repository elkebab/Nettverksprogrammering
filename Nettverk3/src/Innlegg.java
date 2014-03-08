import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Kjetil on 08.03.14.
 */
public class Innlegg {
    final String txt;
    final String tid;
    final String klient;

    public Innlegg(String k, String t) {
        txt = t;
        klient = k;
        java.util.Date nå = new java.util.Date();
        java.text.DateFormat tidsformat =
                java.text.DateFormat.getTimeInstance();
        tid = tidsformat.format(nå);
    }

    public String getTxt() {
        return txt;
    }

    public String getKl() {
        return tid;
    }

    public String getKlient() {
        return klient;
    }

    public String toString() {
        return klient + "("+tid+"): "+txt;
    }
}
