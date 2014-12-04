public abstract class Condition implements Constants {
    public Condition(){
    }
    public boolean isConstant() {
        return false;
    }
    public abstract boolean getValue();
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
        @Override
        public boolean isConstant() {
            return c.isConstant();
        }
        @Override
        public boolean getValue() {
            return c.getValue();
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

        public void accept(ASTVisitor v){
            if (!v.preVisit(this))return;
            if (isConstant()) {
                Condition.boolconst(getValue()).accept(v);
            } else {
                c1.accept(v);
                c2.accept(v);
                v.postVisit(this);
            }
        }
        @Override
        public boolean isConstant() {
            return c1.isConstant() && c2.isConstant();
        }
        @Override
        public boolean getValue() {
            switch (op) {
                case AND:
                    return c1.getValue() && c2.getValue();
                case OR:
                    return c1.getValue() || c2.getValue();
                default:
                    throw new RuntimeException();
            }
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
            String operator = null;
            switch (op) {
                case LEQ:
                    operator = "<=";
                    break;
                case GTQ:
                    operator = ">=";
                    break;
                case GT:
                    operator = ">";
                    break;
                case LE:
                    operator = "<";
                    break;
                case NEQ:
                    operator = "!=";
                    break;
                case EQ:
                    operator = "==";
                    break;
            }
            return e1 + ""+operator + e2;
        }
        public void accept(ASTVisitor v){
            if (!v.preVisit(this))return;
            if (isConstant()) {
                boolconst(getValue()).accept(v);
            } else {
                e1.accept(v);
                e2.accept(v);
                v.postVisit(this);
            }
        }

        @Override
        public boolean isConstant() {
            return e1.isConstant() && e2.isConstant();
        }
        @Override
        public boolean getValue() {
            switch (op) {
                case LEQ:
                    return e1.getValue() <= e2.getValue();
                case GTQ:
                    return e1.getValue() >= e2.getValue();
                case GT:
                    return e1.getValue() > e2.getValue();
                case LE:
                    return e1.getValue() < e2.getValue();
                case EQ:
                    return e1.getValue() == e2.getValue();
                case NEQ:
                    return e1.getValue() != e2.getValue();
                default:
                    throw new RuntimeException();
            }
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
        @Override
        public boolean isConstant() {
            return c.isConstant();
        }
        @Override
        public boolean getValue() {
            return !c.getValue();
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
        @Override
        public boolean isConstant() {
            return true;
        }
        @Override
        public boolean getValue() {
            return b;
        }

    }
    public static Condition boolconst(boolean b){
        return new BoolConst(b);
    }
    public abstract void accept(ASTVisitor v);
}