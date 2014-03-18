package symbol;

import java.util.HashMap;
import java.util.Iterator;

public class ProgramTable {
    private HashMap<Symbol, ClassTable> classes;

    public ProgramTable() {
        classes = new HashMap<Symbol, ClassTable>();
    }

    public boolean put(Symbol key, ClassTable value) {
        if (classes.containsKey(key)) {
            return false;
        }
        classes.put(key, value);
        return true;
    }

    public ClassTable get(Symbol key) {
        return classes.get(key);
    }

    public Iterator<Symbol> keys() {
        // TODO: Proper iteration?
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ProgramTable");
        sb.append("<classes=");
        sb.append(classes.toString());
        sb.append(">");
        return sb.toString();
    }
}