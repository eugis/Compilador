import java_cup.runtime.Symbol;

import java.io.FileReader;


/**
 * Created by santi698 on 06/11/14.
 */
public class Compilador {
    public static void main(String[] args) throws Exception {
          /* create a parsing object */
        //Parser parser_obj = new Parser(new Lexer(new FileReader(args[0])));
        parser parser_obj = new parser(new Lexer(new FileReader(args[0]))); //lo cambie porque no compilaba, no se si te referias a esto.
      /* open input files, etc. here */
        Node parse_tree = null;
        boolean do_debug_parse = false;
        try {
            if (do_debug_parse)
                parse_tree = (Node) parser_obj.debug_parse().value;
            else
                parse_tree = (Node) parser_obj.parse().value;
            /* Acá tendríamos el árbol */

        } catch (Exception e) {
        /* do cleanup here - - possibly rethrow e */
        } finally {
	/* do close out here */
        }
    }

}
