<html>
  <body><b>FoxZcli</b><br>
    =======<br>
    Java command line parser via annotation (very easy to use !)<br>
    <br>
    --- annotation (target: field or method) ---<br>
    @Arg(<br>
    &nbsp;&nbsp;&nbsp; name="...","...", help="...", // optionnal
    <br>
    &nbsp;&nbsp;&nbsp; ignoreCase=true/false //optionnal (but true by default)<br>
    ) <br>
    <br>
    --- annotation (target: class) --- <br>
    &nbsp;&nbsp;&nbsp; @Opt( trigger="...", // optionnal ("space" by default) <br>
    &nbsp;&nbsp;&nbsp; ignoreCase=true/false // optionnal (but true by default)<br>
    &nbsp;)<br>
    <br>
    --- exemple @Arg ---<br>
    class FooClass { @Arg(name="-b",help="test boolean") private Boolean
    help=false; // &lt;--- can be null<br>
    &nbsp;&nbsp;&nbsp; @Arg(name="-s",help="test String") <br>
    &nbsp;&nbsp;&nbsp; private String n; <br>
    &nbsp;&nbsp;&nbsp; <br>
    &nbsp;&nbsp;&nbsp; @ Arg(name="-i", help="test integer")<br>
    &nbsp;&nbsp;&nbsp; private Integer x; // &lt;--- object recommanded to test
    if not null<br>
    <br>
    &nbsp;&nbsp;&nbsp; @Arg(name="+l", help="test list") // &lt;--- sign before
    "name" is totally free (or none, but not recommanded)<br>
    &nbsp;&nbsp;&nbsp; private List<string>foo; // &lt;--- fill list with each
      time +p is parsed (+p 1 +p 2 ...)<br>
      <br>
      &nbsp;&nbsp;&nbsp; @Arg(name="-callit", help="test method") <br>
      &nbsp;&nbsp;&nbsp; private MethodArgs proc(MethodArgs x){} // &lt;--- can
      call function but must be like this (see MethodArgs)<br>
      <br>
      &nbsp;&nbsp;&nbsp; @Arg(name="", help="1st args") // &lt;--- get 1st
      "free" parameter<br>
      &nbsp;&nbsp;&nbsp; private String t;<br>
      <br>
      &nbsp;&nbsp;&nbsp; ...<br>
      &nbsp;&nbsp;&nbsp; String err=Cli.proceed(this,args); // &lt;--- return
      all args stay in buffer (WIP)<br>
      &nbsp;&nbsp;&nbsp; ...</string>&nbsp;&nbsp;&nbsp; <br>
    &nbsp;&nbsp;&nbsp; <string>Map&lt;String,String&gt;
      help=Cli.getHelp(this); // return "-b","test boolean,"
      etc..&nbsp;&nbsp;&nbsp; <br>
      &nbsp;&nbsp;&nbsp; ...<br>
      }<br>
      <string, string=""><br>
        --- exemple @Opt ----<br>
        @Opt(trigger=":");<br>
        Class FooClass{...} // to parse -foo:args style<br>
        <br>
        --- MethodArgs ---<br>
        .getArg &lt;--- get "next" args but stay into buffer <br>
        .consume &lt;--- throw arg from buffer<br>
        <br>
        --- RULES ----<br>
        * fields is proceeded before methods.<br>
        * "free arguments" must declared first for "must be present" and at last
        for optionnal<br>
        * type of field must be String, Integer, Boolean, List, Float (but
        custom class is planned) </string,></string>
  </body>
</html>
