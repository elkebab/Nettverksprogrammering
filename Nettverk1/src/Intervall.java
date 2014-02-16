/**
 * Created by Kjetil on 29.01.14.
 */
public class Intervall {
    private Long fra;
    private Long til;

    public Intervall(Long f, Long t) {
        fra = f;
        til = t;
    }

    public Long getFra() {
        return fra;
    }

    public void setFra(Long fra) {
        this.fra = fra;
    }

    public Long getTil() {
        return til;
    }

    public void setTil(Long til) {
        this.til = til;
    }
}
