/**
 * Created by mariaeugenia on 18/11/14.
 */
public class AssignExpression extends Assign {
    private Expression exp;

    public AssignExpression(Variable variable, Expression exp) throws Exception {
        super(variable);
        if(exp.getType().equals(getType())){
            throw new Exception("tipos no compatibles");
        }
        this.exp = exp;
    }

    public Expression getExpression(){
        return exp;
    }
}
