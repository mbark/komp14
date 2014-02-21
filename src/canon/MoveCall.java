package canon;

class MoveCall extends tree.Stm {
    tree.TEMP dst;
    tree.CALL src;

    MoveCall(tree.TEMP d, tree.CALL s) {
        dst = d;
        src = s;
    }

    public tree.ExpList kids() {
        return src.kids();
    }

    public tree.Stm build(tree.ExpList kids) {
        return new tree.MOVE(dst, src.build(kids));
    }
}