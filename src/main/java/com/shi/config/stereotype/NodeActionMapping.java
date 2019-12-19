package com.shi.config.stereotype;


import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NodeActionMapping {
    String actionName();

}
