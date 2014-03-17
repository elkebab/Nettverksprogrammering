/*
 * BokFasade.java
 *
 * Created on December 8, 2006, 4:43 PM
 */

import java.rmi.*;
import java.util.List;

public interface KontoFasade extends Remote{
    public void lagreNyKonto(Konto k) throws RemoteException;
    public List<Konto> getAlleKontoer(double grense) throws RemoteException;
    public Konto finnKonto(String nr) throws RemoteException;
    public void endreNavn(Konto k) throws RemoteException;
    public void overfør(Konto k1, Konto k2, double sum) throws RemoteException;
}
