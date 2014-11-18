/**
 * Created by santi698 on 18/11/14.
 */
public class Node {
    private StringBuilder codeBuilder;
    public void setCode(String code) {
        this.codeBuilder = new StringBuilder(code);
    }
    public void appendCode(String code) {
        this.codeBuilder.append(code);
    }
    public void prependCode(String code) {
        this.codeBuilder.insert(0,code);
    }
    public String getCode() {
        return codeBuilder.toString();
    }
}