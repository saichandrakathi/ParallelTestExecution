package com.app.webdriver.common.core.drivers;

import com.app.webdriver.common.core.APPWebDriver;
import com.app.webdriver.common.core.drivers.browsers.ChromeBrowser;
import com.app.webdriver.common.logging.Log;

public enum Browser {
	CHROME(ChromeBrowser.class, "CHROME");
	
	private Class<? extends BrowserAbstract> browserClass;
	  private String name;

	  Browser(Class<? extends BrowserAbstract> browserClass, String name) {
	    this.name = name;
	    this.browserClass = browserClass;
	  }

	  public static Browser lookup(String browserName) {
	    for (Browser name : Browser.values()) {
	      if (name.getName().equalsIgnoreCase(browserName)) {
	        return name;
	      }
	    }
	    return null;
	  }

	  public String getName() {
	    return name;
	  }

	  public APPWebDriver getInstance() {
	    try {
	      return browserClass.newInstance().getInstance();
	    } catch (InstantiationException | IllegalAccessException e) {
	      Log.logError("Could not initialize the browser", e);
	    }
	    return null;
	  }

	  public Class<? extends BrowserAbstract> getBrowserClass() {
	    return browserClass;
	  }
	}