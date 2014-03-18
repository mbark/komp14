package symbol;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

public class Table {
    private Deque<HashMap<Symbol, Object>> tables;

    public Table() {
        tables = new ArrayDeque<>();
    }

    public void put(Symbol key, Object value) {
        HashMap<Symbol, Object> table = tables.peek();
        table.put(key, value);
    }

    public Object get(Symbol key) {
        for (HashMap<Symbol, Object> table : tables) {
            Object o = table.get(key);
            if (o != null) {
                return o;
            }
        }

        return null;
    }

    public void beginScope() {
        tables.push(new HashMap<Symbol, Object>());
    }

    public void endScope() {
        tables.pop();
    }

    public Enumeration<Symbol> keys() {
        return new KeyEnumeration(tables);
    }

    public static final class KeyEnumeration implements Enumeration<Symbol> {
        Iterator<HashMap<Symbol, Object>> tableIterator;
        Iterator<Symbol> symbolIterator;

        private KeyEnumeration(Deque<HashMap<Symbol, Object>> tables) {
            tableIterator = tables.iterator();
        }

        @Override
        public boolean hasMoreElements() {
            if (symbolIterator == null || !symbolIterator.hasNext()) {
                if (tableIterator.hasNext()) {
                    HashMap<Symbol, Object> nextTable = tableIterator.next();
                    symbolIterator = nextTable.keySet().iterator();
                } else {
                    return false;
                }
            }

            return symbolIterator.hasNext();
        }

        @Override
        public Symbol nextElement() {
            return symbolIterator.next();
        }

    }
}
