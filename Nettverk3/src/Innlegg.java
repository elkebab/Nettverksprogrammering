import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Kjetil on 08.03.14.
 */
public class Innlegg {
    final String txt;
    final String tid;
    final String fra;
    final String til;

    public Innlegg(String k, String t) {
        txt = t;
        fra = k;
        til = null;
        java.util.Date nå = new java.util.Date();
        java.text.DateFormat tidsformat =
                java.text.DateFormat.getTimeInstance();
        tid = tidsformat.format(nå);
    }

    public Innlegg(String k, String til, String t) {
        txt = t;
        fra = k;
        this.til = til;
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
        return fra;
    }

    public String getTilKlient() {
        return til;
    }

    public String toString() {
        return fra + "("+tid+"): "+txt;
    }
}
