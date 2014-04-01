package frame;

import syntaxtree.FormalList;
import syntaxtree.Type;

/**
 * Implement a factory to return VMFrame and VMRecord objects for the
 * appropriate architecture, e.g., jvm.Factory.
 */
public interface VMFactory {
    public abstract VMFrame newFrame(String methodName, FormalList formals,
            Type returnType);

    public abstract VMRecord newRecord(String name);
}
