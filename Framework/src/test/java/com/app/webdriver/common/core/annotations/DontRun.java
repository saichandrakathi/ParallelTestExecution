package com.app.webdriver.common.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

	 @Retention(value = RetentionPolicy.RUNTIME)
	 @Target(value = {ElementType.METHOD})

	 public @interface DontRun {

	   String[] env() default "";
	   String[] language() default "";
	 }

