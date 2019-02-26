package com.app.webdriver.common.core;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

public class TestContext {

	private static String methodName;
	  private static Method testMethod;
	  private static boolean isFirstLoad = false;

	  public static void writeMethodName(Method method) {
	    methodName = method.getDeclaringClass().getSimpleName()
	                 + StringUtils.capitalize(method.getName());

	    testMethod = method;
	    isFirstLoad = true;
	  }

	  public static String getCurrentMethodName() {
	    return methodName;
	  }

	  public static Method getCurrentTestMethod() {
	    return testMethod;
	  }

	  public static boolean isFirstLoad() {
	    return isFirstLoad;
	  }

	  public static void setFirstLoad(boolean value) {
	    isFirstLoad = value;
	  }
}
