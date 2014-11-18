/**
 * Created by mariaeugenia on 18/11/14.
 */
public class Program extends Node {
    private DeclarationList dl;
    private StatmentList sl;
    private static Program instance = null;

    public static Program getInstance(){
        if(instance == null){
            instance = new Program();
        }
        return instance;
    }

    public void setDeclarationList(DeclarationList dl){
        this.dl = dl;
    }

    public void setStatmentList(StatmentList sl){
        this.sl = sl;
    }

    public DeclarationList getDeclarationList(){
        return dl;
    }

    public StatmentList getStatmentList(){
        return sl;
    }
}
