package foxz.cli;

import foxz.cli.annotations.Par;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map.Entry;

// Process order : 
// Field up to down ordered from declaration
// Method up to down ordered from declaration

public class Testclassic {
    
    @Par(name="",help="1st arg") // <-- if u want optional nopar => declare last
    public String nopar; 
    
    @Par(name = "-s", help="String")
    public String testString; //="foo" allowed (default)
    
    @Par(name = "-b", help="Boolean")
    public Boolean testBool=false; 
    
    @Par(name="-m", help="Method")
    public MethodArgs testMethod(MethodArgs args){ // WIP !!!
        System.out.format("testMethode called with:%s\n--- snapshot at call---\n",args.getArg());
        args.consume();
        show();    // args.stop=true // stop parsing  
        return args; 
    }
    
    @Par(name="-l",help="Cumulatif list")
    List<String>testList; // <--- cumulation into list each time par usage //if =null ArrayList used 
    
    private void show(){
        System.out.format("NoPar:%s\nBoolean: %s\nList:%s\nString:%s\n\n", nopar,testBool,testList,testString);
    }
    
   @Par(name="",help="2d arg (optionnal)")
    public MethodArgs testnopar(MethodArgs s){ // WIP !!!
        System.out.println("Option nopar called with");        
        while(!s.hasEnd()){ // = true if no args in buffer
            System.out.println(s.getArg()); // current args
            // check parameter, if Ok call args.consume
            s.consume(); // throw from buffer
        }
        System.out.println();
        return s; 
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
        Testclassic test=new Testclassic();       
        test.run(args);
    }
    
}
