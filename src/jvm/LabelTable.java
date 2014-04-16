package jvm;

import java.util.HashMap;

public class LabelTable {
    private HashMap<String, Integer> table;
    
    public LabelTable() {
        table = new HashMap<>();
    }
    
    public String newLabel(String name) {
        Integer i = table.get(name);
        if(i == null) {
            i = 0;
        } else {
            i++;
        }
        
        table.put(name, i);
        return "label_" + name + i;
    }
}
