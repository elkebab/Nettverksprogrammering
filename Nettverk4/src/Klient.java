/**
 * Created by Kjetil on 10.03.14.
 */
public class Klient {
    public static void main(String[] args) {
        Konto k1 = new Konto("1234", 200, "Sivert");
        try {
            k1.trekk(250);
        }
        catch (IkkeDekningException e) {
            System.out.println(e);
        }
        System.out.println("hei");

    }
}
