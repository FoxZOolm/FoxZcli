package foxz.cli;

import foxz.cli.annotations.Arg;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map.Entry;

// Process order : 
// Field up to down ordered from declaration
// Method up to down ordered from declaration

public class Testclassic {
    
    @Arg(name="",help="1st arg") // <-- if u want optional nopar => declare last
    public String nopar; 
    
    @Arg(name = "-s", help="String", require=true)
    public String testString; //="foo" allowed (default)
    
    @Arg(name = "-b", help="Boolean")
    public Boolean testBool=false; 
    
    @Arg(name="-m", help="Method")
    public MethodArgs testMethod(MethodArgs args){ // WIP !!!
        System.out.format("testMethode called with:%s\n--- snapshot at call---\n",args.getArg());
        args.consume();
        show();    // args.stop=true // stop parsing  
        return args; 
    }
    
    @Arg(name="-l",help="Cumulatif list")
    List<String>testList; // <--- cumulation into list each time par usage //if =null ArrayList used 
    
    private void show(){
        System.out.format("NoPar:%s\nBoolean: %s\nList:%s\nString:%s\n\n", nopar,testBool,testList,testString);
    }
    
   @Arg(name="",help="2d arg (optionnal)")
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
        Help err=Cli.proceed(this, args);
        if (err!=null){
            System.out.format("error '%s'\n", err.toString(new AbsHelp() {				
				@Override
				public String Help(String name, String help, String d, Boolean r) {					
					return String.format("%s %s", name,d);
				}
			}));
            System.out.println("--- Help ---");
                        
        Cli.getHelp(this).help(new AbsHelp() {			
			@Override
			public String Help(String name, String help, String d, Boolean r) {
				System.out.format("%s = %s", name,help);
				if (!d.isEmpty()) System.out.format(" default='%s'", d);
				if(r) System.out.print(" REQUIRED");
				System.out.println();
				return null;
			}
		});
        }else {
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
