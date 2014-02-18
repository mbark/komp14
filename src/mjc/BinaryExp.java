package mjc;
public class BinaryExp extends Exp {
    String op;
    Exp left, right;
    public BinaryExp(String o, Exp l, Exp r) {op = o; left = l; right = r;}
    public String toString() {return "(" + op + " " + left + " " + right + ")";}
}
