package com.app.webdriver.common.logging;

import com.app.webdriver.common.logging.LogData;;

public enum LogType implements LogData {
	  STEP("step"), STACKTRACE("stacktrace");

	  private String cssClass;

	  LogType(String cssClass) {
	    this.cssClass = cssClass;
	  }
	  public String cssClass() {
	    return cssClass;
	  }
	}