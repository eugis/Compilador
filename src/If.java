

/**
 * Created by mariaeugenia on 18/11/14.
 */
public class If extends Statment {
    private Condition cond;
    private Statment statment;

    public If(Statment stm, Condition cond){
        this.cond = cond;
        this.statment = stm;
    }
}
