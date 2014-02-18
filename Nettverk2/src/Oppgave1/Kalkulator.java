package Oppgave1;

import java.text.ParseException;

/**
 * Oppgave 1
 */
public class Kalkulator {
    String stykke;

    public Kalkulator(String t) {
        stykke = t;
    }

    public int regnUt() throws ParseException {
        try {
            String[] tab = stykke.split(" ");
            Integer tall1 = Integer.parseInt(tab[0]);
            Character operator = tab[1].charAt(0);
            Integer tall2 = Integer.parseInt(tab[2]);

            if (tall1 == null || operator == null || operator.equals("") || tall2 == null || operator != '+' && operator != '-') {
                throw new ParseException("Ugyldig input", 1);
            }

            if (operator == '+') {
                return tall1 + tall2;
            }
            return tall1 - tall2;
        }
        catch (Exception e) {
            throw new ParseException("Ugyldig input", 1);
        }
    }

    public String toString() {
        try {
            return stykke + " = " + regnUt();
        }
        catch (ParseException e) {
            return e.toString();
        }
    }

    public static void main(String[] args) {
        Kalkulator k = new Kalkulator("-10 - -2");
        Kalkulator k1 = new Kalkulator("10 a+ -3");
        Kalkulator k2 = new Kalkulator("-10+ 4");
        Kalkulator k3 = new Kalkulator("0 - +5");
        Kalkulator k4 = new Kalkulator(" - 10");

        System.out.println(k.toString());
        System.out.println(k1.toString());
        System.out.println(k2.toString());
        System.out.println(k3.toString());
        System.out.println(k4.toString());
    }
}
