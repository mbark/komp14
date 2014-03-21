package mjc;

import java.io.FileReader;

import syntaxtree.Program;
import visitor.TypeDefVisitor;

public class JVMMain {

    public static void main(String[] args) {
        try {
            @SuppressWarnings("static-access")
            Program p = new Parser(new FileReader(args[0])).Program();
            TypeDefVisitor stp = new TypeDefVisitor();
            stp.visit(p);
        } catch (Throwable e) {
            System.out.println("Syntax check failed: " + e.getMessage());
            e.printStackTrace(System.out);
            System.exit(1);
        }
    }

}
