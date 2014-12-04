import org.objectweb.asm.*;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CodeGenerator extends ASTVisitor implements Opcodes, Constants {
    private ClassWriter cw;
    private GeneratorAdapter mv;
    private Map<String, Integer> addressTable;
    private String className;
    private Context context;

    public CodeGenerator(String className) {
        this.context = new Context();
        this.className = className;
        this.addressTable = new HashMap<String, Integer>();
        this.cw= new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        cw.visit(V1_5,
                ACC_PUBLIC + ACC_SUPER,
                className,
                null,
                "java/lang/Object",
                null);

        cw.visitSource(className, null);
        Method m = Method.getMethod("void main (String[])");
        mv = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);
    }
    public void postVisit(Program p) {
        mv.returnValue();
        mv.visitMaxs(1, 1);
        mv.visitEnd();
        cw.visitEnd();
        try {
            //convert array of bytes into file
            FileOutputStream fileOuputStream = new FileOutputStream(className + ".class");
            fileOuputStream.write(cw.toByteArray());
            fileOuputStream.close();
        } catch (java.io.IOException e) {
            System.out.println("Error en la grabación del archivo.");
        }
    }
    public boolean preVisit(Statement.Assign i){
        return true;
    }
    public void postVisit(Statement.Assign i){
        mv.storeLocal(addressTable.get(i.lhs));
    }
    public boolean preVisit(Statement.Write i){
        return true;
    }
    public void postVisit(Statement.Write i){
        mv.getStatic(Type.getType(System.class), "out", Type.getType(PrintStream.class));
        if (i.e != null) {
            mv.swap();
            mv.invokeVirtual(Type.getType(PrintStream.class), Method.getMethod("void println (int)"));
        } else {
            mv.push(i.s);
            mv.invokeVirtual(Type.getType(PrintStream.class), Method.getMethod("void println (String)"));
        }
    }
    public void visit(Statement.Read i) {
        if (i.message != null) {
            Statement.write(i.message).accept(this);
        }
        mv.newInstance(Type.getType(java.util.Scanner.class));
        mv.dup();
        mv.getStatic(Type.getType(System.class), "in", Type.getType(InputStream.class));
        mv.invokeConstructor(Type.getType(java.util.Scanner.class), Method.getMethod("void <init> (Ljava/io/InputStream;)"));
        mv.invokeVirtual(Type.getType(java.util.Scanner.class), Method.getMethod("int getInt ()"));
        mv.visitIntInsn(ISTORE, addressTable.get(i.lhs));
    }
    public boolean preVisit(Statement.IfThenElse i){
        if (i.condition.isConstant() && i.condition.getValue()) {
            i.then.accept(this);
            return false;
        }
        else if (i.els != null) {
            i.els.accept(this);
            return false;
        }

        Label l1 = new Label();
        Label l2 = new Label();
        i.condition.accept(this);
        mv.visitJumpInsn(IFEQ, l1);
        i.then.accept(this);
        mv.visitJumpInsn(GOTO, l2);
        mv.visitLabel(l1);
        if (i.els != null)
            i.els.accept(this);
        mv.visitLabel(l2);
        return false;
    }
    public boolean preVisit(Statement.Loop i){
        if (i.condition.isConstant() && !i.condition.getValue())
            return false;
        Label conditionLabel = new Label();
        Label endLabel = new Label();
        context.enterContext(new LoopContext(conditionLabel, endLabel));
        mv.visitLabel(conditionLabel);
        i.condition.accept(this);
        mv.visitJumpInsn(IFEQ, endLabel);
        i.body.accept(this);
        mv.visitJumpInsn(GOTO, conditionLabel);
        mv.visitLabel(endLabel);
        context.exitContext();
        return false;
    }
    public boolean preVisit(Declaration d){
        return true;
    }
    public void postVisit(Declaration d){
        for (Expression.Identifier s : d.varlist) {
            addressTable.put(s.i, mv.newLocal(Type.INT_TYPE));
        }
    }
    public void postVisit(Condition.BUnOp i){

    }
    public void postVisit(Condition.BinCondition i){
        Type type = Type.INT_TYPE;
        Label l1 = new Label();
        Label l2 = new Label();
        switch (i.op) {
            case EQ: mv.ifCmp(type, GeneratorAdapter.EQ, l1); break;
            case NEQ: mv.ifCmp(type, GeneratorAdapter.NE, l1); break;
            case GT: mv.ifCmp(type, GeneratorAdapter.GT, l1); break;
            case GTQ: mv.ifCmp(type, GeneratorAdapter.GE, l1); break;
            case LE: mv.ifCmp(type, GeneratorAdapter.LT, l1); break;
            case LEQ: mv.ifCmp(type, GeneratorAdapter.LE, l1); break;
            default: throw new RuntimeException();
        }
        mv.push(0);
        mv.goTo(l2);
        mv.visitLabel(l1);
        mv.push(1);
        mv.visitLabel(l2);
    }
    public void postVisit(Condition.BBinCondition i){
        Type type = Type.INT_TYPE;
        if (i.op == AND) mv.math(GeneratorAdapter.AND, type);
        if (i.op == OR) mv.math(GeneratorAdapter.OR, type);
    }
    public void visit(Condition.BoolConst d){
        if (d.b) mv.push(1);
        else mv.push(0);
    }
    public void visit(Expression.Identifier d){
        mv.loadLocal(addressTable.get(d.i));
    }
    public void visit(Expression.IntConst d){
        mv.visitLdcInsn(d.i);
    }
    public void postVisit(Expression.Binex i){
        Type type = Type.INT_TYPE;
        switch (i.op) {
            case PLUS: mv.math(GeneratorAdapter.ADD, type); break;
            case MINUS: mv.math(GeneratorAdapter.SUB, type); break;
            case MULT: mv.math(GeneratorAdapter.MUL, type); break;
            case DIV: mv.math(GeneratorAdapter.DIV, type); break;
            case MOD: mv.math(GeneratorAdapter.REM, type); break;
        }
    }
    public void postVisit(Expression.Unex i){
        Type type = Type.INT_TYPE;
        switch (i.op) {
            case UMINUS:
                mv.math(UMINUS, type);
                break;
            case ADDONE:
            case SUBONE:
                String id = ((Expression.Identifier) i.e1).i;
                mv.loadLocal(addressTable.get(id));
                if (i.isPrev)
                    mv.dup();
                mv.push(1);
                mv.math(i.op, type);
                if (!i.isPrev)
                    mv.dup();
                mv.storeLocal(addressTable.get(id));
                break;
            default: throw new RuntimeException();
        }

    }
    public void visit (Statement.AssUnop i) {
        Type type = Type.INT_TYPE;
        switch (i.op) {
            case ADDONE:
                mv.loadLocal(addressTable.get(i.id));
                mv.push(1);
                mv.math(GeneratorAdapter.ADD, type);
                mv.storeLocal(addressTable.get(i.id));
                break;
            case SUBONE:
                mv.loadLocal(addressTable.get(i.id));
                mv.push(1);
                mv.math(GeneratorAdapter.SUB, type);
                mv.storeLocal(addressTable.get(i.id));
                break;
            default: throw new RuntimeException();
        }

    }
    public void visit (Statement.AssBinOp i) {
        Expression.binop(Expression.ident(null, i.id, null), i.op - 16, i.e).accept(this);
        mv.storeLocal(addressTable.get(i.id));
    }
    public boolean preVisit (Statement.ForLoop l) {
        if (l.c.isConstant() && !l.c.getValue())
            return false;
        l.s.accept(this);
        Label startLabel = new Label();
        Label incLabel = new Label();
        Label endLabel = new Label();
        context.enterContext(new LoopContext(incLabel, endLabel));
        mv.visitLabel(startLabel);
        l.c.accept(this);
        mv.visitJumpInsn(IFEQ, endLabel);
        l.body.accept(this);
        mv.visitLabel (incLabel);
        l.f.accept(this);
        mv.visitJumpInsn(GOTO, startLabel);
        mv.visitLabel(endLabel);
        context.exitContext();
        return false;
    }
    public void visit (Statement.Break b) {
        if (!context.isLoop()) throw new RuntimeException();
        mv.goTo(context.getContext().getEnd());
    }
    public void visit (Statement.Continue c) {
        if (!context.isLoop()) throw new RuntimeException();
        mv.goTo(context.getContext().getStart());
    }
}
