package mjc;

import java.io.StringReader;

import symbol.TypeCheckPhaseOneVisitor;
import syntaxtree.Program;

public class JVMMain {

    public static void main(String[] args) {
        try {
            Program p = new Parser(new StringReader(args[0])).Program();
            TypeCheckPhaseOneVisitor stp = new TypeCheckPhaseOneVisitor();
            stp.visit(p);
        } catch (Throwable e) {
            System.out.println("Syntax check failed: " + e.getMessage());
            e.printStackTrace(System.out);
            System.exit(1);
        }
    }

}
