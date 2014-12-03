/**
 * Created by santi698 on 18/11/14.
 */
import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class Expression implements Constants {
    public Expression()                             {    }
    public abstract void accept(ASTVisitor v);
    public boolean isConstant() {return false;}
    public abstract int getValue();
    public static Priority priority(Expression e)   { return new Priority(e);  }
    public static class Priority extends Expression {
        public Expression e;
        public Priority(Expression e)               {  this.e=e; }
        public String toString()              { return "("+e+")"; }
        public void accept(ASTVisitor v) {
            if (!v.preVisit(this)) return;
            e.accept(v);
            v.postVisit(this);
        }
        @Override
        public boolean isConstant() {
            return e.isConstant();
        }
        @Override
        public int getValue() {
            return e.getValue();
        }
    }
    public static Binex binop(Expression e1, int op, Expression e2){ return new Binex(e1,op,e2);  }
    public static class Binex extends Expression {
        public Expression e1, e2;
        public int op;
        public Binex(Expression e1, int op, Expression e2){
            this.e1=e1;
            this.e2=e2;
            this.op=op;
        }
        public String toString(){
            String operator=null;
            switch (op) {
                case PLUS: operator = "+"; break;
                case MINUS: operator = "-"; break;
                case MULT:  operator = "*"; break;
                case DIV:   operator = "/"; break;
                case MOD:   operator = "\\%"; break;
            }

            return e1 + ""+operator + e2;
        }
        public void accept(ASTVisitor v){
            if (!v.preVisit(this)) return;
            if (isConstant()) {
                Expression.intconst(getValue()).accept(v);
                return;
            } else {
                e1.accept(v);
                e2.accept(v);
            }
            v.postVisit(this);
        }
        @Override
        public boolean isConstant() {
            return e1.isConstant() && e2.isConstant();
        }
        @Override
        public int getValue() {
            switch (op) {
                case PLUS: return e1.getValue() + e2.getValue();
                case MINUS: return e1.getValue() - e2.getValue();
                case DIV: return e1.getValue() / e2.getValue();
                case MOD: return e1.getValue() % e2.getValue();
                case MULT: return e1.getValue() * e2.getValue();
                default:
                    throw new RuntimeException();
            }
        }
    }
    public static Unex unop(Expression e, int op)             { return new Unex(e, op);   }
    public static Unex unop(String id, int op)             { return new Unex(new Identifier(null,id,null), op);   }
    public static class Unex extends Expression {
        public Expression e1;
        public int op;
        public Unex(Expression e1, int op) {
            this.e1=e1;
            this.op = op;
        }
        public String toString() {
            switch (op) {
                case SUBONE: break;
                case ADDONE: break;
                case MINUS: break;
                case PLUS: break;
                default: throw new RuntimeException();
            }
            return "-"+e1;
        }
        public void accept(ASTVisitor v)   {
            if (!v.preVisit(this)) return;
            e1.accept(v);
            v.postVisit(this);
        }
        @Override
        public boolean isConstant() {
            return e1.isConstant();
        }
        @Override
        public int getValue() {
            switch (op) {
                case SUBONE:
                case ADDONE:
                case PLUS: return e1.getValue();
                case MINUS: return -e1.getValue();
                default: throw new RuntimeException();
            }
        }


    }
    public static IntConst intconst(int i)      { return new IntConst(i);    }
    public static class IntConst extends Expression {
        public int i;
        public IntConst(int i1, int i2, int op) {
            switch (op) {
                case PLUS: i = i1 + i2; break;
                case MINUS: i = i1 - i2; break;
                case DIV: i = i1 / i2; break;
                case MULT: i = i1 * i2; break;
                case MOD: i = i1 % i2; break;
                default: break;
            }
        }
        public IntConst(int i)                  { this.i=i;	}
        public String toString()                { return i+"";	}

        @Override
        public boolean isConstant() {
            return true;
        }
        @Override
        public int getValue() {
            return i;
        }

        public void accept(ASTVisitor v)   { v.visit(this);	}
    }
    public static Identifier ident(Location l, String s, Location r){	return new Identifier(l,s,r);    }
    public static class Identifier extends Expression {
        public Location left, right;
        public String i;
        public Identifier(Location l, String i,Location r){
            this.i=i;
            this.left=l;
            this.right=r;
        }
        public String toString()                { return i;	}
        public void accept(ASTVisitor v)   { v.visit(this);	}
        @Override
        public boolean isConstant() {
            return false;
        }
        @Override
        public int getValue() {
            throw new RuntimeException();
        }

    }
}