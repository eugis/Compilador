/**
 * Created by mariaeugenia on 18/11/14.
 */
public class ExprCompExpr extends Condition{
    private Expression exp1;
    private Expression exp2;
    private Comparison op;

    public ExprCompExpr(Expression exp1, Expression exp2, Comparison op){
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    public Expression getExp1(){
        return exp1;
    }

    public Expression getExp2(){
        return exp2;
    }

    public Comparison getOperator(){
        return op;
    }

}
