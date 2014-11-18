/**
 * Created by mariaeugenia on 18/11/14.
 */
public class While extends Statment {
    private Condition cond;
    private Statment stm;

    public While(Condition cond, Statment stm){
        this.cond = cond;
        this.stm = stm;
    }
}
