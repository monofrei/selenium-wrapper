package com.testmonkeys.selenium.wrapper.annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PageAccessor {

    String url();

    String name();

    String application() default "";
}
