/**
 * Created by mariaeugenia on 18/11/14.
 */
public class IdentifierList extends Expression{
    private IdentifierList il;
    private Variable identifier;

    public IdentifierList(Variable identifier){
        this.identifier = identifier;
        setType(identifier.getTipo());
        il = null;
    }

    public Variable getIdentifier(){
        return identifier;
    }

    public IdentifierList getIdentifierList(){
        return il;
    }

    public void setIdentifierList(IdentifierList il) throws Exception {
        if(!il.getType().equals(identifier.getTipo())){
            throw new Exception("tipos no compatibles");
        }
        this.il = il;
    }
}
