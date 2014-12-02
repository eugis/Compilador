import org.objectweb.asm.*;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class CodeGenerator extends ASTVisitor implements Opcodes, Constants {
    private ClassWriter cw;
    private GeneratorAdapter mv;
    private Map<String, Integer> addressTable;
    private String className;

    public CodeGenerator(String className) throws java.io.IOException, com.judoscript.jamaica.JavaClassCreatorException {
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
        mv.visitInsn(RETURN);
        mv.visitMaxs(10, 10);
        mv.visitEnd();
        mv.visitMaxs(100, 100);
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
            mv.visitInsn(SWAP);
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
        mv.visitTypeInsn(NEW, "java/util/Scanner");
        mv.visitInsn(DUP);
        mv.visitFieldInsn(
                Opcodes.GETSTATIC,
                "java/lang/System",
                "in",
                "Ljava/io/InputStream;"
        );
        mv.visitMethodInsn(INVOKESPECIAL,
                "java/util/Scanner",
                "<init>",
                "(Ljava/io/InputStream;)V",
                false
        );
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "java/util/Scanner",
                "nextInt",
                "()I",
                false
        );

        mv.visitIntInsn(ISTORE, addressTable.get(i.lhs));
    }
    public boolean preVisit(Statement.IfThenElse i){
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
        Label conditionLabel = new Label();
        Label endLabel = new Label();
        mv.visitLabel(conditionLabel);
        i.condition.accept(this);
        mv.visitJumpInsn(IFEQ, endLabel);
        i.body.accept(this);
        mv.visitJumpInsn(GOTO, conditionLabel);
        mv.visitLabel(endLabel);
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
        Label l1 = new Label();
        Label l2 = new Label();
        switch (i.op) {
            case EQ: mv.visitJumpInsn(IF_ICMPEQ, l1); break;
            case NEQ: mv.visitJumpInsn(IF_ICMPNE, l1); break;
            case GT: mv.visitJumpInsn(IF_ICMPGT, l1); break;
            case GTQ: mv.visitJumpInsn(IF_ICMPGE, l1); break;
            case LE: mv.visitJumpInsn(IF_ICMPLT, l1); break;
            case LEQ: mv.visitJumpInsn(IF_ICMPLE, l1); break;
            default: break;
        }
        mv.visitInsn(ICONST_0);
        mv.visitJumpInsn(GOTO, l2);
        mv.visitLabel(l1);
        mv.visitInsn(ICONST_1);
        mv.visitLabel(l2);
    }
    public void postVisit(Condition.BBinCondition i){
        if (i.op == AND) mv.visitInsn(IAND);
        if (i.op == OR) mv.visitInsn(IOR);
    }
    public void visit(Condition.BoolConst d){
        if (d.b) mv.visitInsn(ICONST_1);
        else mv.visitInsn(ICONST_0);
    }
    public void visit(Expression.Identifier d){
        mv.loadLocal(addressTable.get(d.i));
    }
    public void visit(Expression.IntConst d){
        mv.visitLdcInsn(d.i);
    }
    public void postVisit(Expression.Binex i){
        switch (i.op) {
            case PLUS: mv.visitInsn(IADD); break;
            case MINUS: mv.visitInsn(ISUB); break;
            case MULT: mv.visitInsn(IMUL); break;
            case DIV: mv.visitInsn(IDIV); break;
            case MOD: mv.visitInsn(IREM); break;
        }
    }
    public void postVisit(Expression.Unex i){
        mv.visitInsn(INEG);
    }
}
