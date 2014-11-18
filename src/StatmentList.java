/**
 * Created by mariaeugenia on 18/11/14.
 */
public class StatmentList extends Node{
    private StatmentList sl;
    private Statment s;

    public StatmentList(Statment s){
        this.s = s;
        this.sl = null;
    }

    public StatmentList getStatmentList(){
        return sl;
    }

    public Statment getStatment(){
        return s;
    }

    public void setStatmentList(StatmentList sl){
        this.sl = sl;
    }
}
