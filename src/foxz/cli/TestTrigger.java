package foxz.cli;

import foxz.cli.annotations.Opt;
import foxz.cli.annotations.Arg;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map.Entry;

// Process order : 
// Field up to down ordered from declaration
// Method up to down ordered from declaration

@Opt(trigger = ":") // <--- arg is like <name><:><values ...>
public class TestTrigger {
    
    @Arg(name="",help="1st arg") // <-- if u want optional noarg => declare last
    public String nopar; 
    
    @Arg(name = "-s", help="String")
    public String testString; //="foo" allowed
    
    @Arg(name = "-b", help="Boolean")
    public Boolean testBool=false; 
    
    @Arg(name="-m", help="Method")
    public MethodArgs testMethod(MethodArgs args){ 
        String c1=args.getArg(); // get current arg
        args.consume(); // throw from args list
        System.out.format("testMethode called with:%s\n--- snapshot at call---\n",c1);
        show();      
        return args; 
    }
    
    @Arg(name="-l",help="Cumulatif list")
    List<String>testList; // <--- cumulation into list each time par usage //if =null ArrayList used 
    
    private void show(){
        System.out.format("NoPar:%s\nBoolean: %s\nList:%s\nString:%s\n\n", nopar,testBool,testList,testString);
    }
      
    public void run(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        String err=Cli.proceed(this, args);
        if (err!=null){
            System.out.format("error '%s'\n", err);
            System.out.println("--- Help ---");
            for (Entry h:Cli.getHelp(this).entrySet()){
                System.out.format("%s = %s\n", h.getKey(),h.getValue());
            }
        } else {
            System.out.println("--- show ---");
            show();
            System.out.println(".");
        }
    }
    static public void main(String args[]) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        TestTrigger test=new TestTrigger();  
        args="1 -l:5 -l:6 -m:totor titi tutu".split(" ");
        test.run(args);
    }
    
}

