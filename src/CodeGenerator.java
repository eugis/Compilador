import com.judoscript.jamaica.BCELJavaClassCreator;
import com.judoscript.jamaica.JavaClassCreator;

import java.lang.reflect.Modifier;

/**
 * Created by santi698 on 27/11/14.
 */
public class CodeGenerator extends ASTVisitor {
    private JavaClassCreator creator;
    public CodeGenerator(String filename) throws java.io.IOException, com.judoscript.jamaica.JavaClassCreatorException {
        /* Cómo generar la clase:
        public class AClass {
            public static void main(String[] args) {
                byte y = 10;
                byte x = 5;
                if(x < y) {
                    System.out.println("5");
                }
            }
        }
        */

        this.creator = new BCELJavaClassCreator();
        creator.startClass(Modifier.PUBLIC, "AClass", null, null);
        creator.startMethod(Modifier.PUBLIC | Modifier.STATIC, "main", new String[]{"java.lang.String[]"}, new String[]{"args"}, "void", null);
        creator.addLocalVariable("x", "int", null);
        creator.addLocalVariable("y", "int", null);
        creator.macroSet("y",10);
        creator.macroSet("x", 5);
        creator.macroIf("<", creator.createVarAccess("x"), creator.createVarAccess("y"), "a", false);
        creator.macroPrint("println", null, new Object[]{"5"});
        creator.macroEndIf("a");
        creator.inst_return();
        creator.endMethod();
        creator.endClassToFile(filename);
    }
    public static void main (String[] args) throws Exception {
        new CodeGenerator("AClass.class");
    }
}
