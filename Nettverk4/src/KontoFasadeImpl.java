/*
 * BokFasadeImpl.java
 *
 * Created on December 8, 2006, 4:37 PM
 */

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class KontoFasadeImpl extends UnicastRemoteObject implements KontoFasade {
    private KontoDAO dao;

    public KontoFasadeImpl() throws RemoteException {
    }

    public KontoFasadeImpl(KontoDAO dao) throws RemoteException {
        this.dao = dao;
    }

    public synchronized void lagreNyKonto(Konto k) throws RemoteException {
        dao.lagreNyKonto(k);
    }
    
    public synchronized void endreNavn(Konto k) throws RemoteException {
        dao.endreNavn(k);
    }
    
    public Konto finnKonto(String nr) throws RemoteException {
        return dao.finnKonto(nr);
    }

    public List<Konto> getAlleKontoer() throws RemoteException {
        return dao.getAlleKontoer();
    }
    
    public synchronized void overfør(Konto k1, Konto k2, double sum) throws RemoteException {
        dao.overfør(k1,k2,sum);
    }

    public static void main(String args[]) throws Exception {
        EntityManagerFactory emf = null;


        KontoFasade fasade = null;
        Registry registry = null;
        System.out.println("Starter tjener...");
        try {

            emf = Persistence.createEntityManagerFactory("kontoEntity");
            EntityManager em = emf.createEntityManager();

            System.out.println("EntityManager og Factory opprettet...");
            registry = LocateRegistry.createRegistry(1099);//start rmiregistry
            System.out.println("RmiRegistry startet....");
            fasade = new KontoFasadeImpl(new KontoDAO(emf));
            System.out.println("Fasaden er opprettet...");
            registry.rebind("kontoFasade", fasade);
            System.out.println("Fasaden er bundet i rmiregistret...");

            javax.swing.JOptionPane.showConfirmDialog(null, "Trykk for Ã¥ avslutt tjener");

            emf.close();
            System.exit(0);
        } catch (Exception e) {
            System.out.println(e);

        }
    }
}