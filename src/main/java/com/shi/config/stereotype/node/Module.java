package com.shi.config.stereotype.node;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Module {
    String moduleName();
}
