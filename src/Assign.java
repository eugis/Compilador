/**
 * Created by mariaeugenia on 18/11/14.
 */
public class Assign extends Statment {
    private Variable identifier;

    public Assign(Variable identifier) {
        this.identifier = identifier;
    }

    public Variable getIdentifier(){
        return identifier;
    }

}
