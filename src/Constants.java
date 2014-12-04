import org.objectweb.asm.commons.GeneratorAdapter;

public interface Constants {
    public static int PLUS = GeneratorAdapter.ADD;
    public static int MINUS = GeneratorAdapter.SUB;
    public static int UMINUS = GeneratorAdapter.NEG;
    public static int DIV = GeneratorAdapter.DIV;
    public static int MULT = GeneratorAdapter.MUL;
    public static int MOD = GeneratorAdapter.REM;
    public static int LE = GeneratorAdapter.LT;
    public static int GT = GeneratorAdapter.GT;
    public static int EQ = GeneratorAdapter.EQ;
    public static int LEQ = GeneratorAdapter.LE;
    public static int GTQ = GeneratorAdapter.GE;
    public static int NEQ = GeneratorAdapter.NE;
    public static int AND = GeneratorAdapter.AND;
    public static int OR = GeneratorAdapter.OR;
    public static int INTTYPE = 13;
    public static int ADDONE = GeneratorAdapter.ADD;
    public static int SUBONE = GeneratorAdapter.SUB;
    public static int ADDASS = GeneratorAdapter.ADD;
    public static int SUBASS = GeneratorAdapter.SUB;
    public static int DIVASS = GeneratorAdapter.DIV;
    public static int MULASS = GeneratorAdapter.DIV;
    public static int MODASS = GeneratorAdapter.REM;
}
