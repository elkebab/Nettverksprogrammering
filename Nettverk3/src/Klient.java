/*
 * Klient.java, E.L. 2004-10-11
 *
 * Tellemaskinen henvender seg til et objekt med dette interfacet n�r den skal varsle
 * alle klienter om endringer i dataene.
 */

import java.rmi.*;
import java.util.ArrayList;

interface Klient extends Remote {
  String finnNavn() throws RemoteException;
  void settVindu(Vindu nyttVindu) throws RemoteException;
  void skrivStatus(ArrayList<String> status, ArrayList<String> inL) throws RemoteException;  // tre statuslinjer
}

