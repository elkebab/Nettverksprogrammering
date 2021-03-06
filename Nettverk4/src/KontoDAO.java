/*
 * BokDAO.java
 *
 * Created on November 27, 2008
/*
 * BokDAO.java
 *
 * Created on November 27, 2008, av Tomas Holt
 * Kommentar: Unntakshånteringen er av enkleste type!
 * Alle unntak (exceptions) som kastes av EntityManager arver 
 * PersistenceException. Alle slike unntak (bortsett fra de knyttet til spørringer) 
 * i en transaksjon fører til automatisk tilbakerulling (rollback) av transaksjonen.
 * Å gjøre tilbakerulling i koden er derfor unødvendig (om ikke andre typer unntak kan oppstå).
 * Ved tilbakerulling vil alle entiteter bli frikoblet (detached), da man ikke
 * vet om integriteten på objektene er god. Den naturlig løsningen på dette problemet
 * er å lukke EntityManager, og evt. prøve det man hold på med en nye EntityManager.
 * En EntityManger er et lettvektsobjekt, og det koster derfor lite å opprette 
 * slike (i motsetning til EntityManagerFactory - som setter opp oppkolinger
 * til databasen (gjerne i en pool)).
 *
 */


import javax.persistence.*;
import java.util.List;

public class KontoDAO{
    private EntityManagerFactory emf;
    /* OBS! EntityManagerFactory er thread safe, men det er ikke 
    * EntityManger! Objektvariabel medfører at vi må synkronisere metodene.
    * Vi løser det med å ha EntityManger bare lokalt. Unngår trådproblematikk!
    */
    //private EntityManager em;


    public KontoDAO(EntityManagerFactory emf){
        this.emf = emf;
    }

    //Metoden er laget for å lagre nye bøker
    //Merk at persist() vil fungere som en SQL INSERT
    //Boka (ISBN) kan derfor ikke vre lagret i DB fra før!!
    public void lagreNyKonto(Konto k){
        EntityManager em  = getEM();
        try{
            em.getTransaction().begin();
            em.persist(k);//fører boka inn i lagringskontekt (persistence context)
            em.getTransaction().commit();//lagring skjer her
        }finally{
            lukkEM(em);
        }
    }

    //sprring som henter alle bker
    public List<Konto> getAlleKontoer(double grense){
        EntityManager em = getEM();
        try{
            Query q = em.createQuery("SELECT OBJECT(o) FROM Konto o WHERE o.saldo > " +grense);
            //SELECT o FROM BOK o gir samme resultat
            //MERK at Bok må ha stor B (samme som klassenavn)
            return q.getResultList();
        }
        finally{
            lukkEM(em);
        }
    }
    
    public Konto finnKonto(String nr){
        EntityManager em = getEM();
        try{
            return em.find(Konto.class, nr);
        }finally{
            lukkEM(em);
        }
    }
    
    public void endreNavn(Konto k) {
        EntityManager em = getEM();
        try{
            em.getTransaction().begin();
            Konto b = em.merge(k);//sørger for å føre entiteten inn i lagringskonteksten
            em.getTransaction().commit();//merk at endringene gjort utenfor transaksjonen blir lagret!!!
        }finally{
            lukkEM(em);
        }
    }
    
    public void overfør(Konto k1, Konto k2, double sum) {
        EntityManager em = getEM();
        try{
            em.getTransaction().begin();
            
            k1.trekk(sum);
            k2.trekk(-sum);
            em.merge(k1);
            em.merge(k2);
            
            em.getTransaction().commit();//merk at endringene gjort utenfor transaksjonen blir lagret!!!
        } catch (IkkeDekningException e) {
            System.out.println(e);
        }
        finally{
            lukkEM(em);
        }
    }

    private EntityManager getEM(){
        return emf.createEntityManager();
    }

    private void lukkEM(EntityManager em){
        if (em != null && em.isOpen()) em.close();
    }
}