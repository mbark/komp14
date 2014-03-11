package mjc;

import java.io.StringReader;

import syntaxtree.Program;
import syntaxtree.SyntaxTreePrinter;

public class JVMMain {

    public static void main(String[] args) {
        try {
            Program p = new Parser(new StringReader(args[0])).Program();
            SyntaxTreePrinter stp = new SyntaxTreePrinter(System.out);
            stp.visit(p);
        } catch (Throwable e) {
            System.out.println("Syntax check failed: " + e.getMessage());
            System.exit(1);
        }
    }

}
