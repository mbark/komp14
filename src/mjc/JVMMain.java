package mjc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import symbol.ProgramTable;
import syntaxtree.Program;
import syntaxtree.SyntaxTreePrinter;
import visitor.JVMVisitor;
import visitor.TypeCheckVisitor;
import visitor.TypeDefVisitor;
import error.ErrorMsg;

public class JVMMain {

    private static class Options {
        public boolean generateAssemblyCode = false;
        public boolean printSyntaxTree = false;
        public boolean printSymbolTable = false;
        public Target target = Target.JVM;

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
        List<File> sourceFiles = new LinkedList<File>();
        Options options = new Options();

        // Parse arguments
        for (int i = 0; i < args.length; i++) {
            if (!args[i].startsWith("-")) {
                sourceFiles.add(new File(args[i]));
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

        // Iterate source files
        for (File sourceFile : sourceFiles) {
            compileFile(sourceFile, options);
        }
    }

    private static void compileFile(File sourceFile, Options options) {
        // Parse
        Program p = parseFile(sourceFile);

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

        // Generate and write assembly
        if (options.generateAssemblyCode) {
            if (options.target == Target.JVM) {
                generateAndWriteJVMAssembly(p, pt);
            } else {
                // Write assembly to file
                File assemblyFile = new File("./"
                        + sourceFile.getName().replace(".java", ".s"));
                try {
                    PrintWriter pw = new PrintWriter(assemblyFile);
                    pw.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace(System.err);
                    System.exit(1);
                }
            }
        }
    }

    private static void generateAndWriteJVMAssembly(Program p, ProgramTable pt) {
        JVMVisitor jvmVisitor = new JVMVisitor(pt, new jvm.Factory());
        jvmVisitor.visit(p);
    }

    private static Program parseFile(File sourceFile) {
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
        return p;
    }

    private static void printUsage(PrintStream out) {
        out.println("Usage: java mjc.JVMMain <source files> <options>");
        out.println("");
        out.println("\t-S\tGenerate assembly code");
        out.println("\t-ast\tPrint abstract syntax tree");
        out.println("\t-sym\tPrint symbol table");
        out.println("\t-ir\tPrint intermediate representation");
        out.println("\t-tv\tView intermediate representation");
    }

}
