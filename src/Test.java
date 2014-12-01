import com.judoscript.jamaica.BCELJavaClassCreator;
import com.judoscript.jamaica.JavaClassCreator;

import java.lang.reflect.Modifier;

/**
 * Created by santi698 on 30/11/14.
 */
public class Test {
    public static void main (String[] argv) throws Exception {
        JavaClassCreator creator = new BCELJavaClassCreator();
        creator.startClass(Modifier.PUBLIC, "A", null, null);
        creator.startMethod(Modifier.PUBLIC | Modifier.STATIC, "main", new String[]{"java.lang.String[]"}, new String[]{"args"}, "void", null);
        creator.macroPrint("println", null, new Object[]{"Hola mundo!"});
        creator.endMethod();
        creator.endClassToFile("A.class");
    }
}
