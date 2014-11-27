/**
 * Created by santi698 on 18/11/14.
 */
public abstract class ASTVisitor {
    public boolean preVisit(Program p){
        return true;
    }
    public void postVisit(Program p){
    }
    public void visit(Declaration d){
    }
    public boolean preVisit(Statement.Assign i){
        return true;
    }
    public void postVisit(Statement.Assign i){
    }
    public boolean preVisit(Statement.Write i){
        return true;
    }
    public void postVisit(Statement.Write i){
    }
    public void visit(Statement.Read i){
    }
    public void visit(Statement.Empty e){
    }
    public boolean preVisit(Statement.Compound i){
        return true;
    }
    public void postVisit(Statement.Compound i){
    }
    public boolean preVisit(Statement.IfThenElse i){
        return true;
    }
    public void postVisit(Statement.IfThenElse i){
    }
    public boolean preVisit(Statement.Loop i){
        return true;
    }
    public void postVisit(Statement.Loop i){
    }
    public boolean preVisit(Declaration d){
        return true;
    }
    public void postVisit(Declaration d){
    }
    public boolean preVisit(Condition.BUnOp i){
        return true;
    }
    public void postVisit(Condition.BUnOp i){
    }
    public boolean preVisit(Condition.BBinCondition i){
        return true;
    }
    public void postVisit(Condition.BBinCondition i){
    }
    public boolean preVisit(Condition.BinCondition i){
        return true;
    }
    public void postVisit(Condition.BinCondition i){
    }
    public void visit(Condition.BoolConst d){
    }
    public void visit(Expression.Identifier d){
    }
    public void visit(Expression.IntConst d){
    }
    public boolean preVisit(Expression.Priority i){
        return true;
    }
    public void postVisit(Expression.Priority i){
    }
    public boolean preVisit(Condition.Priority i){
        return true;
    }
    public void postVisit(Condition.Priority i){
    }
    public boolean preVisit(Expression.Binex i){
        return true;
    }
    public void postVisit(Expression.Binex i){
    }
    public boolean preVisit(Expression.Unex i){
        return true;
    }
    public void postVisit(Expression.Unex i){
    }
}
