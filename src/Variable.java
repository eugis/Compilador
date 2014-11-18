/**
 * Created by santi698 on 18/11/14.
 */
public class Variable {
    private String name;
    private Type tipo;
    private Object valor;
    public Variable (String nombre, Type tipo, Object valor) {
        this.name = nombre;
        this.tipo = tipo;
        this.valor = valor;
    }
    public Variable (String nombre) {
        this.name = nombre;
    }

    public Type getTipo(){
        return tipo;
    }
}
