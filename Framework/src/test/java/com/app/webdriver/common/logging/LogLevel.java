package com.app.webdriver.common.logging;
import com.app.webdriver.common.logging.LogData;;


public enum LogLevel implements LogData {
	  ERROR("error"), OK("success"), WARNING("warning"), INFO("info"), DEBUG("lowLevelAction");

	  private String cssClass;

	  LogLevel(String cssClass) {
	    this.cssClass = cssClass;
	  }
	  
	  public String cssClass() {
	    return cssClass;
	  }
	}

