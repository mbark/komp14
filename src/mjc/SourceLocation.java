package mjc;

public class SourceLocation {
    public int beginLine;
    public int beginColumn;
    public int endLine;
    public int endColumn;

    public SourceLocation(int beginLine, int beginColumn, int endLine,
            int endColumn) {
        this.beginLine = beginLine;
        this.beginColumn = beginColumn;
        this.endLine = endLine;
        this.endColumn = endColumn;
    }

    @Override
    public String toString() {
        return String.format("%d:%d-%d:%d", beginLine, beginColumn, endLine,
                endColumn);
    }
}