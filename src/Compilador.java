import java.io.FileReader;

public class Compilador {
    public static void main(String[] args) throws Exception {
        Parser parser_obj = new Parser(new Lexer(new FileReader(args[0])));
        Program parse_tree;
        try {
            parse_tree = (Program) parser_obj.parse().value;
            parse_tree.accept(new CodeGenerator("miClase.class"));
        } catch (Exception e) {}
    }

}
