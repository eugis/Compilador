/**
 * Created by santi698 on 18/11/14.
 */
public class Variable {
    private String name;
    private String tipo;
    private Object valor;
    public Variable (String nombre, String tipo, Object valor) {
        this.name = nombre;
        this.tipo = tipo;
        this.valor = valor;
    }
    public Variable (String nombre) {
        this.name = nombre;
    }
}
