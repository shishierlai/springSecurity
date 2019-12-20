package com.shi.config.stereotype;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonArg {
    String value() default "";

    Class bean() default Object.class;
}
