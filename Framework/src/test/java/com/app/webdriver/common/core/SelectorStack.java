package com.app.webdriver.common.core;

import java.util.Stack;

import org.openqa.selenium.By;


public class SelectorStack {

	 private static Stack<By> byStack = new Stack<By>();
	  private static boolean contextStack = false;

	  public static void contextWrite() {
	    contextStack = true;
	  }

	  public static void contextRead() {
	    contextStack = false;
	  }

	  public static boolean isContextSet() {
	    return contextStack;
	  }

	  public static By read() {
	    return byStack.pop();
	  }

	  public static void write(By selector) {
	    byStack.push(selector);
	  }
}
