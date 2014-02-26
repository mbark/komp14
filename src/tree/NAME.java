package tree;

import temp.Label;

public class NAME extends AbstractExp {
    public Label label;

    public NAME(Label l) {
        label = l;
    }

    public ExpList kids() {
        return null;
    }

    public AbstractExp build(ExpList kids) {
        return this;
    }
}
