package symbol;

import java.util.HashMap;
import java.util.Iterator;

import syntaxtree.Type;

public class ClassTable {
    private Symbol id;
    private HashMap<Symbol, Type> fields;
    private HashMap<Symbol, MethodTable> methods;
    private Symbol superClass;

    public ClassTable(Symbol id) {
        this.id = id;

        fields = new HashMap<Symbol, Type>();
        methods = new HashMap<Symbol, MethodTable>();
    }

    public Symbol getId() {
        return id;
    }

    public int getNrOfFields() {
        return fields.size();
    }

    public void setSuperClass(Symbol name) {
        superClass = name;
    }

    public Symbol getSuperClass() {
        return superClass;
    }

    public boolean put(Symbol key, MethodTable value) {
        if (methods.containsKey(key)) {
            return false;
        }
        methods.put(key, value);
        return true;
    }

    public MethodTable getMethod(Symbol key) {
        return methods.get(key);
    }

    public boolean put(Symbol key, Type value) {
        if (fields.containsKey(key)) {
            return false;
        }
        fields.put(key, value);
        return true;
    }

    public Type getField(Symbol key) {
        return fields.get(key);
    }

    public Iterator<Symbol> keys() {
        // TODO: Proper iteration?
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ClassTable");
        sb.append("<fields=");
        sb.append(fields.toString());
        sb.append(", methods=");
        sb.append(methods.toString());
        sb.append(">");
        return sb.toString();
    }

}
