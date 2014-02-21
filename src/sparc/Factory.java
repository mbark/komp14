package sparc;
import java.util.List;

public class Factory implements frame.Factory
{
    public frame.Frame newFrame(temp.Label name, List<Boolean> formals)
    { return new sparc.Frame(name, formals);  }

    public frame.Record newRecord(String name)  
    { return new sparc.Record(name); }

    
}


    
    
