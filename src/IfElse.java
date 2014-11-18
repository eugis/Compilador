/**
 * Created by mariaeugenia on 18/11/14.
 */
public class IfElse extends If {
    private Statment f;

    public IfElse(Statment t, Condition cond, Statment f ){
        super(t, cond);
        this.f = f;
    }
}
