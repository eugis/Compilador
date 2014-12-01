import java_cup.runtime.ComplexSymbolFactory;

import java_cup.runtime.ComplexSymbolFactory;

import java.io.FileReader;

public class Compilador {
    public static void main(String[] args) throws Exception {
        ComplexSymbolFactory csf = new ComplexSymbolFactory();
        Parser parser_obj = new Parser(new Lexer(new FileReader(args[0]), csf), csf);
        Program parse_tree;
        try {
            parse_tree = (Program) parser_obj.parse().value;
            parse_tree.accept(new CodeGenerator(args[0]));
        } catch (Exception e) {throw e;}
    }

}
