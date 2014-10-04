<html>
<b>FoxZcli</b><br>
=======<br>
<br>Java command line parser via annotation (very easy to use !)<\br>
<br>--- annotation (target: field or method) --- <br>
<br>@Arg(<br>
  name="...","...",<br>
  help="...", // optionnal<br>
  ignoreCase=true/false //optionnal (but true by default)<br>
  <br>
--- annotation (target: class) --- <br>
@Opt(<br>
  trigger="<...>", // optionnal ("space" by default)<br>
  ignoreCase=true/false // optionnal (but true by default)<br>
<br>
--- exemple @Arg ---<br>
class FooClass {<br>
  @Arg(name="-b",help="test boolean")v
  private Boolean help=false; // <--- can be null<br>
  <br>
  @Arg(name="-s",help="test String")<br>
  private String n;<br>
  <br>
  @PArg(name="-i", help="test integer")<br>
  private Integer x;   // <--- object recommanded to test if not null<br>
  <br>
  @Arg(name="+l", help="test list") // <--- sign before "name" is totally free (or none, but not recommanded)<br>
  private List<String>foo; // <--- fill list with each time +p is parsed (+p 1 +p 2 ...)v
  <br>
  @Arg(name="-callit", help="test method") <br>
  private MethodArgs proc(MethodArgs x){} // <--- can call function but must be like this (see MethodArgs)<br>
  <br>
  @Arg(name="", help="1st args") // <--- get 1st "free" parameter<br>
  private String t;<br>
  <br>
  ...<br>
  String err=Cli.proceed(this,args); // <--- return all args stay in buffer (WIP)<br>
  ...<br>
}<br>
<br>
...<br>
Map<String, String> help=Cli.getHelp(this); // return "-b","test boolean," etc..<br>
...<br>
<br>
--- exemple @Opt ----<br>
@Opt(trigger=":");<br>
Class FooClass{...}<br>
// to parse -foo:args style<br>
<br>
--- MethodArgs ---<br>
.getArg <--- get "next" args but stay into buffer <br>
.consume <--- throw arg from buffer<br>
<br>
--- RULES ----<br>
* fields is proceeded before methods.<br>
* "free arguments" must declared first for "must be present" and at last for optionnal<br>
* type of field must be String, Integer, Boolean, List, Float (but custom class is planned)<br>
* </html>
