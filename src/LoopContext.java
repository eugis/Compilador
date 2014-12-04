import org.objectweb.asm.Label;

/**
 * Created by santi698 on 03/12/14.
 */
public class LoopContext extends Context {
    private Label start;
    private Label end;

    public LoopContext(Label start, Label end) {
        this.start = start;
        this.end = end;
    }

    public Label getStart() {
        return start;
    }

    public Label getEnd() {
        return end;
    }

    public boolean isLoop() {
        return true;
    }
}
