/**
 * Created by mariaeugenia on 18/11/14.
 */
public class CondBbinopCond extends Condition {
    private Condition condition1;
    private Condition condition2;
    private BinaryOperator constant;

    public CondBbinopCond(Condition condition1, Condition condition2, BinaryOperator constant){
        this.condition1 = condition1;
        this.condition2 = condition2;
        this.constant = constant;
    }

    public BinaryOperator getConstant() {
        return constant;
    }

    public Condition getCondition1() {
        return condition1;
    }

    public Condition getCondition2() {
        return condition2;
    }

}
