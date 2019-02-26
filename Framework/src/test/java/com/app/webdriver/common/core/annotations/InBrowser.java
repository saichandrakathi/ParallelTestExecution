package com.app.webdriver.common.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.app.webdriver.common.core.drivers.Browser;



@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface InBrowser {

  String browserSize() default "";

  Browser browser() default Browser.CHROME;

  //Emulator emulator() default Emulator.DEFAULT;
}
