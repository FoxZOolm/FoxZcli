<html>
  <head>
    <meta content="text/html; charset=utf-8" http-equiv="content-type">
  </head>
  <body><b>FoxZcli</b><br>
    =======<br>
    Java command line parser via annotation (very easy to use !)<br>
    <br>
    <span style="font-weight: bold;">--- annotation (target: field or method)
      ---</span><br>
    @Arg(<br>
    &nbsp;&nbsp;&nbsp; name="...",<br>
    &nbsp;&nbsp;&nbsp; help="...", <span style="font-style: italic;">//
      optionnal </span><br>
    &nbsp;&nbsp;&nbsp; ignoreCase=true/false <span style="font-style: italic;">//optionnal
      (but true by default)</span><br>
    ) <br>
    <span style="font-weight: bold;"><br>
      --- annotation (target: class) --- </span><br>
    @Opt( <br>
    &nbsp;&nbsp;&nbsp; trigger="...", <span style="font-style: italic;">//
      optionnal ("space" by default) </span><br>
    &nbsp;&nbsp;&nbsp; ignoreCase=true/false<span style="font-style: italic;">
      // optionnal (but true by default)</span><br>
    &nbsp;)<br>
    <span style="font-weight: bold;"><br>
      --- exemple @Arg ---</span><br>
    class FooClass {<br>
    &nbsp;&nbsp;&nbsp; @Arg(name="-b",help="test boolean") <br>
    &nbsp;&nbsp;&nbsp; public Boolean help=false; /<span style="font-style: italic;">/
      &lt;--- can be null</span><br>
    <br>
    &nbsp;&nbsp;&nbsp; @Arg(name="-s",help="test String") <br>
    &nbsp;&nbsp;&nbsp; public String n; <br>
    &nbsp;&nbsp;&nbsp; <br>
    &nbsp;&nbsp;&nbsp; @Arg(name="-i", help="test integer")<br>
    &nbsp;&nbsp;&nbsp; public Integer x; <span style="font-style: italic;">//
      &lt;--- object recommanded to test if not null</span><br>
    <br>
    &nbsp;&nbsp;&nbsp; @Arg(name="+l", help="test list") <span style="font-style: italic;">//
      &lt;--- sign before "name" is totally free (or none, but not recommanded)</span><br>
    &nbsp;&nbsp;&nbsp; public List<string>&lt;foo&gt;; <span style="font-weight: bold;">//
        &lt;--- fill list with each time +l is parsed (+l a1 +l a2 ...)</span><br>
      <br>
      &nbsp;&nbsp;&nbsp; @Arg(name="-callit", help="test method") <br>
      &nbsp;&nbsp;&nbsp; public MethodArgs proc(MethodArgs x){} <span style="font-style: italic;">//
        &lt;--- can call function but must be like this (see MethodArgs)</span><br>
      <br>
      &nbsp;&nbsp;&nbsp; @Arg(name="", help="1st args") <span style="font-style: italic;">//
        &lt;--- get 1st "free" parameter</span><br>
      &nbsp;&nbsp;&nbsp; public String t;<br>
      <br>
      &nbsp;&nbsp;&nbsp; ...<br>
      &nbsp;&nbsp;&nbsp; String err=Cli.proceed(this,args); /<span style="font-style: italic;">/
        &lt;--- if err not null = all args stay in buffer <span style="font-weight: bold; text-decoration: underline;">(WIP)</span></span><br>
      &nbsp;&nbsp;&nbsp; ...</string>&nbsp;&nbsp;&nbsp; <br>
    &nbsp;&nbsp;&nbsp; <string>Map&lt;String,String&gt; help=Cli.getHelp(this);
      <span style="font-style: italic;"> // return "-b","test boolean,"
        etc..&nbsp;&nbsp;&nbsp; </span><br>
      &nbsp;&nbsp;&nbsp; ...<br>
      }<br>
      <string, string=""><span style="font-weight: bold;"><br>
          --- exemple @Opt ----</span><br>
        @Opt(trigger=":");<br>
        Class FooClass{...} // to parse -foo:args style (ex: -s:test)<br>
        <br>
        <span style="font-weight: bold;">--- MethodArgs ---</span><br>
        .getArg() &lt;--- get "next" args but stay into buffer <br>
        .consume() &lt;--- throw arg from buffer<br>
        <br>
        <span style="font-weight: bold;">--- RULES ----</span><br>
        * fields is proceeded before methods.<br>
        * "free arguments" must declared in first for "must be present" and at
        last for optionnal<br>
        * type of field must be String, Integer, Boolean, List, Float (but
        custom class is planned)<br>
        <br>
        <span style="font-weight: bold;">--- Licence ---</span><br>
        CC:ByNC<br>
      </string,></string>
  </body>
</html>
