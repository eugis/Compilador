import com.judoscript.jamaica.BCELJavaClassCreator;
import com.judoscript.jamaica.JavaClassCreator;
import com.judoscript.jamaica.JavaClassCreatorException;

import java.lang.reflect.Modifier;

/**
 * Created by santi698 on 27/11/14.
 */
public class CodeGenerator extends ASTVisitor implements Constants {
    private JavaClassCreator creator;
    private String filename;
    public CodeGenerator(String filename) throws java.io.IOException, com.judoscript.jamaica.JavaClassCreatorException {
        this.filename = filename;
        this.creator = new BCELJavaClassCreator();
        creator.startClass(Modifier.PUBLIC, "AClass", null, null);
        creator.startMethod(Modifier.PUBLIC | Modifier.STATIC, "main", new String[]{"java.lang.String[]"}, new String[]{"args"}, "void", null);
    }
    public static void main (String[] args) throws Exception {
        new CodeGenerator("AClass.class");
    }
    public void postVisit(Program p) {
        try {
            creator.endClassToFile(filename);
        }
        catch (Exception e) {;}

    }
    public boolean preVisit(Statement.Assign i){
        return true;
    }
    public void postVisit(Statement.Assign i){
        try {
            creator.addLocalVariable(i.lhs, "int", null);
            creator.inst_istore(i.lhs);
        }
        catch (Exception e) {}
    }
    public boolean preVisit(Statement.Write i){
        return true;
    }
    public void postVisit(Statement.Write i){
        try {
            creator.inst_invokevirtual("System.out", "println", new String[]{"String"}, "void");
        } catch (Exception e) {}
    }

    public boolean preVisit(Statement.IfThenElse i){
        /* TODO
        i.condition.accept(this);
        creator.inst_;
        i.then.accept(this);
        if (i.els!=null)
            writer.println("JUMP IF"+end);
        writer.print("IF"+e+":");
        if(i.els!=null) {
            i.els.accept(this);
            writer.println("IF"+end+":");
        }
        return false;
        */
        return true;
    }
    public boolean preVisit(Statement.Loop i){
        /* TODO
        int loop = loopcounter++;
        int loopexit = loopcounter++;
        writer.println("//"+ i.cond+" ??" );
        writer.print("L"+ loop +":" );
        i.cond.accept(this);
        writer.println("FJUMP L"+loopexit);
        i.body.accept(this);
        writer.println("JUMP L"+loop);
        writer.print("L"+loopexit+":");
        return false;
        */
        return true;
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
        //TODO
        if (i.op==LEQ) ;
        if (i.op==LE) ;
        if (i.op==GTQ) ;
        if (i.op==GT) ;
        if (i.op==EQ) ;
        if (i.op==NEQ) ;
    }
    public void postVisit(Condition.BBinCondition i){
        //TODO
        if (i.op==AND) ;
        if (i.op==OR) ;
    }
    public void visit(Condition.BoolConst d){
        try {
            creator.inst_ldc(d.b);
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
