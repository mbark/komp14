package symbol;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

public class Table {
    private HashMap<Symbol, Object> table;

    public Table() {
        table = new HashMap<>();
    }

    public boolean put(Symbol key, Object value) {
        if(table.containsKey(key)) {
            return false;
        }
        
        table.put(key, value);
        return true;
    }

    public Object get(Symbol key) {
        return table.get(key);
    }

    public Enumeration<Symbol> keys() {
        return new KeyEnumeration(table);
    }

    public static final class KeyEnumeration implements Enumeration<Symbol> {
        Iterator<Symbol> iterator;

        private KeyEnumeration(HashMap<Symbol, Object> table) {
            iterator = table.keySet().iterator();
        }

        @Override
        public boolean hasMoreElements() {
            return iterator.hasNext();
        }

        @Override
        public Symbol nextElement() {
            return iterator.next();
        }

    }
}
