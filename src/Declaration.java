/**
 * Created by mariaeugenia on 18/11/14.
 */
public class Declaration extends Expression {
    private Variable var;
    private IdentifierList il;

    public Declaration(Type t, String nombre){
        setType(t);
        this.var = new Variable(nombre, t, null);
        il = null;
    }

    public Variable getVar(){
        return var;
    }

    public void setIdentifierList(IdentifierList il) throws Exception {
        if (!il.getType().equals(var.getTipo())){
            throw new Exception("Tipos incompatibles");
        }
        this.il = il;
    }

    public IdentifierList getIdentifierList(){
        return il;
    }
}
