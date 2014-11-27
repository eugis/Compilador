/**
 * Created by santi698 on 27/11/14.
 */
import java.util.List;
public class Program extends Statement.Compound {
    public List<Declaration> ld;
    public Program(List<Declaration> ld, List<Statement> ls){
        super(ls);
        this.ld = ld;
    }
    public String toString(){
        String ret="" ;
        for (Declaration d: ld) ret += d;
        for (Statement s: ls) ret += s;
        return ret;
    }
    public void accept(ASTVisitor v){
        if (!v.preVisit(this)) return;
        for (Declaration d: ld) d.accept(v);
        for (Statement s: ls) s.accept(v);
        v.postVisit(this);
    }
}