package canon;

class ExpCall extends tree.Stm {
    tree.CALL call;

    ExpCall(tree.CALL c) {
        call = c;
    }

    public tree.ExpList kids() {
        return call.kids();
    }

    public tree.Stm build(tree.ExpList kids) {
        return new tree.EXP(call.build(kids));
    }
}