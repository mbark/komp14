package syntaxtree;

import java.util.ArrayList;

public class VarDeclList {
    private ArrayList<VarDecl> list;

    public VarDeclList() {
        list = new ArrayList<VarDecl>();
    }

    public void addElement(VarDecl n) {
        list.add(n);
    }

    public VarDecl elementAt(int i) {
        return list.get(i);
    }

    public int size() {
        return list.size();
    }
}
