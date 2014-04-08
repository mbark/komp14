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
import tree.Stm;
import tree.TreePrinter;
import tree.TreeViewer;
import visitor.JVMVisitor;
import visitor.TreeBuilderVisitor;
import visitor.TypeCheckVisitor;
import visitor.TypeDefVisitor;
import error.ErrorMsg;

public class JVMMain {

    private static class Options {
        public boolean generateAssemblyCode = false;
        public boolean printSyntaxTree = false;
        public boolean printSymbolTable = false;
        public boolean printIntermediateRepresentation = false;
        public boolean viewIntermediateRepresentation = false;
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
            case "-ir":
                printIntermediateRepresentation = true;
                break;
            case "-tv":
                viewIntermediateRepresentation = true;
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

        // Build IR
        frame.Factory factory = new sparc.Factory();
        TreeBuilderVisitor treeBuilder = new TreeBuilderVisitor(pt, factory);
        Stm stm = treeBuilder.visit(p);

        // Print IR
        if (options.printIntermediateRepresentation) {
            new TreePrinter(System.err).prStm(stm);
        }

        // View IR
        if (options.viewIntermediateRepresentation) {
            TreeViewer viewer = new TreeViewer(sourceFile.getName());
            viewer.addStm(stm);
            viewer.expandTree();
        }

        // Generate assembly
        if (options.generateAssemblyCode) {
            String assemblyCode = "";

            // ... for JVM
            if (options.target == Target.JVM) {
                JVMVisitor jvmVisitor = new JVMVisitor(pt, new jvm.Factory());
                assemblyCode = jvmVisitor.visit(p);
            }

            // Write assembly to file
            File assemblyFile = new File("./"
                    + sourceFile.getName().replace(".java", ".s"));
            try {
                PrintWriter pw = new PrintWriter(assemblyFile);
                pw.write(assemblyCode);
                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
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
