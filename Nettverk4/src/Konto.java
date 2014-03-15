import java.io.Serializable;
import javax.persistence.*;
/**
 * Created by Kjetil on 10.03.14.
 */

@Entity
public class Konto implements Serializable {
    @Id
    private String kontonr;
    private double saldo;
    private String navn;
    @Version
    private int laasingsFelt;

    public Konto() {
    }

    public Konto(String knr, double s, String n) {
        kontonr = knr;
        saldo = s;
        navn = n;
    }

    public String getKontonr() {
        return kontonr;
    }

    public void setKontonr(String kontonr) {
        this.kontonr = kontonr;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double s) {
        saldo = s;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String n) {
        navn = n;
    }

    public int getLaasingsFelt() {
        return laasingsFelt;
    }

    public void setLaasingsFelt(int laasingsFelt) {
        this.laasingsFelt = laasingsFelt;
    }

    public synchronized void trekk(double belop) throws IkkeDekningException{
        if (saldo < belop) {
            throw new IkkeDekningException("Ikke nok penger på konto");
        }
        saldo -= belop;
    }

    @Override
    public String toString() {
        return navn+": "+saldo+"kr";
    }
}
