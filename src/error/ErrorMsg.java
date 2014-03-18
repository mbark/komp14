package error;

import java.io.PrintStream;

public class ErrorMsg {
    private boolean anyErrors;
    private PrintStream out;

    public ErrorMsg(PrintStream o) {
        anyErrors = false;
        out = o;
    }

    public void complain(String msg) {
        anyErrors = true;
        out.println(msg);
    }

    public boolean hasAnyErrors() {
        return anyErrors;
    }

}
