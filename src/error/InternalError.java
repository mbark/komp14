package error;

public class InternalError extends Error {
    private String msg;

    public InternalError(String s) {
        msg = "Internal compiler error!\n" + s;
    }

    public String toString() {
        return msg;
    }
}
