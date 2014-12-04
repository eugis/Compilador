import java.util.List;
import java_cup.runtime.ComplexSymbolFactory.Location;
public abstract class Statement {
    public Statement.Compound parent;
    public static class Loop extends Statement {
        public Statement body;
        public Condition condition;
        public Loop(Condition c, Statement t){
            condition = c;
            body= t;
        }
        public String toString(){
            return "while ("+ condition +")"+ body+"\n";
        }
        public void accept(ASTVisitor v){
            if (!v.preVisit(this)) return;
            if (condition.isConstant() && !condition.getValue())
                return;
            condition.accept(v);
            body.accept(v);
            v.postVisit(this);
        }
    }
    public static Statement forloop(Statement.Assign s, Condition c, Statement f, Statement body) {return new ForLoop(s,c,f,body);}
    public static class ForLoop extends Statement {
        Statement.Assign s;
        Statement f;
        Statement body;
        Condition c;

        public ForLoop(Assign s, Condition c, Statement f, Statement body) {
            this.s = s;
            this.f = f;
            this.body = body;
            this.c = c;
        }

        public void accept (ASTVisitor v) {
            if (!v.preVisit(this)) return;
            if (c.isConstant() && !c.getValue())
                return;
            s.accept(v);
            c.accept(v);
            v.postVisit(this);
        }

    }
    public static Statement whileloop(Condition c, Statement s){
        return new Loop(c,s);
    }
    public static class IfThenElse extends Statement {
        public Statement then;
        public Statement els;
        public Condition condition;
        public IfThenElse(Condition c, Statement t, Statement e){
            condition = c;
            then = t;
            els  = e;
        }
        public String toString(){
            return "if("+ condition +")"+ then + ((els==null)?"":("else "+els))+"\n";
        }
        public void accept(ASTVisitor v){
            if (!v.preVisit(this)) return;
            if (condition.isConstant()) {
                if (condition.getValue()) {
                    then.accept(v);
                }
                else if (els != null) {
                    els.accept(v);
                }
            } else {
                condition.accept(v);
                then.accept(v);
                if (els != null) els.accept(v);
                v.postVisit(this);
            }
        }
    }
    public static Statement ifthenelse(Condition c, Statement t, Statement e){
        return new IfThenElse(c,t,e);
    }
    public static Statement ifthen(Condition c, Statement t){
        return new IfThenElse(c,t,null);
    }
    public static class Write extends Statement {
        public Expression e;
        public String s;
        public Write(Expression e){
            this.e = e;
        }
        public Write(String s){
            this.s=s;
        }
        public String toString(){
            if (e==null) return "write(\""+s+"\");\n";
            return "write("+e+");\n";
        }
        public void accept(ASTVisitor v){
            if (!v.preVisit(this)) return;
            if (e!=null) e.accept(v);
            v.postVisit(this);
        }
    }
    public static Statement write(Expression e){
        return new Write(e);
    }
    public static Statement write(String s){
        return new Write(s);
    }
    public static class Read extends Statement {
        public String lhs;
        public String message;
        public Location left,right;
        public Read(Location l, String s, Location r){
            lhs = s;
            left=l;
            right=r;
        }
        public Read(Location l, String ident, String mess, Location r){
            lhs=ident;
            message=mess;
            left=l;
            right=r;
        }
        public String toString(){
            if (message==null) return lhs+"=read();\n";
            return lhs+"=read();\n";
        }
        public void accept(ASTVisitor v){
            v.visit(this);
        }
    }
    public static Statement read(Location l,String s,Location r){ return new Read(l,s,r); }
    public static Statement read(Location l,String ident,String s,Location r){
        return new Read(l,ident,s,r);
    }
    public static class Assign extends Statement {
        public String lhs;
        public Expression rhs;
        public Location left, right;
        public Assign(Location l,String s, Expression e,Location r){
            lhs = s;
            rhs = e;
            left =l;
            right=r;
        }
        public String toString(){
            return lhs+"="+rhs+";\n";
        }
        public void accept(ASTVisitor v){
            v.preVisit(this);
            rhs.accept(v);
            v.postVisit(this);
        }
    }
    public static Statement assign(Location l,String i, Expression e,Location r){
        return new Assign(l,i,e,r);
    }
    public static class Compound extends Statement {
        public List<Statement> ls;
        public Compound(List<Statement> l){
            ls = l;
        }
        public String toString(){
            String ret= "{\n";
            for (Statement s : ls) ret += s;
            return ret+"}";
        }
        public void accept(ASTVisitor v){
            if (!v.preVisit(this)) return;
            for (Statement s: ls) s.accept(v);
            v.postVisit(this);
        }
    }
    public static Statement assunop(String id, int op) { return new AssUnop(id, op);}
    public static class AssUnop extends Statement {
        public String id;
        public int op;
        public AssUnop(String id, int op) {
            this.id = id;
            this.op = op;
        }

        public void accept (ASTVisitor v) { v.visit(this);}
    }
    public static Statement assbinop(String id, int op, Expression e) {return new AssBinOp(id, op, e);}
    public static class AssBinOp extends Statement {
        public String id;
        public int op;
        public Expression e;
        public AssBinOp(String id, int op, Expression e) {
            this.id = id;
            this.op = op;
            this.e = e;
        }
        public void accept (ASTVisitor v) {v.visit(this);}
    }
    public static Statement breakst() { return new Break();}
    public static class Break extends Statement{
        public void accept(ASTVisitor v) {
            v.visit(this);
        }
    }
    public static Statement continuest() {return new Continue();}
    public static class Continue extends Statement {
        public void accept(ASTVisitor v) {
            v.visit(this);
        }
    }
    public static Statement compound(List<Statement> l){
        return new Compound(l);
    }
    public static class Empty extends Statement {
        public Empty(){
        }
        public void accept(ASTVisitor v){
            v.visit(this);
        }
        public String toString() {
            return "";
        }
    }
    public static Statement empty(){
        return new Empty();
    }
    public abstract void accept(ASTVisitor v);

}