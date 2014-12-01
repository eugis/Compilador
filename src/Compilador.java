import java_cup.runtime.ComplexSymbolFactory;

import java_cup.runtime.ComplexSymbolFactory;

import java.io.FileReader;

public class Compilador {
    public static void main(String[] args) throws Exception {
        ComplexSymbolFactory csf = new ComplexSymbolFactory();
        Parser parser_obj = new Parser(new Lexer(new FileReader(args[0]), csf), csf);
        Program parse_tree;
        String className;
        if (args[0].contains(".")) {
            className = args[0].substring(0, args[0].lastIndexOf('.'));
        } else {
            className = args[0];
        }
        try {
            parse_tree = (Program) parser_obj.parse().value;
            parse_tree.accept(new CodeGenerator(className));
        } catch (Exception e) {throw e;}
    }

}
