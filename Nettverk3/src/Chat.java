import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Kjetil on 08.03.14.
 */
interface Chat extends Remote {
    void registrerMeg(Klient klienten) throws RemoteException;

    void meldMegUt(Klient klienten) throws RemoteException;

    void nyMelding(String i) throws RemoteException;

    void nyPrivatMelding(String i, String n) throws RemoteException;
}

