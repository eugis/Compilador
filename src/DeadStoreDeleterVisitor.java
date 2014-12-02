import java.util.HashMap;
import java.util.Map;

/**
 * Borra los stores que nunca se usan
 */
public class DeadStoreDeleterVisitor extends ASTVisitor {
    private Map<String, Statement> stores;
    public DeadStoreDeleterVisitor() {
        stores = new HashMap<String, Statement>();
    }
    public boolean preVisit(Statement.Compound i) {
        for (Statement s : i.ls) {
            s.parent = i;
        }
        return true;
    }
    public boolean preVisit(Program p) {
        for (Statement s : p.ls) {
            s.parent = p;
        }
        return true;
    }

    public void postVisit(Statement.Assign i) {
        if (!stores.containsKey(i.lhs))
            stores.put(i.lhs, i);
    }
    public void visit(Expression.Identifier i) {
        if (stores.containsKey(i.i))
            stores.put(i.i, null);
    }
    public void postVisit(Program p) {
        for (Statement s : stores.values()) {
            if (s != null) s.parent.ls.remove(s);
        }
    }
}