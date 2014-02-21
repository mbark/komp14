package frame;

import java.util.List;

public class Proc {
    public String begin, end;
    public List<assem.Instr> body;

    public Proc(String bg, List<assem.Instr> bd, String ed) {
        begin = bg;
        end = ed;
        body = bd;
    }
}
