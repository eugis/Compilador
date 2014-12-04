import java.util.Stack;

/**
 * Created by santi698 on 03/12/14.
 */
public class Context {
    private Stack<LoopContext> stack;
    public Context () {
        stack = new Stack<LoopContext>();
    }
    public boolean isLoop() {
        if (stack.isEmpty())
            return false;
        return stack.peek().isLoop();
    }
    public void enterContext(LoopContext c) {
        stack.add(c);
    }
    public void exitContext() {
        stack.pop();
    }
    public LoopContext getContext() {
        return stack.peek();
    }
}