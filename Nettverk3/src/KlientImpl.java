/*
 * KlientImpl.java   E.L. 2004-10-11
 *
 * Implementerer interfacet Klient
 */

import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class KlientImpl extends UnicastRemoteObject implements Klient {
  private String navn;
  private Vindu vinduet;

  public KlientImpl(String startNavn) throws RemoteException {
    navn = startNavn;
  }

  public void settVindu(Vindu nyttVindu) throws RemoteException {
    vinduet = nyttVindu;
  }

  public synchronized String finnNavn() throws RemoteException {
    return navn;
  }

  public synchronized void skrivStatus(ArrayList<String> inn, ArrayList<String> innloggede) throws RemoteException {
    String res = "";
      for (int i = 0; i < inn.size(); i++) {
          res += inn.get(i) + "\n";
      }
    vinduet.settChat(res);

      res = "";
      for (int i = 0; i < innloggede.size(); i++) {
          res += innloggede.get(i);
          if (i < innloggede.size()-1) {
              res += ", ";
          }
      }
      vinduet.setInnlogget(res);
  }
}

