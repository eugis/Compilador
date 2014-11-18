/**
 * Created by mariaeugenia on 18/11/14.
 */
public class BunopCondition extends Condition {
    private Condition cond;

    public BunopCondition(Condition cond){
        this.cond = cond;
    }

    public Condition getCondition(){
        return cond;
    }
}
