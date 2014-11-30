/**
 * Created by santi698 on 18/11/14.
 */
import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class Expression implements Constants {
    public Expression()                             {    }
    public abstract void accept(ASTVisitor v);
    public boolean isConstant() {return false;}
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
            if (op==Constants.PLUS)  operator = "+";
            if (op==Constants.MINUS) operator = "-";
            if (op==Constants.MULT)  operator = "*";
            if (op==Constants.DIV)   operator = "/";
            if (op==Constants.MOD)   operator = "\\%";
            return e1 + ""+operator + e2;
        }
        public void accept(ASTVisitor v){
            if (!v.preVisit(this)) return;
            if (e1.isConstant() && e2.isConstant()) {
                new IntConst(((IntConst)e1).i, ((IntConst)e2).i, op).accept(v);
                v.postVisit(this);
                return;
            }
            e1.accept(v);
            e2.accept(v);
            v.postVisit(this);
        }
    }
    public static Unex unop(Expression e)             { return new Unex(e);   }
    public static class Unex extends Expression {
        public Expression e1;
        public Unex(Expression e1)                    { this.e1=e1; 	 }
        public String toString()                { return "-"+e1; }
        public void accept(ASTVisitor v)   {
            if (!v.preVisit(this)) return;
            e1.accept(v);
            v.postVisit(this);
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
    }
}