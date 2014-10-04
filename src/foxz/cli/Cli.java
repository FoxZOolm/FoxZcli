package foxz.cli;

import foxz.cli.annotations.DefaultOpt;
import foxz.cli.annotations.Opt;
import foxz.cli.annotations.Par;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Cli {

    public String co = "(c)SJFN@ foxz.cli [140926] by foxz chez free.fr CC:ByNc";

    static public Map<String, String> getHelp(Object obj) {
        Map<String, String> h = new TreeMap<>();
        Field[] fs = obj.getClass().getDeclaredFields();
        Par a;
        int bp = 1;
        for (Field f : fs) {
            a = f.getAnnotation(Par.class);
            if (a != null) {
                String t = a.name();
                if (t.isEmpty()) {
                    t = "%".concat(String.valueOf(bp++));
                }
                h.put(t, a.help());
            }
        }
        Method[] ms = obj.getClass().getDeclaredMethods();
        for (Method m : ms) {
            a = m.getAnnotation(Par.class);
            if (a != null) {
                String t = a.name();
                if (t.isEmpty()) {
                    t = "%".concat(String.valueOf(bp++));
                }
                h.put(t, a.help());
            }
        }
        return h;
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

    static public String proceed(Object obj, String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Opt opt = obj.getClass().getAnnotation(Opt.class);
        if (opt == null) {
            opt = (new DefaultOpt()).getClass().getAnnotation(Opt.class);
        }
        Field[] fs = obj.getClass().getDeclaredFields();
        LinkedList<String> largs = prepareArgs(args, opt.trigger());
        Par a;
        for (Field f : fs) {
            a = f.getAnnotation(Par.class);
            if (a != null) {
                while (largs.size() > 0) {
                    int i;
                    if (a.name().isEmpty()) {
                        f.set(obj, largs.get(0));
                        largs.remove(0);
                        break;
                    } else if (opt.globalIgnoreCase() && a.ignoreCase()) {
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
            }
        }
        Method[] ms = obj.getClass().getDeclaredMethods();
        for (Method m : ms) {
            a = m.getAnnotation(Par.class);
            if (a != null) {
                while (largs.size() > 0) {
                    Par o = (Par) a;
                    int i;
                    if (a.name().isEmpty()) {
                        String p = largs.get(0);
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
                        String p = largs.get(i);
                        largs.remove(i);
                        MethodArgs r = (MethodArgs) m.invoke(obj, new MethodArgs(largs, i, false));
                        largs = r.args;
                        if (r.stop) {
                            if (largs.size() == 0) {
                                return null;
                            }
                            return largs.get(0);
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        if (largs.size() == 0) {
            return null;
        }
        return largs.get(0);
    }
}
