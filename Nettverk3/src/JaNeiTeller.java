/*
 * JaNeiTeller.java   E.L. 2002-07-18
 *
 * Klassen gj�r det mulig � telle antall ja- og nei-stemmer.
 * Testprogram finner du som l�sning til oppgave 1 etter kap. 4.4.
 *
 */

class JaNeiTeller {
  private int antallJa = 0;
  private int antallNei = 0;

  public void økAntallJa() {
    antallJa++;
  }

  public void økAntallNei() {
    antallNei++;
  }

  public void økAntallJa(int økning) {
    antallJa += økning;
  }

  public void økAntallNei(int økning) {
    antallNei += økning;
  }

  public int finnAntallJa() {
    return antallJa;
  }

  public int finnAntallNei() {
    return antallNei;
  }
  
  public String toString() {
    return "Antall ja: " + antallJa + ", antall nei: " + antallNei;
  }
}