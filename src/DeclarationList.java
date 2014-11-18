import com.sun.javafx.css.Declaration;

/**
 * Created by mariaeugenia on 18/11/14.
 */
public class DeclarationList extends Expression {
    //No se que tipo sería la expresión. Va a haber un tipo para delcaración?
    private Declaration d; //por como está la gramática se exige al menos una declaración.
    private DeclarationList dl;

    public DeclarationList(Declaration d){
        this.d = d;
        this.dl = null;
    }

    public void setDeclarationList(DeclarationList dl){
        this.dl = dl;
    }

    public DeclarationList getDeclarationList(){
        return dl;
    }

    public Declaration getDeclaration(){
        return d;
    }
}
