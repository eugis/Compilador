//import com.judoscript.jamaica.BCELJavaClassCreator;
import com.judoscript.jamaica.ASMJavaClassCreator;
import com.judoscript.jamaica.JavaClassCreator;

import java.lang.reflect.Modifier;

/**
 * Created by santi698 on 30/11/14.
 */
public class Test {
    public static void main (String[] argv) throws Exception {
        //JavaClassCreator creator = new BCELJavaClassCreator();
        JavaClassCreator creator = new ASMJavaClassCreator();
        creator.startClass(Modifier.PUBLIC, "A", null, null);
        creator.startMethod(Modifier.PUBLIC | Modifier.STATIC, "main", new String[]{"java.lang.String[]"}, new String[]{"args"}, "void", null);
        creator.macroIf(">", 1, 2, "1", false);
        creator.macroIf(">", 2, 1, "2", false);
        creator.macroPrint("println", "out", new String[] {"Hola!"});
        creator.macroEndIf("2");
        creator.macroEndIf("1");
        creator.endMethod();
        creator.endClassToFile("A.class");
    }
}
