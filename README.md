FoxZcli
=======

Java command line parser via annotation (very easy to use !)

--- annotation (target: field or method) ---
@Arg(
  name="...","...",
  help="...", // optionnal
  ignoreCase=true/false //optionnal (but true by default)
  
--- annotation (target: class) ---
@Opt(
  trigger="<...>", // optionnal ("space" by default)
  ignoreCase=true/false // optionnal (but true by default)

--- exemple @Arg ---
class FooClass {
  @Arg(name="-b",help="test boolean")
  private Boolean help=false; // <--- can be null
  
  @Arg(name="-s",help="test String")
  private String n;
  
  @PArg(name="-i", help="test integer")
  private Integer x;   // <--- object recommanded to test if not null
  
  @Arg(name="+l", help="test list") // <--- sign before "name" is totally free (or none, but not recommanded)
  private List<String>foo; // <--- fill list with each time +p is parsed (+p 1 +p 2 ...)
  
  @Arg(name="-callit", help="test method") 
  private MethodArgs proc(MethodArgs x){} // <--- can call function but must be like this (see MethodArgs)
  
  @Arg(name="", help="1st args") // <--- get 1st "free" parameter
  private String t;
  
  ...
  String err=Cli.proceed(this,args); // <--- return all args stay in buffer (WIP)
  ...
}

...
Map<String, String> help=Cli.getHelp(this); // return "-b","test boolean," etc..
...


--- exemple @Opt ----
@Opt(trigger=":");
Class FooClass{...}
// to parse -foo:args style

--- MethodArgs ---
.getArg <--- get "next" args but stay into buffer 
.consume <--- throw arg from buffer

--- RULES ----
* fields is proceeded before methods.
* "free arguments" must declared first for "must be present" and at last for optionnal
* type of field must be String, Integer, Boolean, List, Float (but custom class is planned)
