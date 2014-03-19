package symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import syntaxtree.Type;

public class MethodTable {
    private Symbol id;
    private Type returnType;

    private List<Param> params;
    private HashMap<Symbol, Type> locals;

    public MethodTable(Symbol id, Type returnType) {
        this.id = id;
        this.returnType = returnType;

        params = new ArrayList<>();
        locals = new HashMap<Symbol, Type>();
    }

    public Symbol getId() {
        return id;
    }

    public Type getReturnType() {
        return returnType;
    }

    public boolean putParam(Symbol key, Type value) {
        Param param = new Param(key, value);
        if (containsParam(key)) {
            return false;
        }
        
        params.add(param);
        return true;
    }
    
    private static final class Param {
        Symbol key;
        Type value;
        private Param(Symbol key, Type value) {
            this.key = key;
            this.value = value;
        }
    }

    public Type getParam(Symbol key) {
        for(Param param : params) {
            if(param.key.equals(key)) {
                return param.value;
            }
        }
        return null;
    }
    
    private boolean containsParam(Symbol key) {
        for(Param param : params) {
            if(param.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public boolean putLocal(Symbol key, Type value) {
        if (containsParam(key) || locals.containsKey(key)) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MethodTable");
        sb.append("(" + returnType + ")");
        sb.append("<param=");
        sb.append(params.toString());
        sb.append(", locals=");
        sb.append(locals.toString());
        sb.append(">");
        return sb.toString();
    }
}
