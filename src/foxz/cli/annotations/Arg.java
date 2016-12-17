package foxz.cli.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Arg{
    String name();
    String help() default "";
    boolean ignoreCase() default true;
    boolean require() default false;
}