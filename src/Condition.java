/**
 * Created by santi698 on 27/11/14.
 */
public abstract class Condition {
    public String toTexString(){
        return toString();
    }
    public Condition(){
    }
    public static class Priority extends Condition {
        public Condition c;
        public Priority(Condition c){
            this.c=c;
        }
        public String toString(){
            return "("+c+")";
        }
        public void accept(ASTVisitor v){
            if (!v.preVisit(this)) return;
            c.accept(v);
            v.postVisit(this);
        }

    }
    public static Condition priority(Condition c){
        return new Priority(c);
    }
    public static class BBinCondition extends Condition {
        public Condition c1, c2;
        public int op;
        public BBinCondition(Condition c1, int op, Condition c2){
            this.c1=c1;
            this.c2=c2;
            this.op=op;
        }
        public String toString(){
            String operator=null;
            if (op==Constants.AND)  operator = "&&";
            if (op==Constants.OR)  operator = "||";
            return c1 + ""+operator + c2;
        }
        public String toTexString(){
            String operator=null;
            if (op==Constants.AND)  operator = "\\&\\&";
            if (op==Constants.OR)  operator = "||";
            return c1 + ""+operator + c2;
        }

        public void accept(ASTVisitor v){
            if (!v.preVisit(this))return;
            c1.accept(v);
            c2.accept(v);
            v.postVisit(this);
        }

    }
    public static Condition binop(Condition c1, int op, Condition c2){
        return new BBinCondition(c1,op,c2);
    }
    public static class BinCondition extends Condition {
        public Expression e1, e2;
        public int op;
        public BinCondition(Expression e1, int op, Expression e2){
            this.e1=e1;
            this.e2=e2;
            this.op=op;
        }
        public String toString(){
            String operator=null;
            if (op==Constants.LEQ)  operator = "<=";
            if (op==Constants.GTQ)  operator = ">=";
            if (op==Constants.GT)  operator = ">";
            if (op==Constants.LE)  operator = "<";
            if (op==Constants.NEQ) operator = "!=";
            if (op==Constants.EQ) operator = "==";
            return e1 + ""+operator + e2;
        }
        public void accept(ASTVisitor v){
            if (!v.preVisit(this))return;
            e1.accept(v);
            e2.accept(v);
            v.postVisit(this);
        }
    }

    public static Condition binop(Expression e1, int op, Expression e2){
        return new BinCondition(e1,op,e2);
    }
    public static class BUnOp extends Condition {
        public Condition c;
        public BUnOp(Condition c){
            this.c=c;
        }
        public String toString(){
            return "!"+c;
        }
        public void accept(ASTVisitor v){
            if (!v.preVisit(this))return;
            c.accept(v);
            v.postVisit(this);
        }

    }
    public static Condition unop(Condition c){
        return new BUnOp(c);
    }
    public static class BoolConst extends Condition {
        public boolean b;
        public BoolConst(boolean b){
            this.b=b;
        }
        public String toString(){
            if (b) return "true";
            else return "false";
        }
        public void accept(ASTVisitor v){
            v.visit(this);
        }
    }
    public static Condition boolconst(boolean b){
        return new BoolConst(b);
    }
    public abstract void accept(ASTVisitor v);
}