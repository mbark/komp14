package frame;

import java.util.List;

import temp.Label;

/**
 * Implement a factory to return Frame and Record objects for the appropriate
 * architecture, e.g., sparc.Fatory, x86.Factory..
 */
public interface Factory {
    public abstract Frame newFrame(Label name, List<Boolean> formals);

    public abstract Record newRecord(String name);
}
