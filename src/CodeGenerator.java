import com.judoscript.jamaica.BCELJavaClassCreator;
import com.judoscript.jamaica.JavaClassCreator;
import com.judoscript.jamaica.JavaClassCreatorException;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Scanner;

/**
 * Created by santi698 on 27/11/14.
 */
public class CodeGenerator extends ASTVisitor implements Constants {
    private JavaClassCreator creator;
    private String filename;
    private Integer ifNumber;
    private Integer whileNumber;
    public CodeGenerator(String filename) throws java.io.IOException, com.judoscript.jamaica.JavaClassCreatorException {
        this.ifNumber = new Integer(1);
        this.whileNumber = new Integer(1);
        this.filename = filename;
        this.creator = new BCELJavaClassCreator();
        creator.startClass(Modifier.PUBLIC, filename, null, null);
        creator.startMethod(Modifier.PUBLIC | Modifier.STATIC, "main", new String[]{"java.lang.String[]"}, new String[]{"args"}, "void", null);
    }
    public void postVisit(Program p) {
        try {
            creator.inst_return();
            creator.endMethod();
            creator.endClassToFile(filename + ".class");
            System.out.println(filename + ".class");
        }
        catch (JavaClassCreatorException e) {System.out.println(e.getLocalizedMessage());}
        catch (IOException e) {System.out.println(e.getMessage());}
    }
    public boolean preVisit(Statement.Assign i){
        return true;
    }
    public void postVisit(Statement.Assign i){
        try {
            creator.inst_istore(i.lhs);
        }
        catch (Exception e) {}
    }
    public boolean preVisit(Statement.Write i){
        return true;
    }
    public void postVisit(Statement.Write i){
        try {
            creator.inst_getstatic("java.lang.System", "out", "java.io.PrintStream");
            if (i.e != null) {
                creator.inst_swap();
                creator.inst_invokevirtual("java.io.PrintStream", "println", new String[]{"int"}, "void");
            }
            else {
                creator.inst_ldc(i.s);
                creator.inst_invokevirtual("java.io.PrintStream", "println", new String[]{"java.lang.String"}, "void");
            }
        } catch (Exception e) {}
    }
    public void visit(Statement.Read i) {
        try {
            creator.inst_new("java.util.Scanner");
            creator.inst_dup();
            creator.inst_getstatic("java.lang.System", "in", "java.io.InputStream");
            creator.inst_invokespecial("java.util", "Scanner", new String[] {"java.io.InputStream"}, "void");
            creator.inst_swap();
            creator.inst_invokevirtual("java.util.Scanner", "nextInt", null, "int");
            creator.inst_istore(i.lhs);
        } catch (Exception e) {}
    }
    public boolean preVisit(Statement.IfThenElse i){
        try {
            int e = ifNumber++;
            int end = ifNumber++;
            i.condition.accept(this);
            creator.inst_ldc(0);
            creator.inst_if_icmpeq("IF " + e);
            i.then.accept(this);
            creator.inst_goto("ENDIF " + end);
            creator.setLabel("IF " + e);
            if(i.els!=null) {
                i.els.accept(this);
            }
            creator.setLabel("ENDIF " + end);
        } catch (Exception e) {}
        return false;
    }
    public boolean preVisit(Statement.Loop i){
        try {
            int loop = whileNumber++;
            int loopexit = whileNumber++;
            creator.setLabel("WHILE " + loop);
            i.condition.accept(this);
            creator.inst_ldc(1);
            creator.inst_if_icmpeq("ENDWHILE " + loopexit);
            i.body.accept(this);
            creator.inst_goto("WHILE " + loop);
            creator.setLabel("ENDWHILE " + loopexit);
        } catch (Exception e) {}
        return false;
    }
    public boolean preVisit(Declaration d){
        return true;
    }
    public void postVisit(Declaration d){
        try {
            for (Expression.Identifier s : d.varlist)
                creator.addLocalVariable(s.i, "int", null);
        } catch (Exception e) {}
    }
    public void postVisit(Condition.BUnOp i){

    }
    public void postVisit(Condition.BinCondition i){
        try {
            int pos1 = whileNumber++;
            int pos2 = whileNumber++;
            switch (i.op) {
                case EQ: creator.inst_if_icmpeq("" + pos1); break;
                case NEQ: creator.inst_if_icmpne(""+pos1); break;
                case GT: creator.inst_if_icmpgt("" + pos1); break;
                case GTQ: creator.inst_if_icmpge("" + pos1); break;
                case LE: creator.inst_if_icmplt("" + pos1); break;
                case LEQ: creator.inst_if_icmple("" + pos1); break;
                default: throw new Exception();
            }
            creator.inst_ldc(0);
            creator.inst_goto(""+pos2);
            creator.setLabel("" + pos1);
            creator.inst_ldc(1);
            creator.setLabel(""+pos2);
        } catch (Exception e) {}
    }
    public void postVisit(Condition.BBinCondition i){
        try {
            if (i.op == AND) creator.inst_iand();
            if (i.op == OR) creator.inst_ior();
        } catch (Exception e) {}
    }
    public void visit(Condition.BoolConst d){
        try {
            if (d.b) creator.inst_ldc(1);
            else creator.inst_ldc(0);
        } catch (Exception e) {}
    }
    public void visit(Expression.Identifier d){
        try {
            creator.inst_iload(d.i);
        } catch (Exception e) {}
    }
    public void visit(Expression.IntConst d){
        try {
            creator.inst_ldc(d.i);
        } catch (Exception e) {}
    }
    public void postVisit(Expression.Binex i){
        try {
            if (i.op == PLUS) creator.inst_iadd();
            if (i.op == MINUS) creator.inst_isub();
            if (i.op == MULT) creator.inst_imul();
            if (i.op == DIV) creator.inst_idiv();
            if (i.op == MOD) creator.inst_irem();
        } catch (Exception e) {}
    }
    public void postVisit(Expression.Unex i){
        try {
            creator.inst_ineg();
        } catch (Exception e) {}
    }
}
