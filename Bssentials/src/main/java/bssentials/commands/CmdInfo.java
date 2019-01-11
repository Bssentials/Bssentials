package bssentials.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CmdInfo {

    public boolean onlyPlayer() default false;

    public String permission() default "";

    public String[] aliases() default {""};

}