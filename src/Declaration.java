import java.util.List;

public class Declaration {
    public List<Expression.Identifier> varlist;
    public Declaration(List<Expression.Identifier> l){
        varlist = l;
    }
    public String toString(){
        String ret = "int "+varlist.get(0);
        for (int i = 1; i < varlist.size(); i++) ret += ","+varlist.get(i);
        return ret+";\n";
    }
    public void accept(ASTVisitor v){
        v.preVisit(this);
        v.postVisit(this);
    }
}