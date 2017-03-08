package com.testmonkeys.selenium.wrapper.annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ElementAccessor {

    String name();

    String xpath();
}
