package foxz.cli;

import java.util.LinkedList;

public class MethodArgs {
    public LinkedList<String>args;
    public boolean stop=false;
    public int idx=0;
    public boolean hasEnd(){
        return idx>args.size();
    }
    public String getArg(){
        if (hasEnd()) return null;        
        return args.get(idx);
    }
    public void consume(){
        if (idx<args.size()) args.remove(idx);
    }
    public MethodArgs(LinkedList<String> args,int idx,boolean stop){
        this.args=args;
        this.idx=idx;
        this.stop=stop;
    }
}
