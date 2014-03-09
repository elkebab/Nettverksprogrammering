/*
 * TelleVindu.java   E.L. 2004-10-11
 *
 * Vinduet brukes p� klientsiden til � registrere data.
 * Klienten m� v�re koblet opp p� forh�nd. Klienten kobles ned n�r vinduet lukkes.
 */

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.NotSerializableException;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showInputDialog;

class Vindu extends JFrame {
    private static final int plasseringStatusvinduX = 200;
    private static final int plasseringStatusvinduY = 500;
    private static final int breddeStatusvindu = 500;
    private static final int høydeStatusvindu = 80;
    private static final int plasseringTellevinduX = 200;
    private static final int plasseringTellevinduY = 200;

    private JTextArea chatVindu = new JTextArea(10, 15);
    private JScrollPane sp = new JScrollPane(chatVindu);

    private JTextArea innlegg = new JTextArea(1, 15);
    private JButton lagreknapp = new JButton("Send");

    private JButton privMld = new JButton("Send privat melding");
    private JLabel innloggede = new JLabel("");

    private Chat chat;
    private Klient klienten;

    public Vindu(Chat startTellemaskin, Klient startKlienten) {
        try {
            chat = startTellemaskin;
            klienten = startKlienten;

            setTitle("Klient: " + klienten.finnNavn());
            addWindowListener(new Vinduslytter());
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

            add(new StatusPanel(), BorderLayout.NORTH);
            add(new InputPanel(), BorderLayout.CENTER);
            add(new InnloggetPanel(), BorderLayout.SOUTH);
            pack();

            setLocation(plasseringTellevinduX, plasseringTellevinduY);

            Knappelytter knappelytter = new Knappelytter();
            lagreknapp.setMnemonic('L');
            lagreknapp.addActionListener(knappelytter);

            Knappelytter2 kl2 = new Knappelytter2();
            privMld.addActionListener(kl2);

            chatVindu.setEditable(false);
        } catch (Exception e) {
            System.out.println("Feil i Tellevindu sin konstrukt�r: " + e);
            e.printStackTrace();
        }
    }

    public void settChat(String c) {
        chatVindu.setText(c);
    }

    public void setInnlogget(String t) {
        innloggede.setText(t);
    }

    /* Beskriver nordre panel */
    private class StatusPanel extends JPanel {
        public StatusPanel() {
            setLayout(new GridLayout(1, 1));
            add(sp);
            SoftBevelBorder ramme = new SoftBevelBorder(BevelBorder.RAISED);
            Border boks = BorderFactory.createTitledBorder(ramme, "Chat");
            setBorder(boks);
        }
    }

    /* Beskriver midtre panel */
    private class InputPanel extends JPanel {
        public InputPanel() {
            setLayout(new GridLayout(1, 2));
            add(innlegg);
            add(lagreknapp);
            SoftBevelBorder ramme = new SoftBevelBorder(BevelBorder.RAISED);
            try {
                Border boks = BorderFactory.createTitledBorder(ramme, "Send innlegg (som "+klienten.finnNavn()+")");
                setBorder(boks);
            }
            catch (Exception e) {
            }
        }
    }

    private class InnloggetPanel extends JPanel {
        public InnloggetPanel() {
            setLayout(new GridLayout(2, 1));
            add(innloggede);
            add(privMld);
            SoftBevelBorder ramme = new SoftBevelBorder(BevelBorder.RAISED);
            Border boks = BorderFactory.createTitledBorder(ramme, "Innloggede brukere");
            setBorder(boks);
        }
    }


      private class Knappelytter implements ActionListener {
        public void actionPerformed(ActionEvent hendelse) {
          try {
            Innlegg nyI = new Innlegg(klienten.finnNavn(), innlegg.getText());
            System.out.println(nyI.toString());
            chat.nyMelding(nyI.toString());
          } catch (Exception e) {
            System.out.println("Feil oppst�tt i lytteren til lagreknappen: " + e);
            e.printStackTrace();
          }
            innlegg.setText("");
        }
      }

    private class Knappelytter2 implements ActionListener {
        public void actionPerformed(ActionEvent hendelse) {
            try {
                String navn = showInputDialog(null,"Skriv navnet til brukeren du vil sende melding til:", null, 2);
                String innleg = showInputDialog(null,"Skriv beskjed til "+navn+":", null, 2);
                Innlegg nyI = new Innlegg(klienten.finnNavn(), navn, innleg);
                chat.nyPrivatMelding(nyI.toString(), navn);
            } catch (Exception e) {
                System.out.println("Feil oppst�tt i lytteren til lagreknappen: " + e);
                e.printStackTrace();
            }
        }
    }

    private class Vinduslytter extends WindowAdapter {
        public void windowClosing(WindowEvent hendelse) {
            System.out.println("Pr�ver utmelding");
            try {
                chat.meldMegUt(klienten);
                System.out.println("Utmelding ok");
            } catch (Exception e) {
                System.out.println("Feil i Vinduslytter: " + e);
                e.printStackTrace();
            }
            dispose();
            System.exit(0);
        }
    }
}