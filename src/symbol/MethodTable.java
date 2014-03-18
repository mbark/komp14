package symbol;

import java.util.HashMap;
import java.util.Iterator;

import syntaxtree.Type;

public class MethodTable {
    private Symbol id;
    private Type returnType;

    private HashMap<Symbol, Type> params;
    private HashMap<Symbol, Type> locals;
    
    public MethodTable(Symbol id, Type returnType) {
        this.id = id;
        this.returnType = returnType;

        params = new HashMap<Symbol, Type>();
        locals = new HashMap<Symbol, Type>();
    }
    
    public Symbol getId() {
        return id;
    }
    
    public Type getReturnType() {
        return returnType;
    }
    
    public boolean putParam(Symbol key, Type value) {
        if(params.containsKey(key)) {
            return false;
        }
        params.put(key, value);
        return true;
    }

    public Type getParam(Symbol key) {
        return params.get(key);
    }
    
    public boolean putLocal(Symbol key, Type value) {
        if(locals.containsKey(key)) {
            return false;
        }
        locals.put(key, value);
        return true;
    }

    public Type getLocal(Symbol key) {
        return locals.get(key);
    }

    public Iterator<Symbol> keys() {
     // TODO: Proper iteration?
        return null;
    }
}
