package foxz.cli;

import foxz.cli.annotations.DefaultOpt;
import foxz.cli.annotations.Opt;
import foxz.cli.annotations.Arg;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Cli {

    public String co = "(c)SJFN@ foxz.cli [161216] by foxz chez free.fr CC:ByNc";    
    
    static public Helps getHelp(Object obj) throws IllegalArgumentException, IllegalAccessException {
        Field[] fs = obj.getClass().getDeclaredFields();
        Helps res=new Helps();
        int idx=0;
        for(Field f:fs) {
        	Arg a = f.getAnnotation(Arg.class);                   
            if (a != null) {
                String t = a.name();
                String d="";
                if (t.isEmpty()) {
                    t = "%".concat(String.valueOf(idx++));                    
                }
                if (f.get(obj)!=null) d=f.get(obj).toString();                
                res.put(t,new Help(t,a.help(), a.require(), d));
            }	
        }
        
        Method[] ms = obj.getClass().getDeclaredMethods();
        for(Method m:ms) {
        	Arg a = m.getAnnotation(Arg.class);                   
            if (a != null) {
                String t = a.name();
                String d="";
                if (t.isEmpty()) {
                    t = "%".concat(String.valueOf(idx));                    
                }             
                res.put(t,new Help(t,a.help(), a.require(), ""));
            }
        }
        return res;
    }

    static protected LinkedList<String> prepareArgs(String[] args, String separator) {
        LinkedList<String> r = new LinkedList<>();
        for (String s : args) {
            int i;
            if (separator.isEmpty()) {
                r.add(s);
                continue;
            } else {
                i = s.indexOf(separator);
            };
            if (i >= 0) {
                r.add(s.substring(0, i));
                r.add(s.substring(i + 1, s.length()));
            } else {
                r.add(s);
            }
        }
        return r;
    }

    static protected int igCaseIndexOf(List<String> l, String s) {
        for (int n = 0; n < l.size(); n++) {
            if (l.get(n).equalsIgnoreCase(s)) {
                return n;
            }
        }
        return -1;
    }

    static public Help proceed(Object obj, String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Opt opt = obj.getClass().getAnnotation(Opt.class);
        if (opt == null) {
            opt = (new DefaultOpt()).getClass().getAnnotation(Opt.class);
        }
        Field[] fs = obj.getClass().getDeclaredFields();
        LinkedList<String> largs = prepareArgs(args, opt.trigger());
        Arg a;
        int ep=0;
        for (Field f : fs) {        	
            a = f.getAnnotation(Arg.class);            
            if (a != null) {            	
            	boolean ok=!a.require();
            	String err=a.name();
            	if (err.isEmpty()){
            		err=String.format("%%%d", ep++);
            	}
                while (largs.size() > 0) {
                    int i;
                    if (a.name().isEmpty()) {
                        f.set(obj, largs.get(0));
                        largs.remove(0);
                        break;
                    } else if (opt.globalIgnoreCase() || a.ignoreCase()) {
                        i = igCaseIndexOf(largs, a.name());
                    } else {
                        i = largs.indexOf(a.name());
                    }
                    if (i >= 0) {
                        largs.remove(i);
                        f.setAccessible(true);
                        if (f.getType() == String.class) {
                            f.set(obj, largs.get(i));
                            largs.remove(i);
                            break;
                        } else if (f.getType() == Boolean.class) {
                            f.set(obj, new Boolean(true));
                            break;
                        } else if (f.getType() == boolean.class) {
                            f.set(obj, new Boolean(true));
                            // f.setBoolean(obj, true); // dont work ?
                            break;
                        } else if (f.getType() == List.class) {
                            List l = (List) f.get(obj);
                            if (l == null) {
                                l = new ArrayList<String>();
                            }
                            l.add(largs.get(i));
                            f.set(obj, l);
                            largs.remove(i);
                        } else {
                            throw new IllegalArgumentException(a.name());
                        }
                    } else {                    	
                        break;
                    }
                }
                if (!ok){
                	return new Help(err,a.help(),a.require(),"Missing");
                }
            }
        }
        Method[] ms = obj.getClass().getDeclaredMethods();
        ep=0;
        for (Method m : ms) {
            a = m.getAnnotation(Arg.class);
            if (a != null) {
            	boolean ok=!a.require();
            	String err=a.name();
            	if (err.isEmpty()){
            		err=String.format("%%%d", ep++);
            	}
                while (largs.size() > 0) {
                    Arg o = (Arg) a;
                    int i;
                    if (a.name().isEmpty()) {
                        String p =largs.get(0);
                        largs.remove(0);
                        boolean r = (boolean) m.invoke(obj, p);
                        break;

                    } else if (opt.globalIgnoreCase() && o.ignoreCase()) {
                        i = igCaseIndexOf(largs, a.name());
                    } else {
                        i = largs.indexOf(o.name());
                    }
                    if (i >= 0) {
                        largs.remove(i);
                        //String p = largs.get(i);
                        largs.remove(i);
                        MethodArgs r = (MethodArgs) m.invoke(obj, new MethodArgs(largs, i, false));
                        largs = r.args;
                        if (r.stop) {
                            if (largs.size() == 0) {
                                return null;
                            }                            
                            return new Help(err,a.help(),a.require(),"TOo many :"+largs.get(0));
                        }
                        ok=true;
                    } else {
                        break;
                    }
                }
                if (!ok){return new Help(err,a.help(),a.require(),"Missing");}
            }
        }      
        if (largs.size() == 0) {
            return null;
        }
        return new Help("",largs.get(0),true,"WHAT ?");
    }
}
