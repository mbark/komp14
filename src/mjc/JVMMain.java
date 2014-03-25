package mjc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import symbol.ProgramTable;
import syntaxtree.Program;
import syntaxtree.SyntaxTreePrinter;
import visitor.TypeCheckVisitor;
import visitor.TypeDefVisitor;
import error.ErrorMsg;

public class JVMMain {

    private static class Options {
        public boolean generateAssemblyCode = false;
        public boolean printSyntaxTree = false;
        public boolean printSymbolTable = false;

        public boolean parseOption(String option) {
            switch (option) {
            case "-S":
                generateAssemblyCode = true;
                break;
            case "-ast":
                printSyntaxTree = true;
                break;
            case "-sym":
                printSymbolTable = true;
                break;
            default:
                return false;
            }
            return true;
        }
    }

    public static void main(String[] args) {
        List<String> sourceFiles = new LinkedList<String>();
        Options options = new Options();

        // Parse arguments
        for (int i = 0; i < args.length; i++) {
            if (!args[i].startsWith("-")) {
                sourceFiles.add(args[i]);
            } else {
                boolean success = options.parseOption(args[i]);
                if (!success) {
                    printUsage(System.err);
                    System.exit(1);
                }
            }
        }

        // Ensure at least one source file
        if (sourceFiles.isEmpty()) {
            printUsage(System.err);
            System.exit(1);
        }

        for (String sourceFile : sourceFiles) {
            // Parse
            Program p = null;
            try {
                @SuppressWarnings("static-access")
                Program prg = new Parser(new FileReader(sourceFile)).Program();
                p = prg;
            } catch (ParseException e) {
                System.err.println("Syntax check failed: " + e.getMessage());
                e.printStackTrace(System.err);
                System.exit(1);
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + sourceFile);
                System.exit(1);
            }

            // Print syntax tree
            if (options.printSyntaxTree) {
                SyntaxTreePrinter stp = new SyntaxTreePrinter(System.err);
                stp.visit(p);
            }

            // Type check: define types
            ErrorMsg err = new ErrorMsg(System.err);
            TypeDefVisitor tdv = new TypeDefVisitor(err);
            tdv.visit(p);
            if (err.hasAnyErrors()) {
                System.exit(1);
            }

            // Type check: check types
            ProgramTable pt = tdv.getProgramTable();
            TypeCheckVisitor tcv = new TypeCheckVisitor(pt, err);
            tcv.visit(p);
            if (err.hasAnyErrors()) {
                System.exit(1);
            }

            // Print symbol table
            if (options.printSymbolTable) {
                System.err.println(pt);
            }

            // Generate assembly
            if (options.generateAssemblyCode) {
                // TODO: implement this
            }
        }
    }

    private static void printUsage(PrintStream out) {
        out.println("Usage: java mjc.JVMMain <source files> <options>");
        out.println("");
        out.println("\t-S\tGenerate assembly code");
        out.println("\t-ast\tPrint abstract syntax tree");
        out.println("\t-sym\tPrint symbol table");
    }

}
