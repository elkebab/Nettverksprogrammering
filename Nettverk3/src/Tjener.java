/*
 * TellerTjener.java   E.L. 2004-03-30
 *
 * Dette er hovedprogrammet p� tjenersiden.
 */

import java.rmi.Naming;
class Tjener {
  public static void main(String[] args) throws Exception {

    java.rmi.registry.LocateRegistry.createRegistry(1099);

    String objektnavn = "åses_tellebyrå_AS";
    System.out.println("Dette er statusvindu for tjenersiden.");
    Chat tellemaskin = new ChatImpl();
    System.out.println("N� er tellemaskinen laget.");
    Naming.rebind(objektnavn, tellemaskin);
    System.out.println("N� venter vi bare p� at noen skal telle oss opp ...");
    javax.swing.JOptionPane.showMessageDialog(null,
                       "Trykk Ok for � stoppe tjeneren.");
    Naming.unbind(objektnavn);
    System.exit(0);
  }
}

